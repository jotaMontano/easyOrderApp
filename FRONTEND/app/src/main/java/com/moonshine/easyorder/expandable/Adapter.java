package com.moonshine.easyorder.expandable;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moonshine.easyorder.expandable.category.model.Category2;
import com.moonshine.easyorder.expandable.product.model.Product2;
import com.moonshine.easyorder.R;
import com.moonshine.easyorder.expandable.category.CategoryViewHolder;
import com.moonshine.easyorder.expandable.product.ProductViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.text.DecimalFormat;
import java.util.List;

public class Adapter extends ExpandableRecyclerViewAdapter<CategoryViewHolder, ProductViewHolder> {
    private Context context;
    private CategoryViewHolder currentCategory;
    private ProductViewHolder currentProduct;
    private Product2 cproduct;
    private TextView productDetailName;
    private TextView productDetailDescription;
    private TextView productDetailPrice;
    private ImageView productDetailImage;
    private ProductTaskClickListener listener;
    public Adapter(List<? extends ExpandableGroup> groups, Context context, TextView productName, TextView productDetail, TextView productPrice, ImageView productImage,ProductTaskClickListener listener) {
        super(groups);
        this.context = context;
        this.listener = listener;
        productDetailName = productName;
        productDetailDescription = productDetail;
        productDetailPrice = productPrice;
        productDetailImage = productImage;
    }

    @Override
    public CategoryViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_holder,parent, false);
        return new CategoryViewHolder(view, parent.getContext());
    }

    @Override
    public ProductViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_holder, parent, false);
        return new ProductViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindChildViewHolder(ProductViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Product2 product2 = (Product2) group.getItems().get(childIndex);
        holder.bind(product2);
        holder.getLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = holder.getAdapterPosition();
                setCurrentProduct(holder, product2);
                setProductDetails(product2);
                listener.uploadExtras(product2.getId(), group, childIndex, true);
                listener.uploadDiscounts(product2.getId(), group, childIndex);
            }
        });
        changeToDefaultColors(holder);
//        if (childIndex == 0){
//            currentProduct = holder;
//            changeToSelectedColors(holder);
//            listener.uploadExtras(product2.getId(),childIndex);
//
//        } else {
//            changeToDefaultColors(holder);
//        }
    }
    private void setCurrentProduct(ProductViewHolder newCurrentProduct, final Product2 product2){
        if (currentProduct != null){
            changeToDefaultColors(currentProduct);
        }
        changeToSelectedColors(newCurrentProduct);
        setProductDetails(product2);
    }
    private void setProductDetails(final Product2 product2){
        productDetailName.setText(product2.getName());
        productDetailDescription.setText(product2.getDescription());
        DecimalFormat format = new DecimalFormat("###,###,###");
        productDetailPrice.setText("₡ "+format.format(product2.getPrice()));
        Glide
                .with(this.context)
                .load(product2.getUrlImage())
                .into(productDetailImage);
    }

    public void changeToSelectedColors( ProductViewHolder newCurrentProduct ){
        newCurrentProduct.getLayout().setBackgroundColor(ContextCompat.getColor(this.context, R.color.colorPrimary));
        newCurrentProduct.getName().setTextColor(ContextCompat.getColor(this.context, R.color.primaryTextWhite));
        newCurrentProduct.getDescription().setTextColor(ContextCompat.getColor(this.context, R.color.primaryTextWhite));
        newCurrentProduct.getPrice().setTextColor(ContextCompat.getColor(this.context, R.color.primaryTextWhite));
        newCurrentProduct.getSelected().setBackgroundColor(ContextCompat.getColor(this.context, R.color.selectedProduct));
        currentProduct = newCurrentProduct;
    }

    public void changeToDefaultColors(ProductViewHolder holderProduct){
        holderProduct.getLayout().setBackgroundColor(ContextCompat.getColor(this.context, R.color.primaryTextWhite));
        holderProduct.getName().setTextColor(ContextCompat.getColor(this.context, R.color.primaryTextBlack));
        holderProduct.getDescription().setTextColor(ContextCompat.getColor(this.context, R.color.primaryTextBlack));
        holderProduct.getPrice().setTextColor(ContextCompat.getColor(this.context, R.color.colorPrimary));
        holderProduct.getSelected().setBackgroundColor(ContextCompat.getColor(this.context, R.color.primaryTextWhite));
    }

    @Override
    public void onBindGroupViewHolder(CategoryViewHolder holder, int flatPosition, ExpandableGroup group) {
        final Category2 category2 = (Category2) group;
        holder.bind(category2);
    }
    public interface ProductTaskClickListener {
        void uploadExtras(Long id, ExpandableGroup g, int i, boolean selectProduct);
        void uploadDiscounts(Long id, ExpandableGroup g, int i);
    }

    @Override
    public void onGroupCollapsed(int positionStart, int itemCount) {
        super.onGroupCollapsed(positionStart, itemCount);
        this.currentProduct = null;
    }
    @Override
    public List<? extends ExpandableGroup> getGroups() {
        return super.getGroups();
    }
}
