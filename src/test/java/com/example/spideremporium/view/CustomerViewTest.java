package com.example.spideremporium.view;

import javafx.application.Platform;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerViewTest {


    @Test
    public void TestCreateButtons() {
        Platform.runLater(() -> {
            CustomerView customerView = new CustomerView();
            customerView.createButtons();
            assertNotNull(customerView.getAddBtn());
        });

    }

}