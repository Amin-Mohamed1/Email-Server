package com.example.mailServer.mail.services.DataCache;

import com.example.mailServer.mail.services.USER.User;

public class DataHelper {
    public static User getUserByAccount(String account){
        System.out.println(account);
        for(User u : Data.getUsers()){
            System.out.println(u.getEmailaccount());
            if(u.getEmailaccount().equals(account)) {
                System.out.println(u.getUsername()); ;
                return u;
            }
        }
        return null ;
    }

}
