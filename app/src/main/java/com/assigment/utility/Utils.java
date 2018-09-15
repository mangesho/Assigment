package com.assigment.utility;

public class Utils {

    public static Utils utils;

    public static Utils getInstance(){
        if(utils == null){
            utils = new Utils();
        }
        return utils;
    }

    public boolean isStringValidated(String string){
        if(string == null){
            return false;
        }

        if(string.equalsIgnoreCase("null") || string.length() == 0){
            return false;
        }
        return true;
    }
}