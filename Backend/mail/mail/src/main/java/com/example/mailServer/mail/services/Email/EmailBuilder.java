package com.example.mailServer.mail.services.Email;

import com.example.mailServer.mail.services.Attachment;

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
    private List<Attachment> attatchments;
    private int priority;
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

    public List<Attachment> getAttatchments() {
        return attatchments;
    }



    public int getPriority() {
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

    public EmailBuilder setAttatchments(List<Attachment> attatchments) {
        this.attatchments = attatchments;
        return this;
    }

    public EmailBuilder setPriority(int priority) {
        this.priority = priority;
        return this;
    }


    public Email build(){
        return new Email(this);
    }
}
