package com.adt.expensemanagement.util;

public class CapExDetailsUtility {
    public static boolean validateAmount(int amt){
        if(amt<0){
            return false;
        }
        return true;
    }

    public static boolean validateCapEx(String str){
        if(str=="" || str==null || str.matches(".*\\d.*")){
            return false;
        }
        return true;
    }
    public static boolean validateExpense(String str){
        if(str=="" || str==null || str.contains(".")){
            return false;
        }
        return true;
    }
    public static boolean validateGST(String str){
        if(str=="" || str==null || str.matches(".*\\W.*")){
            return false;
        }
        return true;
    }
}
