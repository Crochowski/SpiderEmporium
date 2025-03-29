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
        String fileName = "database/spiders.txt";
        spiderList.clear();
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                // Common spider fields
                String species = data[0];
                String type = data[1];
                float price = Float.parseFloat(data[2]);
                int venomPotency = Integer.parseInt(data[3]);

                Spider spider = null;                    // Initialise the spider object

                if (type.equals("Venomous")) {      // Create the venomous spider object
                    spider = new VenomousSpider(species, venomPotency, price);
                }

                else if (type.equals("Illegal")) {
                    String risk = data[4];
                    // Create the Illegal Spider object
                    spider = new IllegalSpider(species, venomPotency, price, risk);
                    spider.setType("Illegal");
                }

                spiderList.add(spider);                 // Add the new spider to spiderList
            }

        } catch (IOException error) {
            System.err.println("Cannot read spider from file: " + error.getMessage());
        }
    }

    /**
     * This function saves the spiders from the spider list to their respective files.<br>
     */
    public void writeSpidersToFile() {
        Writer writer;                  // Initialise writer
        String fileName = "database/spiders.txt";           // String to store file data to be written to database
        char spiderType = '0';
        Spider spider;

        // Wipe the file to avoid spider duplication
        try {
            new BufferedWriter(new FileWriter("database/spiders.txt")).close();
        }
        catch (IOException error) {
            System.err.println("Cannot write to file");
        }

        for (Spider s : spiderList) {   // For each spider s in the list
            spider = s;
            if (spider.getType().equals("Venomous")) {
                spiderType = 'V';

            } else if (spider.getType().equals("Illegal")) {
                spiderType = 'I';
            }

            try {
                writer = new BufferedWriter(new FileWriter(fileName, true));
                // First store fields common to all spiders
                String spiderData = spider.getSpecies() + "," + spider.getType() +  "," +
                        spider.getPrice();

                // Now write further fields specific to spider type
                if (spiderType == 'V') {
                    VenomousSpider vSpider = (VenomousSpider) spider;  // Downcast so specific fields can be accessed
                    int venomPotency = vSpider.getVenomPotency();
                    // Append new fields to spiderData
                    spiderData += "," + venomPotency;
                }
                else {
                    IllegalSpider iSpider = (IllegalSpider) spider;
                    spiderData += "," + iSpider.getVenomPotency() + "," + iSpider.getRisk();
                }

                writer.write(spiderData + "\n");        // Write spiderData to file
                writer.close();

            } catch (IOException error) {
                System.err.println("Cannot write spider to file: " + error.getMessage());
            }
        }

    }
}
