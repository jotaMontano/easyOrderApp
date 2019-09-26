package com.moonshine.easyorder.PayOrder.View;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.moonshine.easyorder.R;

public class SelectProductHolder extends RecyclerView.ViewHolder{

    ImageView image;
    TextView name;
    ConstraintLayout layout;

    public SelectProductHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.selectedProductImage);
        name = itemView.findViewById(R.id.selectedProductText);
        layout = itemView.findViewById(R.id.layoutSelectProduct);
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public void setLayout(ConstraintLayout layout) {
        this.layout = layout;
    }

    public ImageView getImage() {
        return image;
    }

    public TextView getName() {
        return name;
    }

    public ConstraintLayout getLayout() {
        return layout;
    }
}
