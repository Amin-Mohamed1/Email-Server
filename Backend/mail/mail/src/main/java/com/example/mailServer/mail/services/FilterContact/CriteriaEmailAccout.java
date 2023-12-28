package com.example.mailServer.mail.services.FilterContact;

import com.example.mailServer.mail.services.Contact;

import java.util.ArrayList;

public class CriteriaEmailAccout implements ICriteriaContact{
    private String emailaccount;

    public CriteriaEmailAccout(String emailaccount) { this.emailaccount = emailaccount;}

    @Override
    public ArrayList<Contact> meetCriteria(ArrayList<Contact> contacts) {
        ArrayList<Contact> emailAccount  = new ArrayList<>();

        for (Contact contact : contacts) {
            if(contact.getEmailaccount().toLowerCase().contains(this.emailaccount.toLowerCase())){
                emailAccount.add(contact);
            }
        }
        return emailAccount;
    }
}
