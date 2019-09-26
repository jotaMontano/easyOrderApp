package com.moonshine.easyorder.Products.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.moonshine.easyorder.R;

public class HolderDiscount extends RecyclerView.ViewHolder {

    private TextView discount;
    private TextView discountName;

    public HolderDiscount(@NonNull View itemView) {
        super(itemView);
        discount = itemView.findViewById(R.id.discount);
        discountName = itemView.findViewById(R.id.discountName);
    }

    public void setDiscount(TextView discount) {
        this.discount = discount;
    }

    public void setDiscountName(TextView discountName) {
        this.discountName = discountName;
    }

    public TextView getDiscount() {
        return discount;
    }

    public TextView getDiscountName() {
        return discountName;
    }
}
