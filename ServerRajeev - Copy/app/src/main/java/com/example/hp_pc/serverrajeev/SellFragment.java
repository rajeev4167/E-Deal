package com.example.hp_pc.serverrajeev;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Hp-Pc on 10/8/2017.
 */
public class SellFragment extends Fragment {

    public static Fragment newInstance() {

        Bundle args = new Bundle();

        SellFragment fragment = new SellFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private static final String UPLOAD_URL = "http://192.168.137.1/webapp/item_upload.php";
    //Uri to store the image uri
    private Uri filePath;
    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    private ImageView ItemImage;
    private EditText ItemName, ItemPrice, ItemDes;
    private Spinner ItemTypeSpinner;
    private Button ItemUploadButton;
    private Bitmap bitmap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sell, container, false);

        ItemImage = (ImageView) view.findViewById(R.id.sell_image_view);
        ItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
        if(bitmap != null) {
            ItemImage.setImageBitmap(bitmap);
        }

        ItemName = (EditText) view.findViewById(R.id.sell_item_name);
        ItemPrice = (EditText) view.findViewById(R.id.sell_item_price);
        ItemDes = (EditText) view.findViewById(R.id.sell_item_description);

        ItemTypeSpinner = (Spinner) view.findViewById(R.id.sell_item_type_spinner);

        ItemUploadButton = (Button) view.findViewById(R.id.sell_item_upload_button);
        ItemUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = ItemName.getText().toString().trim();
                String price = ItemPrice.getText().toString().trim();
                int type = ItemTypeSpinner.getSelectedItemPosition();
                String des = ItemDes.getText().toString().trim();

                if(bitmap == null) {
                    Toast.makeText(getActivity(), "Select item image", Toast.LENGTH_SHORT).show();
                }
                else if(name.length() == 0) {
                    Toast.makeText(getActivity(), "Enter name of the item", Toast.LENGTH_SHORT).show();
                }
                else if(price.length() == 0) {
                    Toast.makeText(getActivity(), "Enter price of the item", Toast.LENGTH_SHORT).show();
                }
                else if(des.length() == 0) {
                    Toast.makeText(getActivity(), "Enter description of the item", Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadMultipart(name, price, type, des);
                }
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
                ItemImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadMultipart(String name, String price, int type, String des) {

        //getting the actual path of the image
        String path = getPath(filePath);

        Log.d("TAG", path);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            DealActivity dealActivity = (DealActivity) getActivity();

            //Toast.makeText(getActivity(), dealActivity.UserName, Toast.LENGTH_SHORT).show();

            //Creating a multi part request
            new MultipartUploadRequest(getActivity(), uploadId, UPLOAD_URL)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("username",dealActivity.UserName)
                    .addParameter("name", name) //Adding text parameter to the request
                    .addParameter("price", price)
                    .addParameter("type", String.valueOf(type))
                    .addParameter("des", des)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

            bitmap = null;
            ItemImage.setImageResource(R.drawable.upload_default);
            ItemName.setText("");
            ItemPrice.setText("");
            ItemDes.setText("");
            ItemTypeSpinner.setVerticalScrollbarPosition(0);

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
}
