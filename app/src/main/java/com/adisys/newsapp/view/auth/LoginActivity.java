package com.adisys.newsapp.view.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adisys.newsapp.MainActivity;
import com.adisys.newsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {

    private int counter;
    EditText login,password;
    Button btnConnect;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.loginEmailTV);
        password=findViewById(R.id.loginPasswordTV);
        btnConnect=findViewById(R.id.loginBtnConnect);
        progressDialog=new ProgressDialog(this);
        auth=FirebaseAuth.getInstance();

        btnConnect.setOnClickListener(e->{
            if(login.getText().toString().isEmpty()){
                login.setError("Veuillez entrer votre adresse email");
                login.requestFocus();
            }else if(password.getText().toString().isEmpty()){
                password.setError("Veuillez entrer votre mot de passe");
                password.requestFocus();
            }else{
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("Loading...");
                progressDialog.setTitle("NewsApp");
                progressDialog.show();
                System.out.println("Login...");
                auth.signInWithEmailAndPassword(login.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isComplete()) {
                            if(task.isSuccessful()){
                                System.out.println("Task complet "+task.getResult());
                                FirebaseUser user = auth.getCurrentUser();

                                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }else{
                                System.out.println("Login faiked......");
                                Log.w("error", "Email or password incorrect :failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Echec d'authentification.",
                                        Toast.LENGTH_LONG).show();
                            }
                            progressDialog.cancel();

                        }     else{
                            System.out.println("Login faiked......");
                            Log.w("error", "signinUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }

        });
    }
}