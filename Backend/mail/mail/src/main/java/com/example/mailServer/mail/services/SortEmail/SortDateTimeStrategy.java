package com.example.mailServer.mail.services.SortEmail;

import com.example.mailServer.mail.services.Email.Email;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class SortDateTimeStrategy implements Strategy{
    final static boolean ASCENDING = true;
    @Override
    public ArrayList<Email> doOperation(ArrayList<Email> emails, boolean sortingOrder) {
        if(sortingOrder == ASCENDING){
            emails.sort((firstEmail, secondEmail) -> {
                try {
                    return parseDate(firstEmail).compareTo(
                            parseDate(secondEmail));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }});
        }
        else{
            emails.sort((firstEmail, secondEmail) -> {
                try {
                    return parseDate(secondEmail).compareTo(
                            parseDate(firstEmail));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }});
        }

        return emails;
    }

    public Date parseDate(Email email) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
        Date date = formatter.parse(email.getDateTime());
        return date;
    }
}
