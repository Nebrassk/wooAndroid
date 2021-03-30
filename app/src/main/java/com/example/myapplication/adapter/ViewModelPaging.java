package com.example.myapplication.adapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.example.myapplication.Model.Product;

public class ViewModelPaging extends ViewModel {
    public LiveData<PagedList<Product>> productPagedListLive;
    public  LiveData<PageKeyedDataSource<Integer, Product>> pageKeyedDataSourceLiveData;

    public ViewModelPaging() {
       ProductsDataSourceFactory productsDataSourceFactory = new ProductsDataSourceFactory();
       pageKeyedDataSourceLiveData = productsDataSourceFactory.getMutableLiveData();

       PagedList.Config config =
               (new PagedList.Config.Builder())
               .setEnablePlaceholders(false)
               .setPageSize(10)
               .build();
        productPagedListLive = (new LivePagedListBuilder(productsDataSourceFactory, config)).build();


    }
}
