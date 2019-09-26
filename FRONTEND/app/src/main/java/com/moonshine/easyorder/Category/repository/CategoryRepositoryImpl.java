package com.moonshine.easyorder.Category.repository;

import android.content.Context;
import android.widget.Toast;

import com.moonshine.easyorder.Models.Category;
import com.moonshine.easyorder.httpUtils.httpUtil;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moonshine.easyorder.httpUtils.httpUtil.TOKEN;

public class CategoryRepositoryImpl implements CategoryRepository {

    private Context context;
    private String authToken;
    private CategoryServiceListener listener;

    public CategoryRepositoryImpl(Context context, CategoryServiceListener listener){
        this.context = context;
        this.listener = listener;
    }

    @Override
    public Call<List<Category>> getCategoriesByClient(Long id) {
        CategoryRepository apiService = httpUtil.createService(CategoryRepository.class, TOKEN);
        Call<List<Category>> call = apiService.getCategoriesByClient(id);

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.code() == 200) { // OK
                    listener.OnGetCategoryByClientSuccess(response.body());

                } else {
                    listener.OnCategoryByClientError("ERROR getting resources");
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(context, "Something went wrong!",
                        Toast.LENGTH_LONG).show();
                listener.OnCategoryByClientError("Something went wrong!");
            }
        });
        return null;
    }

    public interface CategoryServiceListener {
        void OnGetCategoryByClientSuccess(List<Category> categories);
        void OnCategoryByClientError(String error);
    }
}
