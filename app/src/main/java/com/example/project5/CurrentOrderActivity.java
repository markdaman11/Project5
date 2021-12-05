package com.example.project5;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

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

    MainActivity mainActivity;
    Order currentOrder;

    public void placeOrder(View view) {
        mainActivity.getStoreOrdersList().orders.add(currentOrder);
        mainActivity.getPendingStoreOrders().orders.remove(currentOrder);
        orderPlacedAlert();
    }

    void orderPlacedAlert() {
        Context context = getApplicationContext();
        CharSequence text = "Order placed!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

    public void removePizza(View view) {
        int indexToRemove = pizzaList.getSelectedItemPosition();
        if (indexToRemove == -1) {
            noPizzaSelectedWarning();
            return;
        }
        currentOrder.pizzas.remove(indexToRemove);
        String pizzaToRemove = pizzaList.getSelectedItem().toString();
        pizzaList.getItems().remove(pizzaToRemove);
        recalculateTotals();
    }

    public void setMainMenuController(MainActivity activity) {
        mainActivity = activity; //now you can reference any private data items through mainController
    }

    void noPizzaSelectedWarning() {
        Context context = getApplicationContext();
        CharSequence text = "No Pizza Selected!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    void recalculateTotals() {
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedTotal = df.format(currentOrder.calcTotal());
        String formattedTax = df.format(currentOrder.calcTax());
        String formattedSubtotal = df.format(currentOrder.calcSubtotal());
        totalText.setText(formattedTotal);
        taxText.setText(formattedTax);
        subtotalText.setText(formattedSubtotal);

    }


    public void initializeOrderView(String num, Order order) {
        currentOrder = order;
        phoneNumTextField.setText(num);
        for (Pizza pizza: order.pizzas) {
            if(pizza instanceof Hawaiian) {
                Hawaiian h = (Hawaiian) pizza;
                String pizzaString = h.toString();
                pizzaList.getItems().add(pizzaString);
            } else if (pizza instanceof Pepperoni) {
                Pepperoni p = (Pepperoni) pizza;
                String pizzaString = p.toString();
                pizzaList.getItems().add(pizzaString);
            } else if (pizza instanceof Deluxe) {
                Deluxe d = (Deluxe) pizza;
                String pizzaString = d.toString();
                pizzaList.getItems().add(pizzaString);
            }
        }
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedTotal = df.format(order.calcTotal());
        String formattedTax = df.format(order.calcTax());
        String formattedSubtotal = df.format(order.calcSubtotal());
        totalText.setText(formattedTotal);
        taxText.setText(formattedTax);
        subtotalText.setText(formattedSubtotal);
    }

}
