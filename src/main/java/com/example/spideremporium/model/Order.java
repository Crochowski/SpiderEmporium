package com.example.spideremporium.model;

import java.io.*;

/**
 * This class models an order that a customer makes for a spider.<br>
 */
public class Order {

    private static int nextOrder;   // The next order number to be assigned
    private int orderNo;            // The unique order number of the order
    private int custNo;             // The custNo that made the order
    private String species;           // The species that was bought
    private float price;            // The price of the order

    static {
        nextOrder = loadNextOrderNo();  // Load the next order number into nextOrder
    }


    /**
     * This constructor creates 1 instance of the class Order.<br>
     * @param _custNo - The unique ID of the customer who made the order.
     * @param _species - The unique species of the spider that was purchased in the order.
     * @param _price - The price of the spider purchased.
     */
    public Order(int _custNo, String _species, float _price) {
        this.custNo = _custNo;
        this.species = _species;
        this.price = _price;

        this.orderNo = nextOrder;
        nextOrder++;
        saveNextOrderNo();
    }


    /**
     * This constructor creates 1 instance of the class Order based on existing information in orders.txt.<br>
     * @param _orderNo - The unique order number.
     * @param _custNo - The unique ID of the customer who made the order.
     * @param _species - The unique species of the spider that was purchased in the order.
     * @param _price - The price of the spider purchased.
     */
    public Order(int _orderNo, int _custNo, String _species, float _price) {
        this.orderNo = _orderNo;
        this.custNo = _custNo;
        this.species = _species;
        this.price = _price;
    }


    /**
     * Given a concrete order (this), the function returns its order number.<br>
     * @return - The order number of the order.
     */
    public int getOrderNo() {
        return this.orderNo;
    }


    /**
     * Given a concrete order (this), the function returns the ID of the customer who made it.<br>
     * @return - The ID number of the customer.
     */
    public int getCustNo() {
        return this.custNo;
    }


    /**
     * Given a concrete order (this), the function returns the ID of the spider that was purchased.<br>
     * @return - The ID number of the spider.
     */
    public String getSpecies() {
        return this.species;
    }


    /**
     * Given a concrete order (this), the function returns the price of the purchased spider.<br>
     * @return - The price of the spider purchased.
     */
    public float getPrice() {
        return this.price;
    }


    /**
     * This function loads the next order number to be assigned from nextOrderNo.txt and returns it.<br>
     * @return - The next order number to be assigned.
     */
    public static int loadNextOrderNo() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("database/nextOrderNo.txt"));
            int NextOrderNo = Integer.parseInt(reader.readLine());
            return NextOrderNo;
        } catch (IOException error) {
            System.err.println("OrderNo could not be read. Starting from OrderNo 1.");
            return 1;       // Start from 1 if file cannot be read
        }
    }


    /**
     * This function saves the next order number to be assigned to nextOrderNo.txt.<br>
     */
    public void saveNextOrderNo() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("database/nextOrderNo.txt"));
            writer.write(Integer.toString(nextOrder));
            writer.close();
        } catch (IOException error) {
            System.err.println("Cannot write orderNo to file: " + error.getMessage());
        }
    }

}
