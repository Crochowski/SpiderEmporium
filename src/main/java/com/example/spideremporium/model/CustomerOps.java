package com.example.spideremporium.model;

import java.io.*;
import java.util.ArrayList;

/**
 * This class models operations such as adding and removing Customer objects from a list.
 */
public class CustomerOps {
    private ArrayList<Customer> customerList = new ArrayList<>();

    public ArrayList<Customer> getCustomerList() {
        return this.customerList;
    }


    /**
     * This method adds a customer to customerList.
     *
     * @param customer - The customer to be added.
     */
    public void addCustomer(Customer customer) {
        customerList.add(customer);
    }


    /**
     * This method removes a customer from customerList.
     *
     * @param customer - The customer to be removed.
     */
    public void removeCustomer(Customer customer) {
        customerList.remove(customer);
    }


    /**
     * This method loads the customers from customers.txt into the customerList.
     */
    public void loadCustomersFromFile() {
        String fileName = "database/customers.ser";
        customerList.clear();

        try {
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);

            customerList = (ArrayList<Customer>) in.readObject();
            in.close();
            file.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Cannot load customers from file: " + e.getMessage());
        }
    }


    /**
     * This function saves the customers from the customer list into customers.txt.
     */
    public void writeCustomersToFile() {
        String fileName = "database/customers.ser";
        try {
            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(customerList);
            out.close();
            file.close();
        }
        catch (IOException e) {
            System.out.println("Cannot save customers to file: " + e.getMessage());
        }
    }
}
