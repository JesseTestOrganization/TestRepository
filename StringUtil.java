package com.edianjucai.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

    public static List<Integer> stringToIntList(String str) {
        String[] strArr = str.split(";");
        List<Integer> strs = new ArrayList<>();
        for (int i = 0; i < strArr.length; i++) {
            strs.add(Integer.valueOf(strArr[i]));
        }
        return strs;
    }
    
    public static List<String> stringToStringList(String str, String Symbol) {
        String[] strArr = str.split(Symbol);
        List<String> strs = new ArrayList<>();
        for (int i = 0; i < strArr.length; i++) {
            strs.add(str);
        }
        return strs;
    }
}
