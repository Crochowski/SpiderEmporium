package com.example.spideremporium.model;

/**
 * This class creates a concrete builder used to build Customers.
 */
public class ConcreteCustomerBuilder implements CustomerBuilder {
    private Customer customer;

    public ConcreteCustomerBuilder() {
        this.customer = new Customer();
    }

    public void buildID(int custID) {
        customer.setCustID(custID);
    }

    public void buildFName(String fName) {
        customer.setfName(fName);
    }

    public void buildLName(String lName) {
        customer.setlName(lName);
    }

    public void buildAddress(String address) {
        customer.setAddress(address);
    }

    public void buildPhone(String phone) {
        customer.setPhone(phone);
    }

    public Customer getCustomer() {
        return this.customer;
    }

}
