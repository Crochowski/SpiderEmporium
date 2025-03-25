package com.example.spideremporium.controller;

import com.example.spideremporium.model.*;
import com.example.spideremporium.view.OrderView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class OrderController {
    private OrderView orderView;
    private OrderOps orderOps;
    private Button addBtn, removeBtn, purchaseBtn;
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
    }


    public void retrieveButtons() {
        this.addBtn = orderView.getAddBtn();
        this.removeBtn = orderView.getRemoveBtn();
        this.purchaseBtn = orderView.getPurchaseBtn();
        this.spiderBox = orderView.getSpiderBox();
        this.customerBox = orderView.getCustomerBox();
        this.checkoutView = orderView.getCheckoutView();
    }

    public void setUpButtonActions() {
        addBtn.setOnAction(e -> addSpider());
        removeBtn.setOnAction(e -> removeSpider());
        purchaseBtn.setOnAction(e -> placeOrder());
    }

    public void addSpider() {
        // Display alert if no customer or spider selected
        Customer selectedCustomer = customerBox.getValue();
        customerBox.setDisable(true);
        Spider selectedSpider = spiderBox.getValue();

        if (selectedCustomer == null || selectedSpider == null) {
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

    public void removeSpider() {
        Spider selectedSpider = checkoutView.getSelectionModel().getSelectedItem();
        if (selectedSpider != null) {
            selectedSpidersList.remove(selectedSpider);
            total -= selectedSpider.getPrice();
            orderView.updateTotalLabel("$" + String.format("%.2f", total));
            checkoutView.setItems(selectedSpidersList);
        }
    }

    public void placeOrder() {
        Customer selectedCustomer = customerBox.getValue();
        // Make sure a customer is selected and has spiders in the basket
        if (selectedCustomer != null && !selectedSpidersList.isEmpty()) {
            orderOps.saveOrderToFile(selectedCustomer, total);
            orderView.displayPurchaseConfirmation(selectedCustomer.getfName(), total);
            resetOrder();
        }
        else {
            orderView.displayItemNotSelectedWarning();
        }
    }

    public void resetOrder() {
        selectedSpidersList.clear();
        total = 0.0;
        checkoutView.setItems(FXCollections.observableArrayList());
        customerBox.setDisable(false);
        orderView.updateTotalLabel("$0.00");
    }
}