package com.example.spideremporium.controller;
import com.example.spideremporium.dataManagement.MySQLManager;
import com.example.spideremporium.dataManagement.SerializationManager;
import com.example.spideremporium.model.Customer;
import com.example.spideremporium.model.CustomerOps;
import com.example.spideremporium.view.CustomerView;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class models an object used to manage the GUI operations.
 */
public class CustomerController {
    private CustomerOps customerOps;
    private CustomerView customerView;
    private Button addBtn, removeBtn, listBtn, serialSaveBtn, serialLoadBtn, dbSaveBtn, dbLoadBtn, exitBtn;

    public CustomerController(CustomerOps _customerOps) {
        this.customerOps = _customerOps;
    }

    public ObservableList<Customer> getCustomerList() {
        return customerOps.getCustomerList();
    }

    // Set the view and set up button references
    public void setCustomerView(CustomerView _customerView) {
        this.customerView = _customerView;

        // Get button references
        this.addBtn = customerView.getAddBtn();
        this.removeBtn = customerView.getRemoveBtn();
        this.listBtn = customerView.getListBtn();
        this.serialSaveBtn = customerView.getSerialSaveBtn();
        this.serialLoadBtn = customerView.getSerialLoadBtn();
        this.dbSaveBtn = customerView.getDbSaveBtn();
        this.dbLoadBtn = customerView.getDbLoadBtn();
        this.exitBtn = customerView.getExitBtn();
    }

    public void setUpButtonActions(Stage stage) {
        addBtn.setOnAction(e -> addCustomer());
        removeBtn.setOnAction(e -> removeCustomer());
        listBtn.setOnAction(e-> customerView.refreshCustomerDisplay());
        serialSaveBtn.setOnAction(e-> saveCustomers());
        serialLoadBtn.setOnAction(e-> loadCustomers());
        exitBtn.setOnAction(e-> exitApplication());
        dbSaveBtn.setOnAction(e-> saveCustomersToDB());
        dbLoadBtn.setOnAction(e-> loadCustomersFromDB());
    }

    public void loadCustomers() {
        SerializationManager.getSerializationManager().deSerializeFile(customerOps.getCustomerList(), Customer.class);
        if (customerView != null) {
            customerView.updateInfoText("Customers loaded.");
            customerView.showDataConfirmationAlert(true, true);
        }
    }

    public void saveCustomers() {
        SerializationManager.getSerializationManager().serializeFile(customerOps.getCustomerList(), Customer.class);
        customerView.updateInfoText("Customers saved.");
        customerView.showDataConfirmationAlert(false, true);
    }

    /**
     * This method exits the application after verifying whether the customer wants to save, not save, or cancel exiting.
     */
    public void exitApplication() {
        int saveBeforeExit = customerView.showExitAlert();
        if (saveBeforeExit == 1) {;
            SerializationManager.getSerializationManager().serializeFile(customerOps.getCustomerList(), Customer.class);
            MySQLManager.getmySQLManager().closeConnection();
            Platform.exit();
        }
        else if (saveBeforeExit == 2) {
            MySQLManager.getmySQLManager().closeConnection();
            Platform.exit();
        }
    }

    /**
     * This method retrieves customer data from the textFields, uses it to create a customer object
     * and add the customer to the list.
     */
    public void addCustomer() {
        String fname = customerView.getFnameData();     // Save data from text fields
        String lname = customerView.getLnameData();
        String address = customerView.getAddressData();
        String phone = customerView.getPhoneData();
        if (!fname.isBlank() && !lname.isBlank() && !address.isBlank() && !phone.isBlank()) {
            Customer customer = new Customer(fname, lname, address, phone);    // Create customer
            customerOps.addCustomer(customer);  // Add customer to list

            customerView.updateInfoText("Customer added.");
            customerView.clearCustomerDisplay();
        }
        else {
            customerView.displayEmptyFieldAlert();
        }
    }

    /**
     * This method removes a customer from the customerView display area.
     */
    public void removeCustomer() {
        Customer chosenCustomer = customerView.getSelectedCustomer();
        if (chosenCustomer != null) {
            MySQLManager.getmySQLManager().flagCustomerForRemoval(chosenCustomer);
            customerOps.removeCustomer(chosenCustomer);
            customerView.updateInfoText("Customer removed.");
        } else {
            customerView.updateInfoText("No customer selected.");
        }
    }

    public void loadCustomersFromDB() {
        ObservableList<Customer> customers = MySQLManager.getmySQLManager().loadCustomersFromDB();
        customerOps.getCustomerList().clear();
        customerOps.getCustomerList().addAll(customers);
        MySQLManager.getmySQLManager().clearRemovedCustomersList();
        customerView.updateInfoText("Customers loaded from DB!");
        customerView.showDataConfirmationAlert(true, false);
    }

    public void saveCustomersToDB() {
        try {
            MySQLManager.getmySQLManager().saveCustomers(customerOps.getCustomerList());
            customerView.updateInfoText("Customers saved to DB!");
            customerView.showDataConfirmationAlert(false, false);
        }
        catch (Exception e) {
            e.printStackTrace();
            customerView.updateInfoText("Could not save customers to DB!");
        }
    }


}