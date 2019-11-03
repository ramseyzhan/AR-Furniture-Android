package com.bongjlee.arfurnitureapp.data;


public class Product {
    private String productName;
    private String productPrice;
    private String productDescription;
    private String productStyles;
    private String shippingInfo;
    private Integer photoId;

    public Product() {}

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductStyles() {
        return productStyles;
    }

    public void setProductStyles(String productStyles) {
        this.productStyles = productStyles;
    }

    public String getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(String shippingInfo) {
        this.shippingInfo = shippingInfo;
    }

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }
}
