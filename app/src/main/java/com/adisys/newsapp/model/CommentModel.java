package com.adisys.newsapp.model;

public class CommentModel {

   private int id;
   private String date_publish;
   private String content;
   private String date_comment;

    public CommentModel(int id, String date_publish, String content, String date_comment) {
        this.id = id;
        this.date_publish = date_publish;
        this.content = content;
        this.date_comment = date_comment;
    }


    public CommentModel(String date_publish, String content, String date_comment) {
        this.date_publish = date_publish;
        this.content = content;
        this.date_comment = date_comment;
    }


    public CommentModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate_publish() {
        return date_publish;
    }

    public void setDate_publish(String date_publish) {
        this.date_publish = date_publish;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate_comment() {
        return date_comment;
    }

    public void setDate_comment(String date_comment) {
        this.date_comment = date_comment;
    }
}
