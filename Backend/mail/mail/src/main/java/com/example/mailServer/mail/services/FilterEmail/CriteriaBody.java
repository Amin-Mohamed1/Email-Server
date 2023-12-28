package com.example.mailServer.mail.services.FilterEmail;

import com.example.mailServer.mail.services.Email.Email;

import java.util.ArrayList;

public class CriteriaBody implements ICriteria{
    String body;
    public CriteriaBody(String body){ this.body = body; }
    @Override
    public ArrayList<Email> meetCriteria(ArrayList<Email> emails) {
        ArrayList<Email> senderEmails = new ArrayList<Email>();

        for (Email email : emails) {
            if(email.getBody().toLowerCase().contains(this.body.toLowerCase())){
                senderEmails.add(email);
            }
        }
        return senderEmails;
    }

}
