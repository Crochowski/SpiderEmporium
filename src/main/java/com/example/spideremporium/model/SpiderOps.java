package com.example.spideremporium.model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class SpiderOps {
    private ObservableList<Spider> spiderList = FXCollections.observableArrayList();

    public ObservableList<Spider> getSpiderList() {
        return this.spiderList;
    }

    public void addSpider(Spider spider) {
        spiderList.add(spider);
    }

    public void removeSpider(Spider spider) {
        spiderList.remove(spider);
    }

}
