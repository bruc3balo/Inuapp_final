package com.example.inuapp.models;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Orders {
    private String orderNo;
    public static final String ORDER_NO = "orderNo";
    private List<Map<String, Products>> productsOrdered;
    public static final String PRODUCTS_ORDERED = "productsOrdered";
    private LinkedList<Products> productsLinkedList;
    private String orderAmount;
    public static final String ORDER_AMOUNT = "orderAmount";
    private String orderedAt;
    public static final String ORDERED_AT = "orderedAt";
    private String deliveryDate;
    public static final String DELIVERY_DATE ="deliveryDate";
    private String userId;
    private String name;


    public static final String ORDERS = "Orders";
    public static final String ORDER_SUR = "OD";

    public Orders(String orderNo, LinkedList<Products> productsLinkedList, String orderAmount, String orderedAt, String userId) {
        this.orderNo = orderNo;
        this.productsLinkedList = productsLinkedList;
        this.orderAmount = orderAmount;
        this.orderedAt = orderedAt;
        this.userId = userId;
    }

    public Orders() {
    }

    public LinkedList<Products> getProductsLinkedList() {
        return productsLinkedList;
    }

    public void setProductsLinkedList(LinkedList<Products> productsLinkedList) {
        this.productsLinkedList = productsLinkedList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public List<Map<String, Products>> getProductsOrdered() {
        return productsOrdered;
    }

    public void setProductsOrdered(List<Map<String, Products>> productsOrdered) {
        this.productsOrdered = productsOrdered;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(String orderedAt) {
        this.orderedAt = orderedAt;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
