package com.example.mailServer.mail.services;

public class Contact {
    private  int id ;
    private String emailaccount;
    private String name;

    public Contact(){}

    public Contact(String emailaccount, String name , int id) {
        this.emailaccount = emailaccount;
        this.name = name;
        this.id = id ;
    }

    public String getEmailaccount() {
        return emailaccount;
    }

    public void setEmailaccount(String emailaccount) {
        this.emailaccount = emailaccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "emailaccount='" + emailaccount + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}