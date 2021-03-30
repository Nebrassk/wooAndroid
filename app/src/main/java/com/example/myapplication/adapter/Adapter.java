package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Product;
import com.example.myapplication.R;
import com.example.myapplication.UI.product_page;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.Objects;


public class Adapter extends PagedListAdapter<Product, Adapter.viewholder> {


    //List<Product> productList;
    Context context;
    boolean isShimmer;
    Interface_getImage_Uri interface_getImage_uri;

    public Adapter(Context context) {
        super(diffCallback);
        this.context = context;
    }


    public void setData( boolean isShimmer, Interface_getImage_Uri interface_getImage_uri) {

        this.isShimmer = isShimmer;
        this.interface_getImage_uri = interface_getImage_uri;

        notifyDataSetChanged();


    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycelview_product, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {


        Product product = getItem(position);


        if (product.getStatus().equals("private")) {


        } else {
//            if (isShimmer) {
//              //  holder.shimmerFrameLayout.startShimmer();
//
//            } else {
//                holder.shimmerFrameLayout.stopShimmer();
//                holder.shimmerFrameLayout.setShimmer(null);

                holder.txtName.setBackground(null);
                holder.txtName.setText(product.getName());
                holder.txtPrice.setBackground(null);
                holder.txtPrice.setText(product.getPrice() + " Omr");
                holder.add_toCart_Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intenti = new Intent(context, product_page.class);
                        intenti.putExtra("product_name", product.getName());
                        intenti.putExtra("price", product.getPrice());
                        intenti.putExtra("id", String.valueOf(product.getId()));
                        intenti.putExtra("description", product.getDescription());
                        for (int i = 0 ; i < product.getImages().size() ;i++) {
                            if (!product.getImages().isEmpty() ) {
                                if (product.getImages().get(i).getSrc() != null) {
                                    intenti.putExtra("image"+i , product.getImages().get(i).getSrc());
                                }
                            }
                        }
                        interface_getImage_uri.set_Intent(intenti);
                        
                    }
                });


                if (!product.getImages().isEmpty() && product.getImages().get(0).getSrc() != null) {
                    holder.img.setBackground(null);

                    interface_getImage_uri.getUri(holder, Uri.parse(product.getImages().get(0).getSrc()));


                }
            //}
        }

    }

//    @Override
//    public int getItemCount() {
//        return isShimmer ? 5 : productList.size();
//    }


    public class viewholder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtPrice;
        public ImageView img;
        ShimmerFrameLayout shimmerFrameLayout;
        ImageButton add_toCart_Btn;


        public viewholder(@NonNull View itemView) {
            super(itemView);
           // shimmerFrameLayout = itemView.findViewById(R.id.shimmer);
            txtName = itemView.findViewById(R.id.product_name1);
            txtPrice = itemView.findViewById(R.id.price);
            img = itemView.findViewById(R.id.image);
            add_toCart_Btn = itemView.findViewById(R.id.btnAddtoCart);

        }
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
                    //return oldItem.equals(newItem);
                }
            };

    public  interface Interface_getImage_Uri {
        void getUri(viewholder viewholder, Uri uri);
        void set_Intent(Intent intent);
        


    }


}
