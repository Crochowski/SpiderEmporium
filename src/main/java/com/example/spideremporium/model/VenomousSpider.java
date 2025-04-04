package com.example.spideremporium.model;

/**
 * This class models a spider with medically significant venom.
 */
public class VenomousSpider extends Spider {

    protected int venomPotency;              // The spider's venom

    /**
     * This constructor creates 1 instance of the class VenomousSpider.<br>
     * @param _species - The species of spider.
     * @param _venomPotency - The venom potency of the spider.
     * @param _price - The price of the spider.
     */
    public VenomousSpider(String _species, int _venomPotency, float _price, int _stockCount) {
        super(_species, _price, _stockCount);
        this.venomPotency = _venomPotency;
        this.type = "Venomous";
    }


    /**
     * Given a concrete Venomous Spider (this), the function returns its venom.
     * @return The venom of the spider.
     */
    public int getVenomPotency() {
        return this.venomPotency;
    }

    public String toString() {
        return this.species + " | " + this.type + " | $" + this.price + " | Potency: " + this.venomPotency;
    }

}
