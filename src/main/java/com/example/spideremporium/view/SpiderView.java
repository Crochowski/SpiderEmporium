package com.example.spideremporium.view;

import com.example.spideremporium.controller.SpiderController;
import com.example.spideremporium.model.Spider;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Optional;

public class SpiderView {
    private VBox root;
    private int stockCount = 1;
    private SpiderController spiderController;
    private TextField speciesData, priceData;
    private ComboBox<String> potencyBox;
    private Label infoLabel, stockLabel;
    private Button addBtn, removeBtn, listBtn, loadBtn, saveBtn, exitBtn, stockBtn, counterBtn;
    private ListView<Spider> spiderListView;
    private ToggleGroup typeGroup;

    public SpiderView() {
        this.root = new VBox(30);
        this.root.setStyle("-fx-background-color: #3e2340");
    }

    public void setSpiderController(SpiderController _spiderController) {
        this.spiderController = _spiderController;
        createUI();
    }

    public VBox getRoot() {return this.root;}

    public Button getAddBtn() {
        return this.addBtn;
    }

    public Button getRemoveBtn() {
        return this.removeBtn;
    }

    public Button getListBtn() {
        return this.listBtn;
    }

    public Button getSaveBtn() {
        return this.saveBtn;
    }

    public Button getLoadBtn() {
        return this.loadBtn;
    }

    public Button getExitBtn() {
        return this.exitBtn;
    }

    public TextField getSpeciesData() {
        return this.speciesData;
    }

    public TextField getPriceData() {
        return this.priceData;
    }

    public ComboBox<String> getPotencyBox() {
        return this.potencyBox;
    }

    public ToggleGroup getTypeGroup() {
        return this.typeGroup;
    }

    public ListView<Spider> getSpiderListView() {
        return this.spiderListView;
    }

    public int getStockCount() { return this.stockCount;}

    public Button getStockBtn() { return this.stockBtn;}

    public Button getCounterBtn() {return this.counterBtn;}

    public void updateInfoText(String info) {this.infoLabel.setText(info);}

    public void incrementStockCounter() {
        stockCount++;
        this.counterBtn.setText(String.valueOf(stockCount) + "+");
    }

    public void resetStockCount() {
        this.stockCount = 1;
        this.counterBtn.setText(String.valueOf(stockCount) + "+");
    }

    public void createHeading() {
        VBox headingBox = new VBox();
        Label headingLabel = new Label("Spider Emporium \uD83D\uDD77");
        headingLabel.setFont(Font.font("Lucida Calligraphy", 30));
        headingLabel.setTextFill(Color.YELLOW);
        headingBox.getChildren().add(headingLabel);
        headingBox.setAlignment(Pos.CENTER);
        root.getChildren().add(headingBox);
    }

    /**
     * This method Creates the top section.
     */
    public void createTopSection() {
        HBox topSection = new HBox(20);
        topSection.setAlignment(Pos.TOP_CENTER);

        VBox tfBox = new VBox(10);
        tfBox.setAlignment(Pos.TOP_LEFT);

        HBox speciesBox = new HBox(20);
        Label speciesLabel = new Label("Species: ");
        speciesLabel.setTextFill(Color.YELLOW);
        this.speciesData = new TextField();
        speciesBox.getChildren().addAll(speciesLabel, speciesData);

        HBox priceBox = new HBox(37);
        Label priceLabel = new Label("Price:");
        priceLabel.setTextFill(Color.YELLOW);
        this.priceData = new TextField();
        priceBox.getChildren().addAll(priceLabel, priceData);

        tfBox.getChildren().addAll(speciesBox, priceBox);

        VBox selectionBox = new VBox(10);

        HBox radioBox = new HBox(10);
        radioBox.setAlignment(Pos.CENTER_RIGHT);

        // Togglegroup prevents multiple buttons from being selected
        typeGroup = new ToggleGroup();

        RadioButton venomousRadio = new RadioButton("Venomous");
        venomousRadio.setToggleGroup(typeGroup);
        venomousRadio.setTextFill(Color.YELLOW);

        RadioButton illegalRadio = new RadioButton("Illegal");
        illegalRadio.setToggleGroup(typeGroup);
        illegalRadio.setTextFill(Color.YELLOW);

        radioBox.getChildren().addAll(venomousRadio, illegalRadio);

        // Drop down for risk
        potencyBox = new ComboBox<>();
        potencyBox.getItems().addAll("1", "2", "3");
        potencyBox.setPromptText("Venom Potency");

        this.counterBtn = new Button(String.valueOf(stockCount) + "+");

        selectionBox.getChildren().addAll(radioBox, potencyBox, counterBtn);

        topSection.getChildren().addAll(tfBox, selectionBox);
        root.getChildren().add(topSection);
    }


