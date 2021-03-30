package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.categories;
import com.example.myapplication.R;

import java.util.ArrayList;

public class Adapter_for_categories_parent1 extends RecyclerView.Adapter<Adapter_for_categories_parent1.viewholder> {


    Context context;
    ArrayList<ArrayList<categories>> all_categoriesArrayList;
    ArrayList<categories> categoriesArrayList = new ArrayList<>();
    ArrayList<categories> categoriesArrayList2 = new ArrayList<>();
    ArrayList<categories> categoriesArrayList3 = new ArrayList<>();



    public Adapter_for_categories_parent1(Context context, ArrayList<ArrayList<categories>> all_categoriesArrayList) {
        this.context = context;
        this.all_categoriesArrayList = all_categoriesArrayList;
        for (int i = 0 ; i  < all_categoriesArrayList.size(); i++){
            for(int ii = 0 ; ii  < all_categoriesArrayList.get(i).size(); ii++){
                if (all_categoriesArrayList.get(i).get(ii).getParent() == 0) {
                    categoriesArrayList.add(all_categoriesArrayList.get(i).get(ii));


                }

            }
        }

        notifyDataSetChanged();
    }





    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_for_parent1_categories, parent,false);
        return new viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position)
    {

        categories categories = categoriesArrayList.get(position);

        holder.textView.setText(categories.getId() + categories.getName());

        for (int i = 0 ; i  < all_categoriesArrayList.size(); i++){
            for(int ii = 0 ; ii  < all_categoriesArrayList.get(i).size(); ii++) {
                if (categoriesArrayList.get(ii).getId() == all_categoriesArrayList.get(i).get(ii).getParent()) {
                    categoriesArrayList2.add(all_categoriesArrayList.get(i).get(ii));

                }
            }}


        for(int i = 0 ; i  < categoriesArrayList2.size(); i++) {
            if (categoriesArrayList2.get(i).getParent() == categories.getId()) {


                Adapter_for_categories_parent2 adapter_for_categories_parent2 = new Adapter_for_categories_parent2(context, categoriesArrayList2);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                holder.rvGroup1.setLayoutManager(layoutManager);
                holder.rvGroup1.setAdapter(adapter_for_categories_parent2);
            }
        }


    }

    @Override
    public int getItemCount() {
        return categoriesArrayList.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder
    {
       TextView textView;
       RecyclerView rvGroup1;


        public viewholder(@NonNull View itemView)
        {
            super(itemView);

            textView = itemView.findViewById(R.id.textGroup1);
            rvGroup1 = itemView.findViewById(R.id.rvGroupParent1);



        }
    }

}



