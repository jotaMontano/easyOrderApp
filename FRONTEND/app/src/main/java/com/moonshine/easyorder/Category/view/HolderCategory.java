package com.moonshine.easyorder.Category.view;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.moonshine.easyorder.Models.Product;
import com.moonshine.easyorder.R;

import java.util.List;

public class HolderCategory extends RecyclerView.ViewHolder {

    private TextView name;
    private ConstraintLayout layout;
    private List<Product> listOfProducts;
    private ConstraintLayout selected;


    public HolderCategory(@NonNull View itemView, List<Product> listOfProducts) {
        super(itemView);
        name = itemView.findViewById(R.id.categoryName);
        layout = itemView.findViewById(R.id.categoryLayout);
        this.listOfProducts = listOfProducts;
        selected = itemView.findViewById(R.id.categorySelected);
    }

    public void setSelected(ConstraintLayout selected) {
        this.selected = selected;
    }

    public ConstraintLayout getSelected() {
        return selected;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public void setLayout(ConstraintLayout layout) {
        this.layout = layout;
    }

    public void setListOfProducts(List<Product> listOfProducts) {
        this.listOfProducts = listOfProducts;
    }

    public TextView getName() {
        return name;
    }

    public ConstraintLayout getLayout() {
        return layout;
    }

    public List<Product> getListOfProducts() {
        return listOfProducts;
    }


}
