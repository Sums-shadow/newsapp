package com.adisys.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.adisys.newsapp.adapter.CategoryRVAdapter;

import com.adisys.newsapp.view.HomeActivity;
import com.adisys.newsapp.view.auth.AuthActivity;
import com.adisys.newsapp.view.auth.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity  {


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
        }else{
            Intent intent = new Intent(MainActivity.this,AuthActivity.class);

            startActivity(intent);
        }
    }
}