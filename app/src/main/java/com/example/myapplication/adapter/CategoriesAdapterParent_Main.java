package com.example.myapplication.adapter;

import android.content.Context;
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
import com.example.myapplication.UI.main.MainActivity;

import java.util.ArrayList;

public class CategoriesAdapterParent_Main extends RecyclerView.Adapter<CategoriesAdapterParent_Main.ViewHolder> {
    ArrayList<categories> categoriesArrayList;
    Context context;
    MainActivity mainActivity;


    public void setDataAdapter(ArrayList<categories> categoriesArrayList, Context context, MainActivity mainActivity){
        this.categoriesArrayList = categoriesArrayList;
        this.context = context;
        this.mainActivity =mainActivity;
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
        categories categoriesClass = categoriesArrayList.get(position);
        holder.txtName.setText(categoriesClass.getName());
        holder.imgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Categories_Parent_Sub1.class);
                intent.putExtra("id" , String.valueOf(categoriesClass.getId()));
                intent.putExtra("name" , String.valueOf(categoriesClass.getName()));
                mainActivity.setIntent(intent);

            }
        });
        holder.txtName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Categories_Parent_Sub1.class);
                intent.putExtra("id" , String.valueOf(categoriesClass.getId()));
                intent.putExtra("name" , String.valueOf(categoriesClass.getName()));

                mainActivity.setIntent(intent);



            }

        });
        if (categoriesClass.getImage() !=null && categoriesClass.getImage().getSrc() != null){
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
            txtName = (TextView) itemView.findViewById(R.id.nameCategoryParent1);
            imgCategory = (ImageView) itemView.findViewById(R.id.imageCategoryParent1);
        }
    }


}
