package com.cjq.bejingunion.utils;

/**
 * Created by CJQ on 2015/12/31.
 */
public class Validator {

    public static boolean validate_ID(String idNumber){
        return idNumber.matches("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{2}\\w$") || idNumber.matches("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}\\w$");
    }
}
