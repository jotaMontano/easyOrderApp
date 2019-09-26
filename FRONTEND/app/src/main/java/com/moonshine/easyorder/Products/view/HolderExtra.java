package com.moonshine.easyorder.Products.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.moonshine.easyorder.R;

public class HolderExtra extends RecyclerView.ViewHolder{

    private CheckBox extraName;
    private TextView extraPrice;

    public HolderExtra(@NonNull View itemView) {
        super(itemView);
        extraName = itemView.findViewById(R.id.extraCheckBox);
        extraPrice = itemView.findViewById(R.id.extraPrice);
    }

    public void setExtraName(CheckBox extraName) {
        this.extraName = extraName;
    }

    public void setExtraPrice(TextView extraPrice) {
        this.extraPrice = extraPrice;
    }

    public CheckBox getExtraName() {
        return extraName;
    }

    public TextView getExtraPrice() {
        return extraPrice;
    }

}
