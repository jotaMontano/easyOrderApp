package com.moonshine.easyorder.Products.view;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.moonshine.easyorder.Models.Product;
import com.moonshine.easyorder.R;

public class HolderProduct extends RecyclerView.ViewHolder{

    private int id;
    private TextView name;
    private TextView description;
    private TextView price;
    private ImageView image;
    private Product product;
    private ConstraintLayout layout;
    private ConstraintLayout selected;

    //variebles for productDetails
    private TextView productDetailName;
    private TextView productDetailDescription;
    private TextView productDetailPrice;

    public HolderProduct(View itemView1){
        super(itemView1);
        name = itemView1.findViewById(R.id.name);
        description = itemView1.findViewById(R.id.description);
        price = itemView1.findViewById(R.id.price);
        image = itemView1.findViewById(R.id.productPhoto);
        layout = itemView1.findViewById(R.id.parentLayout);
        selected = itemView1.findViewById(R.id.selectedProduct);

        productDetailName = itemView1.findViewById(R.id.productDetailName);
        productDetailDescription = itemView1.findViewById(R.id.productDetailDescription);
        
    }

    public ConstraintLayout getSelected() {
        return selected;
    }

    public void setSelected(ConstraintLayout selected) {
        this.selected = selected;
    }

    public TextView getProductDetailName() {
        return productDetailName;
    }

    public TextView getProductDetailDescription() {
        return productDetailDescription;
    }

    public TextView getProductDetailPrice() {
        return productDetailPrice;
    }

    public void setProductDetailName(TextView productDetailName) {
        this.productDetailName = productDetailName;
    }

    public void setProductDetailDescription(TextView productDetailDescription) {
        this.productDetailDescription = productDetailDescription;
    }

    public void setProductDetailPrice(TextView productDetailPrice) {
        this.productDetailPrice = productDetailPrice;
    }

    public void setLayout(ConstraintLayout layout) { this.layout = layout; }

    public ConstraintLayout getLayout() { return this.layout; }

    public void setId(int id) { this.id = id; }

    public int getId() { return id; }

    public void setName(TextView name) {
        this.name = name;
    }

    public void setDescription(TextView description) {
        this.description = description;
    }

    public void setPrice(TextView price) {
        this.price = price;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public void setProduct(Product product) { this.product = product; }

    public TextView getName() { return name; }

    public TextView getDescription() { return description; }

    public TextView getPrice() { return price; }

    public ImageView getImage() { return image; }

    public Product getProduct() { return  product; }

}
