package com.example.mailServer.mail.services.AuthoriticationProxy;

import com.example.mailServer.mail.services.DataCache.Data;
import com.example.mailServer.mail.services.User;
import com.example.mailServer.mail.services.UserDTO;

import java.io.IOException;

public class Authoritication {
    Data d = Data.getInstance() ;

    public Authoritication() throws IOException {
    }

    public String signup(UserDTO u ) throws IOException {
        if(checkEmail(u))
            return "Email has already exist" ;
        if(checkUsername(u))
            return "Username has already exist" ;
        if(!(u.emailaccount.endsWith("@gmail.com")||u.emailaccount.endsWith("@alexu.edu.eg")))
            return "Invalid domain of email" ;
        if(u.password.length()<8)
            return "Password must be at least 8 digits and characters" ;
        d.add(new User(u));
        return "Signed up successfully";
    }

//    public User checkPassword(User user){
//        for (User u: r.getUsers()  ) {
//            if(u.getEmail().equals(user.getEmail()) && u.getPassword().equals(user.getPassword()))
//                return u ;
//        }
//        return null ;
//    }

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
