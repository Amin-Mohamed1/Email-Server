package com.example.mailServer.mail.services.FilterEmail;

import com.example.mailServer.mail.services.Email.Email;

import java.util.ArrayList;

public class CriteriaReciever implements ICriteria{
    private String recievers;
    public CriteriaReciever(String recievers){ this.recievers = recievers; }

    @Override
    public ArrayList<Email> meetCriteria(ArrayList<Email> emails) {
        ArrayList<Email> recieverEmails = new ArrayList<Email>();

        for (Email email : emails) {
            for(String reciever : email.getRecievers()){
                if(reciever.toLowerCase().contains(this.recievers.toLowerCase())){
                    if(!recieverEmails.contains(email))
                        recieverEmails.add(email);
                }
            }
        }
        return recieverEmails;
    }
}
