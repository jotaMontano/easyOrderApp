package com.moonshine.easyorder.Products.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.moonshine.easyorder.Models.Extra;
import com.moonshine.easyorder.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterExtra extends RecyclerView.Adapter<HolderExtra> {

    private List<Extra> extras;
    private List<Extra> extrasSelect =new ArrayList<>();
    private Context context;

    public AdapterExtra(List<Extra> extras, Context context) {
        this.extras = extras;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderExtra onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.extra_holder, viewGroup, false);
        return new HolderExtra(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderExtra holderExtra, int i) {
        final Extra extra = extras.get(i);
        holderExtra.getExtraName().setText(extra.getName());
        DecimalFormat format = new DecimalFormat("###,###,###");
        holderExtra.getExtraPrice().setText("₡ "+format.format(extra.getPrice()));
        holderExtra.getExtraName().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    extrasSelect.add(extra);
                }else {
                    extrasSelect.remove(extra);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return extras.size();
    }

    public void clearExtras(){
        this.extras.clear();
        notifyDataSetChanged();
    }
    public List<Extra> getExtrasSelect(){
        return extrasSelect;
    }
}
