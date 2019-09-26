package com.moonshine.easyorder.discount.repository;
import com.moonshine.easyorder.Models.Discount;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DiscountRepository {

    @GET("discountsByProduct/{id}")
    Call<List<Discount>> getDiscountsByClient(@Path("id") Long id);
}
