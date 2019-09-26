package com.moonshine.easyorder.OrderOk;

import android.content.Context;

import com.moonshine.easyorder.Models.OrderOk;
import com.moonshine.easyorder.httpUtils.httpUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moonshine.easyorder.httpUtils.httpUtil.TOKEN;

public class OrderOkRepositoryImp implements OrderOkRepository{

    private Context context;
    private OrderOkTaskListener listener;
    public OrderOkRepositoryImp(Context context, OrderOkTaskListener listener){
        this.context = context;
        this.listener = listener;
    }

    @Override
    public Call<OrderOk> createOrderOK(OrderOk orderOk) {
        OrderOkRepository api = httpUtil.createService(OrderOkRepository.class, TOKEN);
        Call<OrderOk> call = api.createOrderOK(orderOk);
        call.enqueue(new Callback<OrderOk>() {
            @Override
            public void onResponse(Call<OrderOk> call, Response<OrderOk> response) {
                if (response.isSuccessful()) {
                    listener.onCreateOrderOk(response.body());
                }
            }

            @Override
            public void onFailure(Call<OrderOk> call, Throwable t) {

            }
        });
        return null;
    }

    @Override
    public Call<OrderOk> updateOrderOk(OrderOk orderOk) {
        OrderOkRepository api = httpUtil.createService(OrderOkRepository.class, TOKEN);
        Call<OrderOk> call = api.updateOrderOk(orderOk);
        call.enqueue(new Callback<OrderOk>() {
            @Override
            public void onResponse(Call<OrderOk> call, Response<OrderOk> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<OrderOk> call, Throwable t) {

            }
        });
        return null;
    }

    public interface OrderOkTaskListener {
        void onCreateOrderOk(OrderOk orderOk);
    }
}
