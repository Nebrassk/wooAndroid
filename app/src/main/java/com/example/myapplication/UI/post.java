package com.example.myapplication.UI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.Model.Product2;
import com.example.myapplication.Model.categories;
import com.example.myapplication.Model.images;
import com.example.myapplication.R;
import com.example.myapplication.UI.main.LiveData;
import com.example.myapplication.UI.main.MainActivity;
import com.example.myapplication.adapter.Adapter_for_categories_parent1;
import com.example.myapplication.callAPI.ClientApi;
import com.example.myapplication.databinding.ActivityPostBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class post  extends AppCompatActivity {

    //ActivityPostBinding
    ActivityPostBinding binding;
    private final static int intent_get_img = 1;

    private static final String TAG = "post";


    private String name = "name";
    private String regular_price = "regular_price";
    private String imagesString = "images";
    private String categoriesString = "categories";
    private images images;


    private ArrayAdapter<categories> categoriesArrayAdapter;
    private ProgressDialog progressDialog;

//    //api
//    String baseUrl;
//    String consumer_key;
//    String consumer_secret;

    String productName;
    String description;
    String price;

    String[] array_Category_Strings;
    boolean[] selectedCategory;
    boolean[] selectedsub_sub_Category;
    String[] array_sub_Sub_Category_Strings;
    ArrayList<categories> subSubCategoriesArrayList;
    ArrayList<Integer> idSubCategory_Selected = new ArrayList<>();
    ArrayList<Integer> categorylistInt;
    ArrayList<Integer> categorysub_sublistInt;


    AlertDialog.Builder alertDialog;

    ArrayList<String> imageDaownload = new ArrayList<>();
    ArrayList<Uri> uriImg = new ArrayList<>();
    ArrayList<categories> subCategoriesArrayList = new ArrayList<>();
    ArrayList<ArrayList<categories>> allSubCategoriesArrayList = new ArrayList<>();


    StorageReference storageReference;

    /////////
    ArrayList<categories> getCategoriesArrayListPage = new ArrayList<>();
    Adapter_for_categories_parent1 adapter_for_categories_parent1;
    ArrayList<ArrayList<categories>> getCategoriesArrayListALLPage = new ArrayList<>();
    int pagee = 1;


    /////brand



    ArrayList<Integer> arrayListIntgers;
    boolean[] booleanss;
    String[] stringsBrand;
    AlertDialog.Builder alertDialogBuilder;
    ArrayList<Integer> idSubBrand_Selected = new ArrayList<>();
    int count_Brands_int;

    //  Editor editor;

    //mvvm + live data and Rxjava
    LiveData liveData;
    ClientApi clientApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityPostBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        alertDialog = new AlertDialog.Builder(post.this);
        binding.txtSubCategories.setVisibility(View.GONE);

        clientApi = new ClientApi();


                ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // inshize live data class
        liveData = new ViewModelProvider(this).get(LiveData.class);
        liveData.setContext(this);


//                //get api authentication from intent
//        baseUrl = getIntent().getStringExtra("baseUrl");
//        consumer_key = getIntent().getStringExtra("consumer_key");
//        consumer_secret = getIntent().getStringExtra("consumer_secret");


        //set progress bar
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getResources().getString(R.string.loading));
        progressDialog.setMessage(getResources().getString(R.string.progress_upload_product));


        // set folder for image in firebase
        storageReference = FirebaseStorage.getInstance().getReference().child("Product Image");

 ///<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<On click>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //go to add coupon Activity
        binding.addCode.setOnClickListener(view ->
        {
            startActivity(new Intent(this, Add_Code.class));
        });
        //to get image from gallery
        binding.imageView.setOnClickListener(view -> {

            Intent getImg = new Intent(Intent.ACTION_GET_CONTENT);
            getImg.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            getImg.setType("image/* video/*");
            startActivityForResult(Intent.createChooser(getImg, "حدد صور المنتج"), intent_get_img);

        });
        //send product to woocommerce
        binding.send.setOnClickListener((View.OnClickListener) view -> {

            if (imageDaownload != null) {
                progressDialog.show();
                postproduct();

            } else Toast.makeText(post.this, "يرجى اضافة صوره", Toast.LENGTH_SHORT).show();
        });
        //go to add category
        binding.addCategory.setOnClickListener(view -> {
            Intent intent = new Intent(post.this, add_Category.class);
//            intent.putExtra("baseUrl", baseUrl);
//            intent.putExtra("consumer_key", consumer_key);
//            intent.putExtra("consumer_secret", consumer_secret);
            startActivity(intent);
        });
        //go to add brand
        binding.addBrand.setOnClickListener(view -> {
            startActivity(new Intent(post.this, addBrands.class));
        }) ;
        //show brands list
        binding.txtBrands.setOnClickListener(view -> {
            if (stringsBrand != null) {
                alertDialogBuilder.show();
            }
        });
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<On click>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


        //set data for call api and  send data to live data
        liveData.get_All_Categories(pagee, post.this);
        liveData.set_All_Brands(pagee );
        liveData.setSubscribe_Barnds();
        liveData.setSubscribe_categories1();


        // get  all brands list
        liveData.mutableLiveData_get_All_Brands.observe(this, brands -> {


            count_Brands_int++;


            Log.d(TAG, "onCreate: post brands size" +String.valueOf(brands.size()));


            if (brands.size()> 0) {
                binding.txtBrands.setVisibility(View.VISIBLE);
                Collections.sort(brands, (t1, t2) -> t1.getName().compareTo(t2.getName()));


                booleanss = new boolean[brands.size()];
                stringsBrand = new String[brands.size()];
                arrayListIntgers = new ArrayList<>();

                for (int i = 0; i < brands.size(); i++) {
                    stringsBrand[i] = brands.get(i).getName();


                }

                alertDialogBuilder = new AlertDialog.Builder(post.this);
                alertDialogBuilder.setTitle("اختر براند");

                alertDialogBuilder.setMultiChoiceItems(stringsBrand, booleanss, (dialogInterface, position, isChecked) -> {

                    if (isChecked) {
                        if (!arrayListIntgers.contains(position)) {
                            arrayListIntgers.add(position);
                        }

                    } else {
                        arrayListIntgers.remove(position);
                    }

                });


                alertDialogBuilder.setPositiveButton(getResources().getString(R.string.add), (dialogInterface, i) -> {

                    StringBuilder stringBuilderr = new StringBuilder();
                    for (int w = 0; w < arrayListIntgers.size(); w++) {
                        stringBuilderr.append(stringsBrand[arrayListIntgers.get(w)]);


                        ///  here we get id for selected category name in sub sub categories
                        for (int q = 0; q < brands.size(); q++) {
                            if (brands.get(q).getName() == stringsBrand[arrayListIntgers.get(w)]) {

                                //here we add the id for selected name
                                int ids = brands.get(q).getId();


                                idSubBrand_Selected.add(ids);

                            }
                        }
                        //show category name in text field
                        if (w != arrayListIntgers.size() - 1) {
                            stringBuilderr.append(", ");

                        }
                    }
                    binding.txtBrands.setText(stringBuilderr.toString());

                });
                alertDialogBuilder.setNegativeButton(getResources().getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());

                alertDialogBuilder.setNeutralButton("ازالة الكل", (dialogInterface, i) -> {
                    for (int w = 0; w < brands.size(); w++) {
                        booleanss[w] = false;
                        arrayListIntgers.clear();

                    }

                });
            }
        });


        // get  all categories list parent 0
        liveData.mutableLiveData_categories_Parent1.observe(this, categories -> {


            getCategoriesArrayListPage = categories;
            getCategoriesArrayListALLPage.add(getCategoriesArrayListPage);

            Log.d(TAG, "onCreate: categories size in post" + String.valueOf(categories.size()));




                //   setAdapter(getCategoriesArrayListALLPage);



                binding.go.setOnClickListener(view -> {

                    Intent intent = new Intent(post.this, categoriesList.class);
//                    intent.putExtra("consumer_key", consumer_key);
//                    intent.putExtra("consumer_secret", consumer_secret);
//                    intent.putExtra("baseUrl", baseUrl);
                    startActivity(intent);
                });


                if (categories.size() > 0) {
                    //set Spinner
                    categoriesArrayAdapter = new ArrayAdapter<categories>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, categories);
                    binding.categoriesSpinner.setAdapter(categoriesArrayAdapter);

                }
            });



        //get select sub category
        binding.categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                binding.progressBarCategory.setVisibility(View.VISIBLE);
                binding.progressBarCategory.setProgress(70);

                int id = (Objects.requireNonNull((categories) binding.categoriesSpinner.getSelectedItem()).getId());
                liveData.set_Parent_2_CategoriesCall(id, 1, post.this);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        liveData.mutableLiveData_categories_Parent2.observe(this, categories -> {


            if (categories.size() > 0) {

                allSubCategoriesArrayList.add(categories);


                subCategoriesArrayList.addAll(categories);
                Collections.sort(subCategoriesArrayList, (t1, t2) -> t1.getName().compareTo(t2.getName()));

                binding.progressBarCategory.setVisibility(View.GONE);
                binding.subCategoriesSpinner.setVisibility(View.VISIBLE);
                binding.subCategoriesSpinner.setAdapter(new ArrayAdapter<categories>(post.this, R.layout.support_simple_spinner_dropdown_item, subCategoriesArrayList));
            } else {

                binding.progressBarCategory.setVisibility(View.GONE);
                binding.subCategoriesSpinner.setVisibility(View.GONE);
            }


        });

        // get select sub sub category
        binding.subCategoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.progressBarCategory.setVisibility(View.VISIBLE);

                binding.progressBarCategory.setProgress(70);
                //set data on liveData
                liveData.set_Parent_3_CategoriesCall((Objects.requireNonNull((categories) binding.subCategoriesSpinner.getSelectedItem()).getId()), post.this);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        liveData.mutableLiveData_categories_Parent3.observe(this, categories -> {

            if (categories.size() != 0) {

                subSubCategoriesArrayList = categories;


                binding.progressBarCategory.setVisibility(View.GONE);
                binding.txtSubCategories.setVisibility(View.VISIBLE);


                //Dialog
                selectedsub_sub_Category = new boolean[subSubCategoriesArrayList.size()];
                categorysub_sublistInt = new ArrayList<>();
                array_sub_Sub_Category_Strings = new String[subSubCategoriesArrayList.size()];

                for (int f = 0; f < subSubCategoriesArrayList.size(); f++) {
                    array_sub_Sub_Category_Strings[f] = subSubCategoriesArrayList.get(f).getName();
                }


                //set Dailog
                binding.txtSubCategories.setOnClickListener(view -> {


                    alertDialog.setTitle("اختر تصنيف");
                    alertDialog.setCancelable(false);
                    alertDialog.setMultiChoiceItems(array_sub_Sub_Category_Strings, selectedsub_sub_Category, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position, boolean Ischecked) {

                            if (Ischecked) {
                                if (!categorysub_sublistInt.contains(position)) {

                                    categorysub_sublistInt.add(position);

                                } else {

                                    categorysub_sublistInt.remove(position);

                                }
                            }
                        }
                    });

                    alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            StringBuilder stringBuilder = new StringBuilder();
                            for (int j = 0; j < categorysub_sublistInt.size(); j++) {

                                stringBuilder.append(array_sub_Sub_Category_Strings[categorysub_sublistInt.get(j)]);


                                ///  here we get id for selected category name in sub sub categories
                                for (int w = 0; w < subSubCategoriesArrayList.size(); w++) {
                                    if (subSubCategoriesArrayList.get(w).getName() == array_sub_Sub_Category_Strings[categorysub_sublistInt.get(j)]) {
                                        //here we add the id for selected name


                                        idSubCategory_Selected.add(subSubCategoriesArrayList.get(w).getId());

                                    }
                                }
                                //show category name in text field
                                if (j != categorysub_sublistInt.size() - 1) {
                                    stringBuilder.append(", ");

                                }
                            }

                            binding.txtSubCategories.setText(stringBuilder.toString());
                        }
                    });
                    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();

                        }
                    });
                    alertDialog.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            for (int d = 0; d < selectedsub_sub_Category.length; d++) {
                                selectedsub_sub_Category[d] = false;
                                categorysub_sublistInt.clear();
                                binding.txtSubCategories.setText(" لم يتم الاختيار");
                            }

                        }
                    });
                    alertDialog.show();

                });

            } else {
                binding.progressBarCategory.setVisibility(View.GONE);
                binding.txtSubCategories.setVisibility(View.GONE);
            }
        });





    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {


            if (requestCode == intent_get_img && data != null && resultCode == Activity.RESULT_OK) {
                uriImg = new ArrayList<>();

                if (data.getClipData() != null) {

                    ClipData clipData = data.getClipData();

                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        uriImg.add(item.getUri());
                    }

                    Picasso.get().load(uriImg.get(0)).into(binding.imageView);

                    //upload and get uri image before send product
                    upload_image();
                } else {
                    uriImg.add(data.getData());
                    Picasso.get().load(uriImg.get(0)).into(binding.imageView);

                    //upload and get uri image before send product
                    upload_image();
                }
            }


        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void upload_image() {
        try {


            for (int i = 0; i < uriImg.size(); i++) {
                StorageReference imgPath = storageReference.child(uriImg.get(i).getLastPathSegment() + ".jpg");

                UploadTask uploadTask = imgPath.putFile(uriImg.get(i));


                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(post.this, "Error upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                                if (!task.isSuccessful()) {
                                    Toast.makeText(post.this, "Error upload image" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }
                                return imgPath.getDownloadUrl();
                            }

                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {


                                imageDaownload.add(task.getResult().toString());

                                Toast.makeText(post.this, "تم تحميل" + uriImg.size() + "صوؤة", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                });

            }
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    private void Get_Categories() {
//                public void onResponse(Call<ArrayList<com.example.myapplication.Model.categories>> call, Response<ArrayList<com.example.myapplication.Model.categories>> response) {
//                    if (response.isSuccessful()) {
//                        categoriesArrayList = response.body();
//                        // categoriesArrayList = categoriesArrayList2.get(0);
////
//////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
////                        for (int o = 0 ; o <6 ;o++){
////                            reloadgetCategory(o);
////                        }
//
////                        categoriesArrayAdapter = new ArrayAdapter<categories>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, categoriesArrayList);
////                        binding.categoriesSpinner.setAdapter(categoriesArrayAdapter);
//
//
//                    } else {
//                        Toast.makeText(post.this, "ERROR: API GET Categories", Toast.LENGTH_SHORT).show();
//                    }
//                }


    }

    private void postproduct() {

        try {


            HashMap<String, Object> map = new HashMap<>();

            productName = binding.productName.getText().toString().trim();
            price = binding.productName.getText().toString().trim();
            description = binding.description.getText().toString().trim();


            if (uriImg != null && !productName.isEmpty() && !description.isEmpty() && !price.isEmpty() && binding.categoriesSpinner.getSelectedItem() != null){

                if (imageDaownload.size() == 0) {
                    Toast.makeText(this, R.string.please_add_image, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else {
                map.put(name, binding.productName.getText().toString().trim());
                map.put(regular_price, binding.price.getText().toString().trim());
                ArrayList<images> imagesList = new ArrayList<com.example.myapplication.Model.images>();
                for (int w = 0; w < imageDaownload.size(); w++) {
                    images = new images(imageDaownload.get(w));
                    imagesList.add(images);
                }
                map.put(imagesString, imagesList);

                ArrayList<Integer> brandArrayList;


                brandArrayList = new ArrayList<>();

                for (int w = 0; w < idSubBrand_Selected.size(); w++) {
                    brandArrayList.add(idSubBrand_Selected.get(w));
                }
                map.put("brands", idSubBrand_Selected);


                ArrayList<categories> categoriesArrayList;


                if (idSubCategory_Selected.size() != 0) {

                    categoriesArrayList = new ArrayList<>();
                    for (int i = 0; i < categorysub_sublistInt.size(); i++) {
                        categories categoriess = new categories(idSubCategory_Selected.get(i));
                        if (categoriess.getId() != 0) {
                            categoriesArrayList.add(categoriess);
                        }
                    }
                    map.put(categoriesString, categoriesArrayList);

                }
                if (binding.subCategoriesSpinner.getVisibility() == View.VISIBLE
                        && binding.txtSubCategories.getVisibility() == View.GONE) {

                    categories idcategory = new categories(((categories) binding.subCategoriesSpinner.getSelectedItem()).getId());

                    categoriesArrayList = new ArrayList<>();
                    categoriesArrayList.add(idcategory);
                    map.put(categoriesString, categoriesArrayList);
                }
                if (binding.subCategoriesSpinner.getVisibility() == View.VISIBLE
                        && idSubCategory_Selected.size() == 0) {

                    categories idcategory = new categories(((categories) binding.subCategoriesSpinner.getSelectedItem()).getId());

                    categoriesArrayList = new ArrayList<>();
                    categoriesArrayList.add(idcategory);
                    map.put(categoriesString, categoriesArrayList);
                }
                if ((binding.subCategoriesSpinner.getVisibility() == View.GONE
                        && binding.txtSubCategories.getVisibility() == View.GONE)) {

                    categories idcategory = new categories(((categories) binding.categoriesSpinner.getSelectedItem()).getId());

                    categoriesArrayList = new ArrayList<>();
                    categoriesArrayList.add(idcategory);
                    map.put(categoriesString, categoriesArrayList);
                }


                map.put("description", description);


                clientApi.PRODUCT_CALL_POST(map).enqueue(new Callback<Product2>() {
                    @Override
                    public void onResponse(Call<Product2> call, Response<Product2> response) {
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
                            startActivity(new Intent(post.this, MainActivity.class));


                        } else {
                            Toast.makeText(post.this, "API Error Post" + response.errorBody(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call<Product2> call, Throwable t) {
                        Toast.makeText(post.this, "API Erorr: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        binding.send.setText(t.getMessage());
                        Log.getStackTraceString(t);
                        progressDialog.dismiss();

                        startActivity(new Intent(post.this, MainActivity.class));


                    }
                });
                }
            } else {

                Toast.makeText(this, R.string.please_add_info, Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }


    private void get_sub_Category() {


//
//                        //dilog
//                        selectedCategory = new boolean[subCategoriesArrayList.size()];
//                        categorylistInt = new ArrayList<>();
//                        array_Category_Strings = new String[subCategoriesArrayList.size()];

//                        ArrayList<String> subCategories = new ArrayList<>();
//
//                        for (int f = 0; f < subCategoriesArrayList.size(); f++) {
//                       //     array_Category_Strings [f]= subCategoriesArrayList.get(f).getName();
//                            subCategories.add(subCategoriesArrayList.get(f).getName());
//
//                        }


        //   setSpinner


//                        try {

        //                           binding.txtSubCategories.setOnClickListener(view -> {
//
//
//                                AlertDialog.setTitle("اختر تصنيف");
//                                AlertDialog.setCancelable(false);
//                                AlertDialog.setMultiChoiceItems(array_Category_Strings, selectedCategory, new DialogInterface.OnMultiChoiceClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int position, boolean Ischecked) {
//
//                                   if (Ischecked) {
//                                           if (!categorylistInt.contains(position)) {
//
//                                           categorylistInt.add(position);
//
//                                           }else {
//
//                                                   categorylistInt.remove(position);
//
//                                              }
//                                   }
//                                      }
//                                   });
//
//                                AlertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                                        StringBuilder stringBuilder = new StringBuilder();
//                                        for (int j = 0; j < categorylistInt.size(); j++) {
//
//                                            stringBuilder.append(array_Category_Strings[categorylistInt.get(j)]);
//
//
//                                             ///  here we get id selected name in sub categories
//                                            for (int w = 0; w < subCategoriesArrayList.size(); w++) {
//                                                if (subCategoriesArrayList.get(w).getName() == array_Category_Strings[categorylistInt.get(j)]) {
//                                                    //here we add the id for selected name
//                                                    int ids = subCategoriesArrayList.get(w).getId();
//
//                                                    idSubCategory_Selected.add(ids);
//
//                                                }
//                                            }
//                                            //show category name in text field
//                                            if (j != categorylistInt.size() - 1) {
//                                                stringBuilder.append(", ");
//
//                                            }
//                                        }
//
//                                        binding.txtSubCategories.setText(stringBuilder.toString());
//                                    }
//                                });
//                                AlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                                        dialogInterface.dismiss();
//
//                                    }
//                                });
//                                AlertDialog.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        for (int d = 0; d < selectedCategory.length; d++) {
//                                            selectedCategory[d] = false;
//                                            categorylistInt.clear();
//                                            binding.txtSubCategories.setText(" ");
//                                        }
//
//                                    }
//                                });
//                                AlertDialog.show();
//
//                            });

//
//                        } catch (Exception e) {
//                            Toast.makeText(post.this, "Error" + e, Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    } else {
//                        binding.progressBarCategory.setVisibility(View.GONE);
//
//                    }
//                } else {
//                    binding.progressBarCategory.setVisibility(View.GONE);
//
//                    if (response.errorBody() != null) {
//                        Toast.makeText(post.this, "Error to get sub Categories: " + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<categories>> call, Throwable t) {
//                binding.progressBarCategory.setVisibility(View.GONE);
//                Toast.makeText(post.this, "Error to call sub Categories: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//

    }

    private void get_Sub_Sub_Category() {
//
//
//
//        // get id parent category to get has sub category list
//
//        categories selectedItemObject = (categories) binding.subCategoriesSpinner.getSelectedItem();
//        int categoryParent = selectedItemObject.getId();
//
//
//
//        Call<ArrayList<categories>> categoriesCall = api.get_Sub_Sub_Categories(consumer_key, consumer_secret, String.valueOf(categoryParent));
//
//        categoriesCall.enqueue(new Callback<ArrayList<categories>>() {
//            @Override
//            public void onResponse(Call<ArrayList<categories>> call, Response<ArrayList<categories>> response) {
//
//                if (response.isSuccessful()) {
//                    if (!response.body().isEmpty()) {
//
//
//
//                        } catch (Exception e) {
//                            Toast.makeText(post.this, "Error" + e, Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    } else {
//                        binding.progressBarCategory.setVisibility(View.GONE);
//                        binding.txttSubCategories.setVisibility(View.GONE);
//                    }
//                } else {
//                    binding.progressBarCategory.setVisibility(View.GONE);
//
//                    if (response.errorBody() != null) {
//                        Toast.makeText(post.this, "Error to get sub Categories: " + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<categories>> call, Throwable t) {
//                binding.progressBarCategory.setVisibility(View.GONE);
//                Toast.makeText(post.this, "Error to call sub Categories: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });


    }

//    void reloadgetCategory(int pageC){
//        try {
//
//
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(baseUrl)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//
//            interfaceAPI get_Call = retrofit.create(com.example.myapplication.callAPI.interfaceAPI.class);
//            Call<ArrayList<categories>> call_Category = get_Call.get_Reload_Categories(consumer_key, consumer_secret,pageC );
//
//            call_Category.enqueue(new Callback<ArrayList<com.example.myapplication.Model.categories>>() {
//                @Override
//                public void onResponse(Call<ArrayList<com.example.myapplication.Model.categories>> call, Response<ArrayList<com.example.myapplication.Model.categories>> response) {
//                    if (response.isSuccessful()) {
//                        categoriesArrayList = response.body();
//
//
//
//                        categoriesArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, StringARRY);
//                        binding.categoriesSpinner.setAdapter(categoriesArrayAdapter);
//
//
//
//                    } else {
//                        Toast.makeText(post.this, "ERROR: API GET Categories", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ArrayList<com.example.myapplication.Model.categories>> call, Throwable t) {
//                    Toast.makeText(post.this, "ERROR: API GET Categories" + t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                }
//            });
//
//
//        } catch (Exception e) {
//            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }
//


    public void setGone() {

        binding.subCategoriesSpinner.setVisibility(View.GONE);
        binding.progressBarCategory.setVisibility(View.GONE);
    }

    public void setGone2txt() {
        binding.txtSubCategories.setVisibility(View.GONE);
        binding.progressBarCategory.setVisibility(View.GONE);
    }


//
//    void setAdapter(ArrayList<ArrayList<categories>> get)
//    {
//
//
//        adapter_for_categories_parent1 = new Adapter_for_categories_parent1(post.this,getCategoriesArrayListALLPage);
//        //adapter_for_categories_parent1(getCategoriesArrayListALLPage);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(post.this);
//        binding.rvGroup.setLayoutManager(linearLayoutManager);
//        binding.rvGroup.setAdapter(adapter_for_categories_parent1);

//
//        for (int i = 0 ; i  < get.size(); i++){
//
//        Adapter_for_categories_parent1 adapter_for_categories_parent1 = new Adapter_for_categories_parent1(post.this, get);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(post.this);
//        binding.rvGroup.setLayoutManager(linearLayoutManager);
//        binding.rvGroup.setAdapter(adapter_for_categories_parent1);
//
//
//
//    }


//
//  void getallsubCATEGORIES() {
//
//
//
//
//
//                    // add parent category to sub category list
//                    // subSubCategoriesArrayList.add(selectedItemObject);
//
//
//
//
//                    binding.txttSubCategories2.setVisibility(View.VISIBLE);
//
//
//
//                    //dilog
//                    selectedsub_sub_Category = new boolean[categoriesArrayList2.size()];
//                    categorysub_sublistInt = new ArrayList<>();
//                    array_sub_Sub_Category_Strings = new String[categoriesArrayList2.size()];
//
//                    for (int f = 0; f < categoriesArrayList2.size(); f++) {
//                        array_sub_Sub_Category_Strings [f]= categoriesArrayList2.get(f).getName();
//                    }
//
//
//
//                    try {
//
//                        binding.txttSubCategories2.setOnClickListener(view -> {
//
//
//                            AlertDialog.setTitle("اختر تصنيف");
//                            AlertDialog.setCancelable(false);
//                            AlertDialog.setMultiChoiceItems(array_sub_Sub_Category_Strings, selectedsub_sub_Category, new DialogInterface.OnMultiChoiceClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int position, boolean Ischecked) {
//
//                                    if (Ischecked) {
//                                        if (!categorysub_sublistInt.contains(position)) {
//
//                                            categorysub_sublistInt.add(position);
//
//                                        }else {
//
//                                            categorysub_sublistInt.remove(position);
//
//                                        }
//                                    }
//                                }
//                            });
//
//                            AlertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//
//                                    StringBuilder stringBuilder = new StringBuilder();
//                                    for (int j = 0; j < categorysub_sublistInt.size(); j++) {
//
//                                        stringBuilder.append(array_sub_Sub_Category_Strings[categorysub_sublistInt.get(j)]);
//
//
//                                        ///  here we get id for selected category name in sub sub categories
//                                        for (int w = 0; w < categoriesArrayList2.size(); w++) {
//                                            if (categoriesArrayList2.get(w).getName() == array_sub_Sub_Category_Strings[categorysub_sublistInt.get(j)]) {
//                                                //here we add the id for selected name
//                                                int ids = categoriesArrayList2.get(w).getId();
//
//                                                idSubCategory_Selected.add(ids);
//
//                                            }
//                                        }
//                                        //show category name in text field
//                                        if (j != categorysub_sublistInt.size() - 1) {
//                                            stringBuilder.append(", ");
//
//                                        }
//                                    }
//
//                                    binding.txttSubCategories2.setText(stringBuilder.toString());
//                                }
//                            });
//                            AlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//
//                                    dialogInterface.dismiss();
//
//                                }
//                            });
//                            AlertDialog.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    for (int d = 0; d < selectedsub_sub_Category.length; d++) {
//                                        selectedsub_sub_Category[d] = false;
//                                        categorysub_sublistInt.clear();
//                                        binding.txttSubCategories2.setText(" اختر تصنيف");
//                                    }
//
//                                }
//                            });
//                            AlertDialog.show();
//
//                        });
//
//
//                    } catch (Exception e) {
//                        Toast.makeText(post.this, "Error" + e, Toast.LENGTH_SHORT).show();
//                    }
//
//
//
//
//
//
//
//
//  }


}


