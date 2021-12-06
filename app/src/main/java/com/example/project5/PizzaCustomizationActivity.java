package com.example.project5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class defines the activity for the Pizza Customization GUI. It allows the user to customize the pizza toppings
 * and size before adding it to the current order.
 */
public class PizzaCustomizationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button addPizzaButton;
    private Spinner sizeSpinner;
    private ListView addlToppings, currToppings;
    private TextView priceText, priceLabel, sizeText, customizationLabel;

    private Pizza pizza;
    private Order order;

    private ArrayList<Topping> current = new ArrayList<>();
    private ArrayList<Topping> remaining = new ArrayList<>();

    /**
     * Initializes the pizza customization GUI.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ordercustomization);
        Intent intent = getIntent();
        String title = intent.getStringExtra("TITLE_TEXT");
        order = (Order) intent.getSerializableExtra("ORDER");
        pizza = (Pizza) intent.getSerializableExtra("PIZZA");
        customizationLabel = findViewById(R.id.PizzaCustomizationLabel);
        sizeText = findViewById(R.id.sizeText);
        sizeSpinner = findViewById(R.id.pizzaSizeSpinner);
        addlToppings = findViewById(R.id.additionalToppings);
        currToppings = findViewById(R.id.currentToppingsList);
        addPizzaButton = findViewById(R.id.addPizzaButton);
        priceText = findViewById(R.id.priceText);
        priceLabel = findViewById(R.id.priceLabel);
        customizationLabel.setText(pizza.toString());

        //customizationLabel.setText(title);
        if (title.equals("Deluxe Pizza Customization")) {
            current.add(Topping.Peppers);
            current.add(Topping.Sausage);
            current.add(Topping.Onions);
            current.add(Topping.Pepperoni);
            current.add(Topping.Mushrooms);
            remaining.add(Topping.Olives);
            remaining.add(Topping.Ham);
            remaining.add(Topping.Pineapple);
            ArrayAdapter<Topping> toppingAdapter = new ArrayAdapter<Topping>(this, android.R.layout.simple_list_item_1, current);
            ArrayAdapter<Topping> remainingAdapter = new ArrayAdapter<Topping>(this, android.R.layout.simple_list_item_1, remaining);
            currToppings.setAdapter(toppingAdapter);
            addlToppings.setAdapter(remainingAdapter);
        }
        if (title.equals("Hawaiian Pizza Customization")) {
            current.add(Topping.Ham);
            current.add(Topping.Pineapple);
            remaining.add(Topping.Onions);
            remaining.add(Topping.Pepperoni);
            remaining.add(Topping.Mushrooms);
            remaining.add(Topping.Olives);
            remaining.add(Topping.Peppers);
            remaining.add(Topping.Sausage);
            ArrayAdapter<Topping> toppingAdapter = new ArrayAdapter<Topping>(this, android.R.layout.simple_list_item_1, current);
            ArrayAdapter<Topping> remainingAdapter = new ArrayAdapter<Topping>(this, android.R.layout.simple_list_item_1, remaining);
            currToppings.setAdapter(toppingAdapter);
            addlToppings.setAdapter(remainingAdapter);
        }
        if (title.equals("Pepperoni Pizza Customization")) {
            current.add(Topping.Pepperoni);
            remaining.add(Topping.Pineapple);
            remaining.add(Topping.Onions);
            remaining.add(Topping.Ham);
            remaining.add(Topping.Mushrooms);
            remaining.add(Topping.Olives);
            remaining.add(Topping.Peppers);
            remaining.add(Topping.Sausage);
            ArrayAdapter<Topping> toppingAdapter = new ArrayAdapter<Topping>(this, android.R.layout.simple_list_item_1, current);
            ArrayAdapter<Topping> remainingAdapter = new ArrayAdapter<Topping>(this, android.R.layout.simple_list_item_1, remaining);
            currToppings.setAdapter(toppingAdapter);
            addlToppings.setAdapter(remainingAdapter);
        }

        currToppings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Removes a topping from the pizza.
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Topping toppingToRemove = (Topping) parent.getItemAtPosition(position);
                //System.out.println("It is " + toppingToRemove);

                remaining.add(toppingToRemove);
                current.remove(toppingToRemove);
                pizza.RemoveToppings(toppingToRemove);
                DecimalFormat df = new DecimalFormat("#,###.##");
                String formattedPrice = df.format(pizza.price());
                priceText.setText(formattedPrice);
                if(pizza instanceof Hawaiian && pizza.toppings.size() < Hawaiian.BASE_TOPPINGS){
                    ToppingRemovedAlert();
                } else if(pizza instanceof Deluxe && pizza.toppings.size() < Deluxe.BASE_TOPPINGS){
                    ToppingRemovedAlert();
                } else if(pizza instanceof Pepperoni && pizza.toppings.size() < Pepperoni.BASE_TOPPINGS){
                    ToppingRemovedAlert();
                }

                ((ArrayAdapter)addlToppings.getAdapter()).notifyDataSetChanged();
                ((ArrayAdapter)currToppings.getAdapter()).notifyDataSetChanged();
                addlToppings.invalidateViews();
                currToppings.invalidateViews();

            }
        });

        addlToppings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Adds a topping to the pizza.
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //System.out.println("Additional topping clicked");

                Topping toppingToAdd = (Topping) parent.getItemAtPosition(position);
                //System.out.println("It is " + toppingToAdd);

                if (pizza.addToppings(toppingToAdd)) {
                    System.out.println("This ran");
                    remaining.remove(toppingToAdd);
                    current.add(toppingToAdd);
                    ((ArrayAdapter)addlToppings.getAdapter()).notifyDataSetChanged();
                    ((ArrayAdapter)currToppings.getAdapter()).notifyDataSetChanged();
                    addlToppings.invalidateViews();
                    currToppings.invalidateViews();
                    DecimalFormat df = new DecimalFormat("#,###.##");
                    String formattedPrice = df.format(pizza.price());
                    priceText.setText(formattedPrice);
                } else {
                    ToppingAddedErrorAlert();
                }

            }
        });

        ArrayList<Size> sizes = new ArrayList<Size>();
        sizes.add(Size.small);
        sizes.add(Size.medium);
        sizes.add(Size.large);
        sizeSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<Size> adapter = new ArrayAdapter<Size>(this, android.R.layout.simple_spinner_item, sizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(adapter);
        sizeSpinner.setSelection(0);
    }

    /**
     * Changes the size of the pizza.
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Size size = (Size) sizeSpinner.getSelectedItem();
        pizza.setSize(size);
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedPrice = df.format(pizza.price());
        priceText.setText(formattedPrice);
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    /**
     * Adds the pizza to the order.
     * @param view
     */
    public void addToOrder(View view) {
        order.addToOrder(pizza);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("RETURNED_PIZZA", pizza);
        intent.putExtra("RETURNED_ORDER", order);
        startActivity(intent);
    }

    /*
    public void addToOrder(View view) {
        Size s = (Size) sizeSpinner.getSelectedItem();
        if(s == null) {
            invalidSizeWarning();
            return;
        }
        mainActivity.getCurrentOrder().addToOrder(currentPizza);
        if (currentPizza instanceof Hawaiian){
            currentPizza = PizzaMaker.createPizza("Hawaiian");
            resetHawaiian();
            PizzaAddedAlert();
        } else if (currentPizza instanceof Pepperoni){
            currentPizza = PizzaMaker.createPizza("Pepperoni");
            resetPepperoni();
            PizzaAddedAlert();
        } else if (currentPizza instanceof Deluxe){
            currentPizza = PizzaMaker.createPizza("Deluxe");
            resetDeluxe();
            PizzaAddedAlert();
        }


    }

     */


    void invalidSizeWarning() {
        Context context = getApplicationContext();
        CharSequence text = "No Size Selected!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    void PizzaAddedAlert() {
        Context context = getApplicationContext();
        CharSequence text = "Pizza Added!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /**
     * Toast for notifying the user about invalid toppings.
     */
    void ToppingAddedErrorAlert() {
        Context context = getApplicationContext();
        CharSequence text = "You can have up to 7 toppings!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /**
     * Toast for notifying the user about removing essential toppings.
     */
    void ToppingRemovedAlert() {
        Context context = getApplicationContext();
        CharSequence text = "Essential Topping Removed!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
