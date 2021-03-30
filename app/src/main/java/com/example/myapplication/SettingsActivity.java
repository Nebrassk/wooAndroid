package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.UI.main.MainActivity;
import com.example.myapplication.databinding.SettingsActivityBinding;


public class SettingsActivity extends AppCompatActivity {
    SettingsActivityBinding binding;
    //api
    String baseUrl;
    ;
    String consumer_key;
    String consumer_secret;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SettingsActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //get_Exception_Call
        if (getIntent().getStringExtra("API Exception") != null) {
            Toast.makeText(this, "Please add Correct Api: " + getIntent().getStringExtra("API Exception"), Toast.LENGTH_SHORT).show();
        }

        binding.update.setOnClickListener(view -> {
            Intent set_Authorize_Date = new Intent(SettingsActivity.this, MainActivity.class);
            baseUrl = binding.baseUri.getText().toString().trim();
            consumer_key = binding.consumerKey.getText().toString().trim();
            consumer_secret = binding.consumerSecret.getText().toString().trim();

            set_Authorize_Date.putExtra("baseUrl", baseUrl);
            set_Authorize_Date.putExtra("consumer_key", consumer_key);
            set_Authorize_Date.putExtra("consumer_secret", consumer_secret);

            startActivity(set_Authorize_Date);


        });


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }


    }


    void call_api_check() {


    }


}