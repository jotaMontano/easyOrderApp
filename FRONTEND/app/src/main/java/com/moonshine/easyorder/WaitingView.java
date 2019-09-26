package com.moonshine.easyorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.moonshine.easyorder.PayOrder.PayOrder;

public class WaitingView extends AppCompatActivity {

    private Chronometer myChronometer;
    private int maxEstimatedTime;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("  EASY ORDER");
        toolbar.setLogo(R.drawable.ic_easyorderw);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        maxEstimatedTime = (int) intent.getSerializableExtra("MaxEstimatedTime");
        final TextView estimatedTiem = findViewById(R.id.estimatedTime);
        estimatedTiem.setText(maxEstimatedTime+":00");
        if (MainActivity.chronometer == null){
            MainActivity.chronometer = findViewById(R.id.chronometer);
            MainActivity.chronometer.start();
        }else {
            myChronometer = findViewById(R.id.chronometer);
            myChronometer.setBase(MainActivity.chronometer.getBase());
            myChronometer.setText(MainActivity.chronometer.getText());
            myChronometer.start();
        }

        final Button returnToOrderProduct = findViewById(R.id.button);
        returnToOrderProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToOrder();
            }
        });

        final Button toPay = findViewById(R.id.button2);
        toPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPayOrderActivity();
            }
        });
    }

    private void openPayOrderActivity(){
        Intent intent = new Intent(this, PayOrder.class);
        startActivity(intent);
    }

    private void returnToOrder(){
        MainActivity.chronometer.stop();
        finish();
    }
}
