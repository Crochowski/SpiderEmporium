package com.example.spideremporium.dataManagement;

import com.example.spideremporium.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class MySQLManager {
    private Connection connection;
    private static MySQLManager mySQLManager = new MySQLManager();

    private MySQLManager() {}

    public static MySQLManager getmySQLManager() {
        return mySQLManager;
    }

    public Connection getConnection() {
        // If there is no connection open, create one
        if (connection == null) {
            createConnection();
        }
        return connection;
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
            PreparedStatement pstmt = null;

            try {
                if (connection == null || connection.isClosed()) {
                    createConnection();
                }
                String query = "INSERT INTO customer (id, fname, lname, address, phone)VALUES (?, ?, ?, ?, ?)";

                for (Customer customer : customers) {
                    pstmt = connection.prepareStatement(query);
                    pstmt.setInt(1, customer.getCustID());
                    pstmt.setString(2, customer.getfName());
                    pstmt.setString(3, customer.getlName());
                    pstmt.setString(4, customer.getAddress());
                    pstmt.setString(5, customer.getPhone());

                    pstmt.executeUpdate();
                }
                System.out.println("Customers saved to database!");


            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        }
}
