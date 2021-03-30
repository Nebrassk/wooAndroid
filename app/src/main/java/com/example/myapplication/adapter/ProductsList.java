package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Product;
import com.example.myapplication.R;

import java.util.Objects;

public class ProductsList extends PagedListAdapter<Product, ProductsList.ItemViewHolder> {

    Context context;

    protected ProductsList(Context context) {
        super(diffCallback);
        this.context =context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycelview_product, parent, false);
        return new ProductsList.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position)
    {

    }


    private static DiffUtil.ItemCallback<Product> diffCallback =
            new DiffUtil.ItemCallback<Product>() {
                @Override
                public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
                    return oldItem.getId() == newItem.getId();
                }


                @Override
                public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
                    return Objects.equals(oldItem, newItem);
                }
            };


    static class ItemViewHolder extends RecyclerView.ViewHolder{
        public ItemViewHolder(@NonNull View itemView)
        {
            super(itemView);

        }
    }

}
