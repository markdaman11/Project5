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
    private Pizza newPizza;
    private Order currentOrder;
    private Order orderReturned;
    private StoreOrders storeOrdersList  =new StoreOrders();
    private StoreOrders pendingOrders = new StoreOrders();
    private Pizza pizzaToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        pizzaToAdd = (Pizza) intent.getSerializableExtra("ADDED_PIZZA");
        orderReturned = (Order) intent.getSerializableExtra("BIGGER_ORDER");
        welcome = findViewById(R.id.welcomeText);
        phoneNumberTextBox = findViewById(R.id.editTextPhone);
        addDeluxeButton = findViewById(R.id.orderDeluxeButton);
        addPepperoniButton = findViewById(R.id.orderPepperoniButton);
        addHawaiianButton = findViewById(R.id.orderHawaiianButton);
        currentOrderButton = findViewById(R.id.currentOrderButton);
        storeOrdersButton = findViewById(R.id.storeOrdersButton);
        if (pizzaToAdd != null){
            //welcome.setText(pizzaToAdd.toString());
            welcome.setText(orderReturned.toString());
            pendingOrders.orders.add(orderReturned);
        }
    }


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
            Order o = pendingOrders.findOrder(phone);
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
            Order order = pendingOrders.findOrder(phone);
            if (order == null) {
                order = new Order(phone);
                pendingOrders.orders.add(order);
            }
            currentOrder = order;
            newPizza = PizzaMaker.createPizza("Deluxe");
            try {
                String title = "Deluxe Pizza Customization";
                newPizza = PizzaMaker.createPizza("Deluxe");
                Intent intent = new Intent(this, PizzaCustomizationActivity.class);
                intent.putExtra("ORDER", order);
                intent.putExtra("TITLE_TEXT", title);
                intent.putExtra("PIZZA",newPizza);
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
            Order order = pendingOrders.findOrder(phone);
            if (order == null) {
                order = new Order(phone);
                pendingOrders.orders.add(order);
            }
            currentOrder = order;
            newPizza = PizzaMaker.createPizza("Hawaiian");
            try {
                String title = "Hawaiian Pizza Customization";
                Intent intent = new Intent(this, PizzaCustomizationActivity.class);
                intent.putExtra("TITLE_TEXT", title);
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
            Order order = pendingOrders.findOrder(phone);
            if (order == null) {
                order = new Order(phone);
                pendingOrders.orders.add(order);
            }
            currentOrder = order;
            newPizza = PizzaMaker.createPizza("Pepperoni");
            try {
                String title = "Pepperoni Pizza Customization";
                Intent intent = new Intent(this, PizzaCustomizationActivity.class);
                intent.putExtra("TITLE_TEXT", title);
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
            intent.putExtra("STORE_ORDERS", storeOrdersList);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Pizza getCurrentPizza() {
        return newPizza;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public StoreOrders getStoreOrdersList() {
        return storeOrdersList;
    }

    public StoreOrders getPendingStoreOrders() {
        return pendingOrders;
    }

}