package com.example.googlesignindemo.viewmodel;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.googlesignindemo.api.ApiService;
import com.example.googlesignindemo.model.Product;
import com.example.googlesignindemo.view.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductViewModel extends ViewModel {
    private MutableLiveData<List<Product>> mListProductLiveData;
    private List<Product> mListProduct;
    int pageNumber = 1;
    int numberOfItem = 10;

    public ProductViewModel() {
        mListProduct = new ArrayList<>();
        mListProductLiveData = new MutableLiveData<>();
        fetchData();
    }

    public MutableLiveData<List<Product>> getmListProductLiveData() {
        return mListProductLiveData;
    }

    public void fetchData() {
        ApiService.apiService.getProductList(pageNumber, numberOfItem).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.body() != null) {
                    mListProduct.addAll(response.body());
                    mListProductLiveData.setValue(mListProduct);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.i("Call API", "Failed");
            }
        });
        pageNumber ++;
    }

    public void onProductItemClick(View v, int position) {
        Intent intentProductDetail = new Intent(v.getContext(), ProductDetailsActivity.class);
        intentProductDetail.putExtra("productValue", mListProduct.get(position));
        v.getContext().startActivity(intentProductDetail);
    }
}
