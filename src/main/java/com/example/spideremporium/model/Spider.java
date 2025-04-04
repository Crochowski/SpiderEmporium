package com.example.spideremporium.model;

import java.io.Serializable;

/**
 * This class models a Spider<br>.
 */
public abstract class Spider implements Serializable {

    protected String species;       // The species of spider
    protected String type;          // The type of spider
    protected float price;          // The price of the spider
    protected int stockCount;


    /**
     * This constructor creates 1 instance of the class Spider<br>
     * @param _species - The species of the spider.
     */
    public Spider(String _species, float _price, int _stockCount) {
        this.species = _species;
        this.price = _price;
        this.stockCount = _stockCount;
    }


    /**
     * Given a concrete Spider (this), the function returns its species.<br>
     * @return The species of the spider.
     */
    public String getSpecies() {
        return this.species;
    }


    /**
     * Given a concrete Spider (this), the function returns its type.
     * @return The type of the spider.
     */
    public String getType() {
        return this.type;
    }


    /**
     * Given a concrete Spider (this), the function returns its price.<br>
     * @return The price that is charged for the spider.
     */
    public float getPrice() {
        return this.price;
    }

    public int getStockCount() {
        return this.stockCount;
    }

    public void setType(String _type) {
        this.type = _type;
    }
}

