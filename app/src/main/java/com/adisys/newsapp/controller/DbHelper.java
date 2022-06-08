package com.adisys.newsapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.adisys.newsapp.model.CommentModel;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, "newsapp", null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE comment(_id INTEGER PRIMARY KEY AUTOINCREMENT, date_publish TEXT, content TEXT, date_comment TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS comment");
        onCreate(db);
    }


    public void insertComment(CommentModel comment){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("date_publish",comment.getDate_publish());
        cv.put("date_comment",comment.getDate_comment());
        cv.put("content", comment.getContent());
        db.insert("comment",null,cv);
        db.close();

    }

    public int getCommentSize(String date_publish){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM comment where date_publish=?",new String[]{date_publish});
        cursor.moveToFirst();
        db.close();
        return cursor.getCount();
    }


    public Cursor getComments(String date_publish){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM comment where date_publish=?",new String[]{date_publish});
        return cursor;
    }
}
