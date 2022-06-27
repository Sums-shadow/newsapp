package com.adisys.newsapp.view;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.adisys.newsapp.R;
import com.adisys.newsapp.adapter.CategoryRVAdapter;
import com.adisys.newsapp.adapter.NewsRVAdapter;
import com.adisys.newsapp.controller.RetrofitApi;
import com.adisys.newsapp.model.ArticleModel;
import com.adisys.newsapp.model.CategoryRVModel;
import com.adisys.newsapp.model.NewsModel;
import com.adisys.newsapp.view.auth.AuthActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity implements CategoryRVAdapter.categoryClickInterface {
    private RecyclerView newsRV, categoryRV;
    private ProgressBar pg;
    private Button btnRetry;
    private ArrayList<ArticleModel> articleModels;
    private ArrayList<CategoryRVModel> categoryRVModels;
    private CategoryRVAdapter categoryRVAdapter;
    private NewsRVAdapter newsRVAdapter;
    private FirebaseAuth auth;
    private ImageButton btnProfil, btnExit;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth=FirebaseAuth.getInstance();
        btnProfil=findViewById(R.id.HomeUserProfil);
        btnExit=findViewById(R.id.HomeExit);
        newsRV=findViewById(R.id.recyclenews);
        categoryRV=findViewById(R.id.recycleCategorie);
        pg=findViewById(R.id.progress);
        categoryRVModels=new ArrayList<>();
        articleModels=new ArrayList<>();
        btnRetry=findViewById(R.id.btnRetry);
        newsRVAdapter=new NewsRVAdapter(articleModels,this);
        categoryRVAdapter=new CategoryRVAdapter(categoryRVModels,this,this::onClickCategory);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);
        getAllCategories();
        getNews("Tout");
        newsRVAdapter.notifyDataSetChanged();

        btnRetry.setOnClickListener(e->{
            Log.i("info","Retrying....");
            getNews("Tout");
        });

        btnExit.setOnClickListener(e->{
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("NewsApp")
                        .setMessage("Voulez-vous vous deconnecter?")
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "Deconnecter",Toast.LENGTH_SHORT).show();
                            auth.signOut();
                            startActivity(new Intent(HomeActivity.this, AuthActivity.class));
                        }

                    })
                    .setNegativeButton("Non", null)
                    .show();
        });
        btnProfil.setOnClickListener(e->{
            startActivity(new Intent(HomeActivity.this,ProfilActivity.class));
        });

    }


    private void getAllCategories(){

        categoryRVModels.add(new CategoryRVModel("Tout","https://img.freepik.com/photos-gratuite/digital-world-map-hologram-fond-bleu_1379-901.jpg?t=st=1654075617~exp=1654076217~hmac=da36a6fb97effee5ae6690673373130bda6f10981c16e91fa8cd7061c9bae80a&w=900"));
        categoryRVModels.add(new CategoryRVModel("Science","https://img.freepik.com/photos-gratuite/coup-moyen-homme-portant-lunettes-vr_23-2149126949.jpg?t=st=1654083100~exp=1654083700~hmac=e99e934a55210f911ed0b9f583f83ae6bdb275e488c179bbbfda6d4d8e44934a&w=826"));
        categoryRVModels.add(new CategoryRVModel("Business","https://img.freepik.com/photos-gratuite/details-colonne-marbre-gris-batiment_1359-886.jpg?t=st=1654075567~exp=1654076167~hmac=9f48ab2a8221805d9abdd1461f8e4a001553e70bd5ac5d21268f6a083c26e247&w=740"));
        categoryRVModels.add(new CategoryRVModel("technology","https://img.freepik.com/photos-gratuite/groupe-multiethnique-jeunes-hommes-femmes-etudiant-interieur_1139-989.jpg?t=st=1654083422~exp=1654084022~hmac=d662d1a5eac21936b7bb2795c4672a91fb1f5bfe8a630d448b1b92e810168c8a&w=740"));
        categoryRVModels.add(new CategoryRVModel("sports","https://img.freepik.com/photos-gratuite/gens-dans-concert_1160-737.jpg?t=st=1654083190~exp=1654083790~hmac=44eb4c46b280ee9ce66f0d1cbf0a5ed87b45a1d44d694a77ed011481c1560822&w=740"));
        categoryRVModels.add(new CategoryRVModel("general","https://img.freepik.com/photos-gratuite/pile-pieces-argent-graphique-trading_1150-17752.jpg?t=st=1654083248~exp=1654083848~hmac=d77e3034c8542d8f4748a0f1e86e80aadd9bde6fb31a40adbefe96a29b71a5b3&w=740"));
        categoryRVModels.add(new CategoryRVModel("entertainment","https://img.freepik.com/photos-gratuite/chemin-terre-au-milieu-arbres-forestiers-journee-ensoleillee_181624-2875.jpg?t=st=1654083462~exp=1654084062~hmac=1310ce5227108c8f8cfa6fb7345b0dd3b36704030875cc349e0848efd7277137&w=740"));
        categoryRVModels.add(new CategoryRVModel("health","https://img.freepik.com/free-photo/close-up-african-american-hand-holding-stethoscope_482257-19507.jpg?t=st=1654075352~exp=1654075952~hmac=cef087bd7049a670c9dbd7faf69f990bd7579e56cf832e4e65c0b300ecfe3ff4&w=740"));

    }

    private void getNews(String category){
        Log.i("info","Geting news ....");
        pg.setVisibility(View.VISIBLE);
        btnRetry.setVisibility(View.INVISIBLE);
        articleModels.clear();

        String categoryUrl= "https://newsapi.org/v2/top-headlines?country=fr&category="+category+"&apiKey=7c9fc2de96cb40d795dd2cae9653c8a1";
      //  String allUrl="https://newsapi.org/v2/everything?q=Apple&sortBy=popularity&apiKey=7c9fc2de96cb40d795dd2cae9653c8a1";
        String allUrl="https://newsapi.org/v2/top-headlines?country=fr&category=general&apiKey=7c9fc2de96cb40d795dd2cae9653c8a1";
        String BASE_URL="http://newsapi.org/";
        Retrofit retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitApi retrofitApi=retrofit.create(RetrofitApi.class);
        Call<NewsModel> call;
        if(category.equals("Tout")){
          call=retrofitApi.getAllNews(allUrl);
        }else{
          call=retrofitApi.getNewsCategories(categoryUrl);
         }

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                System.out.println(call.request().url().toString());
                NewsModel newsModel=response.body();
                System.out.println(newsModel);
                pg.setVisibility(View.INVISIBLE);
                ArrayList<ArticleModel> articles=newsModel.getArticles();
                for (int i=0; i<articles.size();i++){
                    articleModels.add(new ArticleModel(articles.get(i).getAuthor(), articles.get(i).getTitle(),articles.get(i).getDescription(),articles.get(i).getUrl(),articles.get(i).getUrlToImage(),articles.get(i).getPublishedAt(), articles.get(i).getContent()));

                }
                newsRVAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                pg.setVisibility(View.INVISIBLE);
                btnRetry.setVisibility(View.VISIBLE);
                Log.e("error","Error on get Data to api ");
                t.printStackTrace();
                Toast.makeText(HomeActivity.this, "Failed to get News", Toast.LENGTH_LONG);
            }
        });

    }

    @Override
    public void onClickCategory(int position) {
        String category=categoryRVModels.get(position).getCategory();
        getNews(category);


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("NewsApp")
                .setMessage("Voulez-vous vous quitter l'application?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("Non", null)
                .show();
    }
}