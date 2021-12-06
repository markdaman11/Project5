package com.example.project5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PizzaCustomizationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button addPizzaButton;
    private Spinner sizeSpinner;
    private ListView addlToppings, currToppings;
    private TextView priceText, priceLabel, sizeText, customizationLabel;

    private Pizza pizza;
    private Order order;

    private ArrayList<Topping> current = new ArrayList<>();
    private ArrayList<Topping> remaining = new ArrayList<>();

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
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Topping toppingToRemove = (Topping) parent.getItemAtPosition(position);
                System.out.println("It is " + toppingToRemove);

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
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Additional topping clicked");

                Topping toppingToAdd = (Topping) parent.getItemAtPosition(position);
                System.out.println("It is " + toppingToAdd);

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Size size = (Size) sizeSpinner.getSelectedItem();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) { }


    private MainActivity mainActivity;

    public void setMainMenuController(MainActivity activity) {
        mainActivity = activity; //now you can reference any private data items through mainController
    }

    Pizza p = PizzaMaker.createPizza("Deluxe");
    Pizza currentPizza;

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
    /*
    void resetDeluxe(){
        currToppings.clearChoices();
        addlToppings.clearChoices();
        sizeSpinner.setSelection(0);
        currToppings.getItems().add(Topping.Peppers);
        currToppings.getItems().add(Topping.Sausage);
        currToppings.getItems().add(Topping.Onions);
        currToppings.getItems().add(Topping.Pepperoni);
        currToppings.getItems().add(Topping.Mushrooms);
        addlToppings.getItems().add(Topping.Olives);
        addlToppings.getItems().add(Topping.Ham);
        addlToppings.getItems().add(Topping.Pineapple);
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedPrice = df.format(Deluxe.BASE_PRICE);
        priceText.setText(formattedPrice);

    }
    */
    /*
    void resetPepperoni(){
        currToppings.clearChoices();
        addlToppings.clearChoices();
        sizeSpinner.setSelection(0);
        currToppings.getItems().add(Topping.Pepperoni);
        addlToppings.getItems().add(Topping.Olives);
        addlToppings.getItems().add(Topping.Ham);
        addlToppings.getItems().add(Topping.Pineapple);
        addlToppings.getItems().add(Topping.Peppers);
        addlToppings.getItems().add(Topping.Sausage);
        addlToppings.getItems().add(Topping.Onions);
        addlToppings.getItems().add(Topping.Mushrooms);
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedPrice = df.format(Pepperoni.BASE_PRICE);
        priceText.setText(formattedPrice);
    }
    */
    /*
    void resetHawaiian(){
        currToppings.clearChoices();
        addlToppings.clearChoices();
        sizeSpinner.setSelection(0);
        currToppings.getItems().add(Topping.Ham);
        currToppings.getItems().add(Topping.Pineapple);
        addlToppings.getItems().add(Topping.Olives);
        addlToppings.getItems().add(Topping.Pepperoni);
        addlToppings.getItems().add(Topping.Peppers);
        addlToppings.getItems().add(Topping.Sausage);
        addlToppings.getItems().add(Topping.Onions);
        addlToppings.getItems().add(Topping.Mushrooms);
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedPrice = df.format(Hawaiian.BASE_PRICE);
        priceText.setText(formattedPrice);
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

    /*
    public void addTopping(View view) {
        Topping toppingToAdd = (Topping) addlToppings.getSelectedItem();
        if (toppingToAdd == null) {
            return;
        }
        if (currentPizza.addToppings(toppingToAdd)) {
            currToppings.getItems().add(toppingToAdd);
            addlToppings.getItems().remove(toppingToAdd);
            DecimalFormat df = new DecimalFormat("#,###.##");
            String formattedPrice = df.format(currentPizza.price());
            priceText.setText(formattedPrice);
        } else {
            ToppingAddedErrorAlert();
        }


        additionalToppings.getSelectionModel().getSelectedIndex();
        currentPizza.addToppings(currentPizza.getAdditionalTopping(additionalToppings.getSelectionModel().getSelectedIndex()));
        additionalToppings.getItems().setAll(currentPizza.additionalToppings);
        currentToppings.getItems().setAll(currentPizza.toppings);

        priceTextField.setText(Double.toString(currentPizza.price()));

    }
    */
    void ToppingAddedErrorAlert() {
        Context context = getApplicationContext();
        CharSequence text = "You can have up to 7 toppings!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void pizzaSizeChanged(View view) {
        if (sizeSpinner.getSelectedItemPosition() == 0) {
            currentPizza.size = Size.small;
        } else if (sizeSpinner.getSelectedItemPosition() == 1) {
            currentPizza.size = Size.medium;
        } else {
            currentPizza.size = Size.large;
        }
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedPrice = df.format(currentPizza.price());
        priceText.setText(formattedPrice);
    }

    /*
    public void removeTopping(View view) {
        Topping toppingToRemove = (Topping) currToppings.getSelectedItem();
        if (toppingToRemove == null) {
            return;
        }
        addlToppings.getItems().add(toppingToRemove);
        currToppings.getItems().remove(toppingToRemove);
        currentPizza.RemoveToppings(toppingToRemove);
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedPrice = df.format(currentPizza.price());
        priceText.setText(formattedPrice);
        if(currentPizza instanceof Hawaiian && currentPizza.toppings.size() < Hawaiian.BASE_TOPPINGS){
            ToppingRemovedAlert();
        } else if(currentPizza instanceof Deluxe && currentPizza.toppings.size() < Deluxe.BASE_TOPPINGS){
            ToppingRemovedAlert();
        } else if(currentPizza instanceof Pepperoni && currentPizza.toppings.size() < Pepperoni.BASE_TOPPINGS){
            ToppingRemovedAlert();
        }
        currentToppings.getSelectionModel().getSelectedIndex();
        currentPizza.RemoveToppings(currentPizza.getTopping(currentToppings.getSelectionModel().getSelectedIndex()));
        additionalToppings.getItems().setAll(currentPizza.additionalToppings);
        currentToppings.getItems().setAll(currentPizza.toppings);

        priceTextField.setText(Double.toString(currentPizza.price()));

    }
    */
    void ToppingRemovedAlert() {
        Context context = getApplicationContext();
        CharSequence text = "Essential Topping Removed!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    /*
    public void initializeDeluxe() {
        currentPizza = mainActivity.getCurrentPizza();
        currToppings.getItems().add(Topping.Peppers);
        currToppings.getItems().add(Topping.Sausage);
        currToppings.getItems().add(Topping.Onions);
        currToppings.getItems().add(Topping.Pepperoni);
        currToppings.getItems().add(Topping.Mushrooms);
        addlToppings.getItems().add(Topping.Olives);
        addlToppings.getItems().add(Topping.Ham);
        addlToppings.getItems().add(Topping.Pineapple);
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedPrice = df.format(Deluxe.BASE_PRICE);
        priceText.setText(formattedPrice);
    }
    */
    /*
    public void initializePepperoni() {
        currentPizza = mainActivity.getCurrentPizza();
        currToppings.getItems().add(Topping.Pepperoni);
        addlToppings.getItems().add(Topping.Mushrooms);
        addlToppings.getItems().add(Topping.Olives);
        addlToppings.getItems().add(Topping.Ham);
        addlToppings.getItems().add(Topping.Pineapple);
        addlToppings.getItems().add(Topping.Peppers);
        addlToppings.getItems().add(Topping.Sausage);
        addlToppings.getItems().add(Topping.Onions);
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedPrice = df.format(Pepperoni.BASE_PRICE);
        priceText.setText(formattedPrice);
    }
    */
    /*
    public void initializeHawaiian() {
        currentPizza = mainActivity.getCurrentPizza();
        currToppings.getItems().add(Topping.Ham);
        currToppings.getItems().add(Topping.Pineapple);
        addlToppings.getItems().add(Topping.Mushrooms);
        addlToppings.getItems().add(Topping.Olives);
        addlToppings.getItems().add(Topping.Pepperoni);
        addlToppings.getItems().add(Topping.Peppers);
        addlToppings.getItems().add(Topping.Sausage);
        addlToppings.getItems().add(Topping.Onions);
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedPrice = df.format(Hawaiian.BASE_PRICE);
        priceText.setText(formattedPrice);
    }

     */

    /*
    public void initialize() {

        //additionalToppings.getItems().setAll(currentPizza.additionalToppings);
        //currentToppings.getItems().setAll(currentPizza.toppings);
        sizeSpinner.getItems().add(Size.small);
        sizeSpinner.getItems().add(Size.medium);
        sizeSpinner.getItems().add(Size.large);
        sizeSpinner.setValue(Size.small);


    }
     */

    public void setPizzaType(String pizzaType) {
        customizationLabel.setText(pizzaType);
    }

}
