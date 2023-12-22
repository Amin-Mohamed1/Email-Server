package com.example.mailServer.mail.services.SortEmail;

import com.example.mailServer.mail.services.Email.Email;

import java.util.ArrayList;
import java.util.Comparator;

public class SortSubjectStrategy implements Strategy{
    final static boolean ASCENDING = true;
    @Override
    public ArrayList<Email> doOperation(ArrayList<Email> emails, boolean sortingOrder) {
        if(sortingOrder == ASCENDING)
            emails.sort(Comparator.comparing(Email::getSubject));
        else
            emails.sort(Comparator.comparing(Email::getSubject).reversed());
        return emails;
    }
}
