package com.adisys.newsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adisys.newsapp.R;
import com.adisys.newsapp.model.CategoryRVModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.ViewHolder> {

    private ArrayList<CategoryRVModel> categoryRVModels;
    private Context context;
    private  categoryClickInterface categoryClickInterface;
    public CategoryRVAdapter(ArrayList<CategoryRVModel> categoryRVModels, Context context) {
        this.categoryRVModels = categoryRVModels;
        this.context = context;
    }

    public CategoryRVAdapter(ArrayList<CategoryRVModel> categoryRVModels, Context context, CategoryRVAdapter.categoryClickInterface categoryClickInterface) {
        this.categoryRVModels = categoryRVModels;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public CategoryRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRVAdapter.ViewHolder holder, int position) {
        CategoryRVModel category=categoryRVModels.get(position);
        holder.categoryTV.setText(category.getCategory());
        Picasso.get().load(category.getCategoryImageUrl()).into(holder.categoryIV);
        holder.itemView.setOnClickListener(e->{
            categoryClickInterface.onClickCategory(position);

        });
    }

    @Override
    public int getItemCount() {
        return categoryRVModels.size();
    }


    public interface  categoryClickInterface{
        void onClickCategory(int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryTV;
        private ImageView categoryIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTV=itemView.findViewById(R.id.idTvCategory);
            categoryIV=itemView.findViewById(R.id.idIvCategory);

        }
    }
}
