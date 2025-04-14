package com.example.spideremporium.model;

public interface CustomerBuilder {
    void buildID(int custID);
    void buildFName(String fName);
    void buildLName(String lName);
    void buildAddress(String address);
    void buildPhone(String phone);
    Customer getCustomer();

}
