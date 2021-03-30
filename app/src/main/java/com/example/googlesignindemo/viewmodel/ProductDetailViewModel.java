package com.example.googlesignindemo.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.googlesignindemo.model.Product;

public class ProductDetailViewModel extends ViewModel {
    private MutableLiveData<Product> mProductLiveData;

    public ProductDetailViewModel() {
        mProductLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Product> getProductLiveData() {
        return mProductLiveData;
    }

    public void setData(Product product) {
        mProductLiveData.setValue(product);
    }
}
