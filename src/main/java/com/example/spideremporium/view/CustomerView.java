package com.example.spideremporium.view;

import com.example.spideremporium.controller.CustomerController;
import com.example.spideremporium.model.Customer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Optional;

public class CustomerView {
    private VBox root;
    private CustomerController customerController;
    private TextField fnameData, lnameData, addressData, phoneData;
    private Label infoLabel;
    private Button addBtn, removeBtn, listBtn, serialLoadBtn, serialSaveBtn, dbSaveBtn, dbLoadBtn, exitBtn;
    private ListView<Customer> customerDisplay;

    public CustomerView() {
        this.root = new VBox(20);
        this.root.setStyle("-fx-background-color: #3e2340");
    }

    public void setCustomerController(CustomerController _customerController) {
        this.customerController = _customerController;
        createUI();
    }

    public VBox getRoot() {return this.root;}

    public Button getAddBtn() {
        return this.addBtn;
    }

    public Button getRemoveBtn() {
        return this.removeBtn;
    }

    public Button getListBtn() {
        return this.listBtn;
    }

    public Button getSerialSaveBtn() {
        return this.serialSaveBtn;
    }

    public Button getSerialLoadBtn() {
        return this.serialLoadBtn;
    }

    public Button getExitBtn() {
        return this.exitBtn;
    }

    public Button getDbSaveBtn() { return this.dbSaveBtn; }

    public Button getDbLoadBtn() { return this.dbLoadBtn; }

    public String getFnameData() {
        return this.fnameData.getText();
    }

    public String getLnameData() {
        return this.lnameData.getText();
    }

    public String getAddressData() {
        return this.addressData.getText();
    }

    public String getPhoneData() {return this.phoneData.getText(); }

    public void updateInfoText(String info) {
        this.infoLabel.setText(info);
    }

    public Customer getSelectedCustomer() {
        return customerDisplay.getSelectionModel().getSelectedItem();
    }

    /**
     * This method sets the top heading for the scene.
     */
    public void createHeading() {
        VBox headingBox = new VBox();
        Label headingLabel = new Label("Spider Emporium \uD83D\uDD77");
        headingLabel.setFont(Font.font("Lucida Calligraphy", 30));
        headingLabel.setTextFill(Color.YELLOW);
        headingBox.getChildren().add(headingLabel);
        headingBox.setAlignment(Pos.CENTER);
        root.getChildren().add(headingBox);
    }

    /**
     * This method sets the textfields where customer information will be entered.
     */
    public void createTextFields() {
        HBox fnameBox = new HBox(20);
        Label fnameLabel = new Label("First Name: ");
        fnameBox.setAlignment(Pos.CENTER);
        fnameLabel.setTextFill(Color.YELLOW);
        this.fnameData = new TextField();
        fnameBox.getChildren().addAll(fnameLabel, fnameData);
        root.getChildren().add(fnameBox);

        HBox lnameBox = new HBox(20);
        Label lnameLabel = new Label("Last Name: ");
        lnameBox.setAlignment(Pos.CENTER);
        lnameLabel.setTextFill(Color.YELLOW);
        this.lnameData = new TextField();
        lnameBox.getChildren().addAll(lnameLabel, lnameData);
        root.getChildren().add(lnameBox);

        HBox addressBox = new HBox(37);
        Label addressLabel = new Label("Address:");
        addressBox.setAlignment(Pos.CENTER);
        addressLabel.setTextFill(Color.YELLOW);
        this.addressData = new TextField();
        addressBox.getChildren().addAll(addressLabel, addressData);
        root.getChildren().add(addressBox);

        HBox phoneBox = new HBox(40);
        Label phoneLabel = new Label("Phone: ");
        phoneBox.setAlignment(Pos.CENTER);
        phoneLabel.setTextFill(Color.YELLOW);
        this.phoneData = new TextField();
        phoneBox.getChildren().addAll(phoneLabel, phoneData);
        root.getChildren().add(phoneBox);
    }


    /**
     * This method sets the listview where the customer information in memory will be displayed.
     */
    public void createCustomerDisplay() {
        HBox customerViewBox = new HBox();
        customerViewBox.setAlignment(Pos.CENTER);
        this.customerDisplay = new ListView<>();
        this.customerDisplay.setPrefWidth(300);
        this.customerDisplay.setPrefHeight(200);
        // Load customers from the list and display them
        this.customerDisplay.setItems(customerController.getCustomerList());

        customerViewBox.getChildren().add(customerDisplay);
        root.getChildren().add(customerViewBox);
    }

