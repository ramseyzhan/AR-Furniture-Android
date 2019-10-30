package com.bongjlee.arfurnitureapp;

import java.util.ArrayList;
import java.util.List;

public class Product {
    String productName;
    String productPrice;
    int photoId;

    Product(String productName, String productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
        //this.photoId = photoId;
        //this.products = products;
    }
    public void setName(String name){
        this.productName = name;
    }
    public void setPrice(String price){
        this.productPrice = price;
    }
    public String getName(){
        return productName;
    }
    public String getProductPrice(){
        return productPrice;
    }
}
