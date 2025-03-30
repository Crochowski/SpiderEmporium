package com.example.spideremporium.controller;

import com.example.spideremporium.model.*;
import com.example.spideremporium.view.SpiderView;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.ArrayList;

public class SpiderController {
    private SpiderOps spiderOps;
    private SpiderView spiderView;
    private ListView<Spider> spiderListView;
    private TextField speciesData, priceData;
    private ToggleGroup typeGroup;
    private ComboBox<String> potencyData;
    private Button addBtn, removeBtn, listBtn, saveBtn, loadBtn, exitBtn;

    public SpiderController(SpiderOps _spiderOps) {
        this.spiderOps = _spiderOps; // Connect to the model
    }

    // Set the view and retrieve references to components
    public void setSpiderView(SpiderView _spiderView) {
        this.spiderView = _spiderView;

        // Get UI references
        this.spiderListView = spiderView.getSpiderListView();
        this.speciesData = spiderView.getSpeciesData();
        this.priceData = spiderView.getPriceData();
        this.typeGroup = spiderView.getTypeGroup();
        this.potencyData = spiderView.getPotencyBox();

        // Get button references
        this.addBtn = spiderView.getAddBtn();
        this.removeBtn = spiderView.getRemoveBtn();
        this.listBtn = spiderView.getListBtn();
        this.saveBtn = spiderView.getSaveBtn();
        this.loadBtn = spiderView.getLoadBtn();
        this.exitBtn = spiderView.getExitBtn();
    }

    public void setUpButtonActions(Stage stage) {
        addBtn.setOnAction(e -> addSpider());
        removeBtn.setOnAction(e -> removeSpider());
        listBtn.setOnAction(e -> displaySpiders());
        saveBtn.setOnAction(e -> saveSpiders());
        loadBtn.setOnAction(e -> loadSpiders());
        exitBtn.setOnAction(e -> exitApplication());
    }

    public void loadSpiders() {
        spiderOps.loadSpidersFromFile();
        if (spiderView != null) {
            spiderView.updateInfoText("Spiders loaded.");
        }
    }

    public void exitApplication() {
        boolean saveBeforeExit = spiderView.showExitAlert();
        if (saveBeforeExit) {
            spiderOps.writeSpidersToFile();
        }
        Platform.exit();

    }

    public void addSpider() {
        String species = speciesData.getText();     // Save data from text fields

        // If all fields have been selected, proceed
        if (!speciesData.getText().isBlank() && !priceData.getText().isBlank() &&
                potencyData.getValue() != null && typeGroup.getSelectedToggle() != null) {

            float price = Float.parseFloat(priceData.getText());
            RadioButton selectedType = (RadioButton) this.typeGroup.getSelectedToggle();
            String type = selectedType.getText();
            int potency = Integer.parseInt(this.potencyData.getValue());

            if (type.equals("Venomous")) {
                VenomousSpider venomousSpider = new VenomousSpider(species, potency, price);
                spiderOps.addSpider(venomousSpider);
                spiderView.updateInfoText("Venomous Spider added.");
            }
            else if (type.equals("Illegal")) {
                IllegalSpider illegalSpider = new IllegalSpider(species, potency, price);
                spiderOps.addSpider(illegalSpider);
                spiderView.updateInfoText("Illegal Spider added.");
            }

            // Clear the textfields & dropdown
            spiderView.clearFields();
        }
        else {
            // Otherwise display the empty field alert
            spiderView.displayEmptyFieldAlert();
        }
    }

    public void removeSpider() {
        Spider chosenSpider = spiderListView.getSelectionModel().getSelectedItem();
        if (chosenSpider != null) {
            spiderOps.removeSpider(chosenSpider);
            spiderView.updateInfoText("Spider removed.");
        } else {
            spiderView.updateInfoText("No spider selected.");
        }
    }

    public void displaySpiders() {
        spiderView.displaySpiders();
    }

    public void saveSpiders() {
        spiderOps.writeSpidersToFile();
        spiderView.updateInfoText("Spiders saved.");
    }

    public ArrayList<Spider> getSpiderList() {
        return spiderOps.getSpiderList();
    }
}