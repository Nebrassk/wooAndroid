package com.example.myapplication.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.categories;
import com.example.myapplication.R;
import com.example.myapplication.callAPI.interfaceAPI;
import com.example.myapplication.databinding.ActivityCategoriesListBinding;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class categoriesList extends AppCompatActivity {

    ActivityCategoriesListBinding binding;
    ArrayList<categories> getarray = new ArrayList<>();
    //all_Categories all_categories  ;

    /////////////////
    ArrayList<categories> getCategoriesArrayListPage;
    ArrayList<ArrayList<categories>> getCategoriesArrayListALLPage = new ArrayList<>();
    int page;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoriesListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Get_CategoriesPage();

       // set_Adapter_All_Categories( getarray);


    }//Out



    void set_Adapter_All_Categories( ArrayList<categories> get)
    {
        all_Adapter_Cateogries all_adapter_cateogries = new all_Adapter_Cateogries( categoriesList.this,get);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(categoriesList.this);
        binding.rvCategories.setLayoutManager(linearLayoutManager);
        binding.rvCategories.setAdapter(all_adapter_cateogries);

    }


    class all_Adapter_Cateogries extends RecyclerView.Adapter<all_Adapter_Cateogries.viewHolder> {

        Context context;
        ArrayList<categories> all__array_ListArrayList ;




        public all_Adapter_Cateogries(Context context, ArrayList<categories> all__array_ListArrayList) {
            this.context = context;
            this.all__array_ListArrayList = all__array_ListArrayList;
            notifyDataSetChanged();

//            for(int i = 0; i<all__array_ListArrayList.size() ;i++)
//            {
//                for (int ii = 0; ii<all__array_ListArrayList.get(i).size() ;ii++)
//                {
//                    a_ListArray.add(all__array_ListArrayList.get(i).get(ii));
//
//                }
//
//            }


        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_row_all_categories, parent, false);
            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder holder, int position) {

            categories categories = all__array_ListArrayList.get(position);
            holder.txt.setText(categories.getName());
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, update_Categories.class);
                    intent.putExtra("id", String.valueOf(categories.getId()));
                    startActivity(intent);

                }
            });

        }

        @Override
        public int getItemCount() {
            return all__array_ListArrayList.size();
        }

        public class viewHolder extends RecyclerView.ViewHolder
        {

            TextView txt;
            Button btn;

            public viewHolder(@NonNull View itemView) {
                super(itemView);
                txt =(TextView)itemView.findViewById(R.id.txtnameCategories);
                btn =(Button) itemView.findViewById(R.id.sendToUpdateCategories);


            }
        }
    }

    private void Get_CategoriesPage() {

        try {


            for (page = 1; page < 5; page++) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(getIntent().getStringExtra("baseUrl"))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                interfaceAPI get_Call = retrofit.create(com.example.myapplication.callAPI.interfaceAPI.class);
                // 0 fro  get just parent Categories
//                Call<ArrayList<categories>> call_Category = get_Call.get_Categories2(getIntent().getStringExtra("consumer_key"), getIntent().getStringExtra("consumer_secret"), page);

//                call_Category.enqueue(new Callback<ArrayList<categories>>() {
//                    @Override
//                    public void onResponse(Call<ArrayList<com.example.myapplication.Model.categories>> call, Response<ArrayList<categories>> response) {
//                        if (response.isSuccessful()) {
//                            getCategoriesArrayListPage = response.body();
//
//                            getCategoriesArrayListALLPage.add(getCategoriesArrayListPage);
//
//
//
//                            if (getCategoriesArrayListALLPage.size() == 4) {
//
//                                //   setAdapter(getCategoriesArrayListALLPage);
//
//                                ArrayList<categories> a_ListArray  = new ArrayList<>();
//
//
//
//                                for(int i = 0; i<getCategoriesArrayListALLPage.size() ;i++)
//                                {
//                                    for (int ii = 0; ii<getCategoriesArrayListALLPage.get(i).size() ;ii++)
//                                    {
//                                        a_ListArray.add(getCategoriesArrayListALLPage.get(i).get(ii));
//
//
//                                    }
//                                    if (getCategoriesArrayListALLPage.size() -1 == i ){
//
//                                       set_Adapter_All_Categories(a_ListArray);
//                                        Toast.makeText(categoriesList.this, "d " +i, Toast.LENGTH_SHORT).show();
//                                    }
//
//
//                                }
//
//
//
//
//
//                                //    for (int w = 0; w < getCategoriesArrayListALLPage.size(); w++) {
//                                for (int i = 0; i < getCategoriesArrayListALLPage.size(); i++) {
//                                    for (int ii = 0; ii < getCategoriesArrayListALLPage.get(i).size(); ii++) {
//
//
//
//                                    }
//                                }
////                                    for (int o = 0; o < getCategoriesArrayListALLPage.size(); o++) {
////                                        for (int oo = 0; oo < getCategoriesArrayListALLPage.get(o).size(); oo++) {
////                                            if (categoriesArrayList1.get(oo).getId() == getCategoriesArrayListALLPage.get(o).get(oo).getParent()) {
////                                                categoriesArrayList2.add(getCategoriesArrayListALLPage.get(o).get(oo));
////
////                                            }
////                                        }
////                                    }
//                                //    }
//
////                                if (!categoriesArrayList2.isEmpty()) {
////                                    Toast.makeText(post.this, "!!isEmpty", Toast.LENGTH_SHORT).show();
////
////                                }
//
//
//
//
//                            }
////                            for(int page =0; page<getCategoriesArrayListPage.size() ; page++)
////                            {
////
////
////
////                                Toast.makeText(post.this, "Successfully get Category " + page , Toast.LENGTH_SHORT).show();
////                            }
//
//
//                        } else {
//                            Toast.makeText(categoriesList.this, "ERROR: API GET Categories", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//
//                    @Override
//                    public void onFailure(Call<ArrayList<com.example.myapplication.Model.categories>> call, Throwable t) {
//                        Toast.makeText(categoriesList.this, "ERROR: API GET Categories" + t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//

            }

        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }


}