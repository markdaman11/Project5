package com.example.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;
import java.util.ResourceBundle;

public class MainActivity extends AppCompatActivity {
    private Button addDeluxeButton,addPepperoniButton, addHawaiianButton, currentOrderButton, storeOrdersButton;
    private TextView welcome;
    private EditText phoneNumberTextBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        welcome = findViewById(R.id.welcomeText);
        phoneNumberTextBox = findViewById(R.id.editTextPhone);
        addDeluxeButton = findViewById(R.id.orderDeluxeButton);
        addPepperoniButton = findViewById(R.id.orderPepperoniButton);
        addHawaiianButton = findViewById(R.id.orderHawaiianButton);
        currentOrderButton = findViewById(R.id.currentOrderButton);
        storeOrdersButton = findViewById(R.id.storeOrdersButton);
    }

    private Pizza currentPizza;
    private Order currentOrder;
    StoreOrders storeOrdersList = new StoreOrders();
    StoreOrders pendingStoreOrders = new StoreOrders();

    boolean validPhoneNumber(String number) {
        if (number.length() != 10) {
            return false;
        }
        try {
            Long num = Long.parseLong(number);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    void invalidPhoneNumWarning() {
        Context context = getApplicationContext();
        CharSequence text = "Invalid Phone Number!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void currentOrder(View view) {
        String phone = phoneNumberTextBox.getText().toString();
        if (phone == "") {
            invalidPhoneNumWarning();
            return;
        }
        if (validPhoneNumber(phone)) {
            Order o = pendingStoreOrders.findOrder(phone);
            if (o != null) {
                if (o.phoneNumber.equals(phone)) {
                    try {
                        Intent intent = new Intent(this, CurrentOrderActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
            } else {
                invalidPhoneNumWarning();
            }
        } else {
            invalidPhoneNumWarning();
        }
    }

    public void orderDeluxe(View view) {
        String phone = phoneNumberTextBox.getText().toString();
        if (phone == "") {
            invalidPhoneNumWarning();
            return;
        }
        Order alreadyPlaced = storeOrdersList.findOrder(phone);
        if (alreadyPlaced != null) {
            orderAlreadyPlaced();
            return;
        }
        if (validPhoneNumber(phone)) {
            Order order = pendingStoreOrders.findOrder(phone);
            if (order == null) {
                order = new Order(phone);
                pendingStoreOrders.orders.add(order);
            }
            currentOrder = order;
            currentPizza = PizzaMaker.createPizza("Deluxe");
            try {
                Intent intent = new Intent(this, PizzaCustomizationActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            invalidPhoneNumWarning();
            return;
        }

    }

    public void orderHawaiian(View view) {
        String phone = phoneNumberTextBox.getText().toString();
        if (phone == "") {
            invalidPhoneNumWarning();
            return;
        }
        Order alreadyPlaced = storeOrdersList.findOrder(phone);
        if (alreadyPlaced != null) {
            orderAlreadyPlaced();
            return;
        }
        if (validPhoneNumber(phone)) {
            Order order = pendingStoreOrders.findOrder(phone);
            if (order == null) {
                order = new Order(phone);
                pendingStoreOrders.orders.add(order);
            }
            currentOrder = order;
            currentPizza = PizzaMaker.createPizza("Hawaiian");
            try {
                Intent intent = new Intent(this, PizzaCustomizationActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            invalidPhoneNumWarning();
            return;
        }
    }

    public void orderPepperoni(View view) {
        String phone = phoneNumberTextBox.getText().toString();
        if (phone == "") {
            invalidPhoneNumWarning();
            return;
        }
        Order alreadyPlaced = storeOrdersList.findOrder(phone);
        if (alreadyPlaced != null) {
            orderAlreadyPlaced();
            return;
        }
        if (validPhoneNumber(phone)) {
            Order order = pendingStoreOrders.findOrder(phone);
            if (order == null) {
                order = new Order(phone);
                pendingStoreOrders.orders.add(order);
            }
            currentOrder = order;
            currentPizza = PizzaMaker.createPizza("Pepperoni");
            try {
                Intent intent = new Intent(this, PizzaCustomizationActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            invalidPhoneNumWarning();
            return;
        }
    }


    void orderAlreadyPlaced() {
        Context context = getApplicationContext();
        CharSequence text = "Order already placed!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void storeOrders(View view) {
        try {
            Intent intent = new Intent(this, StoreOrdersActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Pizza getCurrentPizza() {
        return currentPizza;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public StoreOrders getStoreOrdersList() {
        return storeOrdersList;
    }

    public StoreOrders getPendingStoreOrders() {
        return pendingStoreOrders;
    }

}