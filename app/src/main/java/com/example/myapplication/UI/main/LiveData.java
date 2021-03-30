package com.example.myapplication.UI.main;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Model.Coupons;
import com.example.myapplication.Model.Product;
import com.example.myapplication.Model.brands;
import com.example.myapplication.Model.categories;
import com.example.myapplication.UI.post;
import com.example.myapplication.callAPI.ClientApi;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveData extends ViewModel {
    private static final String TAG = "LiveData";


    Context context;
    int count_Category = 1;
    int count_brand = 1;



    public void setContext(Context context) {
        this.context = context;
    }

    private ClientApi clientApi = new ClientApi();


    public MutableLiveData<ArrayList<categories>> mutableLiveData_get_All_Categories = new MutableLiveData<>();
    public MutableLiveData<ArrayList<categories>> mutableLiveData_categories_Parent1 = new MutableLiveData<>();
    public MutableLiveData<ArrayList<categories>> mutableLiveData_categories_Parent3 = new MutableLiveData<>();
    public MutableLiveData<ArrayList<categories>> mutableLiveData_categories_Parent2 = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Product>> mutableLiveData_Products = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Product>> mutableLiveData_Products_Categories2 = new MutableLiveData<>();
    public MutableLiveData<ArrayList<brands>> mutableLiveData_get_All_Brands = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Coupons>> all_Add_Coupons_MutableLiveData = new MutableLiveData<>();



    public void setSubscribe_categories1() {


        set_Parent_1_CategoriesCall(1,0 ).subscribe(new Observer<ArrayList<categories>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ArrayList<categories> categories) {

                mutableLiveData_categories_Parent1.setValue(categories);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: " + e.getMessage());

            }

            @Override
            public void onComplete() {

            }
        });
    }
    public void setSubscribe_Barnds() {





        set_All_Brands(1).subscribe(new Observer<ArrayList<brands>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ArrayList<brands> brands) {
                 mutableLiveData_get_All_Brands.setValue(brands);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onComplete() {

            }
        });

    }

    public Observable<ArrayList<categories>> set_Parent_1_CategoriesCall(int page, int parent) {

     return clientApi.parent_1_CategoriesCall(count_Category, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(categoriesss -> {
                    if (categoriesss.size() == 0) { ;


                       return Observable.just(categoriesss);
                    }else
                        {

                            count_Category++;
                           // Log.d(TAG, "category: down "+String.valueOf(categoriesss.size()) );


                            return Observable.zip(Observable.just(categoriesss)
                                    ,set_Parent_1_CategoriesCall(count_Category, 0),(product111, product222) -> {
                                        product111.addAll(product222);
                                        return product111;
                                    });


                        }
                }
        );



    }

    public void get_All_Categories(int page, Context context) {

        Single<ArrayList<categories>> single = clientApi.get_All_Categories(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        single.subscribe(observer -> mutableLiveData_get_All_Categories.setValue(observer), e -> Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());


    }

    public void set_Parent_2_CategoriesCall(int parent, int page, post context) {
        clientApi.parent_2_CategoriesCall(parent, page).enqueue(new Callback<ArrayList<categories>>() {
            @Override
            public void onResponse(Call<ArrayList<categories>> call, Response<ArrayList<categories>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().size() != 0) {
                        mutableLiveData_categories_Parent2.setValue(response.body());
                    } else {
                        context.setGone();

                    }
                } else {
                    Toast.makeText(context, "Error Call: " + response.message().toString(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<categories>> call, Throwable t) {

            }
        });


//        Single single = ClientApiApi.parent_2_CategoriesCall( parent ,page)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//
//
//        SingleObserver<ArrayList<categories>>  observer = new SingleObserver<ArrayList<categories>>() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//
//            }
//
//            @Override
//            public void onSuccess(@NonNull ArrayList<categories> categories) {
//
//                mutableLiveData_categories_Parent2.setValue(categories);
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                Toast.makeText(context, "Erorr: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        };
//        single.subscribe(observer);
    }

    public void set_Parent_3_CategoriesCall(int parent, post context) {

        clientApi.parent_3_CategoriesCall(parent).enqueue(new Callback<ArrayList<categories>>() {
            @Override
            public void onResponse(@NotNull Call<ArrayList<categories>> call, @NotNull Response<ArrayList<categories>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().size() != 0) {
                        mutableLiveData_categories_Parent3.setValue(response.body());
                    } else {
                        context.setGone2txt();

                    }
                } else {
                    Toast.makeText(context, "Error call: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<categories>> call, Throwable t) {

            }
        });
//        Single<ArrayList<categories>> single = ClientApiApi.parent_3_CategoriesCall(parent)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//
//
//        single.subscribe(o-> mutableLiveData_categories_Parent2.setValue(o) );
    }

    public void set_Products_List(int page, Context context) {

        Observable observable = clientApi.getProducts_From_Api(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


        Observer<ArrayList<Product>> observer = new Observer<ArrayList<Product>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ArrayList<Product> value) {
                mutableLiveData_Products.setValue(value);
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);


//        ClientApiApi.getProducts_From_Api( page ).enqueue(new Callback<ArrayList<Product>>()
//        {
//            @Override
//            public void onResponse(Call<ArrayList<Product>> call, @NotNull Response<ArrayList<Product>>  response)
//            {
//                if (response.isSuccessful()) {
//                    Toast.makeText(context, "تم الارتباط بالسرفر بنجاح", Toast.LENGTH_SHORT).show();
//                    mutableLiveData_Products.setValue(response.body());
//
//
//                } else {
//                    Toast.makeText(context, "خطاء في الارتباط: تحقق من الشبكة او بيانات الAPI" + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
////                        Intent send_Exception = new Intent(MainActivity.this, SettingsActivity.class);
////                        if (response.errorBody() != null) {
////                            send_Exception.putExtra("API Exception", response.errorBody().toString());
////                        }
////                        startActivity(send_Exception);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });

    }

    public void set_Products_List_Category(String category, post context) {
        Observable observable = clientApi.get_Products_Category(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observer<ArrayList<Product>> observer = new Observer<ArrayList<Product>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ArrayList<Product> products) {
                mutableLiveData_Products_Categories2.setValue(products);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);
    }

    public Observable<ArrayList<brands>> set_All_Brands(int page) {

         return clientApi.get_All_Brands(count_brand)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                 .concatMap(data-> {
                     if (data.size() == 0)
                     {
                         return Observable.just(data);
                      }
                     else {
                         count_brand++;
                         return Observable.zip(
                                 Observable.just(data),
                                 set_All_Brands(count_brand),
                                 (newData, oldData)->{
                                     newData.addAll(oldData);
                                     return newData;
                                 }
                         );
                     }
                 });





    }


}
