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


    /**
     * This method loads the customers from customers.txt into the customerList.
     */
    public void loadCustomersFromFile() {
        BufferedReader reader;
        String fileName = "database/customers.txt";
        customerList.clear();

        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                int custID = Integer.parseInt(data[0]);
                String fName = data[1];
                String lName = data[2];
                String address = data[3];

                Customer customer = new Customer(custID, fName, lName, address);
                customerList.add(customer);

            }
        } catch (IOException error) {
            System.err.println("Cannot read customer from file: " + error.getMessage());
        }
    }


    /**
     * This function saves the customers from the customer list into customers.txt.
     */
    public void writeCustomersToFile() {
        Writer customerWriter;
        String customersFileName = "database/customers.txt";
        String customerData = "";
        Customer customer;

        try {
            new BufferedWriter(new FileWriter(customersFileName)).close(); // Wipe the file to avoid Customer duplication

            customerWriter = new BufferedWriter(new FileWriter(customersFileName, true));

            for (int i=0; i<customerList.size(); i++) {
                customer = customerList.get(i);

                customerData = customer.getCustID() + "," + customer.getfName() + "," + customer.getlName() +
                        "," + customer.getAddress();

                customerWriter.write(customerData + "\n");
            }
            customerWriter.close();
        }
        catch (IOException error) {
            System.err.println("Cannot write to file");
        }
    }

}
