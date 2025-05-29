package com.example.spideremporium.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

/**
 * This class models operations such as adding and removing Customer objects from a list.
 */
public class CustomerOps {

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    public ObservableList<Customer> getCustomerList() {
        return this.customerList;
    }


    /**
     * This method adds a customer to customerList.
     * @param customer - The customer to be added.
     */
    public void addCustomer(Customer customer) {
        customerList.add(customer);
    }


    /**
     * This method removes a customer from customerList.
     * @param customer - The customer to be removed.
     */
    public void removeCustomer(Customer customer) {
        customerList.remove(customer);
    }

}
