package com.example.mailServer.mail.services;

import java.util.ArrayList;
import java.util.List;

public class EmailBuilder{
    //required params
    private String sender;
    private ArrayList<String> recievers;

    //optional params
    private int id;
    private String subject;
    private String body;
    private List<String> attatchments;
    private String priority;
    private String dateTime;
    private boolean isRead ;

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public EmailBuilder(){}
    public EmailBuilder(String sender, ArrayList<String> recievers, int id, String dateTime){
        this.sender = sender;
        this.recievers = recievers;
        this.id = id;
        this.dateTime = dateTime;
    }

    public String getSender() {
        return sender;
    }

    public ArrayList<String> getRecievers() {
        return recievers;
    }

    public int getId() {
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

    public EmailBuilder setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public EmailBuilder setBody(String body) {
        this.body = body;
        return  this;
    }

    public EmailBuilder setAttatchments(List<String> attatchments) {
        this.attatchments = attatchments;
        return this;
    }

    public EmailBuilder setPriority(String priority) {
        this.priority = priority;
        return this;
    }


    public Email build(){
        return new Email(this);
    }
}
