package com.example.spideremporium.model;

import java.io.*;

/**
 * This class models a customer of the store.
 */
public class Customer implements Serializable {

    private static int nextId;          // Stores the next ID to be assigned
    private int custID;                 // The customer's unique ID
    private String fName;               // The customer's first name
    private String lName;               // The customer's last name
    private String address;             // The customer's address
    private String phone;

    static {
        nextId = loadNextID();         // Load the next ID into nextId
    }

    /**
     * This constructor is used to create a first time customer.<br>
     * @param _fName - The customer's first name
     * @param _lName - The customer's last name
     * @param _address - The customer's address
     * @param _phone - The customer's phone number
     */
    public Customer(String _fName, String _lName, String _address, String _phone) {
        this.fName = _fName;
        this.lName = _lName;
        this.address = _address;
        this.phone = _phone;
        this.custID = nextId;
        nextId++;
        saveNextID();               // Save the next ID assigned to files
    }

    /**
     * This constructor is for use with the sql database.
     * @param _id - The customer's id
     * @param _fName - The customer's first name
     * @param _lName - The customer's last name
     * @param _address - The customer's address
     * @param _phone - The customer's phone number

     */
    public Customer(int _id, String _fName, String _lName, String _address, String _phone) {
        this.custID = _id;
        this.fName = _fName;
        this.lName = _lName;
        this.address = _address;
        this.phone = _phone;
    }

    /**
     * Empty constructor for the builder
     */
    public Customer() {

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

    public String getPhone() { return this.phone; }

    public void setCustID(int _custID) {
        this.custID = _custID;
    }

    public void setfName(String _fName) {
        this.fName = _fName;
    }

    public void setlName(String _lName) {
        this.lName = _lName;
    }

    public void setAddress(String _address) {
        this.address = _address;
    }

    public void setPhone(String _phone) {
        this.phone = _phone;
    }


    /**
     * This function loads the next ID to be assigned to a customer from nextCustomerID.txt.<br>
     * @return - The last ID assigned, or 1 if the file fails to load.
     */
    public static int loadNextID() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("database/nextCustomerID.txt"));
            int nextID = Integer.parseInt(reader.readLine());
            return nextID;
        }
        catch (IOException error) {
            System.err.println("ID could not be read. Starting from ID 1.");
            return 1;
        }
    }


    /**
     * The function saves the next ID to be assigned to a customer to nextCustomerID.txt.
     */
    public void saveNextID() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("database/nextCustomerID.txt"));
            writer.write(Integer.toString(nextId));
            writer.close();
        }
        catch (IOException error) {
            System.err.println("Cannot write CustomerID to file: " + error.getMessage());
        }
    }


    /**
     * This function overrides Object.toString to show Customer objects as fname lname address
     * @return - The concatenated string representing a customer object.
     */
    public String toString() {
        return this.custID + " | " + this.fName + " " + this.getlName() + " | " + this.address + " | " + this.phone;
    }
}
