package com.example.hp_pc.serverrajeev;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mEmail;
    private EditText mPassword;
    private Button mRegisterButton, mLoginButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mName = (EditText) findViewById(R.id.r_name_edit_text);

        mEmail = (EditText) findViewById(R.id.r_email_edit_text);

        mPassword = (EditText) findViewById(R.id.r_password_edit_text);

        mRegisterButton = (Button) findViewById(R.id.r_register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = mName.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(name.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Enter name", Toast.LENGTH_SHORT).show();
                }
                else if(email.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Enter user name", Toast.LENGTH_SHORT).show();
                }
                else if(password.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                }
                else {
                    String  method = "register";
                    BackGroundTask backGroundTask = new BackGroundTask(RegisterActivity.this);
                    backGroundTask.execute(method, name, email, password);
                }
            }
        });

        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
