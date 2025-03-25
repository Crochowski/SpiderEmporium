package com.example.spideremporium.view;

import com.example.spideremporium.controller.OrderController;
import com.example.spideremporium.model.Customer;
import com.example.spideremporium.model.Spider;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

/**
 * View class for order management UI
 */
public class OrderView {
    private VBox root;
    private OrderController orderController;
    private ComboBox<Customer> customerBox;
    private ComboBox<Spider> spiderBox;
    private ListView<Spider> checkOutView;
    private ListView<Spider> receiptSpidersView;
    private Button addBtn, removeBtn, purchaseBtn;
    private Label totalLabel, receiptCustomerLabel, receiptDateLabel;

    private ArrayList<Customer> availableCustomers;
    private ArrayList<Spider> availableSpiders;

    public OrderView() {
        this.root = new VBox(10);
        this.root.setStyle("-fx-background-color: #3e2340");
        this.availableCustomers = new ArrayList<>();
        this.availableSpiders = new ArrayList<>();
    }

    public void setOrderController(OrderController _orderController) {
        this.orderController = _orderController;
        createUI();
    }

    /**
     * This method updates the customer list for the customerbox.
     * @param customers List of available customers.
     */
    public void setAvailableCustomers(ArrayList<Customer> customers) {
        this.availableCustomers = customers;
            customerBox.getItems().clear();
            customerBox.getItems().addAll(customers);
    }

    /**
     * This method updates the spider list for the spiderbox.
     * @param spiders List of available spiders.
     */
    public void setAvailableSpiders(ArrayList<Spider> spiders) {
        this.availableSpiders = spiders;
            spiderBox.getItems().clear();
            spiderBox.getItems().addAll(spiders);
    }

    public VBox getRoot() {
        return this.root;
    }

    public Button getAddBtn() {
        return this.addBtn;
    }

    public Button getRemoveBtn() {
        return this.removeBtn;
    }

    public Button getPurchaseBtn() {
        return this.purchaseBtn;
    }

    public ComboBox<Spider> getSpiderBox() {
        return this.spiderBox;
    }

    public ComboBox<Customer> getCustomerBox() {
        return this.customerBox;
    }

    public ListView<Spider> getCheckoutView() {
        return this.checkOutView;
    }

    private void createUI() {
        createHeading();
        createButtons();
        createTopSection();
        createCheckOutView();
        createBottomSection();
    }

    private void createButtons() {
        this.addBtn = new Button("ADD");
        this.removeBtn = new Button("Remove Spider");
        this.purchaseBtn = new Button("Purchase");
    }

    private void createHeading() {
        VBox headingBox = new VBox();
        Label headingLabel = new Label("Spider Emporium \uD83D\uDD77");
        headingLabel.setFont(Font.font("Lucida Calligraphy", 30));
        headingLabel.setTextFill(Color.YELLOW);
        headingBox.getChildren().add(headingLabel);
        headingBox.setAlignment(Pos.CENTER);
        root.getChildren().add(headingBox);
    }

    private void createTopSection() {
        HBox topSection = new HBox(10);
        topSection.setAlignment(Pos.TOP_CENTER);
        VBox selectionBox = new VBox(5);
        selectionBox.setAlignment(Pos.CENTER);
        selectionBox.setPadding(new Insets(10, 0, 0, 0));

        // Customer drop down
        customerBox = new ComboBox<>();
        customerBox.setPrefWidth(250);
        customerBox.setPromptText("Select Customer");
        customerBox.getItems().addAll(availableCustomers);

        // Spider drop down
        spiderBox = new ComboBox<>();
        spiderBox.setPrefWidth(250);
        spiderBox.setPromptText("Select Spider");
        spiderBox.getItems().addAll(availableSpiders);

        selectionBox.getChildren().addAll(customerBox, spiderBox, this.addBtn);
        topSection.getChildren().add(selectionBox);
        root.getChildren().add(topSection);
    }

    private void createCheckOutView() {
        HBox checkOutViewBox = new HBox();
        checkOutViewBox.setPadding(new Insets(10, 0, 0, 0));
        checkOutViewBox.setAlignment(Pos.CENTER);

        this.checkOutView = new ListView<>();
        checkOutView.setPrefWidth(320);
        checkOutView.setPrefHeight(150);

        checkOutViewBox.getChildren().add(checkOutView);
        root.getChildren().add(checkOutViewBox);
    }

    private void createBottomSection() {
        HBox bottomBox = new HBox(50);
        bottomBox.setPadding(new Insets(10, 0, 0, 0));
        bottomBox.setAlignment(Pos.CENTER);

        this.totalLabel = new Label("Total: $0.00");
        this.totalLabel.setTextFill(Color.RED);

        bottomBox.getChildren().addAll(removeBtn, purchaseBtn, totalLabel);
        root.getChildren().add(bottomBox);
    }

    private void createReceiptDisplay() {

    }

    public void updateTotalLabel(String message) {
        this.totalLabel.setText("Total: " + message);
    }

    public void displayPurchaseConfirmation(String customerName, double total) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Thank you " + customerName + "!\n" +
                "Your total is $" + String.format("%.2f", total) + "\nEnjoy your new Spider friends!");
        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.showAndWait();
    }

    public void displayItemNotSelectedWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Select a customer and a spider!");
        alert.showAndWait();
    }
}