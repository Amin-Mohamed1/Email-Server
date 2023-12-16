package com.example.mailServer.mail.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,include = JsonTypeInfo.As.PROPERTY, visible = true)
//@JsonIgnoreProperties(value = "attributes",ignoreUnknown = true)

public class User {
    // primary data for user
    private String username ;
    private String emailaccount ;
    private String password ;
    private String birth ;

    public User(UserDTO u){
        this.birth = u.birth ;
        this.emailaccount = u.emailaccount ;
        this.password = u.password ;
        this.username = u.username ;
    }
    public User(){}

    public String getBirth() {
        return birth;
    }

    public String getEmailaccount() {
        return emailaccount;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setEmailaccount(String emailaccount) {
        this.emailaccount = emailaccount;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
