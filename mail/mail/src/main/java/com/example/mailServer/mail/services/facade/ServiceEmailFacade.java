package com.example.mailServer.mail.services.facade;

import com.example.mailServer.mail.services.DataCache.Data;
import com.example.mailServer.mail.services.DataCache.DataHelper;
import com.example.mailServer.mail.services.Email.Email;
import com.example.mailServer.mail.services.Email.EmailBuilder;
import com.example.mailServer.mail.services.FilterEmail.CriteriaFactory;
import com.example.mailServer.mail.services.FilterEmail.ICriteria;
import com.example.mailServer.mail.services.Folder;
import com.example.mailServer.mail.services.SortEmail.SortFactory;
import com.example.mailServer.mail.services.SortEmail.Strategy;
import com.example.mailServer.mail.services.USER.Acceptable;
import com.example.mailServer.mail.services.USER.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class ServiceEmailFacade {
    private Data d = Data.getInstance() ;

    public ServiceEmailFacade() throws IOException {
    }
    public void sendEmail(EmailBuilder e) throws IOException, ParseException {
        Email email = e.build();
        //email.setRead(true);
        User s = DataHelper.getUserByAccount(email.getSender());
        if (s instanceof Acceptable) {
            s.addEmailToFolder("sent", email);
//            System.out.println(email.getRecievers().get(0));
//            String ema = email.getRecievers().get(0) ;
//            System.out.println("bola hany");
//            System.out.println(ema);
//            if(ema.equals("rafy@gmail.com"))
//                System.out.println("equallllllll");
//            User r = DataHelper.getUserByAccount(ema) ;
//            if (r != null) {
//                r.addEmailToFolder("inbox",email);
//            }
            for (int i = 0; i < email.getRecievers().size(); i++) {
                User r = DataHelper.getUserByAccount(email.getRecievers().get(i));
                r.addEmailToFolder("inbox", email);
            }
            d.saveToJson();
        }
    }


    public ArrayList<Email> filter (String account , String type ,ArrayList<String> criteriaValue){
        User u = DataHelper.getUserByAccount(account) ;
        ArrayList<Email> emails = new ArrayList<>() ;
        for(Folder f : u.getFolders()){
            for(Email e : f.getEmails()){
                if(!emails.contains(e))
                    emails.add(e) ;
            }
        }
        CriteriaFactory factory = new CriteriaFactory() ;
        ICriteria criteria = factory.getCriteria(type ,criteriaValue ) ;
        return criteria.meetCriteria(emails) ;
    }

    public ArrayList<Email> sortEmail (String account, String type, boolean sortingOrder , String folder){
        User u = DataHelper.getUserByAccount(account) ;
        ArrayList<Email> emails = new ArrayList<>() ;
        for(Folder f : u.getFolders()){
            if(f.getName().equals(folder)) {
                for (Email e : f.getEmails()) {
                    if (!emails.contains(e))
                        emails.add(e);
                }
            }
        }
        SortFactory factory = new SortFactory() ;
        Strategy strategy = factory.getSort(type) ;
        return strategy.doOperation(emails, sortingOrder);

    }
}
