package com.example.spideremporium.model;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class OrderOps {
    private ArrayList<Order> orderList = new ArrayList<>();

    public ArrayList<Order> getOrderList() {return this.orderList;}

    /**
     * This function loads orders from a file into the order list.<br>
     */
    public void loadOrdersFromFile() {
        String fileName = "database/orders.ser";
        orderList.clear();

        try {
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);

            orderList = (ArrayList<Order>) in.readObject();
            in.close();
            file.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Cannot load orders from file " + e.getMessage());
        }
    }

    /**
     * Saves the order to file
     */
    public void saveOrderToFile(Customer customer, double total) {
        Order order = new Order(customer, total);
        String fileName = "database/orders.ser";

        loadOrdersFromFile();   // load existing orders to memory
        orderList.add(order);   // append this order to that list

        try {
            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(orderList);
            out.close();
            file.close();
        }
        catch (IOException e) {
            System.out.println("Cannot save order to file: " + e.getMessage());
        }
    }
    
}