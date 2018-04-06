package com.example.hp_pc.serverrajeev;

/**
 * Created by Hp-Pc on 10/6/2017.
 */


import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlickrFetcher {

    private static final String TAG = "FlickrFetcher";
    private static final String API_KEY = "78de89049020a66357cec9e21271b6b4";
    private static final String GET_RECENT_METHOD = "flickr.photos.getRecent";
    private static final String SEARCH_METHOD = "flickr.photos.search";
    private static final Uri ENDPOINT = Uri.parse("http://192.168.137.1/webapp/getimages.php");


    public byte[] getUrlBytes(String urlSpec, String userName) throws IOException {

        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);

        try {

            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        }
        finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec, String userName) throws IOException {
        return new String(getUrlBytes(urlSpec, userName));
    }

    /*
    public List<GalleryItem> getRecentPhotos(Integer page) {
        String url = buildUrl(GET_RECENT_METHOD, null, page);
        return downloadGalleryItems(url);
    }

    public List<GalleryItem> searchPhotos(String query, Integer page) {
        String url = buildUrl(SEARCH_METHOD, query, page);
        return downloadGalleryItems(url);
    }
    */

    /*
    private String buildUrl(String method, String query, Integer page) {
        Uri.Builder uriBuilder = ENDPOINT.buildUpon()
                .appendQueryParameter("method", method)
                .appendQueryParameter("page", page.toString());
        if(method.equals(SEARCH_METHOD))
            uriBuilder.appendQueryParameter("text", query);

        return uriBuilder.toString();
    }
    */

    public List<Photo> downloadBuyItems(String userName) {

        List<Photo> items = new ArrayList<>();
        try {
            String jsonString = getUrlString("http://192.168.137.1/webapp/get_buy_images.php", userName);
            Log.i(TAG, "Received JSON: \n" + jsonString);

            items = parseItems(jsonString);
        }
        catch(IOException e) {
            Log.e(TAG, "Failed to fetch items", e);
        }
        return items;
    }

    public List<Photo> downloadSellItems(String userName) {

        List<Photo> items = new ArrayList<>();
        try {
            String jsonString = getUrlString("http://192.168.137.1/webapp/get_sell_images.php", userName);
            Log.i(TAG, "Received JSON: \n" + jsonString);

            items = parseItems(jsonString);
        }
        catch(IOException e) {
            Log.e(TAG, "Failed to fetch items", e);
        }
        return items;
    }

    public List<UserInfo> getProfileDetails(String userName) {

        List<UserInfo> items = new ArrayList<>();
        try {
            String jsonString = getUrlString("http://192.168.137.1/webapp/get_profile_details.php", userName);
            Log.i(TAG, "Received JSON: \n" + jsonString);

            items = parseUsers(jsonString);
        }
        catch(IOException e) {
            Log.e(TAG, "Failed to fetch items", e);
        }
        return items;
    }

    private List<Photo> parseItems(String jsonString) throws IOException {

        Gson gson = new Gson();
        JsonParserItems parser = gson.fromJson(jsonString, JsonParserItems.class);
        return Arrays.asList(parser.getmPhoto());
    }

    private List<UserInfo> parseUsers(String jsonString) throws IOException {

        Gson gson = new Gson();
        JsonParserUsers parser = gson.fromJson(jsonString, JsonParserUsers.class);
        return Arrays.asList(parser.getmUser());
    }
}

class JsonParserItems {
    @SerializedName("images")
    private Photo[] mPhoto;

    public Photo[] getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(Photo[] mPhoto) {
        this.mPhoto = mPhoto;
    }
}

class JsonParserUsers {
    @SerializedName("user_info")
    private UserInfo[] mUser;

    public UserInfo[] getmUser() {
        return mUser;
    }

    public void setmUser(UserInfo[] mUser) {
        this.mUser = mUser;
    }
}