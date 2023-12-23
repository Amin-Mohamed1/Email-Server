package com.example.mailServer.mail.services;

public class Attachment {
    private String name ;
    private String type ;
    private byte[] format ;

    public Attachment(String name, String type, byte[] format) {
        this.name = name;
        this.type = type;
        this.format = format;
    }
    public Attachment(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getFormat() {
        return format;
    }

    public void setFormat(byte[] format) {
        this.format = format;
    }
}
