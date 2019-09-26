package com.moonshine.easyorder.expandable.product;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moonshine.easyorder.R;
import com.moonshine.easyorder.expandable.product.model.Product2;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.text.DecimalFormat;

public class ProductViewHolder extends ChildViewHolder {

    private TextView name;
    private TextView description;
    private TextView price;
    private ImageView image;
    private ConstraintLayout layout;
    private ConstraintLayout selected;
    private Context context;

    public ProductViewHolder(View itemView, Context context) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        description = itemView.findViewById(R.id.description);
        price = itemView.findViewById(R.id.price);
        image = itemView.findViewById(R.id.productPhoto);
        layout = itemView.findViewById(R.id.parentLayout);
        selected = itemView.findViewById(R.id.selectedProduct);
        this.context = context;
    }
    public void bind(Product2 product2){
        name.setText(product2.getName());
        description.setText(product2.getDescription());
        DecimalFormat format = new DecimalFormat("###,###,###");
        price.setText("₡ "+format.format(product2.getPrice()));
        Glide
                .with(this.context)
                .load(product2.getUrlImage())
                .into(image);
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getDescription() {
        return description;
    }

    public void setDescription(TextView description) {
        this.description = description;
    }

    public TextView getPrice() {
        return price;
    }

    public void setPrice(TextView price) {
        this.price = price;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public ConstraintLayout getLayout() {
        return layout;
    }

    public void setLayout(ConstraintLayout layout) {
        this.layout = layout;
    }

    public ConstraintLayout getSelected() {
        return selected;
    }

    public void setSelected(ConstraintLayout selected) {
        this.selected = selected;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
