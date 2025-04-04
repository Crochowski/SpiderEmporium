package com.example.spideremporium.model;

/**
 * This class models a spider that is so venomous, it is illegal.<br>
 * It is final so no other class will extend from it.
 */
public final class IllegalSpider extends VenomousSpider {

    private final String risk;         // The legal risk borne by the company for selling this spider on a scale from 1-5

    /**
     * The constructor creates 1 instance of the class IllegalSpider.<br>
     * @param _species - The species of the spider.
     * @param _venomPotency - The venom of the spider.
     */
    public IllegalSpider(
                         String _species,
                         int _venomPotency,
                         float _price,
                         int _stockCount) {
        super(_species,
                _venomPotency,
                _price, _stockCount);


        if (this.venomPotency == 1) {
            this.risk = "Low";
        }
        else if (this.venomPotency == 2) {
            this.risk = "Medium";
        }
        else {
            this.risk = "High";
        }

        this.type = "Illegal";

    }

    /**
     * This constructor builds a spider from the spiderlist.
     * @param _species
     * @param _venomPotency
     * @param _price
     * @param _risk
     */
    public IllegalSpider(
                         String _species,
                         int _venomPotency,
                         float _price,
                         String _risk,
                         int _stockCount) {

        super(_species,
                _venomPotency,
                _price,
                _stockCount);

        this.risk = _risk;
    }


    /**
     *  Given a concrete illegal spider (this), the function returns its risk.
     * @return The risk borne by the company in selling this spider.
     */
    public String getRisk() {
        return this.risk;
    }

    public String getType() {
        return this.type;
    }


    public String toString() {
        return this.species + " | " + this.type + " | $" + this.price + " | Potency: " + this.venomPotency + " | Risk: "
                + this.risk;
    }

     }

