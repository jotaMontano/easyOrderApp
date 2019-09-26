package com.moonshine.easyorder.Products.repository;

import android.content.Context;
import android.widget.Toast;

import com.moonshine.easyorder.Models.Category;
import com.moonshine.easyorder.Models.Product;
import com.moonshine.easyorder.httpUtils.httpUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moonshine.easyorder.httpUtils.httpUtil.TOKEN;

public class ProductRepositoryImpl implements ProductRepository {

    private Context context;
    private String authToken;
    private ProductServiceListener listener;
    private String nameCategory;

    public ProductRepositoryImpl(Context context, ProductServiceListener listener, String nameCategory){
        this.context = context;
        this.listener = listener;
        this.nameCategory = nameCategory;
        //TODO falta agregar el toquen de autenticacion
    }
    public ProductRepositoryImpl(Context context, ProductServiceListener listener){
        this.context = context;
        this.listener = listener;
        //
    }

    @Override
    public Call<ArrayList<Product>> getProductsByCategory(Long id) {

        ProductRepository apiService = httpUtil.createService(ProductRepository.class, TOKEN);

        Call<ArrayList<Product>> call = apiService.getProductsByCategory(id);

        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if (response.code() == 200) { // OK
                    listener.OnGetProductByCategorySuccess(response.body(), id, nameCategory);

                } else {
                    listener.OnProductByCategoryError("ERROR getting resources");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Toast.makeText(context, "Something went wrong!",
                        Toast.LENGTH_LONG).show();
                listener.OnProductByCategoryError("Something went wrong!");
            }
        });

        return null;
    }

    public Call<ArrayList<Product>> getProductsTop(Long id) {
         ProductRepository apiService = httpUtil.createService(ProductRepository.class, TOKEN);

        Call<ArrayList<Product>> call = apiService.getProductsTop(id);

        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if(response.isSuccessful()){
                    listener.OnGetProductBySuccess(response.body());
                } else {
                    listener.OnProductByError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                listener.OnProductByError("Something went wrong!");
            }


        });
        return null;
    }

    public interface ProductServiceListener {
        void OnGetProductByCategorySuccess(ArrayList<Product> products, Long id, String nameCategory);
        void OnProductByCategoryError(String error);
        void OnGetProductBySuccess(ArrayList<Product> products);
        void OnProductByError(String error);


    }
}
