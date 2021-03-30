package com.example.myapplication.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.myapplication.Model.Product;
import com.example.myapplication.callAPI.ClientApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsDataSource extends PageKeyedDataSource<Integer, Product> {


    private final int FIRST_PAGE = 1;
    ClientApi clientApi = new ClientApi();


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Product> callback)
    {



        clientApi.getProducts_From_Api2(FIRST_PAGE).enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if (response.isSuccessful() && response.body().size() !=0){
                    callback.onResult(response.body(), null, FIRST_PAGE + 1);


                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Log.d("TAG" , "paging Error: "+ t.getMessage());
            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Product> callback)
    {


        clientApi.getProducts_From_Api2(FIRST_PAGE).enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if (response.isSuccessful() && response.body().size() !=10){
                    Integer key = (params.key > 1) ? params.key -1 :null;
                    callback.onResult(response.body(),  key);

                    Log.d("TAG" , "MYY22 Error: ");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Log.d("TAG" , "MYY2 Error: "+ t.getMessage());
            }
        });


    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Product> callback)
    {


        clientApi.getProducts_From_Api2(FIRST_PAGE).enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if (response.isSuccessful() && response.body().size() !=10){
                   // Integer key = response.body().size()!= 0 ? params.key +1 : null;
                     Integer key = params.key + 1;
                    callback.onResult(response.body(),  key);


                    Log.d("TAG" , "MYY33 Error: ");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

                Log.d("TAG" , "MYY3 Error: "+ t.getMessage());
            }
        });





    }





}
