package com.example.spideremporium;

import com.example.spideremporium.controller.CustomerController;
import com.example.spideremporium.controller.OrderController;
import com.example.spideremporium.controller.SpiderController;
import com.example.spideremporium.model.CustomerOps;
import com.example.spideremporium.model.OrderOps;
import com.example.spideremporium.model.SpiderOps;
import com.example.spideremporium.view.CustomerView;
import com.example.spideremporium.view.OrderView;
import com.example.spideremporium.view.SpiderView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class SpiderApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        TabPane tabPane = new TabPane();

        // Customer Tab
        Tab customerTab = new Tab("Customers");
        customerTab.setClosable(false);

        CustomerOps customerOps = new CustomerOps();
        CustomerView customerView = new CustomerView();
        CustomerController customerController = new CustomerController(customerOps);

        // Connect the view and controller
        customerView.setCustomerController(customerController);
        customerController.setCustomerView(customerView);

        customerTab.setContent(customerView.getRoot());

        // Spider tab
        Tab spiderTab = new Tab("Manage Inventory");
        spiderTab.setClosable(false);

        SpiderOps spiderOps = new SpiderOps();
        SpiderView spiderView = new SpiderView();
        SpiderController spiderController = new SpiderController(spiderOps);

        spiderView.setSpiderController(spiderController);
        spiderController.setSpiderView(spiderView);

        spiderTab.setContent(spiderView.getRoot());

        // Order tab
        Tab orderTab = new Tab("Order Spiders");
        orderTab.setClosable(false);

        OrderOps orderOps = new OrderOps();
        OrderView orderView = new OrderView();
        OrderController orderController = new OrderController(orderView);

        orderView.setOrderController(orderController);

        // Get button references from view
        orderController.retrieveUIComponents();

        // Load customer and spider lists from model into view
        orderView.setAvailableCustomers(customerOps.getCustomerList());
        orderView.setAvailableSpiders(spiderOps.getSpiderList());

        orderTab.setContent(orderView.getRoot());

        // Add tabs to the TabPane
        tabPane.getTabs().add(customerTab);
        tabPane.getTabs().add(orderTab);
        tabPane.getTabs().add(spiderTab);


        Scene scene = new Scene(tabPane, 600, 580);
        stage.setScene(scene);
        stage.setTitle("Spider Emporium \uD83D\uDD77\uFE0F");

        // Set up button actions for all controllers
        customerController.setUpButtonActions(stage);
        spiderController.setUpButtonActions(stage);
        orderController.setUpButtonActions();

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}