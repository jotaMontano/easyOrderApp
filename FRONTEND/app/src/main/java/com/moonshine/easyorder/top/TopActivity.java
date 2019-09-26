package com.moonshine.easyorder.top;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.Resource;
import com.moonshine.easyorder.Category.repository.CategoryRepositoryImpl;
import com.moonshine.easyorder.MainActivity;
import com.moonshine.easyorder.Models.Client;
import com.moonshine.easyorder.Models.Product;
import com.moonshine.easyorder.Products.repository.ProductRepository;
import com.moonshine.easyorder.Products.repository.ProductRepositoryImpl;
import com.moonshine.easyorder.Products.view.AdapterProduct;
import com.moonshine.easyorder.Products.view.AdapterProductTop;
import com.moonshine.easyorder.R;
import com.moonshine.easyorder.login.repository.loginRepository;
import com.moonshine.easyorder.login.repository.loginRepositoryImpl;
import com.moonshine.easyorder.login.view.loginActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import static android.widget.Toast.LENGTH_SHORT;
import static com.moonshine.easyorder.login.view.loginActivity.removeSharePreference;

public class TopActivity extends AppCompatActivity implements ProductRepositoryImpl.ProductServiceListener,  loginRepositoryImpl.UserTaskServiceListener {

    private ViewPager viewPager;
    private AdapterProductTop adapterProduct;
    private List<Product> products;
    private Integer[] colors = null;
    private Client client;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private ConstraintLayout constraintLayout;
    private LinearLayout LinearLayoutDots;
    private TextView[] mDots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("  EASY ORDER");
        toolbar.setLogo(R.drawable.ic_easyorderw);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));

        loginRepository LogRepository = new loginRepositoryImpl(this,this);
        LogRepository.getAccount();

        constraintLayout  = (ConstraintLayout) findViewById(R.id.constraintOrder);
        LinearLayoutDots  = (LinearLayout) findViewById(R.id.constraintDouts);
        Button btnGoToOrder  = (Button) findViewById(R.id.btnOrder);

        btnGoToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goOrder();
            }
        });


    }
    public void goOrder() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
    public void OnGetProductByCategorySuccess(ArrayList<Product> products, Long id, String nameCategory) {

    }

    @Override
    public void OnProductByCategoryError(String error) {

    }

    @Override
    public void OnGetProductBySuccess(ArrayList<Product> listProducts) {
        products = new ArrayList<>();
        products = listProducts;
        adapterProduct = new AdapterProductTop(products, this);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapterProduct);
        viewPager.setPadding(0,0,0,0);
//        viewPager.addOnPageChangeListener(viewListener);
        addDots(0);
        Integer [] colors_temp = {
                getResources().getColor(R.color.color1)

        };

        colors = colors_temp;
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < (adapterProduct.getCount() - 1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                    constraintLayout.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                }else{
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                    constraintLayout.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {
                addDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public void addDots(int position) {
        mDots = new TextView[products.size()];

        if(((LinearLayout) LinearLayoutDots).getChildCount() > 0) {
            LinearLayoutDots.removeAllViews();
        }

        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.whiteTransparent));



            LinearLayoutDots.addView(mDots[i]);
        }

        if (mDots.length > 0 ) {
            mDots[position].setTextColor(getResources().getColor(R.color.colorLightPrimary));
        }
    }
    @Override
    public void OnProductByError(String error) {
        Toast.makeText(this, error, LENGTH_SHORT).show();
    }

    @Override
    public void OnGetTaskByClientSuccess(Client client) {
        this.client = client;
        ProductRepository repository = new ProductRepositoryImpl(this,this);
        repository.getProductsTop(this.client.getId());
    }

    @Override
    public void OnGetTaskByUserError(String error) {
        Toast.makeText(this, error, LENGTH_SHORT).show();
    }

}
