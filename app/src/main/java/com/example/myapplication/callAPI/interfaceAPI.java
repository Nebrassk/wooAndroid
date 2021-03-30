package com.example.myapplication.callAPI;

import com.example.myapplication.Model.Coupons;
import com.example.myapplication.Model.Product;
import com.example.myapplication.Model.Product2;
import com.example.myapplication.Model.brands;
import com.example.myapplication.Model.categories;
import com.example.myapplication.Model.images;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface interfaceAPI {


    @Multipart
    @POST("/")
    Call<ResponseBody> postImage(@Part MultipartBody.Part image, @Part("name") RequestBody name);


    @GET("wp-json/wc/v2/products?")
    Observable<ArrayList<Product2>> PRODUCT_CALL(@Query("page") int page, @Query("consumer_key") String consumer_key
            , @Query("consumer_secret") String consumer_secret);





    @GET("wp-json/wc/v2/products?")
    Call<ArrayList<Product>> PRODUCT_CALL2(@Query("page") int page, @Query("consumer_key") String consumer_key
            , @Query("consumer_secret") String consumer_secret);


    @GET("wp-json/wc/v2/products?")
    Observable<ArrayList<Product>> get_Products_Category( @Query("category") String category, @Query("consumer_key") String consumer_key
            , @Query("consumer_secret") String consumer_secret);


    @POST("wp-json/wc/v2/products?")
    Call<Product2> PRODUCT_CALL_POST(@Body HashMap<String, Object> map, @Query("consumer_key") String consumer_key
            , @Query("consumer_secret") String consumer_secret);


    @GET("wp-json/wc/v2/products/categories?")
    Call<ArrayList<categories>> get_Categories(@Query("consumer_key") String consumer_key,
                                               @Query("consumer_secret") String consumer_secret
            ,@Query("parent") int parent);



    @GET("wp-json/wc/v2/products/categories?")
    Observable<ArrayList<categories>> get_Categories1(@Query("parent") int parent ,@Query("page") int page, @Query("consumer_key") String consumer_key
            , @Query("consumer_secret") String consumer_secret);
    @GET("wp-json/wc/v2/products/categories?")
    Call<ArrayList<categories>> get_Categories2(@Query("parent") int parent ,@Query("page") int page, @Query("consumer_key") String consumer_key
            , @Query("consumer_secret") String consumer_secret);
    @GET("wp-json/wc/v2/products/categories/?")
    Call<ArrayList<categories>> get_Categories3( @Query("parent") int parent, @Query("consumer_key") String consumer_key
            , @Query("consumer_secret") String consumer_secret);


    @GET("wp-json/wc/v2/products/categories?")
    Single<ArrayList<categories>> get_Sub_Categories( @Query("parent") int parent, @Query("consumer_key") String consumer_key
            , @Query("consumer_secret") String consumer_secret);

    @GET("wp-json/wc/v2/products/categories?")
    Single<ArrayList<categories>> get_Sub_Sub_Categories( @Query("parent") int parent, @Query("consumer_key") String consumer_key
            , @Query("consumer_secret") String consumer_secret);




    @POST("wp-json/wc/v2/products/categories?")
    Call<categories> post_Categories(@Body HashMap<Object, Object> map, @Query("consumer_key") String consumer_key
            , @Query("consumer_secret") String consumer_secret);



    @POST("wp-json/wc/v2/products/categories/{id}?")
    Call<categories> updata_Categories(@Path("id") int id,@Query("name") String name , @Query("consumer_key") String consumer_key
            , @Query("consumer_secret") String consumer_secret);




    @PUT("wp-json/wc/v2/products/{id}/?")
    Call<Product> updata_ProductName(@Path("id") int id,  @Query("name") String name, @Query("consumer_key") String consumer_key
            , @Query("consumer_secret") String consumer_secret);

    @PUT("wp-json/wc/v2/products/{id}/?")
    Call<Product> updata_ProductPrice(@Path("id") int id,  @Query("regular_price") String regular_price, @Query("consumer_key") String consumer_key
            , @Query("consumer_secret") String consumer_secret);

    @PUT("wp-json/wc/v2/products/{id}/?")
    Call<Product> updata_Productdescription(@Path("id") int id,  @Query("description") String description, @Query("consumer_key") String consumer_key
            , @Query("consumer_secret") String consumer_secret);

    @PUT("wp-json/wc/v2/products/{id}/?")
    Call<Product> updata_ProductImage(@Path("id") int id,  @Query("images") ArrayList<images> name, @Query("consumer_key") String consumer_key
            , @Query("consumer_secret") String consumer_secret);

    @DELETE("wp-json/wc/v2/products/{id}/?")
    Call<Product> delete_Product(@Path("id") int id,  @Query("force") boolean delete, @Query("consumer_key") String consumer_key
            , @Query("consumer_secret") String consumer_secret);


    @POST("wp-json/wc/v3/products/brands/?")
    Call<brands> addBrand( @Query("name") String name, @Query("consumer_key") String consumer_key
            , @Query("consumer_secret") String consumer_secret);

    @GET("wp-json/wc/v3/products/brands/?")
    Observable<ArrayList<brands>> getAllBrands(@Query("page") int  page, @Query("consumer_key") String consumer_key
            , @Query("consumer_secret") String consumer_secret);

    @GET("wp-json/wc/v2/products/categories?")
    Single<ArrayList<categories>> get_All_Categories(@Query("page") int  page, @Query("consumer_key") String consumer_key
            , @Query("consumer_secret") String consumer_secret);

    @POST("wp-json/wc/v3/coupons?")
    Call<Coupons> add_Coupon(@Query("code") String  code, @Query("amount") String  amount  , @Query("consumer_key") String consumer_key
            , @Query("consumer_secret") String consumer_secret);

    @POST("wp-json/wc/v3/coupons/batch/?")
    Call<Coupons> all_Add_Coupons(@Body HashMap<Object , Object> hashMap , @Query("consumer_key") String consumer_key
            , @Query("consumer_secret") String consumer_secret);

    @GET("wp-json/wc/v3/coupons?")
    Observable<ArrayList<Coupons>> get_List_Codes(  @Query("page") int page, @Query("consumer_key") String consumer_key
            , @Query("consumer_secret") String consumer_secret);




}
