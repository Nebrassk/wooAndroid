package com.example.myapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Model.Product;
import com.example.myapplication.Model.categories;
import com.example.myapplication.adapter.Adapter;
import com.example.myapplication.adapter.CategoriesAdapterParent_2;
import com.example.myapplication.databinding.ActivityCategoriesParentSub1Binding;

import java.util.ArrayList;

public class Categories_Parent_Sub1 extends AppCompatActivity {
    ActivityCategoriesParentSub1Binding binding;
    com.example.myapplication.UI.main.LiveData liveData ;
    CategoriesAdapterParent_2 categoriesAdapterParent_2;
    String id;
    ArrayList<categories> categoriesArrayListParent2 = new ArrayList<>();
    ArrayList<Product> productList = new ArrayList<>();
    Adapter adapter = new Adapter(this);




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding =ActivityCategoriesParentSub1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.progressBarProductsCategory.setVisibility(View.VISIBLE);
//
//        liveData = new ViewModelProvider(this).get(com.example.myapplication.UI.main.LiveData.class);
//        liveData.mutableLiveData_categories_Parent2.observe(this, categories -> {
//            categoriesArrayListParent2.addAll(categories);
//
//            categoriesAdapterParent_2.setDataAdapter(categoriesArrayListParent2,  Categories_Parent_Sub1.this);
//            binding.rvCategoriesParent1.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
//            binding.rvCategoriesParent1.setHasFixedSize(true);
//            binding.rvCategoriesParent1.setAdapter(categoriesAdapterParent_2);
//        });
//        //get products of Category List Data from Api
//        liveData.mutableLiveData_Products_Categories2.observe(this, products -> {
//
//
//            if(products!= null) {
//
//                productList.addAll(products);
//
//
//                //send Data to Adapter
//                adapter.setData(productList, getApplicationContext(), false, new Adapter.Interface_getImage_Uri() {
//                    @Override
//                    public void getUri(Adapter.viewholder holder, Uri uri) {
//
//
//                        Thread thread = new Thread(() -> holder.img.post(() -> {
//
//                            Glide.with(getApplicationContext()).load(uri).into(holder.img);
//                            //  Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
//                        }));
//                        thread.start();
//
//
//                    }
//
//                    @Override
//                    public void set_Intent(Intent intent) {
//                        startActivity(intent);
//                    }
//                });
//
//
//                //set RecyclerView
//                binding.rvProductCategoriesParent1.setAdapter(adapter);
//                binding.rvProductCategoriesParent1.setHasFixedSize(true);
//                binding.rvProductCategoriesParent1.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
//
//
//                //progressbar gone
//                binding.progressBarProductsCategory.setVisibility(View.GONE);
//
//            }else {
//                Toast.makeText(this, "لا توجد تصنيفات  في هذا التصنيف", Toast.LENGTH_LONG).show();
//            }
//        });
//
//
//       id =Objects.requireNonNull(getIntent().getStringExtra("id"));
//
//
//        categoriesAdapterParent_2 = new CategoriesAdapterParent_2();
//
//        liveData.get_Parent_2_CategoriesCall(this, 1, Integer.parseInt(id));
//        liveData.get_Products_List_Category(getIntent().getStringExtra("id"), this);
//
//
//        binding.txtNameProductsCategoryParent1.setText("منتجات تصنيف "  + getIntent().getStringExtra("name") );
//
//



    }


    public void setIntent(Intent intent)
    {
        startActivity(intent );
    }

}