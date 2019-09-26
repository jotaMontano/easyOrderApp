package com.moonshine.easyorder.productByOrder;

import android.content.Context;

import com.moonshine.easyorder.Models.ProductByOrder;
import com.moonshine.easyorder.httpUtils.httpUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moonshine.easyorder.httpUtils.httpUtil.TOKEN;

public class ProductByOrderRepositoryImp implements ProductByOrderRepository {
    private Context context;
    private ProductByOrderTaskListener listener;

    public ProductByOrderRepositoryImp(Context context, ProductByOrderTaskListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public Call<ProductByOrder> createProductByOrder(ProductByOrder productByOrder) {
        ProductByOrderRepository api = httpUtil.createService(ProductByOrderRepository.class, TOKEN);
        Call<ProductByOrder> call = api.createProductByOrder(productByOrder);
        call.enqueue(new Callback<ProductByOrder>() {
            @Override
            public void onResponse(Call<ProductByOrder> call, Response<ProductByOrder> response) {
                if (response.isSuccessful()) {
                    listener.onCreateProductByOrder(response.body(), productByOrder);
                }
            }

            @Override
            public void onFailure(Call<ProductByOrder> call, Throwable t) {

            }
        });
        return null;
    }


    public interface ProductByOrderTaskListener{
        void onCreateProductByOrder(ProductByOrder productByOrder, ProductByOrder productByOrder2);
    }
}
