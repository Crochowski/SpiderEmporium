package com.example.spideremporium.dataManagement;

import com.example.spideremporium.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
public class MySQLManager {
    private Connection connection;
    private static MySQLManager mySQLManager = new MySQLManager();
    private ArrayList<Customer> removedCustomers = new ArrayList<>();

    private MySQLManager() {}

    public static MySQLManager getmySQLManager() {
        return mySQLManager;
    }


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
                int id = rset.getInt("id");
                String fname = rset.getString("fname");
                String lname = rset.getString("lname");
                String address = rset.getString("address");
                String phone = rset.getString("phone");

                Customer customer = new Customer(id, fname, lname, address, phone);
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

        public void saveCustomers(ObservableList<Customer> customers) {

            try {
                if (connection == null || connection.isClosed()) {
                    createConnection();
                }

                String deleteQuery = "DELETE FROM customer WHERE id= ?";
                for (Customer customer: removedCustomers) {
                    PreparedStatement pstmt = connection.prepareStatement(deleteQuery);
                    pstmt.setInt(1, customer.getCustID());
                    pstmt.executeUpdate();
                }


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
