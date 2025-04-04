package com.example.spideremporium.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class VenomousSpiderTest {

    /**
     * This method tests the VenomousSpider constructor, ensuring the correct values
     * are set for the venomousSpider object.
     */
    @Test
    public void validVenomousSpiderCreation() {
        VenomousSpider vSpider = new VenomousSpider("Vicious Tarantula", 3, 200, 5);

        assertEquals("Vicious Tarantula", vSpider.getSpecies());
        assertEquals(3, vSpider.getVenomPotency());
        assertEquals(200, vSpider.getPrice(), .001);
        assertEquals("Venomous", vSpider.getType());
    }

}