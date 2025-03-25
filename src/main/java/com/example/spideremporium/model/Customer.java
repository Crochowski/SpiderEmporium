package com.example.spideremporium.model;

import java.io.*;

/**
 * This class models a customer of the store.
 */
public class Customer {

    private static int nextId;          // Stores the next ID to be assigned
    private int custID;                 // The customer's unique ID
    private String fName;               // The customer's first name
    private String lName;               // The customer's last name
    private String address;             // The customer's address
    private boolean isValued;           // A customer is valued if they have purchased more than 2 spiders
    private boolean isSensitive;        // The customer is sensitive if they have ever purchased an illegal spider
    private int totalSpidersPurchased;  // The total number of spiders a customer has ever purchased

    static {
        nextId = loadNextID();         // Load the next ID into nextId
    }

    /**
     * This constructor is used to create a first time customer.<br>
     * @param _fName - The customer's first name
     * @param _lName - The customer's last name
     */
    public Customer(String _fName, String _lName, String _address) {
        this.fName = _fName;
        this.lName = _lName;
        this.address = _address;
        this.isValued = false;      // New customer is not yet valued
        this.isSensitive = false;   // New customer is not yet sensitive
        totalSpidersPurchased = 0;  // New customer hasn't purchased any spiders yet
        this.custID = nextId;
        nextId++;
        saveNextID();               // Save the next ID assigned to files
    }


    /**
     * This constructor is used to create a customer from existing information in the customers.txt file.<br>
     * @param _custID - The unique ID of the customer
     * @param _fName - The first name of the customer
     * @param _lName - The last name of the customer
     */
    public Customer(int _custID, String _fName, String _lName, String address) {
        this.custID = _custID;
        this.fName = _fName;
        this.lName = _lName;
        this.address = address;
        this.isValued = this.totalSpidersPurchased >= 3;    // The customer is valued if they have purchased more than 3 spiders
    }


    /**
     * Given a concrete customer (this), the function returns its custID.
     * @return The unique ID of the customer.
     */
    public int getCustID() {
        return this.custID;
    }


    /**
     * Given a concrete customer (this), the function returns its first name.
     * @return The first name of the customer.
     */
    public String getfName() {
        return this.fName;
    }


    /**
     * Given a concrete customer (this), the function returns its last name.
     * @return The last name of the customer.
     */
    public String getlName() {
        return this.lName;
    }


    /**
     * Given a concrete customer (this), the function returns its address.
     * @return The address of the customer.
     */
    public String getAddress() { return this.address; }


    /**
     * Given a concrete customer (this), the function returns its sensitive status.
     * @return The sensitive status of the customer.
     */
    public boolean getIsSensitive() {
        return this.isSensitive;
    }


    /**
     * Given a concrete customer (this), the function returns its valued status.
     * @return The valued status of the customer.
     */
    public boolean getIsValued() {
        return this.isValued;
    }


    /**
     * Given a concrete customer (this), the function returns the total number of spiders they have ever purchased.
     * @return The total number of spiders a customer has purchased from the store.
     */
    public int getTotalSpidersPurchased() {
        return this.totalSpidersPurchased;
    }



    /**
     * Given a concrete customer (this), the function sets its sensitive status.
     * @param _isSensitive - The sensitive status of the customer.
     */
    public void setIsSensitive(boolean _isSensitive) {
        this.isSensitive = _isSensitive;
    }


    /**
     * Given a concrete customer (this), the function increments its total spiders purchased by 1.<br>
     * It also sets the customer's valued status to true if they have purchased more than 2 spiders in total.
     */
    public void incrementTotalSpidersPurchased() {
        this.totalSpidersPurchased ++;
        if (this.totalSpidersPurchased >= 3) {
            this.isValued = true;
        }
    }


    /**
     * This function loads the next ID to be assigned to a customer from nextID.txt.<br>
     * @return - The last ID assigned, or 1 if the file fails to load.
     */
    public static int loadNextID() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("database/nextID.txt"));
            int nextID = Integer.parseInt(reader.readLine());
            return nextID;
        }
        catch (IOException error) {
            System.err.println("ID could not be read. Starting from ID 1.");
            return 1;
        }
    }


    /**
     * The function saves the next ID to be assigned to a customer to nextID.txt.
     */
    public void saveNextID() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("database/nextID.txt"));
            writer.write(Integer.toString(nextId));
            writer.close();
        }
        catch (IOException error) {
            System.err.println("Cannot write orderNo to file: " + error.getMessage());
        }
    }


    /**
     * This function overrides Object.toString to show Customer objects as fname lname address
     * @return - The concatenated string representing a customer object.
     */
    public String toString() {
        return this.custID + " | " + this.fName + " " + this.getlName() + " | " + this.address;
    }
}
