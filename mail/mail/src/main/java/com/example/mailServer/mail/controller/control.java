package com.example.mailServer.mail.controller;

import com.example.mailServer.mail.services.Contact;
import com.example.mailServer.mail.services.Email.Email;
import com.example.mailServer.mail.services.Email.EmailBuilder;
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
import java.util.List;

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
    @GetMapping("/showContact/{account}")
    public ResponseEntity<ArrayList<Contact>> showContact(@PathVariable String account) {
        try {
            return ResponseEntity.ok(s.showContact(account));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody EmailBuilder e) {
        try {
            se.sendEmail(e);
            return ResponseEntity.ok("tarsh");
        } catch (Exception w) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("null");

        }
    }
    @PostMapping("/makeRead/{account}/{id}")
    public ResponseEntity<List<Email>> makeRead(@PathVariable String account , @PathVariable int id ) {
        try {
            return ResponseEntity.ok(se.makeRead(account,id));
        } catch (Exception w) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }
    @PostMapping("/saveDraft")
    public ResponseEntity<List<Email>> saveDraft(@RequestBody EmailBuilder e) {
        try {
            return ResponseEntity.ok(se.saveAsDraft(e));
        } catch (Exception w) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }
    @PostMapping("/modifyDraft")
    public ResponseEntity<List<Email>> modifyDraft(@RequestBody EmailBuilder e) {
        try {
            return ResponseEntity.ok(se.modifyDraft(e));
        } catch (Exception w) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }
    @PostMapping("/getEmails/{account}/{folder}")
    public ResponseEntity<List<Email>> getEmails(@PathVariable String account , @PathVariable String folder) {
        try {
            return ResponseEntity.ok(se.getEmails(account, folder));
        } catch (Exception w) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }




    @PostMapping("/filter/{account}/{type}/{value}")
    public ResponseEntity<ArrayList<Email>> filter(@PathVariable String account, @PathVariable String type,@PathVariable ArrayList<String> value) {
        try{
            return ResponseEntity.ok(se.filter(account , type , value));
        }catch (Exception w ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/sortEmail/{account}/{folder}/{type}/{sortingOrder}")
    public ResponseEntity<ArrayList<Email>> sortEmail(@PathVariable String account, @PathVariable String folder, @PathVariable String type, @PathVariable boolean sortingOrder ){
        try{
            return ResponseEntity.ok(se.sortEmail(account, type, sortingOrder , folder));
        }catch (Exception w ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}

