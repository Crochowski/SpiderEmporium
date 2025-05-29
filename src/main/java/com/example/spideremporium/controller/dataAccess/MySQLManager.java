package com.example.spideremporium.controller.dataAccess;

import com.example.spideremporium.model.ConcreteCustomerBuilder;
import com.example.spideremporium.model.Customer;
import com.example.spideremporium.model.CustomerBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
public class MySQLManager {

    private static final MySQLManager mySQLManager = new MySQLManager();
    private Connection connection;
    private ArrayList<Customer> removedCustomers = new ArrayList<>();

    private MySQLManager() {}

    public static MySQLManager getmySQLManager() {
        return mySQLManager;
    }


    /**
     * Creates a connection to the database.
     */
    public void createConnection() {
        try {
            // Load driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/spideremporium";
            String user = "root";
            String password = "root";


            // Establish connection
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection Established!");
        }
        catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
            e.printStackTrace();
        }
        catch(SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    /**
     * Closes the connection to the database.
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed");
            }
        }
        catch (SQLException e) {
            System.out.println("Could not close connection");
            e.printStackTrace();
        }
    }


    /**
     * Loads the customers from the database.Recreates the customer objects
     * using the concrete customerbuilder and returns the customers in a list.
     * @return - The list of customers.
     */
    public ObservableList<Customer> loadCustomersFromDB() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        Statement stmt = null;
        ResultSet rset = null;

        try {
            // If the connection doesn't exist or is inactive, create it
            if (connection == null || connection.isClosed()) {
                createConnection();
            }

            stmt = connection.createStatement();
            String query = "SELECT * FROM customer";
            rset = stmt.executeQuery(query);

            while (rset.next()) {

                CustomerBuilder customerBuilder = new ConcreteCustomerBuilder();
                customerBuilder.buildID(rset.getInt("id"));
                customerBuilder.buildFName(rset.getString("fname"));
                customerBuilder.buildLName(rset.getString("lname"));
                customerBuilder.buildAddress(rset.getString("address"));
                customerBuilder.buildPhone(rset.getString("phone"));

                Customer customer = customerBuilder.getCustomer();
                customers.add(customer);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                stmt.close();
                rset.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return customers;
        }

    /**
     * Saves the customers in memory to the database. If customers have been removed while the application is running,
     * this method also removes those customers from the database.
     * @param customers - The list of customers to be saved.
     */
    public void saveCustomersToDB(ObservableList<Customer> customers) {

            try {
                if (connection == null || connection.isClosed()) {
                    createConnection();
                }

                // Delete any customers that have been removed first
                String deleteQuery = "DELETE FROM customer WHERE id= ?";
                for (Customer customer: removedCustomers) {
                    PreparedStatement pstmt = connection.prepareStatement(deleteQuery);
                    pstmt.setInt(1, customer.getCustID());
                    pstmt.executeUpdate();
                }


                // Insert the customers from the list. On duplicate prevents error from attempting to write an existing
                // customer back into the database.
                String insertQuery = "INSERT INTO customer (id, fname, lname, address, phone)VALUES (?, ?, ?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE fname=VALUES(fname), lname=VALUES(lname), address=VALUES(address), " +
                        "phone=VALUES(phone)";

                for (Customer customer : customers) {
                    PreparedStatement pstmt = connection.prepareStatement(insertQuery);
                    pstmt.setInt(1, customer.getCustID());
                    pstmt.setString(2, customer.getfName());
                    pstmt.setString(3, customer.getlName());
                    pstmt.setString(4, customer.getAddress());
                    pstmt.setString(5, customer.getPhone());

                    pstmt.executeUpdate();
                }

               clearRemovedCustomersList();

            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        }

    /**
     * Flag a customer for removal so that it will be removed when we save the list to db.
     * @param customer - the customer to be added to the removal list.
     */
    public void flagCustomerForRemoval(Customer customer) {
            this.removedCustomers.add(customer);
        }

    public void clearRemovedCustomersList() {
        removedCustomers.clear();
    }

}
