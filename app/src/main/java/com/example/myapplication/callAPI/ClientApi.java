package com.example.myapplication.callAPI;

import com.example.myapplication.Model.Coupons;
import com.example.myapplication.Model.Product;
import com.example.myapplication.Model.Product2;
import com.example.myapplication.Model.brands;
import com.example.myapplication.Model.categories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientApi {
    private static final String baseUrl = "https://mysisshops.com/";
    private static final String consumer_key = "ck_0a6f2f28f738a00280c2c4dd29f6d549a67d88f2";
    private static final String consumer_secret = "cs_8dd8c5458e1ad4fe1fe0e6ba1f38741c87827d4f";
    private interfaceAPI apiClass;
    private static ClientApi instance;



    public void ClientApiApi() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(55, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiClass = retrofit.create(interfaceAPI.class);

    }

    public static ClientApi getInstance() {
        if (instance == null) {
            instance = new ClientApi();
        }
        return instance;
    }

    public Call<categories> categoriesCall(HashMap<Object, Object> hashMap) {

        return apiClass.post_Categories(hashMap, consumer_key, consumer_secret);
    }

    public Observable<ArrayList<categories>> parent_1_CategoriesCall(int page, int parent) {

        ClientApiApi();
        return apiClass.get_Categories1(parent, page, consumer_key, consumer_secret);
    }

    public Call<ArrayList<categories>> parent_2_CategoriesCall(int parent, int page) {

        ClientApiApi();
        return apiClass.get_Categories2(parent, page, consumer_key, consumer_secret);
    }

    public Call<ArrayList<categories>> parent_3_CategoriesCall(int parent) {

        ClientApiApi();
        return apiClass.get_Categories3(parent, consumer_key, consumer_secret);
    }

    public Observable<ArrayList<Product>> get_Products_Category(String category) {

        ClientApiApi();
        return apiClass.get_Products_Category(category, consumer_key, consumer_secret);
    }

    public Observable<ArrayList<Product2>> getProducts_From_Api(int page) {

        ClientApiApi();
        return apiClass.PRODUCT_CALL(page, consumer_key, consumer_secret);
    }

    public Call<ArrayList<Product>> getProducts_From_Api2(int page) {

        ClientApiApi();
        return apiClass.PRODUCT_CALL2(page, consumer_key, consumer_secret);
    }

    public Observable<ArrayList<brands>> get_All_Brands(int page) {

        ClientApiApi();
        return apiClass.getAllBrands(page, consumer_key, consumer_secret);
    }

    public Single<ArrayList<categories>> get_All_Categories(int page) {

        ClientApiApi();
        return apiClass.get_All_Categories(page, consumer_key, consumer_secret);
    }

    public Call<Product> delete_Product (int id, boolean delete)
    {
        ClientApiApi();
        return apiClass.delete_Product(id, delete, consumer_key, consumer_secret);
    }

    public Call<Coupons> add_Coupon (String code, String amount)
    {
        ClientApiApi();
        return apiClass.add_Coupon(code , amount, consumer_key, consumer_secret);
    }

    public Observable<ArrayList<Coupons>> get_List_Codes (int page)
    {
        ClientApiApi();
        return apiClass.get_List_Codes(page, consumer_key, consumer_secret);
    }

    public Call<Coupons> all_Add_Coupons(HashMap<Object, Object> hashMap)
    {
        ClientApiApi();

        return apiClass.all_Add_Coupons(hashMap, consumer_key, consumer_secret);


    }
    public Call<Product2> PRODUCT_CALL_POST(HashMap<String, Object> hashMap)
    {
        ClientApiApi();

        return apiClass.PRODUCT_CALL_POST(hashMap, consumer_key, consumer_secret);


    }


    public Call<brands> addBrand(String name)
    {
        ClientApiApi();

        return apiClass.addBrand(name, consumer_key, consumer_secret);


    }

    public Call<categories> post_Categories(HashMap<Object, Object> hashMap)
    {
        ClientApiApi();

        return apiClass.post_Categories(hashMap, consumer_key, consumer_secret);


    }

    public Call<categories> updata_Categories(int id, String name)
    {
        ClientApiApi();

        return apiClass.updata_Categories(id , name, consumer_key, consumer_secret);


    }

    public Call<Product> updata_ProductName(int id, String name)
    {
        ClientApiApi();

        return apiClass.updata_ProductName(id,  name , consumer_key, consumer_secret);


    }

}
