package com.example.myapplication.UI;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.Model.Product;
import com.example.myapplication.R;
import com.example.myapplication.UI.main.MainActivity;
import com.example.myapplication.callAPI.ClientApi;
import com.example.myapplication.databinding.ActivityProductPageBinding;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class product_page extends AppCompatActivity {


    ActivityProductPageBinding binding;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.productNameinPage.setText(getIntent().getStringExtra("product_name"));
        binding.price.setText(getIntent().getStringExtra("price"));
        binding.description.setText(getIntent().getStringExtra("description"));
        binding.productNameinPage.setBackground(null);



            if(getIntent().getStringExtra("image"+0) != null)
            Glide.with(this).load(Uri.parse(getIntent().getStringExtra("image"+0))).into(binding.productImg);
            Glide.with(this).load(Uri.parse(getIntent().getStringExtra("image"+0))).into(binding.image1);
            if(getIntent().getStringExtra("image"+1) != null)
            Glide.with(this).load(Uri.parse(getIntent().getStringExtra("image"+1))).into(binding.image2);
            if(getIntent().getStringExtra("image"+2) != null)
            Glide.with(this).load(Uri.parse(getIntent().getStringExtra("image"+2))).into(binding.image3);
            if(getIntent().getStringExtra("image"+3) != null)
            Glide.with(this).load(Uri.parse(getIntent().getStringExtra("image"+3))).into(binding.image4);
            if(getIntent().getStringExtra("image"+4) != null)
            Glide.with(this).load(Uri.parse(getIntent().getStringExtra("image"+4))).into(binding.image5);




        id =Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("id")));




        binding.updateD.setOnClickListener(view -> {

            Intent intent = new Intent(product_page.this, updateProduct.class);
            intent.putExtra("id" ,id );
            startActivity(intent);

        });
        binding.btnDeleteProduct.setOnClickListener(view -> {


            AlertDialog alertDialog = new AlertDialog.Builder(product_page.this)
                    .setTitle(R.string.delete)
                    .setMessage(R.string.Do_you_want_to_delete_this_product)
                    .setPositiveButton(R.string.yes, (dialogInterface, i) -> callApi_to_Delete())
                    .setNegativeButton(R.string.cancel, (dialogInterface, i) -> dialogInterface.dismiss())
                    .create();

            alertDialog.show();




        });


    }

    void callApi_to_Delete()
    {

        AlertDialog alertDialog2 = new AlertDialog.Builder(product_page.this)
                .setTitle(R.string.delete)
                .setMessage("يتم حذف المنتج...")
                .create();

        alertDialog2.show();

       ClientApi.getInstance().delete_Product(id, true)
        .enqueue(new Callback<Product>() {
            @Override
            public void onResponse(@NotNull Call<Product> call, @NotNull Response<Product> response) {
                if (response.isSuccessful())
                {
                    Toast.makeText(product_page.this, "تم حذف المنتج بنجاح", Toast.LENGTH_SHORT).show();
                    alertDialog2.dismiss();
                    startActivity(new Intent(product_page.this, MainActivity.class));

                }else
                {
                    assert response.errorBody() != null;
                    Toast.makeText(product_page.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<Product> call, @NotNull Throwable t) {

            }
        });

    }



}