package com.example.mailServer.mail.controller;

import com.example.mailServer.mail.services.Contact;
import com.example.mailServer.mail.services.Email.Email;
import com.example.mailServer.mail.services.Email.EmailBuilder;
import com.example.mailServer.mail.services.Folder;
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
import java.util.Arrays;
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
    @PostMapping("/makeRead/{account}/{folder}/{id}")
    public ResponseEntity<List<Email>> makeRead(@PathVariable String account,@PathVariable String folder, @PathVariable int id ) {
        try {
            return ResponseEntity.ok(se.makeRead(account, folder, id));
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
    @PostMapping("/sendDraftToInbox")
    public ResponseEntity<List<Email>> sendDraftToInbox(@RequestBody EmailBuilder e) {
        try {
            return ResponseEntity.ok(se.sendDraftToSent(e));
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
    @DeleteMapping("/delete/{account}/{folder}")
    public ResponseEntity<List<Email>> delete(@RequestBody int [] id , @PathVariable String account , @PathVariable String folder) {
        try {
            return ResponseEntity.ok(se.deleteEmail(account, folder, id));
        } catch (Exception w) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }
    @PostMapping("/restoreEmail/{account}")
    public ResponseEntity<List<Email>> restoreFromTrash(@RequestBody int [] id , @PathVariable String account ) {
        try {
            return ResponseEntity.ok(se.restoreFromTrash(account , id));
        } catch (Exception w) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }
    @GetMapping("/addFolder/{account}/{folder}")
    public ResponseEntity<String> addFolder( @PathVariable String account ,@PathVariable String folder ) {
        try {
            return ResponseEntity.ok(se.addNewFolder(account , folder));
        } catch (Exception w) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }
    @PostMapping("/moveEmails/{account}/{folder}")
    public ResponseEntity<List<Email>> moveEmailsToFolder(@RequestBody int[] id , @PathVariable String account ,@PathVariable String folder ) {
        try {
            return ResponseEntity.ok(se.moveEmailsToFolder(account, folder, id));
        } catch (Exception w) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }
    @DeleteMapping("/deleteFolder/{account}/{folder}")
    public ResponseEntity<String> moveEmailsToFolder( @PathVariable String account ,@PathVariable String folder ) {
        try {
            return ResponseEntity.ok(se.deleteFolder(account, folder));
        } catch (Exception w) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }
    @GetMapping("/showContact/{account}")
    public ResponseEntity<ArrayList<Folder>> showFolders(@PathVariable String account ) {
        try {
            return ResponseEntity.ok(se.showFolder(account));
        } catch (Exception w) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }



    @PostMapping("/editPriority/{account}/{id}/{priority}")
    public ResponseEntity<List<Email>> editPriority(@PathVariable String account ,@PathVariable int id  , @PathVariable int priority ) {
        try {
            return ResponseEntity.ok(se.editPriority(account, id, priority));
        } catch (Exception w) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }




    @PostMapping("/search/{account}/{type}/{value}")
    public ResponseEntity<ArrayList<Email>> search(@PathVariable String account, @PathVariable String type,@PathVariable String value) {
        try{
            return ResponseEntity.ok(se.search(account , type , value));
        }catch (Exception w ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/filter/{account}/{folder}/{type}/{value}")
    public ResponseEntity<ArrayList<Email>> filter(@PathVariable String account,@PathVariable String folder , @PathVariable String type,@PathVariable String value) {
        try{
            return ResponseEntity.ok(se.filter(account, folder , type , value));
        }catch (Exception w ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PostMapping("/filterContact/{account}/{type}/{value}")
    public ResponseEntity<ArrayList<Contact>> filterContact(@PathVariable String account, @PathVariable String type,@PathVariable String value) {
        try{
            return ResponseEntity.ok(se.filterContact(account,  type , value));
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

