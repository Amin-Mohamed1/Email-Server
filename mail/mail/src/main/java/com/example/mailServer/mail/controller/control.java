package com.example.mailServer.mail.controller;

import com.example.mailServer.mail.services.ServiceUserFacade;
import com.example.mailServer.mail.services.USER.IUser;
import com.example.mailServer.mail.services.USER.NotUser;
import com.example.mailServer.mail.services.USER.User;
import com.example.mailServer.mail.services.USER.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@CrossOrigin("*")
@RequestMapping("/mail")
public class control {
//    @Autowired
//    private Data d = Data.getInstance() ;;
    @Autowired
    private ServiceUserFacade s ;


    public control() throws IOException {
    }

    @PostMapping("/signup")
    public ResponseEntity<IUser> signup(@RequestBody UserDTO u ){
       try{
           IUser status = s.signup(u) ;
            if(status instanceof User)
                return ResponseEntity.ok(status);
            else
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(status);
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null) ;
       }
    }
    @PostMapping("/signin")
    public ResponseEntity<IUser> signin(@RequestBody UserDTO u ){
        try{

            IUser status = s.signin(u) ;
            if(status instanceof User)
                return ResponseEntity.ok(status);
            else
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(status);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null) ;
        }
    }


//    @GetMapping("/load")
//    public ResponseEntity<ArrayList<User>> load () throws IOException {
//       // System.out.println("loaaaad");
//     //   System.out.println(d.loadToJson().get(0).getUsername());
//        if(d.loadToJson()!=null)
//            return ResponseEntity.ok(d.loadToJson());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//    }
}
