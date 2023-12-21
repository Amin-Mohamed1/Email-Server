package com.example.mailServer.mail.controller;

import com.example.mailServer.mail.services.Contact;
import com.example.mailServer.mail.services.Email;
import com.example.mailServer.mail.services.EmailBuilder;
import com.example.mailServer.mail.services.facade.ServiceEmailFacade;
import com.example.mailServer.mail.services.facade.ServiceUserFacade;
import com.example.mailServer.mail.services.USER.*;
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
    private ServiceUserFacade s;
    @Autowired
    private ServiceEmailFacade se;

    public control() throws IOException {
    }

    @PostMapping("/signup")
    public ResponseEntity<IUser> signup(@RequestBody UserDTO u) {
        try {
            IUser status = s.signup(u);
            if (status instanceof Acceptable)
                return ResponseEntity.ok(status);
            else
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(status);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<IUser> signin(@RequestBody UserDTO u) {
        try {

            IUser status = s.signin(u);
            if (status instanceof User)
                return ResponseEntity.ok(status);
            else
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(status);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/addContact/{account}")
    public ResponseEntity<ArrayList<Contact>> addContact(@RequestBody Contact c, @PathVariable String account) {
        try {
            return ResponseEntity.ok(s.addContact(c, account));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/editContact/{account}")
    public ResponseEntity<ArrayList<Contact>> editContact(@RequestBody Contact c, @PathVariable String account) {
        try {
            return ResponseEntity.ok(s.editContact(c, account));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/deleteContact/{account}/{id}")
    public ResponseEntity<ArrayList<Contact>> deleteContact(@PathVariable int id, @PathVariable String account) {
        try {
            return ResponseEntity.ok(s.deleteContact(id, account));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody EmailBuilder e) {
        try{
            se.sendEmail(e);
            return ResponseEntity.ok("tarsh");
        }catch (Exception w ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("null");

        }

    }
    @PostMapping("/filter/{account}/{type}/{value}")
    public ResponseEntity<ArrayList<Email>> sendEmail(@PathVariable String account, @PathVariable String type,@PathVariable ArrayList<String> value) {
        try{

            return ResponseEntity.ok(se.filter(account , type , value));
        }catch (Exception w ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }

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
