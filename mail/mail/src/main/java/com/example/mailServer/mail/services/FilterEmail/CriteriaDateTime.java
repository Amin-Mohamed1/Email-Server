package com.example.mailServer.mail.services.FilterEmail;

import com.example.mailServer.mail.services.Email.Email;

import java.util.ArrayList;

public class CriteriaDateTime implements ICriteria{
    private String dateTime;
    public CriteriaDateTime(String dateTime){ this.dateTime = dateTime; }
    @Override
    public ArrayList<Email> meetCriteria(ArrayList<Email> emails) {
        ArrayList<Email> dateTimeEmails = new ArrayList<Email>();

        for (Email email : emails) {
            if(email.getDateTime().equalsIgnoreCase(this.dateTime)){
                dateTimeEmails.add(email);
            }
        }
        return dateTimeEmails;
    }
}
