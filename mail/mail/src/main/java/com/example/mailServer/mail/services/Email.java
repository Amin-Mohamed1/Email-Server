package com.example.mailServer.mail.services;

import java.util.List;

public class Email {
    private String sender;
    private List<String> reciever;
    private String id;
    private String subject;
    private String body;
    private List<String> attatchments;
    private String priority;
    private String dateTime;

    public Email(){}

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<String> getReciever() {
        return reciever;
    }

    public void setReciever(List<String> reciever) {
        this.reciever = reciever;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getAttatchments() {
        return attatchments;
    }

    public void setAttatchments(List<String> attatchments) {
        this.attatchments = attatchments;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
