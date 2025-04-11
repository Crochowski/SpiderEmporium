package com.example.spideremporium.dataManagement;

import com.example.spideremporium.model.Customer;
import com.example.spideremporium.model.Order;
import com.example.spideremporium.model.Spider;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

/**
 * This singleton class is used to handle serialization/deserialization of store data.
 */
public class SerializationManager {

    private static final SerializationManager serializationManager = new SerializationManager();

    /**
     * This constructor is private to prevent further instances of this class being created.
     */
    private SerializationManager() {
    }

    /**
     * This method returns the single instance of the SerializationManager.
     * @return - The single instance of SerializationManager
     */
    public static SerializationManager getSerializationManager() {
        return serializationManager;
    }

    /**
     * This method deserializes the appropriate file and loads the objects into the corresponding list in memory.
     * @param list - The list to which the objects will be added
     * @param type - The data type of the objects to store in the list
     * @param <T> - The type of object being deserialized
     */
    public <T> void deSerializeFile(ObservableList<T> list, Class<T> type) {
        Thread deSerializerThread = new Thread(() -> {
            String fileName;
            if (type.equals(Customer.class)) {
                fileName = "database/customers.ser";
            } else if (type.equals(Spider.class)) {
                fileName = "database/spiders.ser";
            } else if (type.equals(Order.class)) {
                fileName = "database/orders.ser";
            } else {
                throw new IllegalArgumentException("Invalid class: " + type.getSimpleName());
            }


            try (FileInputStream file = new FileInputStream(fileName);
                 ObjectInputStream in = new ObjectInputStream(file);) {

                ArrayList<T> dataToLoad = (ArrayList<T>) in.readObject();

                Platform.runLater(() -> {
                    list.clear();
                    list.addAll(dataToLoad);
            });

            }
            catch (FileNotFoundException e) {
                System.out.println("File not found: " + fileName);
            }
            catch (IOException e) {
                System.out.println("Cannot load " + type.getSimpleName() + " from file: " + e.getMessage());
            }
            catch (ClassNotFoundException e) {
                System.out.println("Class not found: " + e.getMessage());
            }
        });

        deSerializerThread.start();
    }


    /**
     * This method serializes objects stored in a list to a serial file.
     * @param list - The list containing the objects to be serialized
     * @param type - The data type of the objects
     * @param <T> - The type of object being serialized
     */
    public <T> void serializeFile(ObservableList<T> list, Class<T> type) {
        ArrayList<T> listToSave = new ArrayList<>(list);
        Thread serializerThread = new Thread(() -> {

            String fileName;
            if (type.equals(Customer.class)) {
                fileName = "database/customers.ser";
            } else if (type.equals(Spider.class)) {
                fileName = "database/spiders.ser";
            } else if (type.equals(Order.class)) {
                fileName = "database/orders.ser";
            } else {
                throw new IllegalArgumentException("Invalid class: " + type.getSimpleName());
            }

            File dbDirectory = new File("database");
            if (!dbDirectory.exists()) {
                dbDirectory.mkdirs();
            }

            try ( FileOutputStream file = new FileOutputStream(fileName);
                  ObjectOutputStream out = new ObjectOutputStream(file);){

                out.writeObject(listToSave);

            }
            catch (FileNotFoundException e) {
                System.out.println("Cannot create file: " + fileName + ": " + e.getMessage());
            }
            catch (IOException e) {
                System.out.println("Cannot save " + type.getSimpleName() + " to file: " + e.getMessage());
            }
        });

        serializerThread.start();
    }

}
