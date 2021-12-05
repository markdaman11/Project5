package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ListView;
public class StoreOrdersActivity extends AppCompatActivity {
    private Button cancelOrderButton;
    private TextView StoreOrdersText, phoneNumText;
    private ListView ordersList;
    private Spinner phoneNumSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_orders);
        StoreOrdersText = findViewById(R.id.StoreOrdersText);
        phoneNumText = findViewById(R.id.phoneNumText);
        phoneNumSpinner = findViewById(R.id.phoneNumSpinner);
        ordersList = findViewById(R.id.orderListView);
        cancelOrderButton = findViewById(R.id.cancelOrderButton);
    }
}
