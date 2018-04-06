package com.example.hp_pc.serverrajeev;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private Button mSignInButton;
    private Button mRegisterButton;
    private String mLoginName, mLoginPass;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //check_if_login_shared_pref

        String checkLogin = QueryPreferences.getStoredUserName(LoginActivity.this);

        if(checkLogin != null) {
            Intent intent = DealActivity.newIntent(LoginActivity.this, checkLogin);
            startActivity(intent);
        }


        setContentView(R.layout.activity_login);
        Log.d("TAG", "created");

        mEmail = (EditText) findViewById(R.id.email_edit_text);

        mPassword = (EditText) findViewById(R.id.password_edit_text);

        mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mLoginName = mEmail.getText().toString().trim();
                mLoginPass = mPassword.getText().toString().trim();

                if(mLoginName.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Enter user name", Toast.LENGTH_SHORT).show();
                }
                else if(mLoginPass.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                }
                else {
                    String method = "login";
                    BackGroundTask backGroundTask = new BackGroundTask(LoginActivity.this);
                    backGroundTask.execute(method, mLoginName, mLoginPass);
                }
            }
        });

        mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}