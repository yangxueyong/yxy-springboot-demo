package com.example.yxy.utils;

public class StringUtil {
    public static boolean isNull(String s){
        if(s == null){
            return true;
        }
        return false;
    }

    public static String concat(String s1,String s2){
        return s1 + s2;
    }


    public static boolean contains(String s1,String s2){
        return s1.contains(s2);
    }
}
