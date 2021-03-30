package com.example.googlesignindemo.api;

import com.example.googlesignindemo.model.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    // Link API: https://picsum.photos/v2/list?page=1&limit=100

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-yy HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://picsum.photos/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("v2/list")
    Call<List<Product>> getProductList(
            @Query("page") int pageNumber,
            @Query("limit") int numberOfItem
    );

}
