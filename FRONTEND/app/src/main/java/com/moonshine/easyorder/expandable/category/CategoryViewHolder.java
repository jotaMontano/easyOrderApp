package com.moonshine.easyorder.expandable.category;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.moonshine.easyorder.R;
import com.moonshine.easyorder.expandable.category.model.Category2;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;


public class CategoryViewHolder extends GroupViewHolder {

    private TextView name;
    private ConstraintLayout layout;
    private ConstraintLayout selected;
    private Context context;

    public CategoryViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        name = itemView.findViewById(R.id.categoryName);
        layout = itemView.findViewById(R.id.categoryLayout);
        selected = itemView.findViewById(R.id.categorySelected);
    }
    public void bind(Category2 category2){
        name.setText(category2.getTitle());
    }

    @Override
    public void expand() {
        super.expand();
        getLayout().setBackgroundColor(ContextCompat.getColor(this.context, R.color.colorPrimaryDark));
        getName().setTextColor(ContextCompat.getColor(this.context, R.color.primaryTextWhite));
        getSelected().setBackgroundColor(ContextCompat.getColor(context, R.color.selectedProduct));
    }

    @Override
    public void collapse() {
        super.collapse();
        getLayout().setBackgroundColor(ContextCompat.getColor(this.context, R.color.primaryTextWhite));
        getName().setTextColor(ContextCompat.getColor(this.context, R.color.primaryTextBlack));
        getSelected().setBackgroundColor(ContextCompat.getColor(context, R.color.primaryTextWhite));
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
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
}
