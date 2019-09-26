package com.moonshine.easyorder.Category.repository;

import com.moonshine.easyorder.Models.Category;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CategoryRepository {

    @GET("categories/getCategoriesByClient/{id}")
    Call<List<Category>>getCategoriesByClient(@Path("id") Long id);

}
