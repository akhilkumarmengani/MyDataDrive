package com.udacity.jwdnd.course1.cloudstorage.model;

/* credentialid INT PRIMARY KEY auto_increment,
         url VARCHAR(100),
         username VARCHAR (30),
         key VARCHAR,
         password VARCHAR,
         userid INT,
         foreign key (userid) references USERS(userid)*/
public class Credential {
    private Integer credentialId;
    private String URL;
    private String username;
    private String key;
    private String password;
    private Integer userId;

    public Credential(Integer credentialId, String URL, String username, String key, String password, Integer userId) {
        this.credentialId = credentialId;
        this.URL = URL;
        this.username = username;
        this.key = key;
        this.password = password;
        this.userId = userId;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
