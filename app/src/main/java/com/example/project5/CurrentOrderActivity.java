package com.example.project5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class provides the activity for the current order GUI. It allows the user to remove pizzas from an order and to
 * view totals before placing it.
 */
public class CurrentOrderActivity extends AppCompatActivity {
    private Button placeOrderButton, goToMainMenuButton;
    private ListView pizzaList;
    private TextView subtotalLabel, subtotalText, taxText, taxLabel, totalLabel, totalText,
            phoneNumTextField, phoneNumLabel, currOrderText, removeInfoLabel;
    private Order currentOrder;
    private StoreOrders currentOrdersList;
    private StoreOrders currentPendingOrdersList;

    private ArrayList<String> orderPizzas = new ArrayList<>();

    /**
     * Creates the current order instance.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_order);
        Intent intent = getIntent();
        currentOrder = (Order) intent.getSerializableExtra("CURR_ORDER");
        currentOrdersList = (StoreOrders)intent.getSerializableExtra("CURR_STORE_ORDERS");
        currentPendingOrdersList = (StoreOrders) intent.getSerializableExtra("CURR_PENDING_ORDERS");
        currOrderText = findViewById(R.id.currOrdertext);
        phoneNumLabel = findViewById(R.id.phoneNumLabel);
        phoneNumTextField = findViewById(R.id.phoneNumTextField);
        placeOrderButton = findViewById(R.id.placeOrderButton);
        pizzaList = findViewById(R.id.pizzasListView);
        subtotalLabel = findViewById(R.id.subtotalLabel);
        subtotalText = findViewById(R.id.subtotalText);
        taxText = findViewById(R.id.taxText);
        taxLabel = findViewById(R.id.taxLabel);
        totalLabel = findViewById(R.id.totalLabel);
        totalText = findViewById(R.id.totalText);
        goToMainMenuButton = findViewById(R.id.goToMain);
        removeInfoLabel = findViewById(R.id.removeInfoLabel);
        phoneNumTextField.setText(currentOrder.phoneNumber);
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedTotal = df.format(currentOrder.calcTotal());
        String formattedTax = df.format(currentOrder.calcTax());
        String formattedSubtotal = df.format(currentOrder.calcSubtotal());
        subtotalText.setText(formattedSubtotal);
        taxText.setText(formattedTax);
        totalText.setText(formattedTotal);
        for (Pizza pizza: currentOrder.pizzas) {
            if(pizza instanceof Hawaiian) {
                Hawaiian h = (Hawaiian) pizza;
                orderPizzas.add(pizza.toString());
            } else if (pizza instanceof Pepperoni) {
                Pepperoni p = (Pepperoni) pizza;
                orderPizzas.add(pizza.toString());
            } else if (pizza instanceof Deluxe) {
                Deluxe d = (Deluxe) pizza;
                orderPizzas.add(pizza.toString());
            }
        }

        ArrayAdapter<String> pizzasAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, orderPizzas);
        pizzaList.setAdapter(pizzasAdapter);

        pizzaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Removes the selected pizza.
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == -1) {
                    noPizzaSelectedWarning();
                    return;
                }
                String pizzaToRemove = parent.getItemAtPosition(position).toString();
                currentOrder.pizzas.remove(position);
                orderPizzas.remove(pizzaToRemove);
                ((ArrayAdapter)pizzaList.getAdapter()).notifyDataSetChanged();
                pizzaList.invalidateViews();
                recalculateTotals();
            }
        });

    }

    /**
     * Goes back to the main menu.
     * @param view
     */
    public void backToMainMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("RETURNED_ORDER", currentOrder);
        intent.putExtra("RETURNED_STORE_ORDERS", currentOrdersList);
        intent.putExtra("RETURNED_PENDING_ORDERS", currentPendingOrdersList);
        startActivity(intent);
    }

    /**
     * Places the current order and goes back to the main menu.
     * @param view
     */
    public void placeOrder(View view) {
        currentOrdersList.orders.add(currentOrder);
        currentPendingOrdersList.orders.remove(currentOrder);
        orderPlacedAlert();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("RETURNED_ORDER", currentOrder);
        intent.putExtra("RETURNED_STORE_ORDERS", currentOrdersList);
        intent.putExtra("RETURNED_PENDING_ORDERS", currentPendingOrdersList);
        startActivity(intent);
    }

    /**
     * Order placed toast message.
     */
    void orderPlacedAlert() {
        Context context = getApplicationContext();
        CharSequence text = "Order placed!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /**
     * Toast message for no pizza selected.
     */
    void noPizzaSelectedWarning() {
        Context context = getApplicationContext();
        CharSequence text = "No Pizza Selected!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /**
     * Recalculates the order totals.
     */
    void recalculateTotals() {
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedTotal = df.format(currentOrder.calcTotal());
        String formattedTax = df.format(currentOrder.calcTax());
        String formattedSubtotal = df.format(currentOrder.calcSubtotal());
        totalText.setText(formattedTotal);
        taxText.setText(formattedTax);
        subtotalText.setText(formattedSubtotal);

    }
}
