package com.example.mailServer.mail.services;

import java.util.List;

public class Email {
    //required params
    private String sender;
    private List<String> reciever;

    private String dateTime;
    private String id;

    //optional params
    private String subject;
    private String body;
    private List<String> attatchments;
    private String priority;

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

    public void setReciever(List<String> reciever) {
        this.reciever = reciever;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setAttatchments(List<String> attatchments) {
        this.attatchments = attatchments;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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
