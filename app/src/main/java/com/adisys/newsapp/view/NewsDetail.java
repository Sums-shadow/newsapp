package com.adisys.newsapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.adisys.newsapp.R;
import com.adisys.newsapp.controller.DbHelper;
import com.adisys.newsapp.model.CommentModel;
import com.squareup.picasso.Picasso;

public class NewsDetail extends AppCompatActivity {
    String title, subtitle, image, author, content, publishAt, url;
    TextView titleTV, subtitleTV, authorTV, dateTV, nbCommentTV;
    ImageView bannerIV;
    DbHelper helper = new DbHelper(NewsDetail.this);
    EditText commentText;
    Button btnComment;
    int nbComment = 0;


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

        nbCommentTV = findViewById(R.id.nbComment);
        titleTV = findViewById(R.id.idTVTitleDetail);
        subtitleTV = findViewById(R.id.idTVSubTitleDetail);
        authorTV = findViewById(R.id.idTVAuthor);
        dateTV = findViewById(R.id.idTVDate);
        commentText = findViewById(R.id.idCommentDetail);
        btnComment = findViewById(R.id.idBtnCommentDetail);


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
            helper.insertComment(new CommentModel(publishAt, commentText.getText().toString(), ""));
            nbCommentTV.setText((nbComment + 1) + " Commentaires");
            commentText.setText("");
        });


        nbCommentTV.setOnClickListener(e -> {
            Intent i = new Intent(getApplicationContext(), CommentActivity.class);
            i.putExtra("publishAt",publishAt);
            startActivity(i);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        return true;
    }

}