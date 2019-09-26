package com.moonshine.easyorder.PayOrder.repository;

import com.moonshine.easyorder.Models.OrderOk;
import com.moonshine.easyorder.httpUtils.httpUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moonshine.easyorder.httpUtils.httpUtil.TOKEN;

public class PayOrderRepositoryImpl implements PayOrderRepository {

    private PayOrderTaskListener listener;
    public PayOrderRepositoryImpl(PayOrderTaskListener listener){
        this.listener = listener;
    }

    @Override
    public Call<OrderOk> updateOrderToPay(OrderOk order) {
        PayOrderRepository api = httpUtil.createService(PayOrderRepository.class, TOKEN);
        Call<OrderOk> call = api.updateOrderToPay(order);
        call.enqueue(new Callback<OrderOk>() {
            @Override
            public void onResponse(Call<OrderOk> call, Response<OrderOk> response) {

            }
            @Override
            public void onFailure(Call<OrderOk> call, Throwable t) {

            }
        });
        return null;
    }

    @Override
    public Call<OrderOk> findOrder(Long id) {
        PayOrderRepository api = httpUtil.createService(PayOrderRepository.class, TOKEN);
        Call<OrderOk> call = api.findOrder(id);
        call.enqueue(new Callback<OrderOk>() {
            @Override
            public void onResponse(Call<OrderOk> call, Response<OrderOk> response) {
                if (response.isSuccessful()){
                    listener.findOrder(response.body());
                }
            }
            @Override
            public void onFailure(Call<OrderOk> call, Throwable t) {

            }
        });
        return null;
    }
    public interface PayOrderTaskListener {
        void findOrder(OrderOk orderOk);
    }
}
