package com.example.mailServer.mail.services.FilterEmail;

import com.example.mailServer.mail.services.Email;

import java.util.ArrayList;

public interface ICriteria {
    public ArrayList<Email> meetCriteria(ArrayList<Email> emails);
}
