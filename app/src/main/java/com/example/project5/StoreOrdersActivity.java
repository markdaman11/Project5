package com.example.project5;

import android.content.Intent;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class defines the activity for the store orders GUI. It allows the user to cancel an existing order.
 */
public class StoreOrdersActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button cancelOrderButton, mainMenuButton;
    private TextView StoreOrdersText, phoneNumText, totalText, labelTotal;
    private ListView ordersList;
    private Spinner phoneNumSpinner;
    private StoreOrders currentStoreOrders;
    private StoreOrders currentPendingOrders;

    private ArrayList<String> orderPizzas = new ArrayList<>();

    /**
     * Initializes the store orders GUI.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_orders);
        Intent intent = getIntent();
        currentStoreOrders = (StoreOrders) intent.getSerializableExtra("STORE_ORDERS");
        currentPendingOrders = (StoreOrders) intent.getSerializableExtra("PENDING_ORDERS");
        StoreOrdersText = findViewById(R.id.StoreOrdersText);
        phoneNumText = findViewById(R.id.phoneNumText);
        phoneNumSpinner = findViewById(R.id.phoneNumSpinner);
        ordersList = findViewById(R.id.orderListView);
        cancelOrderButton = findViewById(R.id.cancelOrderButton);
        totalText = findViewById(R.id.activityOrderText);
        labelTotal = findViewById(R.id.activityOrderLabel);
        mainMenuButton = findViewById(R.id.backButton);
        if (currentStoreOrders.orders.size() != 0) {
            ArrayList<String> orderPhoneNums = new ArrayList<String>();
            for (Order o : currentStoreOrders.orders) {
                orderPhoneNums.add(o.phoneNumber);
            }
            phoneNumSpinner.setOnItemSelectedListener(this);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, orderPhoneNums);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            phoneNumSpinner.setAdapter(adapter);
            String phoneNum = (String) phoneNumSpinner.getSelectedItem();
            Order order = currentStoreOrders.findOrder(phoneNum);
            DecimalFormat df = new DecimalFormat("#,###.##");
            String formattedTotal = df.format(order.calcTotal());
            totalText.setText(formattedTotal);

            if (phoneNum == null) {
                return;
            }
            Order currentOrder = currentStoreOrders.findOrder(phoneNum);
            ordersList.clearChoices();
            for (Pizza pizza : currentOrder.pizzas) {
                if (pizza instanceof Hawaiian) {
                    Hawaiian h = (Hawaiian) pizza;
                    String pizzaString = h.toString();
                    orderPizzas.add(pizzaString);
                } else if (pizza instanceof Pepperoni) {
                    Pepperoni p = (Pepperoni) pizza;
                    String pizzaString = p.toString();
                    orderPizzas.add(pizzaString);
                } else if (pizza instanceof Deluxe) {
                    Deluxe d = (Deluxe) pizza;
                    String pizzaString = d.toString();
                    orderPizzas.add(pizzaString);
                }
            }
            ArrayAdapter<String> orderPizzasAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, orderPizzas);
            ordersList.setAdapter(orderPizzasAdapter);
        } else {
            totalText.setText("");
        }
    }

    public void cancelOrder(View view) {
        String phoneNum = (String) phoneNumSpinner.getSelectedItem();
        Order order = currentStoreOrders.findOrder(phoneNum);
        currentStoreOrders.orders.remove(order);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("RETURNED_STORE_ORDERS", currentStoreOrders);
        startActivity(intent);
    }

    public void backToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("RETURNED_STORE_ORDERS", currentStoreOrders);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String phoneNum = (String) phoneNumSpinner.getSelectedItem();
        Order order = currentStoreOrders.findOrder(phoneNum);
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedTotal = df.format(order.calcTotal());
        totalText.setText(formattedTotal);
        Order currentOrder = currentStoreOrders.findOrder(phoneNum);
        ordersList.clearChoices();
        orderPizzas.clear();
        for (Pizza pizza : currentOrder.pizzas) {
            if (pizza instanceof Hawaiian) {
                Hawaiian h = (Hawaiian) pizza;
                String pizzaString = h.toString();
                orderPizzas.add(pizzaString);
            } else if (pizza instanceof Pepperoni) {
                Pepperoni p = (Pepperoni) pizza;
                String pizzaString = p.toString();
                orderPizzas.add(pizzaString);
            } else if (pizza instanceof Deluxe) {
                Deluxe d = (Deluxe) pizza;
                String pizzaString = d.toString();
                orderPizzas.add(pizzaString);
            }
        }
        ordersList.clearChoices();
        ((ArrayAdapter)ordersList.getAdapter()).notifyDataSetChanged();
        ordersList.invalidateViews();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
