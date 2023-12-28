package com.example.mailServer.mail.services.FilterEmail;

import com.example.mailServer.mail.services.Email.Email;

import java.util.ArrayList;

public class CriteriaSubject implements ICriteria{
    private String subject;
    public CriteriaSubject(String subject){ this.subject = subject; }
    @Override
    public ArrayList<Email> meetCriteria(ArrayList<Email> emails) {
        ArrayList<Email> subjectEmails = new ArrayList<Email>();

        for (Email email : emails) {
            if(email.getSubject().toLowerCase().contains(this.subject.toLowerCase())){
                subjectEmails.add(email);
            }
        }
        return subjectEmails;
    }
}
