package com.example.project5;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class CurrentOrderActivity extends AppCompatActivity {
    private Button placeOrderButton, removePizzaButton;
    private ListView pizzaList;
    private TextView subtotalLabel, subtotalText, taxText, taxLabel, totalLabel, totalText,
            phoneNumTextField, phoneNumLabel, currOrderText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_order);
        currOrderText = findViewById(R.id.currOrdertext);
        phoneNumLabel = findViewById(R.id.phoneNumLabel);
        phoneNumTextField = findViewById(R.id.phoneNumTextField);
        removePizzaButton = findViewById(R.id.removePizzaButton);
        placeOrderButton = findViewById(R.id.placeOrderButton);
        pizzaList = findViewById(R.id.pizzasListView);
        subtotalLabel = findViewById(R.id.subtotalLabel);
        subtotalText = findViewById(R.id.sizeText);
        taxText = findViewById(R.id.taxText);
        taxLabel = findViewById(R.id.taxLabel);
        totalLabel = findViewById(R.id.totalLabel);
        totalText = findViewById(R.id.totalText);
    }
}
