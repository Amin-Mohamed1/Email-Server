package com.example.mailServer.mail.services;

import com.example.mailServer.mail.services.AuthoriticationProxy.Authoritication;
import com.example.mailServer.mail.services.DataCache.Data;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Service
public class ServiceUserFacade {
    private Data d = Data.getInstance() ;
    public ServiceUserFacade() throws IOException {
        d.loadToJson() ;
    }
    public String signup(UserDTO u) throws IOException, NoSuchAlgorithmException {
        Authoritication a =new Authoritication() ;
        String status =  a.signup(u) ;
        return status ;
    }
    public String signin(UserDTO u) throws IOException, NoSuchAlgorithmException {
        Authoritication a =new Authoritication() ;
        String status =  a.signin(u) ;
        return status ;
    }

}
