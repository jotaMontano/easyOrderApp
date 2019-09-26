package com.moonshine.easyorder.OrderOk;

import com.moonshine.easyorder.Models.OrderOk;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderOkRepository {

    @POST("order-oks")
    Call<OrderOk> createOrderOK(@Body OrderOk orderOk);

    @PUT("order-oks")
    Call<OrderOk> updateOrderOk(@Body OrderOk orderOk);
}
