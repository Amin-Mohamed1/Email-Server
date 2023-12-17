package com.example.mailServer.mail.controller;

import com.example.mailServer.mail.services.AuthoriticationProxy.Authoritication;
import com.example.mailServer.mail.services.DataCache.Data;
import com.example.mailServer.mail.services.User;
import com.example.mailServer.mail.services.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@Controller
@CrossOrigin("*")
@RequestMapping("/mail")
public class control {
    @Autowired
    private Data d = Data.getInstance() ;;


    public control() throws IOException {
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDTO u ){
       try{
           Authoritication a =new Authoritication() ;
           String status =  a.signup(u) ;
            if(status.equals("Signed up successfully"))
                return ResponseEntity.ok(status);
            else
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(status);
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null) ;
       }
    }
    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody UserDTO u ){
        try{
            Authoritication a =new Authoritication() ;
            String status =  a.signin(u) ;
            if(status.equals("Signed in successfully"))
                return ResponseEntity.ok(status);
            else
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(status);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null) ;
        }
    }


    @GetMapping("/load")
    public ResponseEntity<ArrayList<User>> load () throws IOException {
       // System.out.println("loaaaad");
     //   System.out.println(d.loadToJson().get(0).getUsername());
        if(d.loadToJson()!=null)
            return ResponseEntity.ok(d.loadToJson());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
