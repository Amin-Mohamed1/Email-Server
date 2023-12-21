package com.example.mailServer.mail.services.FilterEmail;

import com.example.mailServer.mail.services.Email.Email;

import java.util.ArrayList;

public class CriteriaSender implements ICriteria{
    private String sender;
    public CriteriaSender(String sender){ this.sender = sender; }
    @Override
    public ArrayList<Email> meetCriteria(ArrayList<Email> emails) {
        ArrayList<Email> senderEmails = new ArrayList<Email>();

        for (Email email : emails) {
            if(email.getSender().equalsIgnoreCase(this.sender)){
                senderEmails.add(email);
            }
        }
        return senderEmails;
    }
}
