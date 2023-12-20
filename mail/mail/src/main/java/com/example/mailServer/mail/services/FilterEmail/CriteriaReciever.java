package com.example.mailServer.mail.services.FilterEmail;

import com.example.mailServer.mail.services.Email;

import java.util.ArrayList;

public class CriteriaReciever implements ICriteria{
    private ArrayList<String> recievers;
    public CriteriaReciever(ArrayList<String> recievers){ this.recievers = recievers; }

    //needs to be tested
    @Override
    public ArrayList<Email> meetCriteria(ArrayList<Email> emails) {
        ArrayList<Email> recieverEmails = new ArrayList<Email>();

        for (Email email : emails) {
            if(email.getRecievers().containsAll(this.recievers)){
                recieverEmails.add(email);
            }
        }
        return recieverEmails;
    }
}
