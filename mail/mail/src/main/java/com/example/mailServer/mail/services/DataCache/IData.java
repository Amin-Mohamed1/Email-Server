package com.example.mailServer.mail.services.DataCache;

import com.example.mailServer.mail.services.User;

import java.util.ArrayList;

public interface IData {
    public  Data getInstance() ;
    public  ArrayList<User> getUsers() ;
}
