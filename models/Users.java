package com.example.inuapp.models;

import android.location.Address;

import java.util.List;

public class Users {
    private String userId;
    private String first_name;
    private String last_name;
    private String createdAt;
    private String emailAddress;
    private String role;
    private List<Address> userLocation;

    public static final String ADMIN = "Administrator";
    public static final String CUSTOMER = "Customer";
    public static final String RIDER = "Rider";
}
