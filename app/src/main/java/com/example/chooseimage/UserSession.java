package com.example.chooseimage;

public class UserSession {
    private static UserSession instance;
    private int userId;
    private boolean isLoggedIn;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setUserId(int userId) {
        this.userId = userId;
        this.isLoggedIn = true;
    }

    public int getUserId() {
        return userId;
    }
    public boolean isLoggedIn() {
        return isLoggedIn;
    }
    public void logout() {
        this.userId = 0;
        this.isLoggedIn = false;
    }
}

