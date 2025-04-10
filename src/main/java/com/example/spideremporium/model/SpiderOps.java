package com.example.spideremporium.model;
import java.io.*;
import java.util.ArrayList;

public class SpiderOps {
    private ArrayList<Spider> spiderList = new ArrayList<>();

    public ArrayList<Spider> getSpiderList() {
        return this.spiderList;
    }

    public void addSpider(Spider spider) {
        spiderList.add(spider);
    }

    public void removeSpider(Spider spider) {
        spiderList.remove(spider);
    }



}