    public void clearCustomerDisplay() {
        this.fnameData.clear();
        this.lnameData.clear();
        this.addressData.clear();
        this.phoneData.clear();
    }

    /**
     * This method refreshes the customers in the customerView display area.
     */
    public void refreshCustomerDisplay() {
        customerDisplay.getItems().clear();
        customerDisplay.getItems().addAll(customerController.getCustomerList());
        updateInfoText("Customer list refreshed.");

    }

    public void showDataConfirmationAlert(boolean isLoadAlert, boolean isSerial) {
        String notification;
        if (isLoadAlert) {
            if (isSerial) {
                notification = "Customers loaded from serial file!";
            }
            else {
                notification = "Customers loaded from database!";
            }
        }
        else {
            if (isSerial) {
                notification = "Customers saved to serial file!";
            }
            else {
                notification = "Customers saved to database!";
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, notification);
        alert.setTitle("Customers loaded");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public int showExitAlert() {
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setTitle("Exit?");
        exitAlert.setHeaderText("Save before exiting?");

        // Define buttons
        ButtonType yesBtn = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noBtn = new ButtonType("No", ButtonBar.ButtonData.NO);
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        exitAlert.getButtonTypes().setAll(yesBtn, noBtn, cancelBtn);

        Optional<ButtonType> result = exitAlert.showAndWait();
        if (result.isPresent()) {
            if (result.get().equals(yesBtn)) {
                return 1;
            }
            else if (result.get().equals(noBtn)) {
                return 2;
            }
            else if (result.get().equals(cancelBtn)) {
                return 3;
            }
            }
        return 3;
    }

    /**
     * This method displays an alert if any textfields are empty when an attempt is made to add a new customer.
     */
    public void displayEmptyFieldAlert() {
        Alert emptyFieldAlert = new Alert(Alert.AlertType.ERROR);
        emptyFieldAlert.setTitle("Blank Fields Present");
        emptyFieldAlert.setHeaderText("Please fill out all fields.");
        emptyFieldAlert.showAndWait();
    }


    /**
     * This method creates the buttons used for GUI functionality.
     */
    public void createButtons() {
        HBox displayOptionsBox = new HBox(10);
        this.addBtn = new Button("ADD");
        this.removeBtn = new Button("REMOVE");
        this.listBtn = new Button("LIST");
        displayOptionsBox.getChildren().addAll(addBtn, removeBtn, listBtn);
        displayOptionsBox.setAlignment(Pos.CENTER);
        root.getChildren().add(displayOptionsBox);

        HBox maintenanceOptionsBox = new HBox(17);
        this.serialSaveBtn = new Button("SAVE (Serial)");
        this.serialLoadBtn = new Button("LOAD (Serial)");
        this.exitBtn = new Button("EXIT");
        maintenanceOptionsBox.getChildren().addAll(serialSaveBtn, serialLoadBtn, exitBtn);
        maintenanceOptionsBox.setAlignment(Pos.CENTER);
        maintenanceOptionsBox.setPadding(new Insets(0, 0, 10, 0));
        root.getChildren().add(maintenanceOptionsBox);

        HBox dbBtnBox = new HBox(20);
        this.dbSaveBtn = new Button("Save (DB)");
        this.dbLoadBtn = new Button("Load (DB)");
        dbBtnBox.getChildren().addAll(dbSaveBtn, dbLoadBtn);
        dbBtnBox.setAlignment(Pos.CENTER);
        root.getChildren().addAll(dbBtnBox);

    }

    public void createInfoLabel() {
        HBox infoBox = new HBox();
        this.infoLabel = new Label();
        infoLabel.setTextFill(Color.RED);
        infoBox.getChildren().add(infoLabel);
        infoBox.setAlignment(Pos.BOTTOM_RIGHT);
        infoBox.setPadding(new Insets(0, 10, 0, 0));
        root.getChildren().add(infoBox);
    }

    public void createUI() {
        createHeading();
        createTextFields();
        createCustomerDisplay();
        createButtons();
        createInfoLabel();
    }
}