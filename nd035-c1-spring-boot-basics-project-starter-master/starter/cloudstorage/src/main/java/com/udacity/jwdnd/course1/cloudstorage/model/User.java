package com.udacity.jwdnd.course1.cloudstorage.model;

public class User {
    private Integer userId;
    private String username;
    private String salt;
    private String firstName;
    private String lastName;
    private String password;

    public User(Integer userId, String username, String salt, String firstName, String lastName, String password) {
        this.userId = userId;
        this.username = username;
        this.salt = salt;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
