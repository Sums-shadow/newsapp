package com.adisys.newsapp.view.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.adisys.newsapp.MainActivity;
import com.adisys.newsapp.R;

public class AuthActivity extends AppCompatActivity {

 Button btnLogin,btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        btnLogin=findViewById(R.id.authLogin);
        btnRegister=findViewById(R.id.authSignup);

        btnLogin.setOnClickListener(e->{
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });

        btnRegister.setOnClickListener(e->{
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        });

    }
}