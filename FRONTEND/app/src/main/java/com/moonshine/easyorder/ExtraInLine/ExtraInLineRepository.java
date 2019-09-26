package com.moonshine.easyorder.ExtraInLine;

import com.moonshine.easyorder.Models.ExtraInLine;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
public interface ExtraInLineRepository {

    @POST("extra-in-lines")
    Call<ExtraInLine> createExtraInLine(@Body ExtraInLine extraInLine);
}
