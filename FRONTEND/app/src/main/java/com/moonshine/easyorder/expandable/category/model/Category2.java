package com.moonshine.easyorder.expandable.category.model;

import com.moonshine.easyorder.expandable.product.model.Product2;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Category2 extends ExpandableGroup<Product2> {
    public Category2(String title, List<Product2> items) {
        super(title, items);
    }
}
