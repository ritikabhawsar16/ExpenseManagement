package com.adt.expensemanagement.util;

import java.math.BigDecimal;

public class GSTInvoiceUtility {
    public static boolean checkValidate(String str){
        if(str=="" || str==null){
            return false;
        }
        return true;
    }

    public static boolean validatePeriod(String str){
        if(str=="" || str==null || !str.matches("^[^A-Za-z]*$")){
            return false;
        }
        return true;
    }
    public static boolean validateString(String str){
        if(str.matches(".*\\d.*")){
            return false;
        }
        return true;
    }

    public static boolean validateEmail(String email){
        if(email=="" || email==null || !email.contains("@") || !email.contains(".")){
            return false;
        }
        return  true;
    }

    public static boolean validateBigDecimal(BigDecimal var){
        if(var.scale() >1 || var.precision() > 7) {
            return false;
        }
        return true;
    }
    public static boolean validatePhoneNo(Long no){
        String mobile=String.valueOf(no);
        if(mobile=="" ||mobile==null || mobile.length()>10 ||mobile.length()<10|| !mobile.matches("^[^A-Za-z]*$") ){
            return false;
        }
        return true;
    }
}
