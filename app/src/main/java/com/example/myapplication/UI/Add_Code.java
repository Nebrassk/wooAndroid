package com.example.myapplication.UI;

import android.Manifest;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.myapplication.Model.Coupons;
import com.example.myapplication.callAPI.ClientApi;
import com.example.myapplication.databinding.ActivityAddCodeBinding;
import com.opencsv.CSVWriter;

import org.jetbrains.annotations.NotNull;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_Code extends AppCompatActivity {


    private static final String TAG = "Add_Code";
    
    ActivityAddCodeBinding binding;
    AlertDialog alertDialog;

    String num;
    String amount;

    ArrayList<Coupons> batch = new ArrayList<>();
    HashMap<Object, Object> hashMap = new HashMap<>();

    int count = 1;


    //export CSV

    String csv = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/الكروت.csv"); // Here csv file name is MyCsvFile.csv
    CSVWriter writerr = null;
   ArrayList<Coupons> arrayLists = new ArrayList<>();


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(Add_Code.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        alertDialog = new Builder(this)
        .setTitle("اضافة كرت")
        .setMessage("يتم اضافة الكرت...")
        .create();



        binding.btnAddCode.setOnClickListener(view ->
        {
            Generates_Codes();
        });

        binding.goListCodes.setOnClickListener(view -> {
            startActivity(new Intent(Add_Code.this , CodeListActivity.class));
        });
        
        
        
       binding.exportToCsv.setOnClickListener(view -> {
           alertDialog.show();
           getAllCodes(1).subscribe(obs->{

                   arrayLists.addAll(obs);

                   alertDialog.dismiss();
                   if (arrayLists.size() > 0 ){
                       try {
                           writerr = new CSVWriter(new FileWriter(csv));

                           ArrayList<String[]> data = new ArrayList<String[]>();
                           for (int ii =0 ; ii<arrayLists.size() ;ii++) {
                               data.add(new String[]{arrayLists.get(ii).getCode(), arrayLists.get(ii).getAmount()});
                           }


                           writerr.writeAll(data); // data is adding to csv

                           writerr.close();
                           Toast.makeText(Add_Code.this, "تم حفظ الملف بنجاح في الذاكرة الداخلية ", Toast.LENGTH_SHORT).show();
                       } catch (IOException ee) {
                           ee.printStackTrace();
                           Toast.makeText(Add_Code.this, ee.getMessage() + "خطاء: ", Toast.LENGTH_SHORT).show();

                       }
                   }
                   else {
                       Toast.makeText(Add_Code.this, "لا يوجد لديك اكواد بعد", Toast.LENGTH_LONG).show();

                   }


           } , error-> {
               Toast.makeText(this, "error: " + error.getMessage(), Toast.LENGTH_LONG).show();
               Log.d(TAG, "Error:" + error.getMessage());
           } );



       });



        ActivityCompat.requestPermissions(Add_Code.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);







    }//out root




    private Observable<ArrayList<Coupons>> getAllCodes(int page){

        return ClientApi.getInstance().get_List_Codes(count)
                        .doOnNext(log->runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alertDialog.setMessage(  "تم استخراج "+ String.valueOf(arrayLists.size()));
                            }
                        }) )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .concatMap(obs-> {
                           if (obs.size() == 0)
                               return Observable.just(obs);
                           else
                               {
                                  count++;
                                   return Observable.zip
                                       (
                                       Observable.just(obs),
                                       getAllCodes(count),
                                       (obs1, obs2)->
                                       {
                                           obs1.addAll(obs2) ;
                                           return obs1;
                                       }
                                     );
                              }

                        })
                ;

        
    }
    



    private void Generates_Codes()
    {

        num = binding.eTxtAddCode.getText().toString().trim();
        amount = binding.eTxtAddCodeAmount.getText().toString().trim();


        if (!num.isEmpty()  && !amount.isEmpty()) {


       final String Numbers = "1234567890";
       final String Letters = "ABCDEFGHYIJKLMNOPQRSTUVWXYZ";
       final char[] charCodes = ((Letters+Letters).toUpperCase() +Numbers ).toCharArray();



       if (Integer.parseInt(num) <= 100) {
           //HERE WE Create Unique code
           ArrayList<StringBuilder> stringBuilders = new ArrayList<>();
           for (int w = 0; w < Integer.parseInt(num); w++) {
               stringBuilders.add(new StringBuilder());
               for (int i = 0; i < 16; i++) {

                   stringBuilders.get(w).append(charCodes[new Random().nextInt(charCodes.length)]);
               }

               batch.add(new Coupons(stringBuilders.get(w).toString(), amount));
           }

           alertDialog.show();
           send_To_Add_Code(batch);
       }else {
           Toast.makeText(this, "يمكنك اضافة 100 كحد اقصى في كل مرة", Toast.LENGTH_SHORT).show();
       }

    }else {
        alertDialog.dismiss();
        Toast.makeText(this, "يرجى اضافة بيانات", Toast.LENGTH_SHORT).show();
    }

    }





    private void send_To_Add_Code (ArrayList<Coupons> batch)
    {
        binding.eTxtAddCodeAmount.setText("");
        binding.eTxtAddCode.setText("");



        hashMap.put("create", batch);
        hashMap.put("usage_limit", 1);


        ClientApi.getInstance().all_Add_Coupons(hashMap).enqueue(new Callback<Coupons>() {
            @Override
            public void onResponse(@NotNull Call<Coupons> call, @NotNull Response<Coupons> response)
            {
                if (response.isSuccessful()) {
                    Toast.makeText(Add_Code.this, "تمت الاضافة بنجاح ", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                    startActivity(new Intent(Add_Code.this , CodeListActivity.class ));


                } else {
                    Toast.makeText(Add_Code.this,"1 ERROR: "+ response.errorBody() != null ? response.errorBody().toString() : null, Toast.LENGTH_LONG).show();
                    alertDialog.dismiss();
                }

            }

            @Override
            public void onFailure(@NotNull Call<Coupons> call, @NotNull Throwable t) {
                Toast.makeText(Add_Code.this,"2 ERROR: "+ t.getMessage(), Toast.LENGTH_LONG).show();
                alertDialog.dismiss();
            }
        });





    }








}