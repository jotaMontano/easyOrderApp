package com.moonshine.easyorder.discount.repository;

import android.content.Context;
import android.util.Log;

import com.moonshine.easyorder.Models.Discount;
import com.moonshine.easyorder.Models.Extra;
import com.moonshine.easyorder.extra.repository.ExtraRepository;
import com.moonshine.easyorder.extra.repository.ExtraRepositoryImp;
import com.moonshine.easyorder.httpUtils.httpUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.moonshine.easyorder.httpUtils.httpUtil.TOKEN;

public class DiscountRepositoryImp implements DiscountRepository {

    private Context context;
    private DiscountRepositoryImp.DiscountTaskServiceListener listener;
    private List<Discount> discounts = new ArrayList<>();
    public DiscountRepositoryImp(Context context, DiscountRepositoryImp.DiscountTaskServiceListener listener){
        this.context = context;
        this.listener = listener;
    }

    @Override
    public Call<List<Discount>> getDiscountsByClient(Long id) {
        DiscountRepository api = httpUtil.createService(DiscountRepository.class,TOKEN);
        Call<List<Discount>> call = api.getDiscountsByClient(id);
        call.enqueue(new Callback<List<Discount>>() {
            @Override
            public void onResponse(Call<List<Discount>> call, Response<List<Discount>> response) {
                if (response.isSuccessful()){
                    discounts = response.body();
                    listener.onGetTaskByDiscounts(response.body());
                }else {
                    Log.e(TAG,"DISCOUNT " + "Error" + " BODY: " +  "No se pudo");
                }

            }
            @Override
            public void onFailure(Call<List<Discount>> call, Throwable t) {
                Log.e(TAG,"DISCOUNT " + "Error" + " BODY: " +  "Error");
            }
        });
        return null;
    }

    public interface DiscountTaskServiceListener {
        void onGetTaskByDiscounts(List<Discount> discounts);
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }
}
