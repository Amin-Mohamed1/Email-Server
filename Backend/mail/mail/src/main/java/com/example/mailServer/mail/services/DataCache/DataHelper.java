package com.example.mailServer.mail.services.DataCache;

import com.example.mailServer.mail.services.USER.User;

public class DataHelper {
    public static User getUserByAccount(String account){
        for(User u : Data.getUsers()){
            if(u.getEmailaccount().equals(account)) {
                return u;
            }
        }
        return null ;
    }

}
