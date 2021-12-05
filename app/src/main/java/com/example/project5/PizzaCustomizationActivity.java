package com.example.project5;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class PizzaCustomizationActivity extends AppCompatActivity {
    private Button addToppingButton,removeToppingButton, addPizzaButton;
    private Spinner sizeSpinner;
    private ListView addlToppings, currToppings;
    private TextView priceText, priceLabel, sizeText, customizationLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ordercustomization);
        customizationLabel = findViewById(R.id.PizzaCustomizationLabel);
        sizeText = findViewById(R.id.sizeText);
        sizeSpinner = findViewById(R.id.pizzaSizeSpinner);
        addlToppings = findViewById(R.id.additionalToppings);
        currToppings = findViewById(R.id.currentToppingsList);
        addPizzaButton = findViewById(R.id.addPizzaButton);
        addToppingButton = findViewById(R.id.addButton);
        removeToppingButton = findViewById(R.id.removeButton);
        priceText = findViewById(R.id.priceText);
        priceLabel = findViewById(R.id.priceLabel);
    }
}
