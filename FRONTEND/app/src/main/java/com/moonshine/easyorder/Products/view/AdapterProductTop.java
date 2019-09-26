package com.moonshine.easyorder.Products.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.moonshine.easyorder.Models.Product;
import com.moonshine.easyorder.R;

import java.util.List;

public class AdapterProductTop extends PagerAdapter {
    private List<Product> products;
    private LayoutInflater layoutInflater;
    private Context context;


    public AdapterProductTop(List<Product> products, Context context){
        this.products = products;
        this.context = context;
    }
    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_cardview, container, false);

        ImageView imageView = view.findViewById(R.id.imageCardView);
        TextView title,desc;

        title = view.findViewById(R.id.title);
        desc = view.findViewById(R.id.desc);

        Glide
                .with(this.context)
                .load(products.get(position).getUrlImage())
                .into(imageView);

        title.setText(products.get(position).getName());
        desc.setText(products.get(position).getDescription());

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
