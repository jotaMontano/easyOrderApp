package com.moonshine.easyorder.Products.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.moonshine.easyorder.R;

public class HolderOrderProduct extends RecyclerView.ViewHolder {

    private TextView prodoctName;
    private TextView productAmountOrder;
    private Button removeProduct;
    private Button addProduct;

    public HolderOrderProduct(@NonNull View itemView) {
        super(itemView);
        prodoctName = itemView.findViewById(R.id.isChecked);
        productAmountOrder = itemView.findViewById(R.id.productAmountOrder);
        removeProduct = itemView.findViewById(R.id.removeProductOrder);
        addProduct = itemView.findViewById(R.id.addProductOrder);
    }

    public void setProdoctName(TextView prodoctName) {
        this.prodoctName = prodoctName;
    }

    public TextView getProdoctName() {
        return prodoctName;
    }
    public void setProductAmountOrder(TextView productAmountOrder) {
        this.productAmountOrder = productAmountOrder;
    }

    public TextView getProductAmountOrder() {
        return productAmountOrder;
    }

    public Button getRemoveProduct() {
        return removeProduct;
    }
    public Button getAddProduct() {
        return addProduct;
    }
}
