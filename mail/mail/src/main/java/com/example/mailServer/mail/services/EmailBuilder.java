package com.example.mailServer.mail.services;

import java.util.List;

public class EmailBuilder{
    //required params
    private String sender;
    private List<String> reciever;

    //optional params
    protected String id;
    private String subject;
    private String body;
    private List<String> attatchments;
    private String priority;
    private String dateTime;

    public EmailBuilder(){}
    public EmailBuilder(String sender, List<String> reciever){
        this.sender = sender;
        this.reciever = reciever;
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

    public EmailBuilder setId(String id) {
        this.id = id;
        return this;
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

    public EmailBuilder setDateTime(String dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public Email build(){
        return new Email(this);
    }
}
