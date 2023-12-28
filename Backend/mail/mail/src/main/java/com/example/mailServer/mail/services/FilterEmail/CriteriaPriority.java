package com.example.mailServer.mail.services.FilterEmail;

import com.example.mailServer.mail.services.Email.Email;

import java.util.ArrayList;

public class CriteriaPriority implements ICriteria{
    private int priority;
    public CriteriaPriority(int priority){ this.priority = priority; }
    @Override
    public ArrayList<Email> meetCriteria(ArrayList<Email> emails) {
        ArrayList<Email> prioritizedEmails = new ArrayList<Email>();

        for (Email email : emails) {
            if(email.getPriority() == this.priority){
                prioritizedEmails.add(email);
            }
        }
        return prioritizedEmails;
    }
}
