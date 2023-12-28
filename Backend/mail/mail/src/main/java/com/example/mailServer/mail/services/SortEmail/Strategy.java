package com.example.mailServer.mail.services.SortEmail;

import com.example.mailServer.mail.services.Email.Email;

import java.util.ArrayList;

public interface Strategy {
    public ArrayList<Email> doOperation(ArrayList<Email> emails, boolean sortingOrder);
}
