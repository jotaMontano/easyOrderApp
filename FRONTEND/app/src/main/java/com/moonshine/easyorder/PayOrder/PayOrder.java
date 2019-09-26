package com.moonshine.easyorder.PayOrder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Chronometer;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.moonshine.easyorder.MainActivity;
import com.moonshine.easyorder.Models.OrderOk;
import com.moonshine.easyorder.Models.ProductByOrder;
import com.moonshine.easyorder.PayOrder.View.AdapterOrderProductsToPay;
import com.moonshine.easyorder.PayOrder.View.SelectProductAdapter;
import com.moonshine.easyorder.PayOrder.repository.PayOrderRepository;
import com.moonshine.easyorder.PayOrder.repository.PayOrderRepositoryImpl;
import com.moonshine.easyorder.R;
import com.moonshine.easyorder.expandable.product.model.Product2;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PayOrder extends AppCompatActivity implements PayOrderRepositoryImpl.PayOrderTaskListener{

    private Toolbar toolbar;
    private boolean payAll = true;
    private List<ProductByOrder> productByOrders = new ArrayList<>();
    private double totalAmount = 0;
    private AdapterOrderProductsToPay adapter;
    private SelectProductAdapter adapter2;
    private TextView totalAmountString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_order);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("  EASY ORDER");
        toolbar.setLogo(R.drawable.ic_easyorderw);
        setSupportActionBar(toolbar);
        totalAmountString = findViewById(R.id.totalToPay);
        for (int i = 0; i < MainActivity.productsForOrder.size(); i++){
            totalAmount += MainActivity.productsForOrder.get(i).getPrice() * MainActivity.productByOrders.get(i).getQuantity();
        }
        for (int j = 0; j < MainActivity.productByOrders.size(); j++){
            if (MainActivity.productByOrders.get(j).getExtrasInLine().size() > 0);
            for (int h = 0; h < MainActivity.productByOrders.get(j).getExtrasInLine().size(); h++){
                totalAmount += MainActivity.productByOrders.get(j).getExtrasInLine().get(h).getPrice() * MainActivity.productByOrders.get(j).getQuantity();
            }
        }
        DecimalFormat format = new DecimalFormat("###,###,###");
        totalAmountString.setText("₡ " +format.format(totalAmount));
        final RecyclerView recyclerView = findViewById(R.id.rvProductsToPay);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        setListProductByOrder();
        adapter = new AdapterOrderProductsToPay(setProducts(), setProductsByOrders(),this,totalAmount,totalAmountString);
        recyclerView.setAdapter(adapter);

        final RecyclerView recyclerView2 = findViewById(R.id.rvSelectProduct);
        recyclerView2.setLayoutManager( new GridLayoutManager(this, 3));
        adapter2 = new SelectProductAdapter(payAll, this, adapter, recyclerView, setProducts(),setProductsByOrders());
        recyclerView2.setAdapter(adapter2);
        RadioGroup group = findViewById(R.id.radioGroup);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.margin){
                    payAll = false;
                    adapter = new AdapterOrderProductsToPay(new ArrayList<>(), new ArrayList<>(),PayOrder.this,0,totalAmountString);
                    recyclerView.setAdapter(adapter);
                    setSelectedProductRecycler(recyclerView2, payAll, adapter, recyclerView);
                    DecimalFormat format = new DecimalFormat("###,###,###");
                    //setPayOrderRecycler(recyclerView, View.GONE);
                } else {
                    totalAmount = 0;
                    payAll = true;
                    setListProductByOrder();
                    adapter = new AdapterOrderProductsToPay(MainActivity.productsForOrder, productByOrders,PayOrder.this,totalAmount,totalAmountString);
                    recyclerView.setAdapter(adapter);
                    setSelectedProductRecycler(recyclerView2, payAll, adapter, recyclerView);
                    setPayOrderRecycler(recyclerView, View.VISIBLE);
                    for (int i = 0; i < MainActivity.productsForOrder.size(); i++){
                        totalAmount += MainActivity.productsForOrder.get(i).getPrice() * MainActivity.productByOrders.get(i).getQuantity();
                    }
                    for (int j = 0; j < MainActivity.productByOrders.size(); j++){
                        if (MainActivity.productByOrders.get(j).getExtrasInLine().size() > 0);
                        for (int h = 0; h < MainActivity.productByOrders.get(j).getExtrasInLine().size(); h++){
                            totalAmount += MainActivity.productByOrders.get(j).getExtrasInLine().get(h).getPrice() * MainActivity.productByOrders.get(j).getQuantity();
                        }
                    }
                    DecimalFormat format = new DecimalFormat("###,###,###");
                    totalAmountString.setText("₡ " +format.format(totalAmount));
                }
            }
        });
    }

    private void setSelectedProductRecycler(RecyclerView recycler, boolean payAll, AdapterOrderProductsToPay adapterProductToPay, RecyclerView recyclerView){
        adapter2 = new SelectProductAdapter(payAll,this, adapterProductToPay, recyclerView,setProducts(), setProductsByOrders());
        recycler.setAdapter(adapter2);
    }
    private void setPayOrderRecycler(RecyclerView recycler, int visible){
        recycler.setVisibility(visible);
    }
    private void setListProductByOrder(){
        for(int i = 0; i<MainActivity.productByOrders.size(); i++){
            ProductByOrder productByOrder = new ProductByOrder();
            productByOrder.setQuantity(MainActivity.productByOrders.get(i).getQuantity());
            this.productByOrders.add(productByOrder);
        }
    }
    public void payProducts(View view){
        double amount = 0;
        List<ProductByOrder> productByOrders = adapter.getProductByOrders();
        List<Product2> product2s = adapter.getProductsForOrder();
        Long idOrder = 0L;
        idOrder = productByOrders.get(0).getOrderOkId();
        if (product2s.size() > 0){
            for (int i = 0; i < product2s.size(); i++ ){
                amount += product2s.get(i).getPrice() * productByOrders.get(i).getQuantity();
            }
            for (int j = 0; j < productByOrders.size(); j++){
                if (productByOrders.get(j).getExtrasInLine().size() > 0);
                for (int h = 0; h < productByOrders.get(j).getExtrasInLine().size(); h++){
                    amount += productByOrders.get(j).getExtrasInLine().get(h).getPrice() * productByOrders.get(j).getQuantity();
                }
            }
            adapter.clear();
            totalAmount = totalAmount - amount;
            DecimalFormat format = new DecimalFormat("###,###,###");
            totalAmountString.setText("₡ " +format.format(totalAmount));

            if (totalAmount == 0){
                PayOrderRepository repository = new PayOrderRepositoryImpl(this);
                repository.findOrder(idOrder);
                finish();
                MainActivity.chronometer = null;
                MainActivity.productsForOrder.clear();
                MainActivity.productByOrders.clear();
                startActivity(new Intent(this, MainActivity.class));
            }
        }else {
            Toast.makeText(this,"No ha seleccionado un producto", Toast.LENGTH_SHORT).show();
        }

    }
    private List<Product2> setProducts(){
        List<Product2> product2s = new ArrayList<>();
        for (Product2 product2 : MainActivity.productsForOrder){
            product2s.add(product2);
        }
        return product2s;
    }
    private List<ProductByOrder> setProductsByOrders(){
        List<ProductByOrder> productByOrders = new ArrayList<>();
        for (ProductByOrder productByOrder : MainActivity.productByOrders){
            productByOrders.add(productByOrder);
        }
        return productByOrders;
    }

    @Override
    public void findOrder(OrderOk orderOk) {
        orderOk.setStatus(true);
        PayOrderRepository repository = new PayOrderRepositoryImpl(this);
        repository.updateOrderToPay(orderOk);
    }
}
