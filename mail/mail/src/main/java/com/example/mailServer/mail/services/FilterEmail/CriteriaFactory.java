package com.example.mailServer.mail.services.FilterEmail;

import java.util.ArrayList;

public class CriteriaFactory {
    static final int FIRSTELEMENT = 0;
    public ICriteria getCriteria(String criteriaName, ArrayList<String> criteriaValue){
        if (criteriaName == null)
            return null;
        else if (criteriaName.equalsIgnoreCase("DATETIME"))
            return new CriteriaDateTime(criteriaValue.get(FIRSTELEMENT));
        else if (criteriaName.equalsIgnoreCase("PRIORITY"))
            return new CriteriaPriority(Integer.parseInt(criteriaValue.get(FIRSTELEMENT)));
        else if (criteriaName.equalsIgnoreCase("RECIEVER"))
            return new CriteriaReciever(criteriaValue);
        else if (criteriaName.equalsIgnoreCase("SENDER"))
            return new CriteriaSender(criteriaValue.get(FIRSTELEMENT));
        else if (criteriaName.equalsIgnoreCase("SUBJECT"))
            return new CriteriaSubject(criteriaValue.get(FIRSTELEMENT));
        else
            return null;
    }
}
