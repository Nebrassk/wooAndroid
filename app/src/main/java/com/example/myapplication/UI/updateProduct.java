package com.example.myapplication.UI;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Model.Product;
import com.example.myapplication.UI.main.MainActivity;
import com.example.myapplication.callAPI.ClientApi;
import com.example.myapplication.databinding.ActivityUpdateProductBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class updateProduct extends AppCompatActivity {
    ActivityUpdateProductBinding binding;

    int id;
    String product_name ;
    String regular_price ;
    String  description ;


    ClientApi clientApi = new ClientApi();

    List<com.example.myapplication.Model.images> images;

    StorageReference storageReference;
    int intent_get_img;
    String imageDaownload;
    Uri uriimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // set folder for image in firebase
        storageReference = FirebaseStorage.getInstance().getReference().child("Product Image");






        binding.imageViewUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent getimg = new Intent(Intent.ACTION_GET_CONTENT);
                getimg.setType("image/*");
                startActivityForResult(getimg, intent_get_img);

            }
        });


       binding.send.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               if (getIntent().getStringExtra("id") != null) {
                   id = Integer.parseInt(getIntent().getStringExtra("id"));


                   product_name = binding.productname1.getText().toString().trim();
                   regular_price = binding.pricee.getText().toString().trim();
                   description = binding.descriptionn.getText().toString().trim();



                   if ( !product_name.isEmpty()    )
                   {
                       putAPI_Name();

                   }
                   if ( !description.isEmpty()){
                     //  putAPI_description();
                   }
                   if (!regular_price.isEmpty()){
                     //  putAPI_Price();
                   }
                   if ( imageDaownload != null){

                     //  putAPI_images();
                   }
                    else {
                       Toast.makeText(updateProduct.this, "يرجى اضافة بيانات", Toast.LENGTH_SHORT).show();
                   }
               }

           }
       });



    }




    void putAPI_Name(){

        clientApi.updata_ProductName(id, product_name).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                //if1
                if (response.isSuccessful() ){

                    Product product = response.body();
                    Toast.makeText(updateProduct.this, "تم بنجاح تحديث المنتج", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(updateProduct.this, MainActivity.class );
                    startActivity(intent);


                }
                //if1
                else {

                    Toast.makeText(updateProduct.this, "خطاء في الاستجابة مع الخادم", Toast.LENGTH_SHORT).show();
                   binding.send.setText(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });


    }


//
//    void putAPI_Price() {
//
//        clientApi.updata_ProductPrice(id, regular_price).enqueue(new Callback<Product>() {
//            @Override
//            public void onResponse(Call<Product> call, Response<Product> response) {
//                //if1
//                if (response.isSuccessful()) {
//
//                    Product product = response.body();
//                    Toast.makeText(updateProduct.this, "تم بنجاح تحديث المنتج", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(updateProduct.this, MainActivity.class);
//                    startActivity(intent);
//
//
//                }
//                //if1
//                else {
//
//                    Toast.makeText(updateProduct.this, "خطاء في الاستجابة مع الخادم", Toast.LENGTH_SHORT).show();
//                    binding.send.setText(response.toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Product> call, Throwable t) {
//
//            }
//        });
//    }
//
//
//    void putAPI_description() {
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://mysisshops.com")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//
//        interfaceAPI interfaceAPI2 = retrofit.create(com.example.myapplication.callAPI.interfaceAPI.class);
//
//
//
//        Call<Product> putProduct = interfaceAPI2.updata_Productdescription(id, description);
//
//        putProduct.enqueue(new Callback<Product>() {
//            @Override
//            public void onResponse(Call<Product> call, Response<Product> response) {
//                //if1
//                if (response.isSuccessful()) {
//
//                    Product product = response.body();
//                    Toast.makeText(updateProduct.this, "تم بنجاح تحديث المنتج", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(updateProduct.this, MainActivity.class);
//                    startActivity(intent);
//
//
//                }
//                //if1
//                else {
//
//                    Toast.makeText(updateProduct.this, "خطاء في الاستجابة مع الخادم", Toast.LENGTH_SHORT).show();
//                    binding.send.setText(response.toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Product> call, Throwable t) {
//
//            }
//        });
//    }
//
//
//    void putAPI_images() {
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://mysisshops.com")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//
//        interfaceAPI interfaceAPI2 = retrofit.create(com.example.myapplication.callAPI.interfaceAPI.class);
//
//        images images = new images(imageDaownload);
//        ArrayList<images> imagesArrayList = new ArrayList<>();
//        imagesArrayList.add(images);
//
//
//        Call<Product> putProduct = interfaceAPI2.updata_ProductImage(id, imagesArrayList);
//
//        putProduct.enqueue(new Callback<Product>() {
//            @Override
//            public void onResponse(Call<Product> call, Response<Product> response) {
//                //if1
//                if (response.isSuccessful()) {
//
//                    Product product = response.body();
//                    Toast.makeText(updateProduct.this, "تم بنجاح تحديث المنتج", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(updateProduct.this, MainActivity.class);
//                    startActivity(intent);
//
//
//                }
//                //if1
//                else {
//
//                    Toast.makeText(updateProduct.this, "خطاء في الاستجابة مع الخادم", Toast.LENGTH_SHORT).show();
//                    binding.send.setText(response.toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Product> call, Throwable t) {
//
//            }
//        });
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {


            if (requestCode == intent_get_img && data != null && resultCode == Activity.RESULT_OK) {

                uriimg = data.getData();
                Picasso.get().load(uriimg).into(binding.imageViewUpdateProduct);

                //upload and get uri image before send product
                upload_image();


            }

        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void upload_image() {
        try {


            StorageReference imgPath = storageReference.child(uriimg.getLastPathSegment() + ".jpg");

            UploadTask uploadTask = imgPath.putFile(uriimg);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(updateProduct.this, "Error upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(updateProduct.this, "تم رفع الصورة بنجاح", Toast.LENGTH_SHORT).show();


                    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                            if (!task.isSuccessful()) {
                                Toast.makeText(updateProduct.this, "Error upload image" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                            return imgPath.getDownloadUrl();
                        }

                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {


                            imageDaownload = task.getResult().toString();



                        }
                    });

                }
            });


        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }




}