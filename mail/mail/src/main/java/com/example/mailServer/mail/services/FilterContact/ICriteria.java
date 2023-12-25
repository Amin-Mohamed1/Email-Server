package com.example.mailServer.mail.services.FilterContact;

import com.example.mailServer.mail.services.Contact;
import java.util.ArrayList;

public interface ICriteria {
    public ArrayList<Contact> meetCriteria(ArrayList<Contact> contacts);
}
