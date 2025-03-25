module com.example.spideremporium {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.spideremporium to javafx.fxml;
    exports com.example.spideremporium;
}