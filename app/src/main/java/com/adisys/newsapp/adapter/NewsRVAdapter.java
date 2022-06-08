package com.adisys.newsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adisys.newsapp.R;
import com.adisys.newsapp.model.ArticleModel;
import com.adisys.newsapp.view.NewsDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.viewHolder> {

    private ArrayList<ArticleModel> articleModels;
    private Context context;

    public NewsRVAdapter(ArrayList<ArticleModel> articleModels, Context context) {
        this.articleModels = articleModels;
        this.context = context;
    }



    @NonNull
    @Override
    public NewsRVAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
         return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRVAdapter.viewHolder holder, int position) {
        ArticleModel article=articleModels.get(position);
        holder.subTitleTV.setText(article.getDescription());
        holder.titleTV.setText(article.getTitle());
        Picasso.get().load(article.getUrlToImage()).into(holder.newsIV);
        holder.itemView.setOnClickListener(e->{
            Intent i=new Intent(context, NewsDetail.class);
            i.putExtra("title",article.getTitle());
            i.putExtra("subTitle", article.getDescription());
            i.putExtra("author",article.getAuthor());
            i.putExtra("image",article.getUrlToImage());
            i.putExtra("publishAt",article.getPublishedAt());
            i.putExtra("content",article.getContent());
            i.putExtra("url",article.getUrl());
            context.startActivity(i);


        });
    }

    @Override
    public int getItemCount() {
        return articleModels.size();
    }

    public class viewHolder extends  RecyclerView.ViewHolder{

        private TextView titleTV,subTitleTV;
        private ImageView newsIV;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV=itemView.findViewById(R.id.idNewsHeading);
            subTitleTV=itemView.findViewById(R.id.idTvSubTitle);
            newsIV=itemView.findViewById(R.id.idIvNews);
        }
    }
}
