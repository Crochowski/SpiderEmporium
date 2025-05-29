package com.example.spideremporium.controller.service;

import com.example.spideremporium.controller.dataAccess.SerializationManager;
import com.example.spideremporium.model.*;
import com.example.spideremporium.view.OrderView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import java.util.Comparator;

public class OrderController {
    private boolean isAscendingAlpha = false;
    private boolean isAscendingPrice = false;
    private OrderView orderView;
    private OrderOps orderOps;
    private Button addBtn, removeBtn, purchaseBtn, sortAZBtn, sortPriceBtn, neworderBtn, viewOrdersBtn;
    private ComboBox<Spider> spiderBox;
    private ComboBox<Customer> customerBox;
    private ListView<Spider> checkoutView;
    private ObservableList<Spider> selectedSpidersList;
    private double total;


    public OrderController(OrderView _orderView) {
        this.orderView = _orderView;
        this.selectedSpidersList = FXCollections.observableArrayList();
        this.total = 0.0;
        this.orderOps = new OrderOps();
//        orderOps.clearAllOrders();          // For testing purposes
    }

    public ObservableList<Order> getOrderList() {
        return orderOps.getOrderList();
    }

    public void retrieveUIComponents() {
        this.addBtn = orderView.getAddBtn();
        this.removeBtn = orderView.getRemoveBtn();
        this.purchaseBtn = orderView.getPurchaseBtn();
        this.sortAZBtn = orderView.getSortAZBtn();
        this.sortPriceBtn = orderView.getSortPriceBtn();
        this.neworderBtn = orderView.getNewOrderBtn();
        this.viewOrdersBtn = orderView.getViewOrdersBtn();
        this.spiderBox = orderView.getSpiderBox();
        this.customerBox = orderView.getCustomerBox();
        this.checkoutView = orderView.getCheckoutView();

    }

    /**
     * This method sets up the buttons to trigger functionality.
     */
    public void setUpButtonActions() {
        addBtn.setOnAction(e -> addSpider());
        removeBtn.setOnAction(e -> removeSpider());
        purchaseBtn.setOnAction(e -> placeOrder());
        sortAZBtn.setOnAction(e -> sortPurchasedSpiders(selectedSpidersList, true));
        sortPriceBtn.setOnAction(e -> sortPurchasedSpiders(selectedSpidersList, false));
        neworderBtn.setOnAction(e -> resetOrder());
        viewOrdersBtn.setOnAction(e -> {
            loadOrdersFromFile();
            orderView.displayPastOrders();
        });

    }

    /**
     * Adds a spider to the basket. Displays a warning if any required items are not selected.
     */
    public void addSpider() {
        // Display alert if no customer or spider selected
        Customer selectedCustomer = customerBox.getValue();
        Spider selectedSpider = null;
        // Lock the customer in after a spider is added
        if (selectedCustomer != null) {
            customerBox.setDisable(true);
            selectedSpider = spiderBox.getValue();
        }

        if (selectedCustomer == null || selectedSpider == null) {
            // Display warning if either customer or spider not selected
            orderView.displayItemNotSelectedWarning();
        }
        else {
            // Otherwise, add the spider and update the total
            selectedSpidersList.add(selectedSpider);
            total += selectedSpider.getPrice();
            orderView.updateTotalLabel("$" + String.format("%.2f", total));
            checkoutView.setItems(selectedSpidersList);
        }
    }

    /**
     * Removes a spider from the basket.
     */
    public void removeSpider() {
        Spider selectedSpider = checkoutView.getSelectionModel().getSelectedItem();
        if (selectedSpider != null) {
            selectedSpidersList.remove(selectedSpider);
            total -= selectedSpider.getPrice();
            orderView.updateTotalLabel("$" + String.format("%.2f", total));
            checkoutView.setItems(selectedSpidersList);
        }
    }

    /**
     * Places an order by a customer for all the spiders in the basket. Places the purchased spiders into the receipt window
     * and generates an order record.
     */
    public void placeOrder() {
        Customer selectedCustomer = customerBox.getValue();
        // Make sure a customer is selected and has spiders in the basket
        if (selectedCustomer != null && !selectedSpidersList.isEmpty()) {
            // Save the order record to disk
            Order order = new Order(selectedCustomer, total);
            saveOrderToFile(order);
            orderView.displayCustomerAndDate(selectedCustomer);
            // Sort spiders alphabetically and display
            FXCollections.sort(selectedSpidersList, Comparator.comparing(spider -> spider.getSpecies().toLowerCase()));
            orderView.displayOrderReceiptInfo(selectedSpidersList);
            checkoutView.setItems(FXCollections.observableArrayList());
        }
        else {
            orderView.displayItemNotSelectedWarning();
        }
    }

    /**
     * Resets the window so a new order can be placed.
     */
    public void resetOrder() {
        selectedSpidersList.clear();
        total = 0.0;
        checkoutView.setItems(FXCollections.observableArrayList());
        customerBox.setDisable(false);
        orderView.clearOrderComponents();
        orderView.updateTotalLabel("$0.00");
    }


    /**
     * Sorts the spiders in the receipt window either alphabetically or by price
     * @param selectedSpidersList - The list of spiders to sort.
     * @param isAlphaSort - Whether to sort alphabetically or by price.
     */
    public void sortPurchasedSpiders(ObservableList<Spider> selectedSpidersList, boolean isAlphaSort) {
        if (isAlphaSort) {
            isAscendingAlpha = !isAscendingAlpha;
            if (isAscendingAlpha) {
                FXCollections.sort(selectedSpidersList, Comparator.comparing(spider -> spider.getSpecies().toLowerCase()));
                orderView.changeBtnTxt(sortAZBtn, "Sort Z-A");
            } else {
                FXCollections.sort(selectedSpidersList, Comparator.comparing((Spider spider) -> spider.getSpecies().toLowerCase()).reversed());
                orderView.changeBtnTxt(sortAZBtn, "Sort A-Z");
            }
        }

        else {
            isAscendingPrice = !isAscendingPrice;
            if (isAscendingPrice) {
                FXCollections.sort(selectedSpidersList, Comparator.comparingDouble(Spider::getPrice));
                orderView.changeBtnTxt(sortPriceBtn, "Sort Price ↓");
            }
            else {
                FXCollections.sort(selectedSpidersList, Comparator.comparingDouble(Spider::getPrice).reversed());
                orderView.changeBtnTxt(sortPriceBtn, ("Sort Price ↑"));
            }
                    }
        orderView.displayOrderReceiptInfo(selectedSpidersList);
    }

    /**
     * This function loads orders from a file into the order list.<br>
     */
    public void loadOrdersFromFile() {
       SerializationManager.getSerializationManager().deSerializeFile(orderOps.getOrderList(), Order.class);
    }

    /**
     * Saves the order to file.
     */
    public void saveOrderToFile(Order order) {
        loadOrdersFromFile();
        orderOps.getOrderList().add(order);
        SerializationManager.getSerializationManager().serializeFile(orderOps.getOrderList(), Order.class);
    }



}