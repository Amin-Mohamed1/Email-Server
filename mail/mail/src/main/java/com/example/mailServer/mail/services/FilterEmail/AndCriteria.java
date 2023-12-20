package com.example.mailServer.mail.services.FilterEmail;

import com.example.mailServer.mail.services.Email;

import java.util.ArrayList;

public class AndCriteria implements ICriteria{
    private ICriteria firstCriteria;
    private ICriteria secondCriteria;
    private ICriteria thirdCriteria;
    private ICriteria fourthCriteria;
    private ICriteria fifthCriteria;

    public AndCriteria(ICriteria firstCriteria, ICriteria secondCriteria) {
        this.firstCriteria = firstCriteria;
        this.secondCriteria = secondCriteria;
    }

    public AndCriteria(ICriteria firstCriteria, ICriteria secondCriteria, ICriteria thirdCriteria) {
        this.firstCriteria = firstCriteria;
        this.secondCriteria = secondCriteria;
        this.thirdCriteria = thirdCriteria;
    }

    public AndCriteria(ICriteria firstCriteria, ICriteria secondCriteria,
                      ICriteria thirdCriteria, ICriteria fourthCriteria) {
        this.firstCriteria = firstCriteria;
        this.secondCriteria = secondCriteria;
        this.thirdCriteria = thirdCriteria;
        this.fourthCriteria = fourthCriteria;
    }

    public AndCriteria(ICriteria firstCriteria, ICriteria secondCriteria,
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
        ArrayList<Email> answer = this.secondCriteria.meetCriteria(firstCriteriaItems);

        if(this.thirdCriteria != null){
            answer = this.thirdCriteria.meetCriteria(answer);
        }

        if(this.fourthCriteria != null){
            answer = this.fourthCriteria.meetCriteria(answer);
        }

        if(this.fifthCriteria != null){
            answer = this.fifthCriteria.meetCriteria(answer);
        }

        return answer;
    }
}
