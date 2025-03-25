package com.example.spideremporium.model;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrderOps {

    /**
     * Saves the order to file
     */
    public void saveOrderToFile(Customer customer, double total) {
        BufferedWriter writer = null;
        String filename = "database/orders.txt";

        LocalDate todaysDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = todaysDate.format(formatter);

        try {
            writer = new BufferedWriter(new FileWriter(filename, true));
            writer.write(customer.getCustID() + "," + customer.getfName() + "," +
                    customer.getlName() + "," + total + "," + formattedDate + "\n");
        } catch (IOException e) {
            System.out.println("Cannot save order to disk: " + e.getMessage());
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println("Cannot close writer: " + e.getMessage());
            }
        }
    }
}