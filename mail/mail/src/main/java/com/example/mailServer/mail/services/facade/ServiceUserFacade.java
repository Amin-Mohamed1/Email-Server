package com.example.mailServer.mail.services.facade;

import com.example.mailServer.mail.services.AuthoriticationProxy.Authoritication;
import com.example.mailServer.mail.services.Contact;
import com.example.mailServer.mail.services.DataCache.Data;
import com.example.mailServer.mail.services.DataCache.DataHelper;
import com.example.mailServer.mail.services.USER.IUser;
import com.example.mailServer.mail.services.USER.User;
import com.example.mailServer.mail.services.USER.UserDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

@Service
public class ServiceUserFacade {
    private Data d = Data.getInstance() ;
    public ServiceUserFacade() throws IOException {
        d.loadToJson() ;
    }
    public IUser signup(UserDTO u) throws IOException, NoSuchAlgorithmException {
        Authoritication a =new Authoritication() ;
        return a.signup(u)  ;
    }
    public IUser signin(UserDTO u) throws IOException, NoSuchAlgorithmException {
        Authoritication a =new Authoritication() ;

        return a.signin(u) ;
    }
    public ArrayList<Contact> addContact(Contact contact , String account) throws IOException {
        User u = DataHelper.getUserByAccount(account) ;
        u.addContact(contact.getName(),contact.getEmailaccount(),contact.getId());
        d.saveToJson();
        return u.getContacts() ;
    }
    public ArrayList<Contact> editContact(Contact contact , String account) throws IOException {
        User u = DataHelper.getUserByAccount(account) ;
        for(int i = 0 ; i < u.getContacts().size() ; i++){
            if(u.getContacts().get(i).getId() == contact.getId()){
                u.getContacts().set(i , contact ) ;
                d.saveToJson();
                return u.getContacts() ;
            }
        }
        return addContact(contact , account ) ;
    }
    public ArrayList<Contact> deleteContact(int id , String account) throws IOException {
        User u = DataHelper.getUserByAccount(account) ;
        for(int i = 0 ; i < u.getContacts().size() ; i++){
            if(u.getContacts().get(i).getId() == id){
                u.getContacts().remove(i) ;
                d.saveToJson();
                return u.getContacts() ;
            }
        }
        return u.getContacts() ;
    }

}
