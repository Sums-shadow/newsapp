package com.adisys.newsapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.adisys.newsapp.R;
import com.adisys.newsapp.controller.DbHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class CommentActivity extends AppCompatActivity {


    ListView commentList;
    DbHelper db;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    TextView tv;
    ProgressBar pg;
    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        String publishAt = getIntent().getStringExtra("publishAt");
        String content = getIntent().getStringExtra("content");
        commentList = findViewById(R.id.commentList);
        tv=findViewById(R.id.commentNothing);
        tv.setVisibility(View.INVISIBLE);

        pg=findViewById(R.id.commentProgress);
        pg.setVisibility(View.VISIBLE);
        db = new DbHelper(this);
        Cursor res = db.getComments(publishAt);
        String date = res.getColumnName(1).split("T")[0];

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.comment_item, res,
                new String[]{res.getColumnName(2), res.getColumnName(1)}, new int[]{R.id.commentContent, R.id.commentContentDate}, 1);


        String[] from = {"commentContent", "commentContentDate"};
        int to[] = {R.id.commentContent, R.id.commentContentDate};






        firebaseDatabase = FirebaseDatabase.getInstance();
// below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("comment");

        ArrayList<HashMap<String, Object>> list = new ArrayList<>();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snap: snapshot.getChildren()) {
                    // TODO: handle the post
                    System.out.println(snap.child("date_publish").getValue().toString()+" VS "+publishAt);

                    if(snap.child("date_publish").getValue().toString().equals(publishAt)){
                        // creating an Object of HashMap class
                        HashMap<String, Object> map = new HashMap<>();

                        // Data entry in HashMap
                        map.put("commentContent", snap.child("content").getValue());
                        map.put("commentContentDate", snap.child("date_comment").getValue());
                        list.add(map);
                    }
                }
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), list, R.layout.comment_item, from, to);
                pg.setVisibility(View.INVISIBLE);
                if(list.size()==0){
                    tv.setVisibility(View.VISIBLE);
                }else{
                    tv.setVisibility(View.INVISIBLE);
                }

                commentList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}