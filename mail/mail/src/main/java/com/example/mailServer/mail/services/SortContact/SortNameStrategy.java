package com.example.mailServer.mail.services.SortContact;

import com.example.mailServer.mail.services.Contact;
import com.example.mailServer.mail.services.Email.Email;

import java.util.ArrayList;
import java.util.Comparator;

public class SortNameStrategy implements Strategy{
    final static boolean ASCENDING = true;
    @Override
    public ArrayList<Contact> doOperation(ArrayList<Contact> contacts, boolean sortingOrder) {
        if(sortingOrder == ASCENDING)
            contacts.sort(Comparator.comparing(Contact::getName));
        else
            contacts.sort(Comparator.comparing(Contact::getName).reversed());
        return contacts;
    }


}
