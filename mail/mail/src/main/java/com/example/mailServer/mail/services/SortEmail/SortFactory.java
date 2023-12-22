package com.example.mailServer.mail.services.SortEmail;

import java.util.ArrayList;

public class SortFactory {
        public Strategy getSort(String type){
            if (type == null)
                return null;
            else if (type.equalsIgnoreCase("DATETIME"))
                return new SortDateTimeStrategy();
            else if (type.equalsIgnoreCase("PRIORITY"))
                return new SortPriorityStrategy();
            else if (type.equalsIgnoreCase("SUBJECT"))
                return new SortSubjectStrategy();
            else if (type.equalsIgnoreCase("SENDER"))
                return new SortSenderStrategy();
            else
                return null;
        }
}
