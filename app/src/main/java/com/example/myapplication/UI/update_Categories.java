package com.example.myapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Model.categories;
import com.example.myapplication.callAPI.ClientApi;
import com.example.myapplication.databinding.ActivityUpdateCategoriesBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class update_Categories extends AppCompatActivity {

    ActivityUpdateCategoriesBinding binding;
    int id;
    String name;
    String baseUrl = "https://mysisshops.com";
    String consumer_key ="ck_0a6f2f28f738a00280c2c4dd29f6d549a67d88f2";
    String consumer_secret ="cs_8dd8c5458e1ad4fe1fe0e6ba1f38741c87827d4f";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        id = Integer.parseInt(getIntent().getStringExtra("id"));



        binding.sendUpdateCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                    getPutAPI();

            }
        });

}




    void getPutAPI(){

        ClientApi.getInstance().updata_Categories(id ,name ).enqueue(new Callback<categories>() {
            @Override
            public void onResponse(Call<categories> call, Response<categories> response) {
                if(response.isSuccessful()){

                    Toast.makeText(update_Categories.this, "تم التحديث", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(update_Categories.this, categoriesList.class);
                    intent.putExtra("baseUrl", baseUrl);
                    intent.putExtra("consumer_key", consumer_key);
                    intent.putExtra("consumer_secret", consumer_secret);

                    startActivity(intent);

                }else {

                    Toast.makeText(update_Categories.this, "خطاء", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<categories> call, Throwable t) {

            }
        });


    }



    }