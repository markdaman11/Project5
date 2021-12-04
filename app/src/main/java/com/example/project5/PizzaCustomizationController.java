package com.example.project4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class PizzaCustomizationController {

    private MainMenuController mainController;
    String[] additional = {"Pepperoni", "Ham", "Pineapple", "Sausage", "Onions", "Peppers", "Mushrooms", "Olives"};
    String[] current = {};

    public void setMainMenuController(MainMenuController controller) {
        mainController = controller; //now you can reference any private data items through mainController
    }
    Pizza p = PizzaMaker.createPizza("Deluxe");
    Image deluxe = new Image("deluxePizza.jpeg");
    Image pepperoni = new Image("pepperoniPizza.jpeg");
    Image hawaiian = new Image("hawaiianPizza.jpeg");
    Pizza currentPizza;
    //Order currentOrder = mainController.getCurrentOrder();

    @FXML
    private Button addToOrderButton;

    @FXML
    private Button addToppingButton;

    @FXML
    private ListView additionalToppings;

    @FXML
    private ListView currentToppings;

    @FXML
    private ComboBox pizzaSizeComboBox;

    @FXML
    private Label pizzaTypeLabel;

    @FXML
    private ImageView pizzaImage;

    @FXML
    private TextField priceTextField;

    @FXML
    private Button removeToppingButton;

    @FXML
    void addToOrder(ActionEvent event) {
        Size s = (Size) pizzaSizeComboBox.getSelectionModel().getSelectedItem();
        if(s == null) {
            invalidSizeWarning();
            return;
        }
        mainController.getCurrentOrder().addToOrder(currentPizza);
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
        currentToppings.getItems().clear();
        additionalToppings.getItems().clear();
        pizzaSizeComboBox.setValue(Size.small);
        currentToppings.getItems().add(Topping.Peppers);
        currentToppings.getItems().add(Topping.Sausage);
        currentToppings.getItems().add(Topping.Onions);
        currentToppings.getItems().add(Topping.Pepperoni);
        currentToppings.getItems().add(Topping.Mushrooms);
        additionalToppings.getItems().add(Topping.Olives);
        additionalToppings.getItems().add(Topping.Ham);
        additionalToppings.getItems().add(Topping.Pineapple);
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedPrice = df.format(Deluxe.BASE_PRICE);
        priceTextField.setText(formattedPrice);

    }

    void resetPepperoni(){
        currentToppings.getItems().clear();
        additionalToppings.getItems().clear();
        pizzaSizeComboBox.setValue(Size.small);
        currentToppings.getItems().add(Topping.Pepperoni);
        additionalToppings.getItems().add(Topping.Olives);
        additionalToppings.getItems().add(Topping.Ham);
        additionalToppings.getItems().add(Topping.Pineapple);
        additionalToppings.getItems().add(Topping.Peppers);
        additionalToppings.getItems().add(Topping.Sausage);
        additionalToppings.getItems().add(Topping.Onions);
        additionalToppings.getItems().add(Topping.Mushrooms);
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedPrice = df.format(Pepperoni.BASE_PRICE);
        priceTextField.setText(formattedPrice);
    }

    void resetHawaiian(){
        currentToppings.getItems().clear();
        additionalToppings.getItems().clear();
        pizzaSizeComboBox.setValue(Size.small);
        currentToppings.getItems().add(Topping.Ham);
        currentToppings.getItems().add(Topping.Pineapple);
        additionalToppings.getItems().add(Topping.Olives);
        additionalToppings.getItems().add(Topping.Pepperoni);
        additionalToppings.getItems().add(Topping.Peppers);
        additionalToppings.getItems().add(Topping.Sausage);
        additionalToppings.getItems().add(Topping.Onions);
        additionalToppings.getItems().add(Topping.Mushrooms);
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedPrice = df.format(Hawaiian.BASE_PRICE);
        priceTextField.setText(formattedPrice);
    }


    void invalidSizeWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText("No size selected");
        alert.setContentText("Please choose a pizza size.");
        alert.showAndWait();
    }

    void PizzaAddedAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Pizza Added");
        alert.setContentText("The pizza has been added to the order.");
        alert.showAndWait();

    }



    @FXML
    void addTopping(ActionEvent event) {
        Topping toppingToAdd = (Topping) additionalToppings.getSelectionModel().getSelectedItem();
        if (toppingToAdd == null) {
            return;
        }
        if (currentPizza.addToppings(toppingToAdd)) {
            currentToppings.getItems().add(toppingToAdd);
            additionalToppings.getItems().remove(toppingToAdd);
            DecimalFormat df = new DecimalFormat("#,###.##");
            String formattedPrice = df.format(currentPizza.price());
            priceTextField.setText(formattedPrice);
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
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Unable to add topping");
        alert.setContentText("The maximum number of toppings is 7.");
        alert.showAndWait();
    }

    @FXML
    void pizzaSizeChanged(ActionEvent event) {
        if (pizzaSizeComboBox.getSelectionModel().getSelectedIndex() == 0) {
            currentPizza.size = Size.small;
        } else if (pizzaSizeComboBox.getSelectionModel().getSelectedIndex() == 1) {
            currentPizza.size = Size.medium;
        } else {
            currentPizza.size = Size.large;
        }
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedPrice = df.format(currentPizza.price());
        priceTextField.setText(formattedPrice);
    }

    @FXML
    void removeTopping(ActionEvent event) {
        Topping toppingToRemove = (Topping) currentToppings.getSelectionModel().getSelectedItem();
        if (toppingToRemove == null) {
            return;
        }
        additionalToppings.getItems().add(toppingToRemove);
        currentToppings.getItems().remove(toppingToRemove);
        currentPizza.RemoveToppings(toppingToRemove);
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedPrice = df.format(currentPizza.price());
        priceTextField.setText(formattedPrice);
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
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Essential Topping Removed");
        alert.setContentText("You now have below the essential topping amount for this pizza.");
        alert.showAndWait();
    }

    public void initializeDeluxe() {
        currentPizza = mainController.getCurrentPizza();
        currentToppings.getItems().add(Topping.Peppers);
        currentToppings.getItems().add(Topping.Sausage);
        currentToppings.getItems().add(Topping.Onions);
        currentToppings.getItems().add(Topping.Pepperoni);
        currentToppings.getItems().add(Topping.Mushrooms);
        additionalToppings.getItems().add(Topping.Olives);
        additionalToppings.getItems().add(Topping.Ham);
        additionalToppings.getItems().add(Topping.Pineapple);
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedPrice = df.format(Deluxe.BASE_PRICE);
        priceTextField.setText(formattedPrice);
    }

    public void initializePepperoni() {
        currentPizza = mainController.getCurrentPizza();
        currentToppings.getItems().add(Topping.Pepperoni);
        additionalToppings.getItems().add(Topping.Mushrooms);
        additionalToppings.getItems().add(Topping.Olives);
        additionalToppings.getItems().add(Topping.Ham);
        additionalToppings.getItems().add(Topping.Pineapple);
        additionalToppings.getItems().add(Topping.Peppers);
        additionalToppings.getItems().add(Topping.Sausage);
        additionalToppings.getItems().add(Topping.Onions);
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedPrice = df.format(Pepperoni.BASE_PRICE);
        priceTextField.setText(formattedPrice);
    }

    public void initializeHawaiian() {
        currentPizza = mainController.getCurrentPizza();
        currentToppings.getItems().add(Topping.Ham);
        currentToppings.getItems().add(Topping.Pineapple);
        additionalToppings.getItems().add(Topping.Mushrooms);
        additionalToppings.getItems().add(Topping.Olives);
        additionalToppings.getItems().add(Topping.Pepperoni);
        additionalToppings.getItems().add(Topping.Peppers);
        additionalToppings.getItems().add(Topping.Sausage);
        additionalToppings.getItems().add(Topping.Onions);
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedPrice = df.format(Hawaiian.BASE_PRICE);
        priceTextField.setText(formattedPrice);
    }


    public void initialize() {

        //additionalToppings.getItems().setAll(currentPizza.additionalToppings);
        //currentToppings.getItems().setAll(currentPizza.toppings);
        pizzaSizeComboBox.getItems().add(Size.small);
        pizzaSizeComboBox.getItems().add(Size.medium);
        pizzaSizeComboBox.getItems().add(Size.large);
        pizzaSizeComboBox.setValue(Size.small);


    }

    public void setPizzaType(String pizzaType) {
        pizzaTypeLabel.setText(pizzaType);
    }


    public void setPizzaImage(String pizzaType) {
        if (pizzaType == "Deluxe Pizza") {
            pizzaImage.setImage(deluxe);
        } else if (pizzaType == "Pepperoni Pizza") {
            pizzaImage.setImage(pepperoni);
        } else {
            pizzaImage.setImage(hawaiian);
        }
    }
}