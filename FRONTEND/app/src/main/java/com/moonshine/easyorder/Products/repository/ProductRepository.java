package com.moonshine.easyorder.Products.repository;

import com.moonshine.easyorder.Models.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductRepository {

    @GET("getProductsByCategory/{id}")
    Call<ArrayList<Product>>getProductsByCategory(@Path("id") Long id);

    @GET("products/getProductsTop/{id}")
    Call<ArrayList<Product>>getProductsTop(@Path("id") Long id);
}
