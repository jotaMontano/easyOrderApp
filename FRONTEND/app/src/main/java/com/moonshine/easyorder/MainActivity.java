package com.moonshine.easyorder;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.moonshine.easyorder.Category.repository.CategoryRepositoryImpl;
import com.moonshine.easyorder.ExtraInLine.ExtraInLineRepository;
import com.moonshine.easyorder.ExtraInLine.ExtraInLineRepositoryImp;
import com.moonshine.easyorder.Models.Category;
import com.moonshine.easyorder.Models.Client;
import com.moonshine.easyorder.Models.Discount;
import com.moonshine.easyorder.Models.Extra;
import com.moonshine.easyorder.Models.ExtraInLine;
import com.moonshine.easyorder.Models.OrderOk;
import com.moonshine.easyorder.Models.Product;
import com.moonshine.easyorder.Category.view.AdapterCategory;
import com.moonshine.easyorder.Models.ProductByOrder;
import com.moonshine.easyorder.OrderOk.OrderOkRepositoryImp;
import com.moonshine.easyorder.OrderOk.OrderOkRepository;
import com.moonshine.easyorder.Products.repository.ProductRepository;
import com.moonshine.easyorder.Products.repository.ProductRepositoryImpl;
import com.moonshine.easyorder.Products.view.AdapterExtra;
import com.moonshine.easyorder.Products.view.AdapterDiscount;
import com.moonshine.easyorder.Products.view.AdapterOrderProducts;
import com.moonshine.easyorder.Products.view.AdapterProduct;
import com.moonshine.easyorder.discount.repository.DiscountRepository;
import com.moonshine.easyorder.discount.repository.DiscountRepositoryImp;
import com.moonshine.easyorder.expandable.Adapter;
import com.moonshine.easyorder.expandable.category.model.Category2;
import com.moonshine.easyorder.expandable.product.model.Product2;
import com.moonshine.easyorder.extra.repository.ExtraRepository;
import com.moonshine.easyorder.extra.repository.ExtraRepositoryImp;
import com.moonshine.easyorder.login.repository.loginRepository;
import com.moonshine.easyorder.login.repository.loginRepositoryImpl;
import com.moonshine.easyorder.login.view.loginActivity;
import com.moonshine.easyorder.productByOrder.ProductByOrderRepository;
import com.moonshine.easyorder.productByOrder.ProductByOrderRepositoryImp;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.moonshine.easyorder.top.TopActivity;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.moonshine.easyorder.login.view.loginActivity.removeSharePreference;

public class MainActivity extends AppCompatActivity implements CategoryRepositoryImpl.CategoryServiceListener, ProductRepositoryImpl.ProductServiceListener, ExtraRepositoryImp.ExtraTaskServiceListener, DiscountRepositoryImp.DiscountTaskServiceListener, AdapterProduct.ProductTaskClickListener, AdapterCategory.CategoryTaskClickListener, OrderOkRepositoryImp.OrderOkTaskListener, ProductByOrderRepositoryImp.ProductByOrderTaskListener, ExtraInLineRepositoryImp.ExtraInLineTaskListener, loginRepositoryImpl.UserTaskServiceListener, Adapter.ProductTaskClickListener {

    public static Chronometer chronometer;
    private TextView productDetailName;
    private TextView productDetailDescription;
    private TextView productDetailPrice;
    private ImageView productDetailImage;

    //adapters
    private AdapterExtra adapterExtra;
    private AdapterDiscount adapterDiscount;
    private Adapter adapter;

    //services to get categories, products and extras
    private CategoryRepositoryImpl categoryRepository;
    private List<Extra> extras =  new ArrayList<>();
    private List<Discount> discounts =  new ArrayList<>();
    private List<Product> top;
    public static List<Product2> productsForOrder = new ArrayList<>();
    public static List<ProductByOrder> productByOrders = new ArrayList<>();
    private int iProduct = 0;
    private ExpandableGroup iGroup = null;
    private Client client;
    private boolean existsProducts = false;
    Toolbar toolbar;

