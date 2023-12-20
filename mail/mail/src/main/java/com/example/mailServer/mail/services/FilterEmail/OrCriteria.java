package com.example.mailServer.mail.services.FilterEmail;

import com.example.mailServer.mail.services.Email;

import java.util.ArrayList;

public class OrCriteria implements ICriteria{
    private ICriteria firstCriteria;
    private ICriteria secondCriteria;
    private ICriteria thirdCriteria;
    private ICriteria fourthCriteria;
    private ICriteria fifthCriteria;

    public OrCriteria(ICriteria firstCriteria, ICriteria secondCriteria) {
        this.firstCriteria = firstCriteria;
        this.secondCriteria = secondCriteria;
    }

    public OrCriteria(ICriteria firstCriteria, ICriteria secondCriteria, ICriteria thirdCriteria) {
        this.firstCriteria = firstCriteria;
        this.secondCriteria = secondCriteria;
        this.thirdCriteria = thirdCriteria;
    }

    public OrCriteria(ICriteria firstCriteria, ICriteria secondCriteria,
                      ICriteria thirdCriteria, ICriteria fourthCriteria) {
        this.firstCriteria = firstCriteria;
        this.secondCriteria = secondCriteria;
        this.thirdCriteria = thirdCriteria;
        this.fourthCriteria = fourthCriteria;
    }

    public OrCriteria(ICriteria firstCriteria, ICriteria secondCriteria,
                      ICriteria thirdCriteria, ICriteria fourthCriteria, ICriteria fifthCriteria) {
        this.firstCriteria = firstCriteria;
        this.secondCriteria = secondCriteria;
        this.thirdCriteria = thirdCriteria;
        this.fourthCriteria = fourthCriteria;
        this.fifthCriteria = fifthCriteria;
    }

    @Override
    public ArrayList<Email> meetCriteria(ArrayList<Email> emails) {

        ArrayList<Email> firstCriteriaItems = this.firstCriteria.meetCriteria(emails);
        ArrayList<Email> secondCriteriaItems = this.secondCriteria.meetCriteria(emails);

        for (Email email : secondCriteriaItems) {
            if(!firstCriteriaItems.contains(email)) {
                firstCriteriaItems.add(email);
            }
        }

        if(this.thirdCriteria != null){
            ArrayList<Email> thirdCriteriaItems = this.thirdCriteria.meetCriteria(emails);
            for(Email email : thirdCriteriaItems) {
                if(!firstCriteriaItems.contains(email)) {
                    firstCriteriaItems.add((email));
                }
            }
        }

        if(this.fourthCriteria != null){
            ArrayList<Email> fourthCriteriaItems = this.fourthCriteria.meetCriteria(emails);
            for(Email email : fourthCriteriaItems) {
                if(!firstCriteriaItems.contains(email)) {
                    firstCriteriaItems.add((email));
                }
            }
        }

        if(this.fifthCriteria != null){
            ArrayList<Email> fifthCriteriaItems = this.fifthCriteria.meetCriteria(emails);
            for(Email email : fifthCriteriaItems) {
                if(!firstCriteriaItems.contains(email)) {
                    firstCriteriaItems.add((email));
                }
            }
        }

        return firstCriteriaItems;
    }
}
