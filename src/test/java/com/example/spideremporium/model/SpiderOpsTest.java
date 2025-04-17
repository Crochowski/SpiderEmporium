package com.example.spideremporium.model;

import com.example.spideremporium.controller.dataAccess.SerializationManager;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpiderOpsTest {
    private SerializationManager serializationManager = SerializationManager.getSerializationManager();

    /**
     * This method tests whether the spiders are successfully loaded from spiders.ser into the spider list.
     */
    @Test
    public void testLoadSpidersFromFile() {
        SpiderOps spiderOps = new SpiderOps();
        serializationManager.deSerializeFile(spiderOps.getSpiderList(), Spider.class);
        assertFalse(spiderOps.getSpiderList().isEmpty());
    }
}