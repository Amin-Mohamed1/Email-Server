package com.example.mailServer.mail.services.AuthoriticationProxy;

import com.example.mailServer.mail.services.DataCache.Data;
import com.example.mailServer.mail.services.User;
import com.example.mailServer.mail.services.UserDTO;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Authoritication {
    Data d = Data.getInstance() ;

    public Authoritication() throws IOException {

    }

    public String signup(UserDTO u ) throws IOException, NoSuchAlgorithmException {
        if(checkEmail(u))
            return "Email has already exist" ;
        if(checkUsername(u))
            return "Username has already exist" ;
        if(!(u.emailaccount.endsWith("@gmail.com")||u.emailaccount.endsWith("@alexu.edu.eg")))
            return "Invalid domain of email" ;
        if(u.password.length()<8)
            return "Password must be at least 8 digits and characters" ;
        u.password = Encryption.getSHA(u.password) ;

        User user= d.add(new User(u));
        user.addFolder("inbox");
        user.addFolder("draft");
        user.addFolder("sent");
        user.addFolder("trash");
        return "Signed up successfully";
    }

    public String signin (UserDTO u ) throws IOException, NoSuchAlgorithmException {
       // System.out.println(u.emailaccount+"  " + u.password);

        if(!checkEmail(u))
            return  "Wrong email , check email" ;
        if(!checkPassword(u))
            return "wrong password" ;
        return "Signed in successfully" ;
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
