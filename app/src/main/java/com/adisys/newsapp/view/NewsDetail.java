package com.adisys.newsapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adisys.newsapp.R;
import com.adisys.newsapp.controller.DbHelper;
import com.adisys.newsapp.model.CommentModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class NewsDetail extends AppCompatActivity {
    String title, subtitle, image, author, content, publishAt, url;
    TextView titleTV, subtitleTV, authorTV, dateTV, nbCommentTV;
    ImageView bannerIV;
    DbHelper helper = new DbHelper(NewsDetail.this);
    EditText commentText;
    Button btnComment;
    int nbComment = 0;
    private View baseView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        title = getIntent().getStringExtra("title");
        subtitle = getIntent().getStringExtra("subTitle");
        image = getIntent().getStringExtra("image");
        author = getIntent().getStringExtra("author");
        content = getIntent().getStringExtra("content");
        publishAt = getIntent().getStringExtra("publishAt");
        url = getIntent().getStringExtra("url");
        this.baseView = this.findViewById(R.id.baseView);
        nbCommentTV = findViewById(R.id.nbComment);
        titleTV = findViewById(R.id.idTVTitleDetail);
        subtitleTV = findViewById(R.id.idTVSubTitleDetail);
        authorTV = findViewById(R.id.idTVAuthor);
        dateTV = findViewById(R.id.idTVDate);
        commentText = findViewById(R.id.idCommentDetail);
        btnComment = findViewById(R.id.idBtnCommentDetail);
        auth = FirebaseAuth.getInstance();


        bannerIV = findViewById(R.id.idIVDetail);
        titleTV.setText(title);
        subtitleTV.setText(subtitle);
        authorTV.setText(author);
        dateTV.setText(publishAt.split("T")[0]);
        Picasso.get().load(image).into(bannerIV);


        nbComment = helper.getCommentSize(publishAt);
        System.out.println("NB COMMENT " + nbComment);
        nbCommentTV.setText(nbComment + " Commentaires");
        btnComment.setOnClickListener(e -> {
            System.out.println("Comment with " + commentText.getText() + " For " + publishAt);
            System.out.println("Commentaire vide " + commentText.getText().length());
            if (!commentText.getText().toString().equals("")) {
                //  helper.insertComment(new CommentModel(publishAt, commentText.getText().toString(), ""));
                nbCommentTV.setText((nbComment + 1) + " Commentaires");

                FirebaseUser user = auth.getCurrentUser();
                System.out.println("USER " + user.getEmail());
                System.out.println("USER2 " + user.getDisplayName());
                System.out.println(user.getUid());
                Map<String, Object> comment = new HashMap<>();

                comment.put("uid", user.getUid());
                comment.put("email", user.getEmail());
                comment.put("publishat", publishAt);
                comment.put("content", commentText.getText());

                System.out.println("COMMENT TOSEND " + comment);


                firebaseDatabase = FirebaseDatabase.getInstance();
// below line is used to get reference for our database.
                databaseReference = firebaseDatabase.getReference("comment");
                CommentModel commentModel = new CommentModel(user.getUid().toString(), publishAt, commentText.getText().toString(), "20/06/2022");
                String userId = databaseReference.push().getKey();

                // Add a new document with a generated ID
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(userId).setValue(commentModel);
                        Snackbar snackbar = Snackbar
                                .make(NewsDetail.this.baseView, "Commentaire envoyé", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Snackbar snackbar = Snackbar
                                .make(NewsDetail.this.baseView, "Commentaire Echec", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });

            } else {
                Snackbar snackbar = Snackbar
                        .make(this.baseView, "Veuillez saisir un commentaire", Snackbar.LENGTH_LONG);
                // Show
                snackbar.show();
            }
        });


        nbCommentTV.setOnClickListener(e -> {
            Intent i = new Intent(getApplicationContext(), CommentActivity.class);
            i.putExtra("publishAt", publishAt);
            i.putExtra("content",title.toLowerCase().trim());
            startActivity(i);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

}