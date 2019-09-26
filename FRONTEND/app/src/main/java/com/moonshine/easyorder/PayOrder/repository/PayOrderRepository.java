package com.moonshine.easyorder.PayOrder.repository;

import com.moonshine.easyorder.Models.OrderOk;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PayOrderRepository {

    @PUT("order-oks-toPay")
    Call<OrderOk> updateOrderToPay(@Body OrderOk order);
    @GET("order-oks/{id}")
    Call<OrderOk> findOrder(@Path("id") Long id);
}
