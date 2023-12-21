package com.example.mailServer.mail.services.SortContact;

import com.example.mailServer.mail.services.Contact;
import com.example.mailServer.mail.services.Email.Email;

import java.util.ArrayList;

public interface Strategy {
    public ArrayList<Contact> doOperation(ArrayList<Contact> contacts, boolean sortingOrder);
}
