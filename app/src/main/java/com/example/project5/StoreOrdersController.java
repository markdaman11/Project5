package com.example.project4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileWriter;   // Import the FileWriter class
import java.text.DecimalFormat;

public class StoreOrdersController {

    StoreOrders currentStoreOrders;
    MainMenuController mainController;

    @FXML
    private Button cancelOrderButton;

    @FXML
    private ComboBox<String> customerPhoneNumberComboBox;

    @FXML
    private Button exportButton;

    @FXML
    private ListView<String> orderItemsListView;

    @FXML
    private TextField orderTotalTextField;

    @FXML
    void cancelOrder(ActionEvent event) {
        String orderNumber = customerPhoneNumberComboBox.getSelectionModel().getSelectedItem();
        if(orderNumber == null) {
            noNumberSelectedWarning();
            return;
        }
        int indexToRemove = customerPhoneNumberComboBox.getSelectionModel().getSelectedIndex();
        Order currentOrder = currentStoreOrders.findOrder(orderNumber);
        customerPhoneNumberComboBox.getItems().remove(orderNumber);
        currentStoreOrders.orders.remove(currentOrder);
        orderTotalTextField.setText("");
        customerPhoneNumberComboBox.setValue(null);
        orderItemsListView.getItems().clear();

    }

    @FXML
    void customerPhoneNumberClicked(ActionEvent event) {
        String orderNumber = customerPhoneNumberComboBox.getSelectionModel().getSelectedItem();
        if(orderNumber == null) {
            //noNumberSelectedWarning();
            return;
        }
        Order currentOrder = currentStoreOrders.findOrder(orderNumber);
        orderItemsListView.getItems().clear();
        for (Pizza pizza: currentOrder.pizzas) {
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
        String formattedTotal = df.format(currentOrder.calcTotal());
        orderTotalTextField.setText(formattedTotal);
    }

    void noNumberSelectedWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText("No order selected");
        alert.setContentText("Please choose an order to view.");
        alert.showAndWait();
    }

    @FXML
    void exportStoreOrders(ActionEvent event) {

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Target File for the Export");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", ".txt"),
                new FileChooser.ExtensionFilter("All Files", ".*"));
        Stage stage = new Stage();
        File targetFile = chooser.showSaveDialog(stage); //get the reference of the target file

        try {
            FileWriter myWriter = new FileWriter(targetFile);
            for (Order order : currentStoreOrders.orders){
                myWriter.write(order.toString());
                myWriter.write("\n\n");
            }
            myWriter.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText("File Created.");
            alert.setContentText("The StoreOrders.txt file has been populated with the store orders");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Cannot Add to File.");
            alert.setContentText("For some reason, the file StoreOrders.txt cannot be edited");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            e.printStackTrace();
        }

    }

    public void setMainMenuController(MainMenuController controller) {
        mainController = controller; //now you can reference any private data items through mainController
    }

    public void initializeStoreOrdersView(StoreOrders ordersList) {
        currentStoreOrders = ordersList;
        for (Order order : currentStoreOrders.orders){
            String phoneNumber = order.phoneNumber;
            customerPhoneNumberComboBox.getItems().add(phoneNumber);
        }
    }

}