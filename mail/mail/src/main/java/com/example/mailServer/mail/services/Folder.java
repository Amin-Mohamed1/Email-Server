package com.example.mailServer.mail.services;

import com.example.mailServer.mail.services.Email;

import java.util.List;

public class Folder {
    private List<Email> emails;

    private String name;

    public Folder(){}

    public Folder(List<Email> emails, String name) {
        this.emails = emails;
        this.name = name;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
