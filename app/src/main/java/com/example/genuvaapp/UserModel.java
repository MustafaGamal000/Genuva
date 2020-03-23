package com.example.genuvaapp;

public class UserModel {
    private String username,userNumber,url;

    public UserModel() {
    }

    public UserModel(String username, String userNumber, String url) {
        this.username = username;
        this.userNumber = userNumber;
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
