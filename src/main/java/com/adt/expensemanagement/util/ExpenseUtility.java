package com.adt.expensemanagement.util;

import java.time.LocalDate;

public class ExpenseUtility {

    public static boolean validateAmountAndId(int amt ){
        if(amt<0){
            return false;
        }
         return true;

    }

    public static boolean validateExpenses(String str) {
        if (str == null || str == "" || str.matches(".*\\d.*")|| str.contains(".") ||str.matches("[a-zA-Z0-9\\s!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?`~]+\n")) {
            return false;
        }
        return true;

    }
    public static boolean validateDescription(String str){
        if(str == null || str == ""){
            return false;
        }
        return true;
    }

}

