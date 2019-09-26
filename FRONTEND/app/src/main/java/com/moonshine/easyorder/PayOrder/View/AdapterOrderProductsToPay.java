package com.moonshine.easyorder.PayOrder.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moonshine.easyorder.MainActivity;
import com.moonshine.easyorder.Models.ProductByOrder;
import com.moonshine.easyorder.Products.view.HolderOrderProduct;
import com.moonshine.easyorder.R;
import com.moonshine.easyorder.expandable.product.model.Product2;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterOrderProductsToPay extends RecyclerView.Adapter<HolderOrderProduct>{
    private List<Product2> productsForOrder = new ArrayList<>();
    private List<ProductByOrder> productByOrders = new ArrayList<>();
    private Context context;
    private double totalAmount;
    private TextView amountToPay;

    public AdapterOrderProductsToPay(List<Product2> listProductForOrder,List<ProductByOrder> productByOrderList, Context context, double totalAmount, TextView amountToPay) {
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

    public void setProductByOrders(ProductByOrder productByOrders, Product2 product2) {
        this.productsForOrder.add(product2);
        this.productByOrders.add(productByOrders);
        notifyDataSetChanged();
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

    public void clear(){
        this.productByOrders.clear();
        this.productsForOrder.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return productsForOrder.size();
    }

    private void addProduct(int i, TextView amount){
        int amountInt = Integer.parseInt(amount.getText()+"");
        if (amountInt < MainActivity.productByOrders.get(i).getQuantity()){
            amountInt++;
            amount.setText(amountInt+"");
            productByOrders.get(i).setQuantity(amountInt);
            this.totalAmount += productsForOrder.get(i).getPrice();
            DecimalFormat format = new DecimalFormat("###,###,###");
            this.amountToPay.setText("Total: ₡ "+format.format(totalAmount));
        }
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

    public List<ProductByOrder> getProductByOrders() {
        return productByOrders;
    }

    public List<Product2> getProductsForOrder() { return productsForOrder; }
}
