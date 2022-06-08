package com.adisys.newsapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.adisys.newsapp.R;
import com.adisys.newsapp.controller.DbHelper;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {


    ListView commentList;
    DbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
    String publishAt=getIntent().getStringExtra("publishAt");
    Log.i("info","Publish at "+publishAt);

        commentList=findViewById(R.id.commentList);
        db=new DbHelper(this);
        Cursor res=db.getComments(publishAt);

System.out.println(res);
        SimpleCursorAdapter cursorAdapter=new SimpleCursorAdapter(this, R.layout.comment_item,res,
                new String[]{res.getColumnName(2),res.getColumnName(1).split("T")[0]}, new int[]{R.id.commentContent,R.id.commentContentDate},1);
        commentList.setAdapter(cursorAdapter);




    }
}