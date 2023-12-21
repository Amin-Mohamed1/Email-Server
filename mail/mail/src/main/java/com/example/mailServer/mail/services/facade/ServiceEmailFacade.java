package com.example.mailServer.mail.services.facade;

import com.example.mailServer.mail.services.DataCache.Data;
import com.example.mailServer.mail.services.DataCache.DataHelper;
import com.example.mailServer.mail.services.Email;
import com.example.mailServer.mail.services.EmailBuilder;
import com.example.mailServer.mail.services.USER.Acceptable;
import com.example.mailServer.mail.services.USER.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class ServiceEmailFacade {
    private Data d = Data.getInstance() ;

    public ServiceEmailFacade() throws IOException {
    }
    public void sendEmail(EmailBuilder e ) throws IOException {
        Email email = e.build() ;
        //email.setRead(true);
        User s = DataHelper.getUserByAccount(email.getSender()) ;
        if(s instanceof Acceptable){
            s.addEmailToFolder("sent" , email);
            email.setRead(false);
            for(String rs : email.getRecievers() ){
                System.out.println(rs);
                s = DataHelper.getUserByAccount(rs) ;
                if(s != null)
                    System.out.println(s.getUsername());
//                if(s instanceof Acceptable){
//                    s.addEmailToFolder("inbox" , email);
//                    System.out.println("addddeddd");
//                }
            }
            d.saveToJson();
        }
    }
}
