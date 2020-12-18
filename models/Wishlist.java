package com.example.inuapp.models;

public class Wishlist {
    public static final String WISHLIST = "Wishlist";
    private String productId;
    private String dateAdded;
    public static final String DATE_ADDED = "dateAdded";

    public Wishlist(String productId, String dateAdded) {
        this.productId = productId;
        this.dateAdded = dateAdded;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}
