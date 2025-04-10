package com.example.spideremporium.controller;

import com.example.spideremporium.model.Customer;
import com.example.spideremporium.model.CustomerOps;
import com.example.spideremporium.model.Spider;
import com.example.spideremporium.model.SpiderOps;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class SerializationManager {

    private static SerializationManager serializationManager = new SerializationManager();

    private SerializationManager() {

    }

    public static SerializationManager getSerializationManager() {
        return serializationManager;
    }

    /**
     * This method loads the customers from customers.txt into the customerList.
     */
    public void loadCustomersFromFile(CustomerOps customerOps) {
        ArrayList<Customer> customerList = customerOps.getCustomerList();
        String fileName = "database/customers.ser";
        customerList.clear();

        try {
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);

            ArrayList<Customer> customers = (ArrayList<Customer>) in.readObject();
            customerList.addAll(customers);
            in.close();
            file.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Cannot load customers from file: " + e.getMessage());
        }
    }


    /**
     * This function saves the customers from the customer list into customers.txt.
     */
    public void writeCustomersToFile(CustomerOps customerOps) {
        ArrayList<Customer> customerList = customerOps.getCustomerList();
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

    /**
     * This function loads spiders from a file into the spider list.<br>
     */
    public void loadSpidersFromFile(SpiderOps spiderOps) {
        ArrayList<Spider> spiderList = spiderOps.getSpiderList();
        String fileName = "database/spiders.ser";
        spiderList.clear();

        try {
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);

            ArrayList<Spider> spiders = (ArrayList<Spider>) in.readObject();
            spiderList.addAll(spiders);
            in.close();
            file.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Cannot load spiders from file " + e.getMessage());
        }
    }


    /**
     * This function saves the spiders from the spider list to their respective files.<br>
     */
    public void writeSpidersToFile(SpiderOps spiderOps) {
        ArrayList<Spider> spiderList = spiderOps.getSpiderList();
        String fileName = "database/spiders.ser";           // String to store file data to be written to database
        try {
            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(spiderList);
            out.close();
            file.close();
        }
        catch (IOException e) {
            System.out.println("Cannot save spiders to file: " + e.getMessage());
        }

    }

}
