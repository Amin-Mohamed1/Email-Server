package com.example.mailServer.mail.services;

import com.example.mailServer.mail.services.AuthoriticationProxy.Authoritication;
import com.example.mailServer.mail.services.DataCache.Data;
import com.example.mailServer.mail.services.USER.IUser;
import com.example.mailServer.mail.services.USER.UserDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

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
        ;
        return a.signin(u) ;
    }

}
