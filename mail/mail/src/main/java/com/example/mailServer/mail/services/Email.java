package com.example.mailServer.mail.services;

import java.util.List;

public class Email {
    //required params
    private String sender;
    private List<String> reciever;

    //optional params
    private String id;
    private String subject;
    private String body;
    private List<String> attatchments;
    private String priority;
    private String dateTime;

    public Email(){}

    public Email(EmailBuilder builder){
        this.sender = builder.getSender();
        this.reciever = builder.getReciever();
        this.id = builder.getId();
        this.subject = builder.getSubject();
        this.body = builder.getBody();
        this.attatchments = builder.getAttatchments();
        this.priority = builder.getPriority();
        this.dateTime = builder.getDateTime();
    }

    public String getSender() {
        return sender;
    }


    public List<String> getReciever() {
        return reciever;
    }

    public String getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }


    public String getBody() {
        return body;
    }


    public List<String> getAttatchments() {
        return attatchments;
    }

    public String getPriority() {
        return priority;
    }

    public String getDateTime() {
        return dateTime;
    }

}
