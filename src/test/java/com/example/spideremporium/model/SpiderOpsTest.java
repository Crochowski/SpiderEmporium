package com.example.spideremporium.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class SpiderOpsTest {

    /**
     * This method tests whether the spiders are successfully loaded from spiders.ser into the spider list.
     */
    @Test
    public void testLoadSpidersFromFile() {
        SpiderOps spiderOps = new SpiderOps();
        spiderOps.loadSpidersFromFile();
        assertFalse(spiderOps.getSpiderList().isEmpty());
    }
}