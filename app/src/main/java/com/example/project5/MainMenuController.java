package com.example.project4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class MainMenuController implements Initializable {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("PizzaCustomization-view.fxml"));
    PizzaCustomizationController PizzaController = loader.getController();
    //PizzaController.setMainMenuController(this);

    Image deluxe = new Image("deluxePizza.jpeg");
    Image pepperoni = new Image("pepperoniPizza.jpeg");
    Image hawaiian = new Image("hawaiianPizza.jpeg");
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

    @FXML
    private Button CurrentOrdersButton;

    @FXML
    private Button StoreOrdersButton;

    @FXML
    private Button orderDeluxeButton;

    @FXML
    private Button orderHawaiianButton;

    @FXML
    private Button orderPepperoniButton;

    @FXML
    private ImageView orderHawaiianImage;

    @FXML
    private ImageView orderPepperoniImage;

    @FXML
    private ImageView orderDeluxeImage;

    @FXML
    private TextField phoneNumTextField;
    @FXML
    void currentOrder(ActionEvent event) {
        String phone = phoneNumTextField.getText();
        if (phone == "") {
            invalidPhoneNumWarning();
            return;
        }
        if (validPhoneNumber(phone)) {
            Order o = pendingStoreOrders.findOrder(phone);
            if (o != null) {
                if (o.phoneNumber.equals(phone)) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("CurrentOrder-view.fxml"));
                        Parent root = loader.load();
                        CurrentOrderController coc = loader.getController();
                        coc.setMainMenuController(this);
                        coc.initializeOrderView(phone, o);
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Order Detail");
                        stage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setHeaderText("Invalid Phone number.");
                alert.setContentText("The phone number provided is not associated with any active order. If you are " +
                        "looking to edit a previously placed order, please cancel it and order again.");
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.showAndWait();
            }
        } else {
            invalidPhoneNumWarning();
        }
    }

    void invalidPhoneNumWarning() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText("Invalid Phone number.");
        alert.setContentText("Please enter a valid 10 digit phone number (digits only).");
        alert.showAndWait();
    }

    @FXML
    void orderDeluxe(ActionEvent event) {
        String phone = phoneNumTextField.getText();
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("PizzaCustomization-view.fxml"));
                Parent root = loader.load();
                PizzaCustomizationController pcc = loader.getController();
                pcc.setMainMenuController(this);
                pcc.setPizzaType("Deluxe Pizza");
                pcc.setPizzaImage("Deluxe Pizza");
                pcc.initializeDeluxe();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Pizza Customization");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            invalidPhoneNumWarning();
            return;
        }

    }


    @FXML
    void orderHawaiian(ActionEvent event) {
        String phone = phoneNumTextField.getText();
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("PizzaCustomization-view.fxml"));
                Parent root = loader.load();
                PizzaCustomizationController pcc = loader.getController();
                pcc.setMainMenuController(this);
                pcc.setPizzaType("Hawaiian Pizza");
                pcc.setPizzaImage("Hawaiian Pizza");
                pcc.initializeHawaiian();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Pizza Customization");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            invalidPhoneNumWarning();
            return;
        }

    }

    @FXML
    void orderPepperoni(ActionEvent event) {
        String phone = phoneNumTextField.getText();
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("PizzaCustomization-view.fxml"));
                Parent root = loader.load();
                PizzaCustomizationController pcc = loader.getController();
                pcc.setMainMenuController(this);
                pcc.setPizzaType("Pepperoni Pizza");
                pcc.setPizzaImage("Pepperoni Pizza");
                pcc.initializePepperoni();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Pizza Customization");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            invalidPhoneNumWarning();
            return;
        }


    }

    void orderAlreadyPlaced() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("Order already placed.");
        alert.setContentText("An order with this phone number has already been placed. Please cancel the previous" +
                " order to create a new order with this phone number.");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    @FXML
    void storeOrders(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StoreOrders-view.fxml"));
            Parent root = loader.load();
            StoreOrdersController soc = loader.getController();
            soc.setMainMenuController(this);
            soc.initializeStoreOrdersView(storeOrdersList);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Store Orders Viewer");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderDeluxeImage.setImage(deluxe);
        orderHawaiianImage.setImage(hawaiian);
        orderPepperoniImage.setImage(pepperoni);

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