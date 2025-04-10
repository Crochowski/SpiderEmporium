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
    private Button addBtn, removeBtn, listBtn, saveBtn, loadBtn, exitBtn, counterBtn, stockBtn;
    private SerializationManager serializationManager = SerializationManager.getSerializationManager();

    public SpiderController(SpiderOps _spiderOps) {
        this.spiderOps = _spiderOps; // Connect to the model
    }

    public ArrayList<Spider> getSpiderList() {
        return spiderOps.getSpiderList();
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
        this.counterBtn = spiderView.getCounterBtn();
        this.stockBtn = spiderView.getStockBtn();
    }

    public void setUpButtonActions(Stage stage) {
        addBtn.setOnAction(e -> addSpider());
        removeBtn.setOnAction(e -> removeSpider());
        listBtn.setOnAction(e -> displaySpiders());
        saveBtn.setOnAction(e -> saveSpiders());
        loadBtn.setOnAction(e -> loadSpiders());
        exitBtn.setOnAction(e -> exitApplication());
        counterBtn.setOnAction(e -> incrementStockCount());
        stockBtn.setOnAction(e -> viewStock());
    }

    public void loadSpiders() {
        serializationManager.deSerializeFile(spiderOps.getSpiderList(), Spider.class);
        if (spiderView != null) {
            spiderView.updateInfoText("Spiders loaded.");
        }
    }

    public void exitApplication() {
        boolean saveBeforeExit = spiderView.showExitAlert();
        if (saveBeforeExit) {
            serializationManager.serializeFile(spiderOps.getSpiderList(), Spider.class);
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
            int stock = spiderView.getStockCount();

            if (type.equals("Venomous")) {
                VenomousSpider venomousSpider = new VenomousSpider(species, potency, price, stock);
                spiderOps.addSpider(venomousSpider);
                spiderView.updateInfoText("Venomous Spider added.");
            }
            else if (type.equals("Illegal")) {
                IllegalSpider illegalSpider = new IllegalSpider(species, potency, price, stock);
                spiderOps.addSpider(illegalSpider);
                spiderView.updateInfoText("Illegal Spider added.");
            }

            // Clear the textfields & dropdown and reset the stock count
            spiderView.clearFields();
            spiderView.resetStockCount();

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
        serializationManager.serializeFile(spiderOps.getSpiderList(), Spider.class);
        spiderView.updateInfoText("Spiders saved.");
    }

    public void incrementStockCount() {
        spiderView.incrementStockCounter();
    }

    public void viewStock() {
        Spider spider = spiderListView.getSelectionModel().getSelectedItem();
        if (spider != null) {
            spiderView.displayStockNum(spider.getStockCount());
        }

    }

}