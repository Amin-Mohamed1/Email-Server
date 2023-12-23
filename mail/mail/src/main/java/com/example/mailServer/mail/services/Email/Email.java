package com.example.mailServer.mail.services.Email;

import com.example.mailServer.mail.services.Attachment;

import java.util.ArrayList;
import java.util.List;

public class Email {
    //required params
    private String sender;
    private ArrayList<String> recievers ;

    private String dateTime;
    private int id;

    //optional params
    private String subject;
    private String body;
    private List<Attachment> attatchments;
    private int priority;
    private boolean isRead ;

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public Email(){}

    public Email(EmailBuilder builder){
        this.sender = builder.getSender();
        this.recievers = builder.getRecievers() ;
        this.id = builder.getId();
        this.subject = builder.getSubject();
        this.body = builder.getBody();
        this.attatchments = builder.getAttatchments();
        this.priority = builder.getPriority();
        this.dateTime = builder.getDateTime();
    }

    public void setRecievers(ArrayList<String> recievers) {
        this.recievers = recievers;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setDateTime(String dateTime) {
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

    public List<Attachment> getAttatchments() {
        return attatchments;
    }

    public void setAttatchments(List<Attachment> attatchments) {
        this.attatchments = attatchments;
    }

    public String getBody() {
        return body;
    }



    public int getPriority() {
        return priority;
    }

    public String getDateTime() {
        return dateTime;
    }

}
