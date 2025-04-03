package com.example.spideremporium.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerTest {


    /**
     * This method tests the Customer constructor, ensuring the correct values
     * are set for the customer object.
     */
    @Test
    public void validCustomerCreation() {
        String fName = "Kaladin";
        String lName = "Stormblessed";
        String address = "Hearthstone";

        Customer customer = new Customer(fName, lName, address);
        assertEquals(fName, customer.getfName());
        assertEquals(lName, customer.getlName());
        assertEquals(address, customer.getAddress());
    }
}