package com.moonshine.easyorder.extra.repository;

import android.content.Context;
import android.util.Log;

import com.moonshine.easyorder.Models.Extra;
import com.moonshine.easyorder.httpUtils.httpUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.moonshine.easyorder.httpUtils.httpUtil.TOKEN;

public class ExtraRepositoryImp implements ExtraRepository {

    private Context context;
    private ExtraTaskServiceListener listener;
    private List<Extra> extras = new ArrayList<>();
    public ExtraRepositoryImp(Context context, ExtraTaskServiceListener listener){
        this.context = context;
        this.listener = listener;
    }

    @Override
    public Call<List<Extra>> getExtrasByClient(Long id) {
        ExtraRepository api = httpUtil.createService(ExtraRepository.class,TOKEN);
        Call<List<Extra>> call = api.getExtrasByClient(id);
        call.enqueue(new Callback<List<Extra>>() {
            @Override
            public void onResponse(Call<List<Extra>> call, Response<List<Extra>> response) {
                if (response.isSuccessful()){
                    extras = response.body();
                    listener.onGetTaskByExtras(response.body());
                }else {
                    Log.e(TAG,"EXTRA " + "Error" + " BODY: " +  "No se pudo");
                }

            }
            @Override
            public void onFailure(Call<List<Extra>> call, Throwable t) {
                Log.e(TAG,"EXTRA " + "Error" + " BODY: " +  "ERRORRRRRRR");
            }
        });
        return null;
    }

    public interface ExtraTaskServiceListener {
        void onGetTaskByExtras(List<Extra> extras);
    }

    public List<Extra> getExtras() {
        return extras;
    }
}
