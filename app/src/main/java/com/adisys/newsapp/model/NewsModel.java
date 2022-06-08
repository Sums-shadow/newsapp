package com.adisys.newsapp.model;

import java.util.ArrayList;

public class NewsModel {
    private String status;
    private String totalResults;
    private ArrayList<ArticleModel> articles;

    public NewsModel(String status, String totalResults, ArrayList<ArticleModel> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public NewsModel() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public ArrayList<ArticleModel> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<ArticleModel> articles) {
        this.articles = articles;
    }
}
