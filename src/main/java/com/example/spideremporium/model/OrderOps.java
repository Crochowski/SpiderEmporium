package com.example.spideremporium.model;

import com.example.spideremporium.dataManagement.SerializationManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrderOps {
    private ObservableList<Order> orderList = FXCollections.observableArrayList();

    public ObservableList<Order> getOrderList() {return this.orderList;}

    public void clearAllOrders() {
        // Create an empty list
        ObservableList<Order> emptyList = FXCollections.observableArrayList();

        SerializationManager.getSerializationManager().serializeFile(emptyList, Order.class);
        orderList.clear();

        System.out.println("All orders have been cleared.");
    }
}