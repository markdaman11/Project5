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
    private Pizza currentPizza;
    private Order currentOrder;
    private StoreOrders currentStoreOrders  = new StoreOrders();
    private StoreOrders currentPendingOrders = new StoreOrders();
    private Pizza pizzaReturned;
    private Order orderReturned;
    private StoreOrders storeOrdersReturned;
    private StoreOrders pendingOrdersReturned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        pizzaReturned = (Pizza) intent.getSerializableExtra("RETURNED_PIZZA");
        orderReturned = (Order) intent.getSerializableExtra("RETURNED_ORDER");
        storeOrdersReturned = (StoreOrders) intent.getSerializableExtra("RETURNED_STORE_ORDERS");
        pendingOrdersReturned = (StoreOrders) intent.getSerializableExtra("RETURNED_STORE_ORDERS");
        welcome = findViewById(R.id.welcomeText);
        phoneNumberTextBox = findViewById(R.id.editTextPhone);
        addDeluxeButton = findViewById(R.id.orderDeluxeButton);
        addPepperoniButton = findViewById(R.id.orderPepperoniButton);
        addHawaiianButton = findViewById(R.id.orderHawaiianButton);
        currentOrderButton = findViewById(R.id.currentOrderButton);
        storeOrdersButton = findViewById(R.id.storeOrdersButton);
        if (pizzaReturned != null){
            //welcome.setText(pizzaToAdd.toString());
            welcome.setText(orderReturned.toString());
            currentPendingOrders.orders.add(orderReturned);
        }
        if(storeOrdersReturned != null) {
            //welcome.setText("store orders are null");
            //welcome.setText(storeOrdersReturned.toString());
            currentStoreOrders = storeOrdersReturned;
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
            Order o = currentPendingOrders.findOrder(phone);
            if (o != null) {
                if (o.phoneNumber.equals(phone)) {
                    try {
                        Intent intent = new Intent(this, CurrentOrderActivity.class);
                        intent.putExtra("CURR_ORDER", o);
                        intent.putExtra("CURR_STORE_ORDERS", currentStoreOrders);
                        intent.putExtra("CURR_PENDING_ORDERS", currentPendingOrders);
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
        Order alreadyPlaced = currentStoreOrders.findOrder(phone);
        if (alreadyPlaced != null) {
            orderAlreadyPlaced();
            return;
        }
        if (validPhoneNumber(phone)) {
            Order order = currentPendingOrders.findOrder(phone);
            if (order == null) {
                order = new Order(phone);
                currentPendingOrders.orders.add(order);
            }
            currentOrder = order;
            //newPizza = PizzaMaker.createPizza("Deluxe");
            try {
                String title = "Deluxe Pizza Customization";
                currentPizza = PizzaMaker.createPizza("Deluxe");
                Intent intent = new Intent(this, PizzaCustomizationActivity.class);
                intent.putExtra("ORDER", order);
                intent.putExtra("TITLE_TEXT", title);
                intent.putExtra("PIZZA",currentPizza);
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
        Order alreadyPlaced = currentStoreOrders.findOrder(phone);
        if (alreadyPlaced != null) {
            orderAlreadyPlaced();
            return;
        }
        if (validPhoneNumber(phone)) {
            Order order = currentPendingOrders.findOrder(phone);
            if (order == null) {
                order = new Order(phone);
                currentPendingOrders.orders.add(order);
            }
            currentOrder = order;
            currentPizza = PizzaMaker.createPizza("Hawaiian");
            try {
                String title = "Hawaiian Pizza Customization";
                currentPizza = PizzaMaker.createPizza("Hawaiian");
                Intent intent = new Intent(this, PizzaCustomizationActivity.class);
                intent.putExtra("ORDER", order);
                intent.putExtra("TITLE_TEXT", title);
                intent.putExtra("PIZZA",currentPizza);
                startActivity(intent);;
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
        Order alreadyPlaced = currentStoreOrders.findOrder(phone);
        if (alreadyPlaced != null) {
            orderAlreadyPlaced();
            return;
        }
        if (validPhoneNumber(phone)) {
            Order order = currentPendingOrders.findOrder(phone);
            if (order == null) {
                order = new Order(phone);
                currentPendingOrders.orders.add(order);
            }
            currentOrder = order;
            try {
                String title = "Pepperoni Pizza Customization";
                currentPizza = PizzaMaker.createPizza("Pepperoni");
                Intent intent = new Intent(this, PizzaCustomizationActivity.class);
                intent.putExtra("ORDER", order);
                intent.putExtra("TITLE_TEXT", title);
                intent.putExtra("PIZZA", currentPizza);
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
            intent.putExtra("STORE_ORDERS", currentStoreOrders);
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
        return currentStoreOrders;
    }

    public StoreOrders getPendingStoreOrders() {
        return currentPendingOrders;
    }

}