    private List<Category2> listCategories = new ArrayList<>();
    private int numCategories = 0;
    private int iCategories = 0;
    private boolean selectProduct = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("  EASY ORDER");
        toolbar.setLogo(R.drawable.ic_easyorderw);
        setSupportActionBar(toolbar);

        productDetailName = findViewById(R.id.productDetailName);
        productDetailDescription = findViewById(R.id.productDetailDescription);
        productDetailPrice = findViewById(R.id.productDetailPrice);
        productDetailImage = findViewById(R.id.productDetailPhoto);

        loginRepository repository = new loginRepositoryImpl(this,this);
        repository.getAccount();

        //Set onClickLiesteners from add and remove buttons
        final Button addButton = findViewById(R.id.addAmount);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAmount();
            }
        });
        final Button removeButton = findViewById(R.id.removeAmount);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAmount();
            }
        });

        final Button addProduct = findViewById(R.id.btnAddProduct);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (existsProducts && selectProduct){
                    int i = adapter.getGroups().indexOf(iGroup);
                    Product2 product2 =  (Product2) adapter.getGroups().get(i).getItems().get(iProduct);
                    if (productsForOrder.contains(product2)) {
                        Toast.makeText(MainActivity.this, "Ya agrego este producto",Toast.LENGTH_SHORT).show();
                    }else {
                        productsForOrder.add((Product2) adapter.getGroups().get(i).getItems().get(iProduct));
                        ProductByOrder productByOrder = new ProductByOrder();
                        TextView productAmount = findViewById(R.id.productAmount);
                        TextView comment = findViewById(R.id.procuctDetailSpecialInstruction);
                        productByOrder.setQuantity(Integer.parseInt(productAmount.getText().toString()));
                        if (!comment.getText().toString().isEmpty()) {
                            productByOrder.setComment(comment.getText().toString());
                        }else {
                            productByOrder.setComment("");
                        }
                        productByOrder.setProductsId(productsForOrder.get(productsForOrder.size()-1).getId());
                        productByOrder.setStatus(false);
                        if (adapterExtra.getExtrasSelect().size() > 0){
                            productByOrder.setExtrasInLine(adapterExtra.getExtrasSelect());
                        }
                        productByOrders.add(productByOrder);
                        Toast.makeText(MainActivity.this,"Producto "+product2.getName()+" agregado",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,"No ha seleccionado ningun producto",Toast.LENGTH_SHORT).show();

                }

            }
        });
        final Button createOrder = findViewById(R.id.btnOrder);
        createOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productByOrders.size() > 0){
                    alertCheckOrder();
                }else {
                    Toast.makeText(MainActivity.this,"No hay productos agregados a la orden",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void alertCheckOrder(){
        LayoutInflater inflater= getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_pop_order, null);
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerOrderProducts);
        double totalAmount = 0;
        for (int i = 0; i < productsForOrder.size();i++){
            totalAmount += productsForOrder.get(i).getPrice() * productByOrders.get(i).getQuantity();
        }
        for (int j = 0; j < productByOrders.size(); j++){
            if (productByOrders.get(j).getExtrasInLine().size() > 0);
            for (int h = 0; h < productByOrders.get(j).getExtrasInLine().size(); h++){
                totalAmount += productByOrders.get(j).getExtrasInLine().get(h).getPrice() * productByOrders.get(j).getQuantity();
            }
        }
        final TextView totalAmountString = view.findViewById(R.id.amountToPay);
        DecimalFormat format = new DecimalFormat("###,###,###");
        totalAmountString.setText(totalAmountString.getText()+"₡ "+format.format(totalAmount));
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        AdapterOrderProducts adapter= new AdapterOrderProducts(this.productsForOrder, this.productByOrders,this,totalAmount,totalAmountString);
        recyclerView.setAdapter(adapter);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.popupTitle);
        alert.setView(view);
        alert.setCancelable(false);
        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OrderOk orderOk = new OrderOk();
                orderOk.setClientId(client.getId());
                orderOk.setStatus(false);
                orderOk.setTotal(0.0);
                OrderOkRepository repository = new OrderOkRepositoryImp(MainActivity.this, MainActivity.this);
                repository.createOrderOK(orderOk);
                openWaitingView();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
    private void openWaitingView(){
        Intent intent = new Intent(this, WaitingView.class);
        int maxEstimatedTime = 0;
        for (int i = 0; i<productsForOrder.size();i++){
            if (productsForOrder.get(i).getWaitAverage()>maxEstimatedTime){
                maxEstimatedTime = productsForOrder.get(i).getWaitAverage();
            }
        }
        intent.putExtra("MaxEstimatedTime",maxEstimatedTime);
        startActivity(intent);
    }
    private List<Extra> createListOfExtras(){
        return extras;
    }

    private List<Discount> createListOfDiscounts(){
        return discounts;
    }

    private void setFirstProduct(Product product){

        productDetailName.setText(product.getName());
        productDetailDescription.setText(product.getDescription());
        DecimalFormat format = new DecimalFormat("###,###,###");
        productDetailPrice.setText("₡ "+format.format(product.getPrice()));
        Glide
                .with(this)
                .load(product.getUrlImage())
                .into(productDetailImage);

    }

    private void addAmount(){
        TextView productAmount = findViewById(R.id.productAmount);
        int amount = Integer.parseInt(productAmount.getText().toString())+1;
        productAmount.setText(amount+"");
    }

    private void removeAmount(){
        TextView productAmount = findViewById(R.id.productAmount);
        int amount = Integer.parseInt(productAmount.getText().toString());
        if (amount > 1){
            productAmount.setText((amount-1)+"");
        }
    }

    @Override
    public void onGetTaskByExtras(List<Extra> extras) {
        RecyclerView rvProducts = findViewById(R.id.rvExtras);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvProducts.setLayoutManager(manager);
        rvProducts.setHasFixedSize(true);
        AdapterExtra adapterExtra = new AdapterExtra(extras,this);
        this.adapterExtra = adapterExtra;
        rvProducts.setAdapter(this.adapterExtra);
    }

    @Override
    public void uploadExtras(Long id, ExpandableGroup g, int i, boolean selectProduct) {
        this.selectProduct = selectProduct;
        this.iProduct = i;
        this.iGroup = g;
        ExtraRepository repository = new ExtraRepositoryImp(this,MainActivity.this);
        repository.getExtrasByClient(id);
    }

    @Override
    public void onGetTaskByDiscounts(List<Discount> discounts) {
        RecyclerView rvProducts = findViewById(R.id.rvDiscounts);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvProducts.setLayoutManager(manager);
        rvProducts.setHasFixedSize(true);
        AdapterDiscount adapterDiscount = new AdapterDiscount(discounts,this);
        this.adapterDiscount = adapterDiscount;
        rvProducts.setAdapter(this.adapterDiscount);
    }

    @Override
    public void uploadDiscounts(Long id, ExpandableGroup g, int i) {
        this.iProduct = i;
        this.iGroup = g;
        DiscountRepository repository = new DiscountRepositoryImp(this,MainActivity.this);
        repository.getDiscountsByClient(id);
    }
    //Metodos para llenar la lista de categorias
    @Override
    public void OnGetCategoryByClientSuccess(List<Category> categories) {
        numCategories = categories.size();
        for (Category item : categories){
            ProductRepository repository = new ProductRepositoryImpl(this,this, item.getName());
            repository.getProductsByCategory(item.getId());
        }
    }

    @Override
    public void OnCategoryByClientError(String error) {
        Toast.makeText(this,error,Toast.LENGTH_LONG).show();
    }

    //Metodos para llenar la lista de productos
    @Override
    public void OnGetProductByCategorySuccess(ArrayList<Product> products, Long id, String nameCategory) {
        iCategories++;
        List<Product2> listProduct2s = new ArrayList<>();
        for (Product product : products){
            Product2 product2 = new Product2(product.getId(),product.getName(),product.getDescription(),product.getPrice(),product.getWaitAverage(),product.getUrlImage(),product.getType(),product.getStatus(),product.getCategoriesId(),product.getClientId());
            listProduct2s.add(product2);
        }
        Category2 category2 = new Category2(nameCategory, listProduct2s);
        listCategories.add(category2);

        if (iCategories == numCategories){
            RecyclerView recyclerView = findViewById(R.id.rvCombos);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            this.adapter = new Adapter(listCategories, this, productDetailName, productDetailDescription, productDetailPrice, productDetailImage,this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
        }
        if (products.size() > 0) {
            existsProducts = true;
        }

    }
    @Override
    public void OnProductByCategoryError(String error) {
        Toast.makeText(this,error,Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnGetProductBySuccess(ArrayList<Product> listProducts) {
        top = listProducts;
        Menu menu = toolbar.getMenu();
        MenuItem register = menu.findItem(R.id.top);
        register.setVisible(top.size() >= 1);  //u

    }

    @Override
    public void OnProductByError(String error) {
        Menu menu = toolbar.getMenu();
        MenuItem register = menu.findItem(R.id.top);
        register.setVisible(top != null);  //u

    }

    @Override
    public void uploadProducts(Long id) {
    }

    @Override
    public void onCreateOrderOk(OrderOk orderOk) {
        Double total = 0.0;
        OrderOkRepository repository2 = new OrderOkRepositoryImp(this,this);
        ProductByOrderRepository repository = new ProductByOrderRepositoryImp(this,this);
        for (ProductByOrder item : this.productByOrders){
            item.setOrderOkId(orderOk.getId());
            repository.createProductByOrder(item);
            for(int i = 0; i < this.productsForOrder.size(); i++){
                if (productsForOrder.get(i).getId() == item.getProductsId()){
                    if (item.getExtrasInLine().size() > 0){
                        for (Extra extra: item.getExtrasInLine()){
                            total+= extra.getPrice() * item.getQuantity();
                        }
                    }
                    total += productsForOrder.get(i).getPrice() * item.getQuantity();
                }
            }
        }
        orderOk.setTotal(total);
        repository2.updateOrderOk(orderOk);
//        productByOrders.clear();
//        productsForOrder.clear();
    }

    @Override
    public void onCreateProductByOrder(ProductByOrder productByOrder, ProductByOrder productByOrder2) {
        ExtraInLineRepository repository = new ExtraInLineRepositoryImp(this,this);
        if (productByOrder2.getExtrasInLine().size() > 0){
            for (Extra extra : productByOrder2.getExtrasInLine()){
                ExtraInLine extraInLine = new ExtraInLine();
                extraInLine.setExtraId(extra.getId());
                extraInLine.setProductByOrderId(productByOrder.getId());
                repository.createExtraInLine(extraInLine);
            }
        }
        Toast.makeText(MainActivity.this,"Su orden a sido agregada",Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                removeSharePreference(this);
                startActivity(new Intent(this, loginActivity.class));
                finish();
                break;

            case R.id.top:
                startActivity(new Intent(this, TopActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnGetTaskByClientSuccess(Client client) {
        this.client = client;
        categoryRepository = new CategoryRepositoryImpl(this, this);
        categoryRepository.getCategoriesByClient(client.getId());

        ProductRepository repository = new ProductRepositoryImpl(this,this);
        repository.getProductsTop(this.client.getId());
    }

    @Override
    public void OnGetTaskByUserError(String error) {

    }

    @Override
    public void uploadExtras(Long id, int i) {

    }

    @Override
    public void uploadDiscounts(Long id, int i) {

    }
}