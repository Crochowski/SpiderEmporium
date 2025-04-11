package com.example.spideremporium.controller;
import com.example.spideremporium.model.Customer;
import com.example.spideremporium.model.CustomerOps;
import com.example.spideremporium.view.CustomerView;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * This class models an object used to manage the GUI operations.
 */
public class CustomerController {
    private CustomerOps customerOps;
    private CustomerView customerView;
    private Button addBtn, removeBtn, listBtn, saveBtn, loadBtn, exitBtn;

    public CustomerController(CustomerOps _customerOps) {
        this.customerOps = _customerOps;
    }

    // Set the view and set up button references
    public void setCustomerView(CustomerView _customerView) {
        this.customerView = _customerView;

        // Get button references
        this.addBtn = customerView.getAddBtn();
        this.removeBtn = customerView.getRemoveBtn();
        this.listBtn = customerView.getListBtn();
        this.saveBtn = customerView.getSaveBtn();
        this.loadBtn = customerView.getLoadBtn();
        this.exitBtn = customerView.getExitBtn();
    }

    public void setUpButtonActions(Stage stage) {
        addBtn.setOnAction(e -> addCustomer());
        removeBtn.setOnAction(e -> removeCustomer());
        listBtn.setOnAction(e-> customerView.refreshCustomerDisplay());
        saveBtn.setOnAction(e-> saveCustomers());
        loadBtn.setOnAction(e-> loadCustomers());
        exitBtn.setOnAction(e-> exitApplication());
    }

    public void loadCustomers() {
        SerializationManager.getSerializationManager().deSerializeFile(customerOps.getCustomerList(), Customer.class);
        if (customerView != null) {
            customerView.updateInfoText("Customers loaded.");
            customerView.showDataConfirmationAlert(true);
        }
    }

    public void saveCustomers() {
        SerializationManager.getSerializationManager().serializeFile(customerOps.getCustomerList(), Customer.class);
        customerView.updateInfoText("Customers saved.");
        customerView.showDataConfirmationAlert(false);
    }

    /**
     * This method exits the application after verifying whether the customer wants to save, not save, or cancel exiting.
     */
    public void exitApplication() {
        int saveBeforeExit = customerView.showExitAlert();
        if (saveBeforeExit == 1) {;
            SerializationManager.getSerializationManager().serializeFile(customerOps.getCustomerList(), Customer.class);
            Platform.exit();
        }
        else if (saveBeforeExit == 2) {
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
            customerOps.removeCustomer(chosenCustomer);
            customerView.updateInfoText("Customer removed.");
        } else {
            customerView.updateInfoText("No customer selected.");
        }
    }



    public ObservableList<Customer> getCustomerList() {
        return customerOps.getCustomerList();
    }
}