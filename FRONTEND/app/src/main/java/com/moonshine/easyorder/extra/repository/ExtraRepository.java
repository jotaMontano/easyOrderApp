package com.moonshine.easyorder.extra.repository;
import com.moonshine.easyorder.Models.Extra;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ExtraRepository {

    @GET("extrasByProduct/{id}")
    Call<List<Extra>> getExtrasByClient(@Path("id") Long id);
}
