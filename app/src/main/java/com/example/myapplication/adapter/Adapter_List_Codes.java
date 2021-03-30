package com.example.myapplication.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Coupons;
import com.example.myapplication.R;

import java.util.ArrayList;


public class Adapter_List_Codes  extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private final int TYPE_LOADING = 0;
    private final int TYPE_ITEM = 1;


    ArrayList<Coupons> couponsArrayList = new ArrayList<>();
    Context context;

    public void setDataAdapter(ArrayList<Coupons> couponsArrayList, Context context) {
        this.couponsArrayList = couponsArrayList;
        this.context = context;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (TYPE_ITEM == viewType) {
            View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.codes_list, parent, false);
            return new viewholder(view);
        }else {
            View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading, parent, false);
            return new viewholderNull(view);
        }


    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (couponsArrayList != null && holder instanceof viewholder) {
            Coupons coupons = couponsArrayList.get(position);
            ((viewholder) holder).txtView.setText(coupons.getCode());
            ((viewholder) holder).txtView.setOnClickListener(view -> {
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData cData = ClipData.newPlainText("code", coupons.getCode());
                cm.setPrimaryClip(cData);
                Toast.makeText(context, "تم النسخ", Toast.LENGTH_SHORT).show();
            });
            ((viewholder) holder).btnCopy.setOnClickListener(view -> {
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData cData = ClipData.newPlainText("code", coupons.getCode());
                cm.setPrimaryClip(cData);
                Toast.makeText(context, "تم النسخ", Toast.LENGTH_SHORT).show();
            });
            ((viewholder) holder).priceCodesListt.setText(coupons.getAmount());
            ((viewholder) holder).priceCodesListt.setOnClickListener(view -> {
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData cData = ClipData.newPlainText("code", coupons.getCode());
                cm.setPrimaryClip(cData);
                Toast.makeText(context, "تم النسخ", Toast.LENGTH_SHORT).show();
            });

        }
    }

    @Override
    public int getItemCount() {
        return couponsArrayList == null ? 0 :  couponsArrayList.size() ;
    }


    @Override
    public int getItemViewType(int position) {

            return couponsArrayList.get(position) == null ? TYPE_LOADING : TYPE_ITEM;

        }





    public static class  viewholder extends RecyclerView.ViewHolder
    {
        TextView txtView;
        TextView priceCodesListt;
        Button btnCopy;
        Button btnDelete;

        public viewholder(@NonNull View itemView)
        {
            super(itemView);
            txtView = itemView.findViewById(R.id.txtCodesList);
            priceCodesListt = itemView.findViewById(R.id.priceCodesListt);
            btnCopy =  itemView.findViewById(R.id.btnCodesList);
            btnDelete =  itemView.findViewById(R.id.btnCodesDelete);
        }
    }

    public static class  viewholderNull extends RecyclerView.ViewHolder {

        public viewholderNull(@NonNull View itemView) {
            super(itemView);
        }
    }










}


