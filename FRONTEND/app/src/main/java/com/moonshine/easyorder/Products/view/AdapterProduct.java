package com.moonshine.easyorder.Products.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moonshine.easyorder.Models.Product;
import com.moonshine.easyorder.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<HolderProduct> {

    private List<Product> products;
    private Context context;
    private TextView productDetailName;
    private TextView productDetailDescription;
    private TextView productDetailPrice;
    private ImageView productDetailImage;
    private HolderProduct currentProduct;
    private ProductTaskClickListener listener;

    public AdapterProduct(Context context, ArrayList<Product> listOfProduct, TextView productName, TextView productDetail, TextView productPrice, ImageView productImage, ProductTaskClickListener listener) {
        this.context = context;
        this.products = listOfProduct;
        productDetailName = productName;
        productDetailDescription = productDetail;
        productDetailPrice = productPrice;
        productDetailImage = productImage;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HolderProduct onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view1 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_holder, viewGroup, false);
        return new HolderProduct(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull final HolderProduct holderProduct, final int i) {
        final Product product = products.get(i);
        holderProduct.getName().setText(product.getName());
        holderProduct.getDescription().setText(product.getDescription());
        DecimalFormat format = new DecimalFormat("###,###,###");
        holderProduct.getPrice().setText("₡ "+format.format(product.getPrice()));
        holderProduct.setProduct(product);
        holderProduct.getLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = holderProduct.getAdapterPosition();
                setCurrentProduct(i, holderProduct);
                setProductDetails(i);
                listener.uploadExtras(products.get(i).getId(), i);
                listener.uploadDiscounts(products.get(i).getId(), i);
            }
        });
        if (i == 0){
            currentProduct = holderProduct;
            changeToSelectedColors(holderProduct);
            listener.uploadExtras(products.get(i).getId(),i);
            listener.uploadDiscounts(products.get(i).getId(),i);

        } else {
            changeToDefaultColors(holderProduct);
        }
        Glide
                .with(this.context)
                .load(product.getUrlImage())
                .into(holderProduct.getImage());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    private void setCurrentProduct(int i , HolderProduct newCurrentProduct){
        changeToDefaultColors(currentProduct);
        changeToSelectedColors(newCurrentProduct);
        setProductDetails(i);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setProductDetails(int i){
        productDetailName.setText(products.get(i).getName());
        productDetailDescription.setText(products.get(i).getDescription());
        DecimalFormat format = new DecimalFormat("###,###,###");
        productDetailPrice.setText("₡ "+format.format(products.get(i).getPrice()));
        Glide
                .with(this.context)
                .load(products.get(i).getUrlImage())
                .into(productDetailImage);
    }

    public void changeToSelectedColors( HolderProduct newCurrentProduct ){
        newCurrentProduct.getLayout().setBackgroundColor(ContextCompat.getColor(this.context, R.color.colorPrimary));
        newCurrentProduct.getName().setTextColor(ContextCompat.getColor(this.context, R.color.primaryTextWhite));
        newCurrentProduct.getDescription().setTextColor(ContextCompat.getColor(this.context, R.color.primaryTextWhite));
        newCurrentProduct.getPrice().setTextColor(ContextCompat.getColor(this.context, R.color.primaryTextWhite));
        newCurrentProduct.getSelected().setBackgroundColor(ContextCompat.getColor(this.context, R.color.selectedProduct));
        currentProduct = newCurrentProduct;
    }

    public void changeToDefaultColors(HolderProduct holderProduct){
        holderProduct.getLayout().setBackgroundColor(ContextCompat.getColor(this.context, R.color.primaryTextWhite));
        holderProduct.getName().setTextColor(ContextCompat.getColor(this.context, R.color.primaryTextBlack));
        holderProduct.getDescription().setTextColor(ContextCompat.getColor(this.context, R.color.primaryTextBlack));
        holderProduct.getPrice().setTextColor(ContextCompat.getColor(this.context, R.color.colorPrimary));
        holderProduct.getSelected().setBackgroundColor(ContextCompat.getColor(this.context, R.color.primaryTextWhite));
    }

    public interface ProductTaskClickListener {
        void uploadExtras(Long id, int i);
        void uploadDiscounts(Long id, int i);
    }



}
