package com.example.mailServer.mail.services.FilterContact;

import com.example.mailServer.mail.services.Contact;

import java.util.ArrayList;

public class CriteriaName implements ICriteria{
    private String name;

    public CriteriaName(String name) { this.name = name;}

    @Override
    public ArrayList<Contact> meetCriteria(ArrayList<Contact> contacts) {
        ArrayList<Contact> emailName  = new ArrayList<>();

        for (Contact contact : contacts) {
            if(contact.getName().toLowerCase().contains(this.name.toLowerCase())){
                emailName.add(contact);
            }
        }
        return emailName;
    }
}
