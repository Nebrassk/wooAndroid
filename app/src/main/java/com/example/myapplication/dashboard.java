package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.UI.main.MainActivity;
import com.example.myapplication.UI.categoriesList;
import com.example.myapplication.databinding.ActivityDashboredBinding;

public class dashboard extends AppCompatActivity {
    ActivityDashboredBinding binding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityDashboredBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.productLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(dashboard.this, MainActivity.class));

            }
        });
        binding.categoriesLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dashboard.this, categoriesList.class));

            }
        });




    }





}