package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class StoreOrdersActivity extends AppCompatActivity {
    private Button cancelOrderButton;
    private TextView StoreOrdersText, phoneNumText, totalText, totalLabel;
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
        totalText = findViewById(R.id.activityOrderText);
        totalLabel = findViewById(R.id.activityOrderLabel);
    }

    StoreOrders currentStoreOrders;
    MainActivity mainActivity;

    public void cancelOrder(View view) {
        String orderNumber = phoneNumSpinner.getSelectedItem().toString();
        if(orderNumber == null) {
            noNumberSelectedWarning();
            return;
        }
        int indexToRemove = phoneNumSpinner.getSelectedItemPosition();
        Order currentOrder = currentStoreOrders.findOrder(orderNumber);
        phoneNumSpinner.getItems().remove(orderNumber);
        currentStoreOrders.orders.remove(currentOrder);
        totalText.setText("");
        phoneNumSpinner.setValue(null);
        ordersList.getItems().clear();

    }

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

    void noNumberSelectedWarning() {
        Context context = getApplicationContext();
        CharSequence text = "No order selected!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void setMainMenuController(MainActivity activity) {
        mainActivity = activity; //now you can reference any private data items through mainController
    }

    public void initializeStoreOrdersView(StoreOrders ordersList) {
        currentStoreOrders = ordersList;
        for (Order order : currentStoreOrders.orders){
            String phoneNumber = order.phoneNumber;
            phoneNumSpinner.getItems().add(phoneNumber);
        }
    }

}
