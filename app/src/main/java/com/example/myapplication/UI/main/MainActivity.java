package com.example.myapplication.UI.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Model.Coupons;
import com.example.myapplication.Model.Product;
import com.example.myapplication.Model.categories;
import com.example.myapplication.R;
import com.example.myapplication.UI.post;
import com.example.myapplication.adapter.Adapter;
import com.example.myapplication.adapter.CategoriesAdapterParent_Main;
import com.example.myapplication.adapter.ViewModelPaging;
import com.example.myapplication.callAPI.ClientApi;
import com.example.myapplication.databinding.ActivityMainBinding;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    private static final String TAG = "MainActivity";

    // SharedPreferences sharedPreferences;

    RecyclerView rv;
    RecyclerView rvCategories;
    Adapter adpter;
    CategoriesAdapterParent_Main categoriesAdapterParent_Main;


    //api
//    String baseUrl = "https://mysisshops.com";
//    String consumer_key = "ck_0a6f2f28f738a00280c2c4dd29f6d549a67d88f2";
//    String consumer_secret = "cs_8dd8c5458e1ad4fe1fe0e6ba1f38741c87827d4f";

//    DrawerLayout drawerLayout;
//    NavigationView navigationView;
//    Toolbar toolbar;

    //loadMore
    int pageLoadMore = 1;

    //shimmer
    // ShimmerFrameLayout shimmerFrameLayout;

    Context context = MainActivity.this;

    ArrayList<Product> productList = new ArrayList<>();
    ArrayList<categories> categoriesArrayList = new ArrayList<>();

    //mvvm
    LiveData liveData;


    //fot Testing
   // int s = 1;
    int count = 1;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ////////////////////////////////////////////////////////

        rv = findViewById(R.id.rv);
        rvCategories = findViewById(R.id.rvCategories);

        ViewModelPaging viewModelPaging = new ViewModelProvider(this).get(ViewModelPaging.class);

        Adapter adapterPaging = new Adapter(MainActivity.this);
        categoriesAdapterParent_Main = new CategoriesAdapterParent_Main();
        rvCategories.setAdapter(categoriesAdapterParent_Main);


        //set RecyclerView
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        viewModelPaging.productPagedListLive.observe(this, products -> {
            adapterPaging.submitList(products);
            Log.d("Main", String.valueOf(products.snapshot().size()) );

        });

        adapterPaging.setData(  false, new Adapter.Interface_getImage_Uri() {
            @Override
            public void getUri(Adapter.viewholder holder, Uri uri) {


                Thread thread = new Thread(() -> holder.img.post(() ->
                        Glide.with(context).load(uri).into(holder.img)));
                thread.start();


            }

            @Override
            public void set_Intent(Intent intent) {
                startActivity(intent);
            }
        });
        rv.setAdapter(adapterPaging);


/////for save Authorization and check API key
//        sharedPreferences = getSharedPreferences("S", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        if (baseUrl == null && consumer_key == null && consumer_secret == null) {
//
//            if (getIntent().getStringExtra("baseUrl") != null &&
//                    getIntent().getStringExtra("consumer_key") != null &&
//                    getIntent().getStringExtra("consumer_secret") != null) {
//
//                baseUrl = getIntent().getStringExtra("baseUrl");
//                consumer_key = getIntent().getStringExtra("consumer_key");
//                consumer_secret = getIntent().getStringExtra("consumer_secret");
//
//
//                editor.putString("baseUrl", baseUrl);
//                editor.putString("consumer_key", consumer_key);
//                editor.putString("consumer_secret", consumer_secret);
//                editor.apply();
//
//
//            }
//        }
//
//
//        if (baseUrl == null && consumer_key == null && consumer_secret == null) {
//            baseUrl = sharedPreferences.getString("baseUrl", null);
//            consumer_key = sharedPreferences.getString("consumer_key", null);
//            consumer_secret = sharedPreferences.getString("consumer_secret", null);
//        }


        liveData = new ViewModelProvider(this).get(LiveData.class);
        liveData.setContext(this);
        liveData.setSubscribe_categories1();
        //get List Data from Api
        liveData.mutableLiveData_Products.observe(this, products -> {
            liveData.setContext(getApplicationContext());
            productList.addAll(products);
            Log.d("Main2", String.valueOf(products.size()) );


        });

        //get categories Parent 1
        liveData.mutableLiveData_categories_Parent1.observe(this, categories -> {
            if (categories.size() > 0){
                binding.prbHome.setVisibility(View.GONE);
            }

            //get Data form LiveData from Api
            categoriesArrayList.addAll(categories);


            rvCategories.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            rvCategories.setHasFixedSize(true);
            categoriesAdapterParent_Main.setDataAdapter(categoriesArrayList, MainActivity.this, MainActivity.this);


        });



        //go to post Activity
        binding.floatingBTN.setOnClickListener(view ->
        {
//            if (baseUrl != null && consumer_key != null && consumer_secret != null) {
                Intent intent = new Intent(MainActivity.this, post.class);
//                intent.putExtra("baseUrl", baseUrl);
//                intent.putExtra("consumer_key", consumer_key);
//                intent.putExtra("consumer_secret", consumer_secret);
                startActivity(intent);
//            }

        });


        //get Authorization if Null
        //       if (baseUrl == null) {

//            Intent set_Authorize_intent = new Intent(MainActivity.this, SettingsActivity.class);
//            startActivity(set_Authorize_intent);




//        //Load More Product In Scrolling Down
//        binding.nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
//            if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
//
//
//                pageLoadMore++;
//                liveData.get_Products_List( pageLoadMore , MainActivity.this);
//
//
//            }
//
//
//        });


    //go to post product activity
//        FloatingActionButton Actionfbtn = findViewById(R.id.Actionfbtn);
//
//        Actionfbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (baseUrl != null && consumer_key != null && consumer_secret != null) {
//                    Intent intent = new Intent(MainActivity.this, post.class);
//                    intent.putExtra("baseUrl", baseUrl);
//                    intent.putExtra("consumer_key", consumer_key);
//                    intent.putExtra("consumer_secret", consumer_secret);
//                    startActivity(intent);
//                }
//
//
//            }
//        });



      //  if (baseUrl != null && consumer_key != null && consumer_secret != null) {

            //set data products from api to liveData
            liveData.set_Products_List( pageLoadMore , this);





      // getPageAndNext(1).subscribe(x-> Log.d(TAG, "sizepage onCreate: "+ String.valueOf(x.size()))  , e->  Log.d(TAG, "sizepage Error: "+ String.valueOf(e)) );





    }//Out OnCreat





    Observable<ArrayList<Coupons>> getPageAndNext(int page) {


        return ClientApi.getInstance().get_List_Codes(count)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .concatMap(obsOld-> {

                    if (obsOld.size() == 0) {

                        return Observable.just(obsOld);
                    }
                    else {
                        count ++;
                        return Observable.zip(Observable.just(obsOld)
                        ,getPageAndNext(count),(product11, product22) -> {
                                    product11.addAll(product22);
                                    return product11;
                                });

                    }
                }
                );


    }

    public void setIntent(Intent intent)
    {
        startActivity(intent );
    }

}//Out Class