package com.example.myapplication.UI;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Coupons;
import com.example.myapplication.UI.main.LiveData;
import com.example.myapplication.adapter.Adapter_List_Codes;
import com.example.myapplication.callAPI.ClientApi;
import com.example.myapplication.databinding.ActivityCodeListBinding;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CodeListActivity extends AppCompatActivity {
    ActivityCodeListBinding binding;
    AlertDialog alert ;
    Adapter_List_Codes adp;
    LiveData liveData;

    boolean isLoading = true ;
    
    private final String TAG = "TAG";
    int page = 1;

    ArrayList<Coupons> arrayCoupons = new ArrayList<>();
    ArrayList< ArrayList<Coupons> > all_ArrayCoupons = new ArrayList<>();
    
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCodeListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        liveData = new ViewModelProvider(this).get(LiveData.class);



        adp = new Adapter_List_Codes();

        alert = new Builder(this)
        .setTitle("قائمة الكروت")
        .setMessage("يتم تحميل...")
        .setCancelable(false)
        .create();

        //show Alert
        alert.show();


        get_List_Codes(page);
        //set RecyclerView
        binding.rvCodesList.setAdapter(adp);
        binding.rvCodesList.setLayoutManager(new LinearLayoutManager(CodeListActivity.this));
        binding.rvCodesList.setHasFixedSize(true);


        binding.rvCodesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@androidx.annotation.NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager =(LinearLayoutManager) recyclerView.getLayoutManager();

                if (isLoading){
                    if (linearLayoutManager!= null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == arrayCoupons.size() - 1 ) {
                        page++;

                       // isLoading(page);
                        isLoading = true;
                    }


                }
            }
        });


    }//Out

   private void get_List_Codes(int pagee)
   {




           Observable<ArrayList<Coupons>> observable = ClientApi.getInstance().get_List_Codes(pagee)
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread());

           Observer<ArrayList<Coupons>> observer = new Observer<ArrayList<Coupons>>() {
               @Override
               public void onSubscribe(@NonNull Disposable d) {

               }

               @Override
               public void onNext(@NonNull ArrayList<Coupons> coupons) {
                   all_ArrayCoupons.add(coupons);



                       for (int i = 0; i < all_ArrayCoupons.size(); i++) {
                           arrayCoupons.addAll(all_ArrayCoupons.get(i));
                       }
                       alert.dismiss();
                       adp.setDataAdapter(arrayCoupons, CodeListActivity.this);


               }

               @Override
               public void onError(@NonNull Throwable e) {
                   alert.dismiss();
                   Toast.makeText(CodeListActivity.this, "Error: "+ e.getMessage(), Toast.LENGTH_SHORT).show();

               }

               @Override
               public void onComplete() {

               }
           };
           observable.subscribe(observer);



       }








       private void isLoading(int page)
       {





//
//               binding.rvCodesList.post(new Runnable() {
//                   @Override
//                   public void run() {
//                       arrayCoupons.add(null);
//                       adp.notifyDataSetChanged();
//                       adp.notifyItemInserted(arrayCoupons.size() -1);
//
//                       arrayCoupons.remove(arrayCoupons.size() - 1);
//                       adp.notifyDataSetChanged();
//
//
//                   }
//               });




           for (int i= 0 ; i <3 ; i++) {

               Observable<ArrayList<Coupons>> observable = ClientApi.getInstance().get_List_Codes(page)
                       .subscribeOn(Schedulers.io())
                       .observeOn(AndroidSchedulers.mainThread());


               Observer<ArrayList<Coupons>> observer = new Observer<ArrayList<Coupons>>() {
                   @Override
                   public void onSubscribe(@NonNull Disposable d) {

                   }

                   @Override
                   public void onNext(@NonNull ArrayList<Coupons> coupons) {


                       binding.rvCodesList.post(new Runnable() {
                           @Override
                           public void run() {



                               arrayCoupons.addAll(coupons);
                               adp.notifyDataSetChanged();
                           }
                       });
                   }

                   @Override
                   public void onError(@NonNull Throwable e) {

                       Toast.makeText(CodeListActivity.this, "Error2: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                   }

                   @Override
                   public void onComplete() {

                   }
               };
               observable.subscribe(observer);

           }







           isLoading =false;





       }







   }


