package com.example.spideremporium.view;

import com.example.spideremporium.controller.service.OrderController;
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

    private ObservableList<Customer> availableCustomers;
    private ObservableList<Spider> availableSpiders;

    public OrderView() {
        this.root = new VBox(10);
        this.root.setStyle("-fx-background-color: #3e2340");
        this.availableCustomers = FXCollections.observableArrayList();
        this.availableSpiders = FXCollections.observableArrayList();
    }

    public void setOrderController(OrderController _orderController) {
        this.orderController = _orderController;
        createUI();
    }

    /**
     * This method updates the customer list for the customerbox.
     * @param customers List of available customers.
     */
    public void setAvailableCustomers(ObservableList<Customer> customers) {
        this.availableCustomers = customers;
            customerBox.getItems().clear();
            customerBox.setItems(customers);
    }

    /**
     * This method updates the spider list for the spiderbox.
     * @param spiders List of available spiders.
     */
    public void setAvailableSpiders(ObservableList<Spider> spiders) {
        this.availableSpiders = spiders;
            spiderBox.getItems().clear();
            spiderBox.setItems(spiders);
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

    public Button getViewOrdersBtn() { return this.viewOrdersBtn;}

    public ComboBox<Spider> getSpiderBox() {
        return this.spiderBox;
    }

    public ComboBox<Customer> getCustomerBox() {
        return this.customerBox;
    }

    public ListView<Spider> getCheckoutView() {
        return this.checkOutView;
    }

    /**
     * This method calls the relevant methods that set up the UI.
     */
    private void createUI() {
        createHeading();
        createButtons();
        createTopSection();
        createCheckOutView();
        createPurchaseSection();
        createReceiptSection();
    }

    /**
     * This method creates the buttons for the UI.
     */
    private void createButtons() {
        this.addBtn = new Button("ADD");
        this.removeBtn = new Button("Remove Spider");
        this.purchaseBtn = new Button("Purchase");
        this.newOrderBtn = new Button("New Order");
        this.viewOrdersBtn = new Button("View Order History");
    }

    /**
     * Given a button and a String, the text of the button is changed.
     * @param button - The button with the text we wish to change.
     * @param text - The text we want the button to display.
     */
    public void changeBtnTxt(Button button, String text) {
        button.setText(text);
    }

    /**
     * This method creates the heading section of the UI.
     */
    private void createHeading() {
        VBox headingBox = new VBox();
        Label headingLabel = new Label("Spider Emporium \uD83D\uDD77");
        headingLabel.setFont(Font.font("Lucida Calligraphy", 30));
        headingLabel.setTextFill(Color.YELLOW);
        headingBox.getChildren().add(headingLabel);
        headingBox.setAlignment(Pos.CENTER);
        root.getChildren().add(headingBox);
    }

    /**
     * This method creates the top section of the UI.
     */
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
        customerBox.setItems(availableCustomers);

        // Spider drop down
        spiderBox = new ComboBox<>();
        spiderBox.setPrefWidth(250);
        spiderBox.setPromptText("Select Spider");
        spiderBox.setItems(availableSpiders);

        selectionBox.getChildren().addAll(customerBox, spiderBox, this.addBtn);
        topSection.getChildren().add(selectionBox);
        root.getChildren().add(topSection);
    }

    /**
     * This method creates the checkoutView where the spiders to purchase will be displayed.
     */
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

    /**
     * This method creates the purchase section where the total cost of the order and buttons to modify or place
     * the order will be displayed.
     */
    private void createPurchaseSection() {
        HBox bottomBox = new HBox(50);
        bottomBox.setPadding(new Insets(10, 0, 0, 0));
        bottomBox.setAlignment(Pos.CENTER);

        this.totalLabel = new Label("Total: $0.00");
        this.totalLabel.setTextFill(Color.RED);

        bottomBox.getChildren().addAll(removeBtn, purchaseBtn, totalLabel);
        root.getChildren().add(bottomBox);
    }

    /**
     * This method creates the receipt section, where information about the placed order will be displayed.
     */
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


    /**
     * This method updates the displayed total with the formatted total that is passed in.
     * @param total - The formatted total String to be displayed.
     */
    public void updateTotalLabel(String total) {
        this.totalLabel.setText("Total: " + total);
    }

    /**
     * This method creates and opens a new window in which past orders are displayed.
     */
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

    /**
     * This method displays an alert window if not all required items are selected.
     */
    public void displayItemNotSelectedWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Select a customer and a spider!");
        alert.showAndWait();
    }

    /**
     * This method displays the customer name and the date in the receipt section.
     * @param customer
     */
    public void displayCustomerAndDate(Customer customer) {
        this.receiptCustomerLabel.setText("Customer: " + customer.getfName() + " " + customer.getlName());
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String formattedDate = today.format(dateFormatter);
        this.receiptDateLabel.setText("Date: " + formattedDate);
    }

    /**
     * This method clears the customer name and date.
     */
    public void clearOrderComponents() {
        this.receiptCustomerLabel.setText("Customer: ");
        this.receiptDateLabel.setText("Date: ");
        this.orderReceiptView.getItems().clear();
    }

    /**
     * This method displays the purchased spiders in the receipt window.
     * @param selectedSpidersList
     */
    public void displayOrderReceiptInfo(ObservableList<Spider> selectedSpidersList) {
        orderReceiptView.getItems().clear();
        orderReceiptView.getItems().addAll(selectedSpidersList);
    }

}