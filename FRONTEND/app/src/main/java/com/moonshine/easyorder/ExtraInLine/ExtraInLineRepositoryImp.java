package com.moonshine.easyorder.ExtraInLine;

import android.content.Context;

import com.moonshine.easyorder.Models.ExtraInLine;
import com.moonshine.easyorder.httpUtils.httpUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moonshine.easyorder.httpUtils.httpUtil.TOKEN;

public class ExtraInLineRepositoryImp implements ExtraInLineRepository {
    private Context context;
    private ExtraInLineTaskListener listener;

    public ExtraInLineRepositoryImp(Context context, ExtraInLineTaskListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public Call<ExtraInLine> createExtraInLine(ExtraInLine extraInLine) {
        ExtraInLineRepository api = httpUtil.createService(ExtraInLineRepository.class, TOKEN);
        Call<ExtraInLine> call = api.createExtraInLine(extraInLine);
        call.enqueue(new Callback<ExtraInLine>() {
            @Override
            public void onResponse(Call<ExtraInLine> call, Response<ExtraInLine> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<ExtraInLine> call, Throwable t) {

            }
        });
        return null;
    }


    public interface ExtraInLineTaskListener {

    }
}
