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


    /**
     * This function loads spiders from a file into the spider list.<br>
     */
    public void loadSpidersFromFile() {
        String fileName = "database/spiders.ser";
        spiderList.clear();

        try {
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);

            spiderList = (ArrayList<Spider>) in.readObject();
            in.close();
            file.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Cannot load spiders from file " + e.getMessage());
        }
    }


    /**
     * This function saves the spiders from the spider list to their respective files.<br>
     */
    public void writeSpidersToFile() {
        String fileName = "database/spiders.ser";           // String to store file data to be written to database
        try {
            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(spiderList);
            out.close();
            file.close();
        }
        catch (IOException e) {
            System.out.println("Cannot save spiders to file: " + e.getMessage());
        }

    }
}
