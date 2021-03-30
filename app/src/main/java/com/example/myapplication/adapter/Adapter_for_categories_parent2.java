package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.categories;
import com.example.myapplication.R;

import java.util.ArrayList;

public class Adapter_for_categories_parent2 extends RecyclerView.Adapter<Adapter_for_categories_parent2.viewholder> {

    Context context;
    ArrayList<categories > arrayList ;

    public Adapter_for_categories_parent2(Context context, ArrayList<categories> arrayList) {
        this.context = context;
        this.arrayList = arrayList;




    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_for_parent2_categories, parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        categories categories = arrayList.get(position);

        holder.textView.setText(categories.getName());


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView textView;
        RecyclerView recyclerView;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textGroup2);
            recyclerView = itemView.findViewById(R.id.rvGroupParent2);



        }
    }
}
