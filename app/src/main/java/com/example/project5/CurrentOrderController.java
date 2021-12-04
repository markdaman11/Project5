package com.example.project4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class CurrentOrderController {
    MainMenuController mainController;
    Order currentOrder;

    @FXML
    private TextField customerPhoneNumberTextField;

    @FXML
    private ListView<String> orderItemsListView;

    @FXML
    private TextField orderTotalTextField;

    @FXML
    private Button placeOrderButton;

    @FXML
    private Button removePizzaButton;

    @FXML
    private TextField subtotalTextField;

    @FXML
    private TextField taxTextField;

    @FXML
    void placeOrder(ActionEvent event) {
        mainController.getStoreOrdersList().orders.add(currentOrder);
        mainController.getPendingStoreOrders().orders.remove(currentOrder);
        orderPlacedAlert();
    }

    void orderPlacedAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Order Placed");
        alert.setContentText("The order has been placed.");
        alert.showAndWait();

    }

    @FXML
    void removePizza(ActionEvent event) {
        int indexToRemove = orderItemsListView.getSelectionModel().getSelectedIndex();
        if (indexToRemove == -1) {
            noPizzaSelectedWarning();
            return;
        }
        currentOrder.pizzas.remove(indexToRemove);
        String pizzaToRemove = orderItemsListView.getSelectionModel().getSelectedItem();
        orderItemsListView.getItems().remove(pizzaToRemove);
        recalculateTotals();
    }

    public void setMainMenuController(MainMenuController controller) {
        mainController = controller; //now you can reference any private data items through mainController
    }

    void noPizzaSelectedWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText("No pizza selected");
        alert.setContentText("Please choose a pizza to remove from the order.");
        alert.showAndWait();
    }

    void recalculateTotals() {
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedTotal = df.format(currentOrder.calcTotal());
        String formattedTax = df.format(currentOrder.calcTax());
        String formattedSubtotal = df.format(currentOrder.calcSubtotal());
        orderTotalTextField.setText(formattedTotal);
        taxTextField.setText(formattedTax);
        subtotalTextField.setText(formattedSubtotal);

    }


    public void initializeOrderView(String num, Order order) {
        currentOrder = order;
        customerPhoneNumberTextField.setText(num);
        for (Pizza pizza: order.pizzas) {
            if(pizza instanceof Hawaiian) {
                Hawaiian h = (Hawaiian) pizza;
                String pizzaString = h.toString();
                orderItemsListView.getItems().add(pizzaString);
            } else if (pizza instanceof Pepperoni) {
                Pepperoni p = (Pepperoni) pizza;
                String pizzaString = p.toString();
                orderItemsListView.getItems().add(pizzaString);
            } else if (pizza instanceof Deluxe) {
                Deluxe d = (Deluxe) pizza;
                String pizzaString = d.toString();
                orderItemsListView.getItems().add(pizzaString);
            }
        }
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedTotal = df.format(order.calcTotal());
        String formattedTax = df.format(order.calcTax());
        String formattedSubtotal = df.format(order.calcSubtotal());
        orderTotalTextField.setText(formattedTotal);
        taxTextField.setText(formattedTax);
        subtotalTextField.setText(formattedSubtotal);
    }
}