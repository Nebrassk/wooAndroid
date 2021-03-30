package com.example.myapplication.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Model.categories;
import com.example.myapplication.R;
import com.example.myapplication.UI.Categories_Parent_Sub1;

import java.util.ArrayList;

public class CategoriesAdapterParent_2 extends RecyclerView.Adapter<CategoriesAdapterParent_2.ViewHolder> {

    ArrayList<categories> categoriesArrayList;
    Categories_Parent_Sub1 context ;




    public void setDataAdapter(ArrayList<categories> categoriesArrayList, Categories_Parent_Sub1 context){
        this.categoriesArrayList = categoriesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_categoriesparent1,parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        if(categoriesArrayList.get(position) != null) {
            categories categoriesClass = categoriesArrayList.get(position);
            holder.txtName.setText(categoriesClass.getName());
            holder.txtName.setOnClickListener(view -> {

                Intent intent = new Intent(context, Categories_Parent_Sub1.class);
                intent.putExtra("id", String.valueOf(categoriesClass.getId()));
                intent.putExtra("name", String.valueOf(categoriesClass.getName()));

                context.setIntent(intent);


            });

            holder.imgCategory.setOnClickListener(view -> {

                Intent intent = new Intent(context, Categories_Parent_Sub1.class);
                intent.putExtra("id", String.valueOf(categoriesClass.getId()));
                intent.putExtra("name", String.valueOf(categoriesClass.getName()));

                context.setIntent(intent);


            });
            if (categoriesClass.getImage() !=null && categoriesClass.getImage().getSrc() != null)
                Glide.with(context).load(Uri.parse(categoriesClass.getImage().getSrc())).into(holder.imgCategory);


        }
    }

    @Override
    public int getItemCount() {

        return categoriesArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView txtName;
        ImageView imgCategory;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.nameCategoryParent1);
            imgCategory =  itemView.findViewById(R.id.imageCategoryParent1);

        }

    }



}
