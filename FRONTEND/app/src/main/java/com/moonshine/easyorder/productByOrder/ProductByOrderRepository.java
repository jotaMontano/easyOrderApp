package com.moonshine.easyorder.productByOrder;

import com.moonshine.easyorder.Models.ProductByOrder;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
public interface ProductByOrderRepository {

    @POST("product-by-orders")
    Call<ProductByOrder> createProductByOrder(@Body ProductByOrder productByOrder);
}
