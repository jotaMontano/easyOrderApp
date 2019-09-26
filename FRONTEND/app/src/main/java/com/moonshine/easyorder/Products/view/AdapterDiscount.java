package com.moonshine.easyorder.Products.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moonshine.easyorder.Models.Discount;
import com.moonshine.easyorder.R;
import java.util.List;

public class AdapterDiscount extends RecyclerView.Adapter<HolderDiscount> {

    private List<Discount> discounts;
    private Context context;

    public AdapterDiscount(List<Discount> discounts, Context context) {
        this.discounts = discounts;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderDiscount onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.discount_holder, viewGroup, false);
        return new HolderDiscount(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderDiscount holderDiscount, int i) {
        final Discount discount = discounts.get(i);
        holderDiscount.getDiscount().setText((int)discount.getPercentage() + "%");
        holderDiscount.getDiscountName().setText(discount.getName());
    }

    @Override
    public int getItemCount() {
        return discounts.size();
    }

    public void clearDiscounts(){
        this.discounts.clear();
        notifyDataSetChanged();
    }
}
