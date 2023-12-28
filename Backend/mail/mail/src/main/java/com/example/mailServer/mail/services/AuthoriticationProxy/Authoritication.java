package com.example.mailServer.mail.services.AuthoriticationProxy;

import com.example.mailServer.mail.services.DataCache.Data;
import com.example.mailServer.mail.services.DataCache.DataHelper;
import com.example.mailServer.mail.services.USER.IUser;
import com.example.mailServer.mail.services.USER.NotUser;
import com.example.mailServer.mail.services.USER.User;
import com.example.mailServer.mail.services.USER.UserDTO;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Authoritication {
    Data d = Data.getInstance() ;

    public Authoritication() throws IOException {

    }

    public IUser signup(UserDTO u ) throws IOException, NoSuchAlgorithmException {
        if(checkEmail(u))
            return new NotUser("Email has already exist");
        if(checkUsername(u))
            return new NotUser("Username has already exist") ;
        if(!(u.emailaccount.endsWith("@gmail.com")||u.emailaccount.endsWith("@alexu.edu.eg")))
            return new NotUser("Invalid domain of email") ;
        if(u.password.length()<8)
            return new NotUser("Password must be at least 8 digits and characters") ;
        u.password = Encryption.getSHA(u.password) ;

        User user= d.add(new User(u));
        user.addFolder("inbox");
        user.addFolder("draft");
        user.addFolder("sent");
        user.addFolder("trash");
        //System.out.println( user.toString() );
        // System.out.println(Data.getUsers().get(0).toString());
        d.saveToJson();
        return user;
    }

    public IUser signin (UserDTO u ) throws IOException, NoSuchAlgorithmException {
       // System.out.println(u.emailaccount+"  " + u.password);

        if(!checkEmail(u))
            return  new NotUser("Wrong email , check email") ;
        if(!checkPassword(u))
            return new NotUser("wrong password") ;
        return DataHelper.getUserByAccount(u.emailaccount);
    }

    public boolean checkPassword(UserDTO user) throws NoSuchAlgorithmException {
        String hashedPass = Encryption.getSHA(user.password) ;
        for (User u: d.getUsers()  ) {
            if(u.getEmailaccount().equals(user.emailaccount) && u.getPassword().equals(hashedPass))
                return true ;
        }
        return false ;
    }

    public boolean checkEmail(UserDTO user){
        for (User u: Data.getUsers()  ) {
            if(u.getEmailaccount().equals(user.emailaccount))
                return true ;
        }
        return false ;
    }
    public boolean checkUsername(UserDTO user){
        for (User u: Data.getUsers()  ) {
            if(u.getUsername().equals(user.username))
                return true ;
        }
        return false ;
    }
}
