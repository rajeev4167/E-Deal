package com.example.hp_pc.serverrajeev;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Hp-Pc on 10/8/2017.
 */
public class ProfileFragment extends Fragment {

    public static Fragment newInstance() {

        Bundle args = new Bundle();

        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private static final String UPLOAD_URL = "http://192.168.137.1/webapp/profile_pic_upload.php";
    //Uri to store the image uri
    private Uri filePath;
    //Image request code
    private int PICK_IMAGE_REQUEST = 1;
    Bitmap bitmap = null;

    private CircleImageView profileImage;
    private TextView profileName, profileUserName, profileMobileNo;
    private EditText profileMobileNoEdit;
    private Button editProfile, updateProfile, signOut;
    private boolean isEditing = false;
    private List<UserInfo> userInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        new FetchItemsTask().execute();

        profileImage = (CircleImageView) view.findViewById(R.id.profile_image_view);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEditing) return;
                showFileChooser();
            }
        });

        profileName = (TextView) view.findViewById(R.id.profile_name_text_view);

        profileUserName = (TextView) view.findViewById(R.id.profile_user_name_text_view);

        profileMobileNo = (TextView) view.findViewById(R.id.profile_mobile_no_text_view);
        profileMobileNoEdit = (EditText) view.findViewById(R.id.profile_mobile_no_edit_text);
        profileMobileNoEdit.setVisibility(View.GONE);

        editProfile = (Button) view.findViewById(R.id.edit_profile_button);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEditing) return;
                isEditing = true;
                profileMobileNoEdit.setText(profileMobileNo.getText());
                profileMobileNo.setVisibility(View.GONE);
                profileMobileNoEdit.setVisibility(View.VISIBLE);
            }
        });

        updateProfile = (Button) view.findViewById(R.id.update_profile_button);
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEditing) return;
                isEditing = false;
                profileMobileNo.setText(profileMobileNoEdit.getText());
                profileMobileNoEdit.setVisibility(View.GONE);
                profileMobileNo.setVisibility(View.VISIBLE);

                if(bitmap != null) {
                    uploadMultipart();
                }
                String method = "mobile_num_update";
                BackGroundTask backGroundTask = new BackGroundTask(getActivity());
                backGroundTask.execute(method, profileUserName.getText().toString(), profileMobileNo.getText().toString());
            }
        });

        signOut = (Button) view.findViewById(R.id.sign_out_button);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QueryPreferences.removeStoredUserName(getActivity());
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                profileImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadMultipart() {

        //getting the actual path of the image
        String path = getPath(filePath);

        Log.d("TAG", path);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            DealActivity dealActivity = (DealActivity) getActivity();

            //Toast.makeText(getActivity(), dealActivity.UserName, Toast.LENGTH_SHORT).show();

            //Creating a multi part request
            MultipartUploadRequest multipartUploadRequest = new MultipartUploadRequest(getActivity(), uploadId, UPLOAD_URL);
            multipartUploadRequest
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("username",dealActivity.UserName)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

            bitmap = null;

        } catch (Exception exc) {
            Toast.makeText(getActivity(), exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //method to get the file path from uri
    private String getPath(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    private class FetchItemsTask extends AsyncTask<Void, Void, List<UserInfo>> {

        @Override
        protected List<UserInfo> doInBackground(Void... voids) {
            DealActivity dealActivity = (DealActivity) getActivity();
            return new FlickrFetcher().getProfileDetails(dealActivity.UserName);
        }

        @Override
        protected void onPostExecute(List<UserInfo> userInfos) {
            userInfo = userInfos;
            UserInfo user = userInfo.get(0);

            profileName.setText(user.getName());
            profileUserName.setText(user.getUseremail());

            if(user.getMobilenum() != null) {
                profileMobileNo.setText(user.getMobilenum());
            }

            if(user.getProfilepic() != null) {

                Picasso.with(getActivity())
                        .load(user.getProfilepic())
                        .into(profileImage);
            }
        }
    }
}
