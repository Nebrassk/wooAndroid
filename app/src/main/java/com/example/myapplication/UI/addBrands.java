package com.example.myapplication.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.UI.main.MainActivity;
import com.example.myapplication.callAPI.ClientApi;
import com.example.myapplication.databinding.ActivityAddBarndsBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addBrands extends AppCompatActivity {
    ActivityAddBarndsBinding binding;

    String name;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBarndsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("يتم رفع التصنيف");
        progressDialog.setMessage("يرجى الانتظار...");




        binding.btnAddBrand.setOnClickListener(view -> {
            progressDialog.show();
            callApiToAddBrand();
        });



    }

    void callApiToAddBrand()
    {


        name =binding.eTxtAddBrand.getText().toString().trim();

        ClientApi.getInstance().addBrand( name ).enqueue(new Callback<com.example.myapplication.Model.brands>() {
            @Override
            public void onResponse(Call<com.example.myapplication.Model.brands> call, Response<com.example.myapplication.Model.brands> response) {
                if (response.isSuccessful())
                {
                    Toast.makeText(addBrands.this, "تم اضافة البراند بنجاح", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    startActivity(new Intent(addBrands.this , MainActivity.class));

                }else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(addBrands.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();

                    }
            }

            @Override
            public void onFailure(Call<com.example.myapplication.Model.brands> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(addBrands.this,"T: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    ////outLine
}