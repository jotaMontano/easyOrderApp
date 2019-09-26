package com.moonshine.easyorder.Category.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moonshine.easyorder.Models.Category;
import com.moonshine.easyorder.Products.view.AdapterProduct;
import com.moonshine.easyorder.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterCategory extends RecyclerView.Adapter<HolderCategory>{

    private List<Category> categories;
    private Context context;
    private HolderCategory currentCategory;
    private AdapterProduct adapterProduct;
    private CategoryTaskClickListener listener;
    public AdapterCategory(List<Category> arrayOfCategories, Context context, CategoryTaskClickListener listener) {
        this.categories = arrayOfCategories;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HolderCategory onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_holder, viewGroup, false);
        return new HolderCategory(view,categories.get(i).getListOfProducts());
    }

    @Override
    public void onBindViewHolder(@NonNull final HolderCategory holderCategory, int i) {
        final Category category = categories.get(i);
        holderCategory.getName().setText(category.getName());
        holderCategory.getLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = holderCategory.getAdapterPosition();
                changeCurrentCategory(i, holderCategory);
                listener.uploadProducts(categories.get(i).getId());
            }
        });
        if (i == 0){
            changeToSelectedColors(holderCategory);
            listener.uploadProducts(categories.get(i).getId());
        }
    }

    private void changeCurrentCategory(int i, HolderCategory newHolderCategory){
        changeToDefaultColors();
        changeToSelectedColors(newHolderCategory);
    }

    public void changeToDefaultColors(){
        currentCategory.getLayout().setBackgroundColor(ContextCompat.getColor(this.context, R.color.primaryTextWhite));
        currentCategory.getName().setTextColor(ContextCompat.getColor(this.context, R.color.primaryTextBlack));
        currentCategory.getSelected().setBackgroundColor(ContextCompat.getColor(context, R.color.primaryTextWhite));
    }

    public void changeToSelectedColors(HolderCategory holderCategory){
        holderCategory.getLayout().setBackgroundColor(ContextCompat.getColor(this.context, R.color.colorAccent));
        holderCategory.getName().setTextColor(ContextCompat.getColor(this.context, R.color.primaryTextWhite));
        holderCategory.getSelected().setBackgroundColor(ContextCompat.getColor(context, R.color.selectedProduct));
        currentCategory = holderCategory;

    }

    public void setAdapterProduct(AdapterProduct adapterProduct) {
        this.adapterProduct = adapterProduct;
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public interface CategoryTaskClickListener {
        void uploadProducts(Long id);
    }
}
