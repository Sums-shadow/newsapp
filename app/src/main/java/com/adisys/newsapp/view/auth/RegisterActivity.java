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

import com.adisys.newsapp.R;
import com.adisys.newsapp.view.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    ProgressDialog progressDialog;

    private EditText email, password, password2;
    private Button btnSignin;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email=findViewById(R.id.registerEmailTV);
        password=findViewById(R.id.registerPasswordTV);
        password2=findViewById(R.id.registerPasswordTV2);
        btnSignin=findViewById(R.id.registerBtnConnect);
        progressDialog=new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();

        btnSignin.setOnClickListener(e->{
            if(email.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Veuillez remplir votre nom",Toast.LENGTH_LONG).show();
                email.setError("Veuillez remplir votre adresse email");
                email.requestFocus();
            }else if(password.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Veuillez remplir votre mot de passe",Toast.LENGTH_LONG).show();
                password.setError("Veuillez remplir votre mot de passe");

                password.requestFocus();
            }else if(!password.getText().toString().contentEquals(password2.getText())){
                Toast.makeText(getApplicationContext(),"Mot de passe different",Toast.LENGTH_LONG).show();
                password2.requestFocus();
            }else{
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("Loading...");
                progressDialog.setTitle("NewsApp");
                progressDialog.show();
                auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           Log.d("info", "createUserWithEmail:success");
                           FirebaseUser user = auth.getCurrentUser();
                           progressDialog.cancel();

                           startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                       }else {
                           progressDialog.cancel();
                           // If sign in fails, display a message to the user.
                           Log.w("error", "createUserWithEmail:failure", task.getException());
                           Toast.makeText(getApplicationContext(), "Authentication failed.",
                                   Toast.LENGTH_SHORT).show();
                           email.requestFocus();
                       }

                    }





                });
            }
        });
    }
}