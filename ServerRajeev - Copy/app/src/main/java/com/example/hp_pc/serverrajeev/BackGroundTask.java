package com.example.hp_pc.serverrajeev;

/**
 * Created by Hp-Pc on 9/1/2017.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Chinmay on 01-09-2017.
 */

public class BackGroundTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    private AlertDialog mAlertDialog;
    private String UserName;

    public BackGroundTask(Context context) {
        mContext = context;
    }

    @Override
    protected void onPreExecute() {

        mAlertDialog = new AlertDialog.Builder(mContext).create();
        mAlertDialog.setTitle("Login Information...");
    }

    @Override
    protected String doInBackground(String... params) {
        String reg_url = "http://192.168.137.1/webapp/register.php";
        String login_url = "http://192.168.137.1/webapp/login.php";
        String mobile_num_update_url = "http://192.168.137.1/webapp/profile_mobile_num_update.php";
        String method = params[0];

        if(method.equals("register")) {
            String name = params[1];
            String email = params[2];
            String pass = params[3];

            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                Log.d("TAG", "abc1");
                httpURLConnection.setDoOutput(true);
                Log.d("TAG", "abc2");
                OutputStream outputStream = httpURLConnection.getOutputStream();
                Log.d("TAG", "abc7");

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                Log.d("TAG", "abc8");

                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("user_email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8");

                bufferedWriter.write(data);
                Log.d("TAG", "abc9");
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                Log.d("TAG", "abc10");
                inputStream.close();

                return "Registration Success...";

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("TAG", "Malformed");
            } catch (IOException e) {
                Log.d("TAG", "IOException");
                e.printStackTrace();
            }
        }
        else if(method.equals("login")) {

            String loginName = params[1];
            String loginPass = params[2];
            UserName = loginName;

            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("user_email", "UTF-8") + "=" + URLEncoder.encode(loginName, "UTF-8") + "&" +
                              URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder.encode(loginPass, "UTF-8");

                Log.d("TAG", loginName + loginPass);

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";

                while((line =  bufferedReader.readLine()) != null) {

                    response += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.d("TAG", response);
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(method.equals("mobile_num_update")) {

            String username = params[1];
            String mobile_num = params[2];

            try {
                URL url = new URL(mobile_num_update_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                        URLEncoder.encode("mobile_num", "UTF-8") + "=" + URLEncoder.encode(mobile_num, "UTF-8");

                Log.d("TAG", username + mobile_num);

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";

                while((line =  bufferedReader.readLine()) != null) {

                    response += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.d("TAG", response);
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "Unsuccessful";
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {

        if(result.equals("Registration Success...")) {
            Toast.makeText(mContext, result, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent);
        }
        else {

            if(result.contains("Success")) {
                QueryPreferences.setStoredUserName(mContext, UserName);
                Intent intent = DealActivity.newIntent(mContext, UserName);
                mContext.startActivity(intent);
            }
            else if(result.contains("Wrong")) {
                mAlertDialog.setMessage(result);
                mAlertDialog.show();
            }
        }
    }
}

