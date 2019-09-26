package com.moonshine.easyorder.PayOrder.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moonshine.easyorder.MainActivity;
import com.moonshine.easyorder.Models.ProductByOrder;
import com.moonshine.easyorder.Products.view.HolderOrderProduct;
import com.moonshine.easyorder.R;
import com.moonshine.easyorder.expandable.product.model.Product2;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SelectProductAdapter extends RecyclerView.Adapter<SelectProductHolder> {

    private boolean payAll;
    private Context context;
    private AdapterOrderProductsToPay adapterOrderProductsToPay;
    private RecyclerView recyclerView;
    private List<ProductByOrder> productByOrderList = new ArrayList<>();
    private List<Product2> product2s = new ArrayList<>();
    private TextView amount;
    private int num = 0;
    private double price = 0;
    public SelectProductAdapter(boolean payAll, Context context, AdapterOrderProductsToPay adapterOrderProductsToPay, RecyclerView recyclerView, List<Product2> product2s, List<ProductByOrder> productByOrders) {
        this.payAll = payAll;
        this.context = context;
        this.adapterOrderProductsToPay = adapterOrderProductsToPay;
        this.recyclerView = recyclerView;
        this.amount = amount;
        this.product2s = product2s;
        this.productByOrderList = productByOrders;
    }

    @NonNull
    @Override
    public SelectProductHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.select_product_holder, viewGroup, false);
        return new SelectProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectProductHolder selectProductHolder, int i) {
        Product2 product = product2s.get(i);
        Glide
                .with(this.context)
                .load(product.getUrlImage())
                .into(selectProductHolder.getImage());
        selectProductHolder.getName().setText(product.getName()+"");
        if ( payAll ){
            selectProductHolder.getLayout().setBackgroundColor(ContextCompat.getColor(this.context, R.color.colorPrimary));
            selectProductHolder.getName().setTextColor(ContextCompat.getColor(this.context, R.color.primaryTextWhite));
        } else {
            selectProductHolder.getLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectProductHolder.getLayout().setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                    selectProductHolder.getName().setTextColor(ContextCompat.getColor(context, R.color.primaryTextWhite));
                    recyclerView.setVisibility(View.VISIBLE);
                    Product2 product2 = product2s.get(i);
                    ProductByOrder productByOrder = productByOrderList.get(i);
                    //productByOrderList.add(productByOrder);
//                    if (num < productByOrderList.size()){
//                        price += product2.getPrice() * productByOrder.getQuantity();
//                        if (productByOrder.getExtrasInLine().size() > 0){
//                            for (int j = 0; j < productByOrderList.get(i).getExtrasInLine().size(); j++){
//                                price += productByOrderList.get(i).getExtrasInLine().get(j).getPrice();
//                            }
//                        }
////                        DecimalFormat format = new DecimalFormat("###,###,###");
////                        amount.setText("₡ " +format.format(price));
                        adapterOrderProductsToPay.setProductByOrders(productByOrder,product2);
                        num++;
                        product2s.remove(i);
                        productByOrderList.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i, product2s.size());
//                    }
                }
            });
        }
    }

    public List<ProductByOrder> getProductByOrderList() {
        return productByOrderList;
    }

    public List<Product2> getProduct2s() {
        return product2s;
    }

    @Override
    public int getItemCount() {
        return product2s.size();
    }

    public void setPayAll(boolean payAll) {
        this.payAll = payAll;
    }
}
