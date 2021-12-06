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
    private Button cancelOrderButton;
    private TextView StoreOrdersText, phoneNumText, totalText, labelTotal;
    private ListView ordersList;
    private Spinner phoneNumSpinner;
    private StoreOrders currentStoreOrders;

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
        StoreOrdersText = findViewById(R.id.StoreOrdersText);
        phoneNumText = findViewById(R.id.phoneNumText);
        phoneNumSpinner = findViewById(R.id.phoneNumSpinner);
        ordersList = findViewById(R.id.orderListView);
        cancelOrderButton = findViewById(R.id.cancelOrderButton);
        totalText = findViewById(R.id.activityOrderText);
        labelTotal = findViewById(R.id.activityOrderLabel);
        ArrayList<String> orderPhoneNums = new ArrayList<String>();
        for (Order o : currentStoreOrders.orders){
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

        if(phoneNum == null) {
            //noNumberSelectedWarning();
            return;
        }
        Order currentOrder = currentStoreOrders.findOrder(phoneNum);
        ordersList.clearChoices();
        for (Pizza pizza: currentOrder.pizzas) {
            if(pizza instanceof Hawaiian) {
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

    }

    MainActivity mainActivity;

    /*
    public void customerPhoneNumberClicked(View view) {
        String orderNumber = phoneNumSpinner.getSelectedItem().toString();
        if(orderNumber == null) {
            //noNumberSelectedWarning();
            return;
        }
        Order currentOrder = currentStoreOrders.findOrder(orderNumber);
        ordersList.getItems().clear();
        for (Pizza pizza: currentOrder.pizzas) {
            if(pizza instanceof Hawaiian) {
                Hawaiian h = (Hawaiian) pizza;
                String pizzaString = h.toString();
                ordersList.getItems().add(pizzaString);
            } else if (pizza instanceof Pepperoni) {
                Pepperoni p = (Pepperoni) pizza;
                String pizzaString = p.toString();
                ordersList.getItems().add(pizzaString);
            } else if (pizza instanceof Deluxe) {
                Deluxe d = (Deluxe) pizza;
                String pizzaString = d.toString();
                ordersList.getItems().add(pizzaString);
            }
        }
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedTotal = df.format(currentOrder.calcTotal());
        totalText.setText(formattedTotal);
    }

     */

    void noNumberSelectedWarning() {
        Context context = getApplicationContext();
        CharSequence text = "No order selected!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*
    public void initializeStoreOrdersView(StoreOrders ordersList) {
        currentStoreOrders = ordersList;
        for (Order order : currentStoreOrders.orders){
            String phoneNumber = order.phoneNumber;
            phoneNumSpinner.getItems().add(phoneNumber);
        }
    }

     */

}
