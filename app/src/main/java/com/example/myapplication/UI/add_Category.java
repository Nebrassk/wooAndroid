package com.example.myapplication.UI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.Model.categories;
import com.example.myapplication.Model.image;
import com.example.myapplication.R;
import com.example.myapplication.UI.main.MainActivity;
import com.example.myapplication.callAPI.ClientApi;
import com.example.myapplication.databinding.ActivityAddCategoryBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class add_Category extends AppCompatActivity {
    ActivityAddCategoryBinding binding;

    //api
    String baseUrl;
    String consumer_key;
    String consumer_secret;

    String name;

    EditText category_name;
    Button btnSend;
    ImageView imgCategory;

    //for get image intent
    private final static int finalgetimageIntent = 1;
    Uri imageUri_Intent;
    String imageDaownload;

    //firebase upload image
    StorageReference storageReference;
   //for spinner1
    HashMap<Object, Object> hashMapObject = new HashMap<>();
    ArrayList< ArrayList<categories> > all_Get_Categories1 = new ArrayList<>();
    ArrayList<categories> get_Categories1 = new ArrayList<>();
    ArrayAdapter<categories> arrayAdapterSpinner1;
    //for spinner2
    ArrayList< ArrayList<categories> > all_Get_Categories2 = new ArrayList<>();
    ArrayList<categories> get_Categories2 = new ArrayList<>();
    ArrayAdapter<categories> arrayAdapterSpinner2;
    //for spinner3
    ArrayList< ArrayList<categories> > all_Get_Categories3 = new ArrayList<>();
    ArrayList<categories> get_Categories3 = new ArrayList<>();
    ArrayAdapter<categories> arrayAdapterSpinner3;

    //view Spinner
    categories categories1;
    categories categories2;
    categories categories3;
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    categories default_Name = new categories("اختر تصنيف");

    //progress
    ProgressBar progressBar ;
    ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        storageReference = FirebaseStorage.getInstance().getReference().child("Product Image");

        baseUrl = getIntent().getStringExtra("baseUrl");
        consumer_key = getIntent().getStringExtra("consumer_key");
        consumer_secret = getIntent().getStringExtra("consumer_secret");


        category_name = findViewById(R.id.categoryname);
        btnSend = findViewById(R.id.btnsend);
        imgCategory = findViewById(R.id.imgCategory);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        progressBar = findViewById(R.id.loadcategoriesinaddCategory);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("يتم رفع التصنيف");
        progressDialog.setMessage("يرجى الانتظار...");



        imgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, finalgetimageIntent);


            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!category_name.getText().toString().isEmpty()) {
                    name = category_name.getText().toString().trim();
                    if (baseUrl != null && imageUri_Intent != null) {
                        upload_img_Category();
                    }

                    progressDialog.show();
                    get_call_post();

                }








            }
        });






             for (int i = 1; i <= 10 ; i++ )
             {
                 get_Category_Parent_1(i);
             }

             spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
             {
                 @Override
                 public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                     if(adapterView.getItemAtPosition(i).equals(default_Name))
                     {}else {
                         progressBar.setVisibility(View.VISIBLE);
                          categories1 = (categories) spinner1.getSelectedItem();
                       //   get_Category_Parent_2(categories1.getId() );
                     }

                 }

                 @Override
                 public void onNothingSelected(AdapterView<?> adapterView) {

                 }
             });
             spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
             {
                 @Override
                 public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                     if(adapterView.getItemAtPosition(i).equals(default_Name))
                     {}else {
                         categories2 = (categories) spinner2.getSelectedItem();
                         progressBar.setVisibility(View.VISIBLE);
                         get_Category_Parent_3(categories2.getId() );
                     }
                 }

                 @Override
                 public void onNothingSelected(AdapterView<?> adapterView) {

                 }
             });
             spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
             {
                 @Override
                 public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                     if(adapterView.getItemAtPosition(i).equals(default_Name))
                     {}else {
                     categories3 = (categories) spinner3.getSelectedItem();

                     }
                 }

                 @Override
                 public void onNothingSelected(AdapterView<?> adapterView) {

                 }
             });








    }

    void upload_img_Category() {
        try {


            StorageReference imgPath = storageReference.child(imageUri_Intent.getLastPathSegment() + ".jpg");

            UploadTask uploadTask = imgPath.putFile(imageUri_Intent);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(add_Category.this, "Error upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(add_Category.this, "Successfully Upload Image", Toast.LENGTH_SHORT).show();


                    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                            if (!task.isSuccessful()) {
                                Toast.makeText(add_Category.this, "Error upload image" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                            return imgPath.getDownloadUrl();
                        }

                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {


                            imageDaownload = task.getResult().toString();
                            get_call_post();


                        }
                    });

                }
            });


        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == finalgetimageIntent && data != null && resultCode == Activity.RESULT_OK) {
            imageUri_Intent = data.getData();

            Picasso.get().load(String.valueOf(imageUri_Intent)).into(imgCategory);
        }
    }

    private void get_call_post()
    {

        HashMap<Object, Object> hashMap = new HashMap<>();
        if (imageDaownload != null)
        {
            image image = new image(imageDaownload);
            ArrayList<image> imageE = new ArrayList<>();
            imageE.add(image);
            hashMap.put("image", imageE);
        }else { Toast.makeText(this, "لم يتم رفع صورة للتصنيف", Toast.LENGTH_SHORT).show();  }


        if (spinner3.getSelectedItem()  != null) {
            if (!spinner3.getSelectedItem().equals(default_Name)) {
                hashMap.put("parent", categories3.getId());
            }}


        if (spinner2.getSelectedItem()  != null) {
            if (spinner3.getVisibility() != View.GONE){
                if (!spinner2.getSelectedItem().equals(default_Name)  &spinner3.getSelectedItem().equals(default_Name)) {
                    hashMap.put("parent", categories2.getId());
                }}}


        if (spinner1.getSelectedItem()  != null) {
            if (spinner3.getVisibility() != View.GONE & spinner2.getVisibility() != View.GONE){
                if (!spinner1.getSelectedItem().equals(default_Name) &spinner2.getSelectedItem().equals(default_Name) & spinner3.getSelectedItem().equals(default_Name)) {
                    hashMap.put("parent", categories1.getId());
                }}}


        hashMap.put("name", name);

            ClientApi.getInstance().post_Categories(hashMap).enqueue(new Callback<categories>() {
                @Override
                public void onResponse(Call<categories> call, Response<categories> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(add_Category.this, "Successfully Add Category", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent intent = new Intent(add_Category.this, MainActivity.class);
                        startActivity(intent);


                    } else {
                        try {
                            progressDialog.dismiss();
                            Toast.makeText(add_Category.this, "ERROR: " + response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<categories> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(add_Category.this, "Error call: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });



    }

    private void get_Category_Parent_1(int page)

    {
//    Single<ArrayList<categories>> single =    ClientApi.getInstance().parent_1_CategoriesCall(page, 0)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread());
//
//

//
//       .enqueue(new Callback<ArrayList<categories>>() {
//            @Override
//            public void onResponse(Call<ArrayList<categories>> call, Response<ArrayList<categories>> response)
//            {
//                if (response.isSuccessful())
//                {
//
//                    if (page ==1 ) {  get_Categories1.add(default_Name); }
//
//                    all_Get_Categories1.add(response.body());
//
//
//                    if (all_Get_Categories1.size() ==10)
//                    {
//                        for  (int ii = 0; ii < all_Get_Categories1.size() ; ii++ )
//                        {
//                            get_Categories1.addAll(all_Get_Categories1.get(ii));
//                        }
//                        progressBar.setVisibility(View.GONE);
//                        arrayAdapterSpinner1 = new ArrayAdapter<categories>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, get_Categories1);
//                        spinner1.setAdapter(arrayAdapterSpinner1);
//                        spinner1.setVisibility(View.VISIBLE);
//
//
//                    }
//
//
//                }
//                else { Toast.makeText(add_Category.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show(); }
//
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<categories>> call, Throwable t) {
//                Toast.makeText(add_Category.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });

    }

//    private void get_Category_Parent_2(int parent)
//    {
//        ClientApiApi.getInstance().parent_2_CategoriesCall(parent, 1).enqueue(new Callback<ArrayList<categories>>() {
//            @Override
//            public void onResponse(Call<ArrayList<categories>> call, Response<ArrayList<categories>> response)
//            {
//                if (response.isSuccessful())
//                {
//                    get_Categories2 = response.body();
//                    progressBar.setVisibility(View.GONE);
//                   if (response.body().size() != 0) {
//                       get_Categories2.add(categories1);
//                       get_Categories2.add(default_Name);
//                       arrayAdapterSpinner2 = new ArrayAdapter<categories>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, get_Categories2);
//                       spinner2.setAdapter(arrayAdapterSpinner2);
//                       spinner2.setSelection(get_Categories2.size() - 1);
//
//                       spinner2.setVisibility(View.VISIBLE);
//
//                   }
//
//                }
//                else {
//                    Toast.makeText(add_Category.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
//                btnSend.setText(response.errorBody().toString());}
//
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<categories>> call, Throwable t)
//            {
//                Toast.makeText(add_Category.this, t.getMessage(), Toast.LENGTH_SHORT).show();     }
//
//
//        });
//    }

    private void get_Category_Parent_3(int parent)
    {
//        ClientApiApi.getInstance().parent_3_CategoriesCall(parent).enqueue(new Callback<ArrayList<categories>>() {
//            @Override
//            public void onResponse(Call<ArrayList<categories>> call, Response<ArrayList<categories>> response)
//            {
//                if (response.isSuccessful())
//                {
//                    get_Categories3 = response.body();
//                    progressBar.setVisibility(View.GONE);
//
//                    if (response.body().size() != 0) {
//                        get_Categories3.add(categories2);
//                        get_Categories3.add(default_Name);
//                        arrayAdapterSpinner3 = new ArrayAdapter<categories>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, get_Categories3);
//                        spinner3.setAdapter(arrayAdapterSpinner3);
//                        spinner3.setSelection(get_Categories3.size() - 1);
//                        spinner3.setVisibility(View.VISIBLE);
//                    }
//
//                }
//                else {
//                    Toast.makeText(add_Category.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
//                    btnSend.setText(response.errorBody().toString());}
//
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<categories>> call, Throwable t)
//            {
//                Toast.makeText(add_Category.this, t.getMessage(), Toast.LENGTH_SHORT).show();     }
//
//
//        });
    }






}