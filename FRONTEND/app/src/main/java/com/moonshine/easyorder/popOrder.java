package com.moonshine.easyorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.moonshine.easyorder.Models.ProductByOrder;
import com.moonshine.easyorder.Products.view.AdapterOrderProducts;
import com.moonshine.easyorder.expandable.product.model.Product2;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class popOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_order);
    }
}
