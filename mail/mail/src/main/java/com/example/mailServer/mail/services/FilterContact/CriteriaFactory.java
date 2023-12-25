package com.example.mailServer.mail.services.FilterContact;


public class CriteriaFactory {
    public ICriteria getCriteria(String criteriaName, String criteriaValue){
        if (criteriaName == null)
            return null;
        else if (criteriaName.equalsIgnoreCase("EMAILACCOUNT"))
            return new CriteriaEmailAccout(criteriaValue);
        else if (criteriaName.equalsIgnoreCase("NAME"))
            return new CriteriaName(criteriaValue);
        else if (criteriaName.equalsIgnoreCase("ALL"))
            return new OrCriteria(new CriteriaEmailAccout(criteriaValue), new CriteriaName(criteriaValue));
        else
            return null;
    }
}
