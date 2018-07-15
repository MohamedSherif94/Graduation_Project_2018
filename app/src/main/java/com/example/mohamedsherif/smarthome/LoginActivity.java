package com.example.mohamedsherif.smarthome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mohamedsherif.smarthome.connectionToServer.Login;

public class LoginActivity extends AppCompatActivity {

    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private Button mButtonLogin;
    private Button mButtonForgetPassword;
    private View mProgressBarLogin;
    public static String emailString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEditTextEmail = (EditText) findViewById(R.id.edit_text_email);
        mEditTextPassword = (EditText) findViewById(R.id.edit_text_password);
        mButtonLogin = (Button) findViewById(R.id.button_log_in);
        mButtonForgetPassword = (Button) findViewById(R.id.button_forget_password);
        mProgressBarLogin = findViewById(R.id.progress_bar_login);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = "login";
                String email = mEditTextEmail.getText().toString().trim();
                String password = mEditTextPassword.getText().toString().trim();
                emailString = email;

                Login login = new Login(LoginActivity.this, mProgressBarLogin);
                login.execute(type, email, password);
            }
        });
    }
}

