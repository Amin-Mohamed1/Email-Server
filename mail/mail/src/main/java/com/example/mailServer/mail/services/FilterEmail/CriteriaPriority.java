package com.example.mailServer.mail.services.FilterEmail;

import com.example.mailServer.mail.services.Email.Email;

import java.util.ArrayList;

public class CriteriaPriority implements ICriteria{
    private String priority;
    public CriteriaPriority(String priority){ this.priority = priority; }
    @Override
    public ArrayList<Email> meetCriteria(ArrayList<Email> emails) {
        ArrayList<Email> prioritizedEmails = new ArrayList<Email>();

        for (Email email : emails) {
            if(email.getPriority().equalsIgnoreCase(this.priority)){
                prioritizedEmails.add(email);
            }
        }
        return prioritizedEmails;
    }
}
