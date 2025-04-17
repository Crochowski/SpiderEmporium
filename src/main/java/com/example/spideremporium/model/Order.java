package com.example.spideremporium.model;

import java.io.*;
import java.time.LocalDate;

public class Order implements Serializable {
    private static int nextId;          // Stores the next ID to be assigned
    private int orderID;
    private Customer customer;
    private double total;
    private LocalDate date;

    static {
        nextId = loadNextID();         // Load the next ID into nextId
    }

    /**
     * This constructor creates an instance of an order to save to orders.ser.
     * @param _customer - The customer who placed the order.
     * @param _total - The total price of the order.
     */
    public Order(Customer _customer, double _total) {
        this.customer = _customer;
        this.total = _total;
        this.date = LocalDate.now();
        this.orderID = nextId;
        nextId++;
        saveNextId();
    }

    /**
     * Saves the next OrderID to nextOrderId.txt.
     */
    private void saveNextId() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("database/nextOrderID.txt"));
            writer.write(Integer.toString(nextId));
            writer.close();
        }
        catch (IOException error) {
            System.err.println("Cannot write orderID to file: " + error.getMessage());
        }
    }

    /**
     * Loads the next ID to be assigned from nextOrderId.txt and returns it.
     * @return - The next orderID to be assigned.
     */
    public static int loadNextID() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("database/nextOrderID.txt"));
            int nextID = Integer.parseInt(reader.readLine());
            return nextID;
        }
        catch (IOException error) {
            System.err.println("ID could not be read. Starting from ID 1.");
            return 1;
        }
    }

    public String toString() {
        return "OrderID: " + this.orderID + " Date: " + this.date + " Name: " + this.customer.getfName() + " " +
        this.customer.getlName() + " Total: " + String.format("€%.2f", this.total);
    }

}
