module com.example.spideremporium {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.spideremporium to javafx.fxml;
    exports com.example.spideremporium.model;
    exports com.example.spideremporium.main;
    exports com.example.spideremporium.view;
    opens com.example.spideremporium.main to javafx.fxml;
    exports com.example.spideremporium.controller.dataAccess;
    exports com.example.spideremporium.controller.service;
}