    /**
     * This method sets the listview where the spider information in memory will be displayed.
     */
    public void createListView() {
        HBox spiderViewBox = new HBox();
        spiderViewBox.setAlignment(Pos.CENTER);
        this.spiderListView = new ListView<>();
        this.spiderListView.setPrefWidth(350);
        this.spiderListView.setPrefHeight(200);

        spiderController.loadSpiders();
        this.spiderListView.setItems(spiderController.getSpiderList());
        //this.spiderListView.getItems().addAll(spiderController.getSpiderList());

        spiderViewBox.getChildren().add(spiderListView);
        root.getChildren().add(spiderViewBox);
    }

    /**
     * This method refreshes the spiders in the listView display area.
     */
    public void displaySpiders() {
            spiderListView.getItems().clear();
            spiderListView.getItems().addAll(spiderController.getSpiderList());
            updateInfoText("Spider list refreshed.");
    }

    public void clearFields() {
        this.speciesData.clear();
        this.priceData.clear();
        this.potencyBox.setValue(null);
    }

    public boolean showExitAlert() {
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setTitle("Exit?");
        exitAlert.setHeaderText("Save before exiting?");

        // Define buttons
        ButtonType yesBtn = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noBtn = new ButtonType("No", ButtonBar.ButtonData.NO);
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        exitAlert.getButtonTypes().setAll(yesBtn, noBtn, cancelBtn);

        Optional<ButtonType> result = exitAlert.showAndWait();
        return result.isPresent() && result.get().equals(yesBtn);
    }

    public void showDataConfirmationAlert(boolean isLoadAlert) {
        String notification;
        if (isLoadAlert) {
            notification = "Spiders loaded from serial file!";
        }
        else {
            notification = "Spiders saved to serial file!";
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, notification);
        alert.setTitle("Spiders loaded");
        alert.setHeaderText(null);
        alert.showAndWait();
    }


    /**
     * This method displays an alert if any textfields are empty when an attempt is made to add a new spider.
     */
    public void displayEmptyFieldAlert() {
        Alert emptyFieldAlert = new Alert(Alert.AlertType.ERROR);
        emptyFieldAlert.setTitle("Blank Fields Present");
        emptyFieldAlert.setHeaderText("Please fill out all fields.");
        emptyFieldAlert.showAndWait();
    }

    /**
     * This method creates the buttons used for GUI functionality.
     */
    public void createButtons() {
        HBox displayOptionsBox = new HBox(10);
        this.addBtn = new Button("ADD");
        this.removeBtn = new Button("REMOVE");
        this.listBtn = new Button("LIST");
        displayOptionsBox.getChildren().addAll(addBtn, removeBtn, listBtn);
        displayOptionsBox.setAlignment(Pos.CENTER);
        root.getChildren().add(displayOptionsBox);

        HBox maintenanceOptionsBox = new HBox(17);
        this.saveBtn = new Button("SAVE (Serial)");
        this.loadBtn = new Button("LOAD (Serial)");
        this.exitBtn = new Button("EXIT");
        maintenanceOptionsBox.getChildren().addAll(saveBtn, loadBtn, exitBtn);
        maintenanceOptionsBox.setAlignment(Pos.CENTER);
        maintenanceOptionsBox.setPadding(new Insets(0, 0, 10, 0));
        root.getChildren().add(maintenanceOptionsBox);

        HBox stockBox = new HBox(20);
        this.stockBtn = new Button("Check Stock");
        this.stockLabel = new Label("Stock: ");
        this.stockLabel.setTextFill(Color.RED);
        stockBox.getChildren().addAll(stockBtn, stockLabel);
        stockBox.setAlignment(Pos.CENTER);
        root.getChildren().add(stockBox);

    }

    public void createInfoLabel() {
        HBox infoBox = new HBox();
        this.infoLabel = new Label();
        infoLabel.setTextFill(Color.RED);
        infoBox.getChildren().add(infoLabel);
        infoBox.setAlignment(Pos.BOTTOM_RIGHT);
        infoBox.setPadding(new Insets(0, 10, 0, 0));
        root.getChildren().add(infoBox);
    }

    public void createUI() {
        createHeading();
        createTopSection();
        createListView();
        createButtons();
        createInfoLabel();
    }

    public void displayStockNum(int stockNo) {
        this.stockLabel.setText("Stock: " + stockNo);
    }
}