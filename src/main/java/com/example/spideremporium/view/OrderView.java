package com.example.spideremporium.view;

import com.example.spideremporium.controller.OrderController;
import com.example.spideremporium.model.Customer;
import com.example.spideremporium.model.Order;
import com.example.spideremporium.model.Spider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private ListView<Spider> orderReceiptView;
    private Button addBtn, removeBtn, purchaseBtn, sortAZBtn, sortPriceBtn, newOrderBtn, viewOrdersBtn;
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

    public Button getSortAZBtn() { return this.sortAZBtn; }

    public Button getSortPriceBtn() { return this.sortPriceBtn; }

    public Button getNewOrderBtn() { return this.newOrderBtn; }

    public Button getViewOrdersBtn() { return this.viewOrdersBtn;
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
        createPurchaseSection();
        createReceiptSection();
    }

    private void createButtons() {
        this.addBtn = new Button("ADD");
        this.removeBtn = new Button("Remove Spider");
        this.purchaseBtn = new Button("Purchase");
        this.newOrderBtn = new Button("New Order");
        this.viewOrdersBtn = new Button("View Order History");
    }

    public void changeBtnTxt(Button button, String text) {
        button.setText(text);
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

    private void createPurchaseSection() {
        HBox bottomBox = new HBox(50);
        bottomBox.setPadding(new Insets(10, 0, 0, 0));
        bottomBox.setAlignment(Pos.CENTER);

        this.totalLabel = new Label("Total: $0.00");
        this.totalLabel.setTextFill(Color.RED);

        bottomBox.getChildren().addAll(removeBtn, purchaseBtn, totalLabel);
        root.getChildren().add(bottomBox);
    }

    private void createReceiptSection() {
        VBox orderSummaryBox = new VBox(5);
        orderSummaryBox.setPadding(new Insets(10, 0, 0, 0));
        orderSummaryBox.setAlignment(Pos.CENTER);

        // Create customer label
        this.receiptCustomerLabel = new Label("Customer: ");
        receiptCustomerLabel.setTextFill(Color.WHITE);

        // Create date label
        this.receiptDateLabel = new Label("Date: ");
        receiptDateLabel.setTextFill(Color.WHITE);

        VBox customerAndDateBox = new VBox(5);
        customerAndDateBox.getChildren().addAll(receiptCustomerLabel, receiptDateLabel);
        customerAndDateBox.setPadding(new Insets(0, 0, 0, 140));

        // ListView for spiders purchased this order
        this.orderReceiptView = new ListView<>();
        orderReceiptView.setMaxWidth(320);
        orderReceiptView.setPrefHeight(150);

        // Sort & new order buttons
        HBox sortingBox = new HBox(10);
        sortingBox.setAlignment(Pos.CENTER);
        this.sortAZBtn = new Button("Sort A-Z");
        this.sortPriceBtn = new Button("Sort Price \u2191");
        sortingBox.getChildren().addAll(sortAZBtn, sortPriceBtn, newOrderBtn, viewOrdersBtn);


        orderSummaryBox.getChildren().addAll(customerAndDateBox, orderReceiptView, sortingBox);
        orderSummaryBox.setPadding(new Insets(0, 0, 10, 0));
        root.getChildren().add(orderSummaryBox);

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

    public void displayPastOrders()  {
        Stage pastOrderStage = new Stage();
        pastOrderStage.setTitle("Past Orders");

        ListView<Order> pastOrdersListView = new ListView<>();
        ObservableList<Order> orderList = FXCollections.observableArrayList(orderController.getOrderList());
        pastOrdersListView.setItems(orderList);

        VBox pastOrdersLayout = new VBox(10);
        pastOrdersLayout.getChildren().add(pastOrdersListView);

        Scene scene = new Scene(pastOrdersLayout, 400, 400);
        pastOrderStage.setScene(scene);
        pastOrderStage.show();



    }

    public void displayItemNotSelectedWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Select a customer and a spider!");
        alert.showAndWait();
    }

    public void displayCustomerAndDate(Customer customer) {
        this.receiptCustomerLabel.setText("Customer: " + customer.getfName() + " " + customer.getlName());
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String formattedDate = today.format(dateFormatter);
        this.receiptDateLabel.setText("Date: " + formattedDate);
    }

    public void clearOrderComponents() {
        this.receiptCustomerLabel.setText("Customer: ");
        this.receiptDateLabel.setText("Date: ");
        this.orderReceiptView.getItems().clear();
    }

    public void displayOrderReceiptInfo(ObservableList<Spider> selectedSpidersList) {
        orderReceiptView.getItems().clear();
        orderReceiptView.getItems().addAll(selectedSpidersList);
    }


}