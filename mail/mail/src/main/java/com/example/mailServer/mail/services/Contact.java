package com.example.mailServer.mail.services;

public class Contact {
    private String emailaccount;
    private String name;

    public Contact(){}

    public Contact(String emailaccount, String name) {
        this.emailaccount = emailaccount;
        this.name = name;
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

    @Override
    public String toString() {
        return "Contact{" +
                "emailaccount='" + emailaccount + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}