package com.moonshine.easyorder.Products.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moonshine.easyorder.Models.Product;
import com.moonshine.easyorder.Models.ProductByOrder;
import com.moonshine.easyorder.R;
import com.moonshine.easyorder.expandable.product.model.Product2;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterOrderProducts extends RecyclerView.Adapter<HolderOrderProduct> {

    private List<Product2> productsForOrder;
    private List<ProductByOrder> productByOrders = new ArrayList<>();
    private Context context;
    private double totalAmount;
    private TextView amountToPay;

    public AdapterOrderProducts(List<Product2> listProductForOrder,List<ProductByOrder> productByOrderList, Context context, double totalAmount, TextView amountToPay) {
        this.productsForOrder = listProductForOrder;
        this.productByOrders = productByOrderList;
        this.context = context;
        this.totalAmount = totalAmount;
        this.amountToPay = amountToPay;
    }

    @NonNull
    @Override
    public HolderOrderProduct onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_products, viewGroup, false);
        return new HolderOrderProduct(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderOrderProduct holderOrderProduct, int i) {
        Product2 product = productsForOrder.get(i);
        ProductByOrder productByOrder = productByOrders.get(i);
        holderOrderProduct.getProdoctName().setText(product.getName());
        holderOrderProduct.getProductAmountOrder().setText(productByOrder.getQuantity()+"");
        TextView amount = holderOrderProduct.getProductAmountOrder();
        holderOrderProduct.getAddProduct().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct(holderOrderProduct.getAdapterPosition(), amount);
            }
        });
        holderOrderProduct.getRemoveProduct().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeProduct(holderOrderProduct.getAdapterPosition(), amount);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsForOrder.size();
    }

    private void addProduct(int i, TextView amount){
        int amountInt = Integer.parseInt(amount.getText()+"");
        amountInt++;
        amount.setText(amountInt+"");
        productByOrders.get(i).setQuantity(amountInt);
        this.totalAmount += productsForOrder.get(i).getPrice();
        DecimalFormat format = new DecimalFormat("###,###,###");
        this.amountToPay.setText("Total: ₡ "+format.format(totalAmount));
    }
    private void removeProduct(int i, TextView amount){
        int amountInt = Integer.parseInt(amount.getText()+"");
        if (amountInt > 1){
            amountInt--;
            amount.setText(amountInt+"");
            productByOrders.get(i).setQuantity(amountInt);
            this.totalAmount -= productsForOrder.get(i).getPrice();
            DecimalFormat format = new DecimalFormat("###,###,###");
            this.amountToPay.setText("Total: ₡ "+format.format(totalAmount));
        }
    }
}
