package com.example.mailServer.mail.services.USER;

public class NotUser implements IUser{
    private String status ;
    public NotUser(String status){
        this.status = status ;
    }

    public String getStatus() {
        return status;
    }

}
