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

public class PizzaCustomizationActivity extends AppCompatActivity {
    private Button addToppingButton,removeToppingButton, addPizzaButton;
    private Spinner sizeSpinner;
    private ListView addlToppings, currToppings;
    private TextView priceText, priceLabel, sizeText, customizationLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ordercustomization);
        customizationLabel = findViewById(R.id.PizzaCustomizationLabel);
        sizeText = findViewById(R.id.sizeText);
        sizeSpinner = findViewById(R.id.pizzaSizeSpinner);
        addlToppings = findViewById(R.id.additionalToppings);
        currToppings = findViewById(R.id.currentToppingsList);
        addPizzaButton = findViewById(R.id.addPizzaButton);
        addToppingButton = findViewById(R.id.addButton);
        removeToppingButton = findViewById(R.id.removeButton);
        priceText = findViewById(R.id.priceText);
        priceLabel = findViewById(R.id.priceLabel);
    }

    private MainActivity mainActivity;
    String[] additional = {"Pepperoni", "Ham", "Pineapple", "Sausage", "Onions", "Peppers", "Mushrooms", "Olives"};
    String[] current = {};

    public void setMainMenuController(MainActivity activity) {
        mainActivity = activity; //now you can reference any private data items through mainController
    }

    Pizza p = PizzaMaker.createPizza("Deluxe");
    Pizza currentPizza;

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

        /*
        additionalToppings.getSelectionModel().getSelectedIndex();
        currentPizza.addToppings(currentPizza.getAdditionalTopping(additionalToppings.getSelectionModel().getSelectedIndex()));
        additionalToppings.getItems().setAll(currentPizza.additionalToppings);
        currentToppings.getItems().setAll(currentPizza.toppings);

        priceTextField.setText(Double.toString(currentPizza.price()));
         */
    }
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
        /*
        currentToppings.getSelectionModel().getSelectedIndex();
        currentPizza.RemoveToppings(currentPizza.getTopping(currentToppings.getSelectionModel().getSelectedIndex()));
        additionalToppings.getItems().setAll(currentPizza.additionalToppings);
        currentToppings.getItems().setAll(currentPizza.toppings);

        priceTextField.setText(Double.toString(currentPizza.price()));

         */
    }
    void ToppingRemovedAlert() {
        Context context = getApplicationContext();
        CharSequence text = "Essential Topping Removed!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

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


    public void initialize() {

        //additionalToppings.getItems().setAll(currentPizza.additionalToppings);
        //currentToppings.getItems().setAll(currentPizza.toppings);
        sizeSpinner.getItems().add(Size.small);
        sizeSpinner.getItems().add(Size.medium);
        sizeSpinner.getItems().add(Size.large);
        sizeSpinner.setValue(Size.small);


    }

    public void setPizzaType(String pizzaType) {
        customizationLabel.setText(pizzaType);
    }

}
