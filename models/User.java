package com.example.inuapp.models;

public class User {
    private String userId;
    public static final String UID = "userId";
    private String name;
    public static final String NAME = "name";
    private String email;
    public static final String EMAIL = "email";
    private String role;
    public static final String ROLE = "role";

    public static final String ROLE_ADMIN = "Admin";
    public static final String ROLE_CUSTOMER = "Customer";

    public static final String USERS = "Users";
    public static final String USER_DETAILS = "UserDetails";

    public User(String userId, String name, String email, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
