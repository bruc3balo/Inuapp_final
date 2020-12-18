package com.example.inuapp.models;

public class Cart {
    private String productId;
    private Double productPrice;
    private String dateAdded;
    private int numberOfItems;
    private String productImageUrl;
    private String productName;
    public static final String NO_OF_ITEMS = "numberOfItems";
    public static final String PRODUCT_PRICE = "productPrice";
    public static final String CART = "Cart";

    public Cart(String productId, Double productPrice, String dateAdded, int numberOfItems, String productImageUrl, String productName) {
        this.productId = productId;
        this.productPrice = productPrice;
        this.dateAdded = dateAdded;
        this.numberOfItems = numberOfItems;
        this.productImageUrl = productImageUrl;
        this.productName = productName;
    }

    public Cart(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}
