package com.example.myapplication.adapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.example.myapplication.Model.Product;

public class ProductsDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, Product>> mutableLiveData = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource create() {
        ProductsDataSource productsDataSource =new ProductsDataSource();
        mutableLiveData.postValue(productsDataSource);

        return productsDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Product>> getMutableLiveData() {
        return mutableLiveData;
    }
}
