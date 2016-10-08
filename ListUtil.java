package com.edianjucai.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

    public static List<Object> dereplication(List<Object> list) {
        List<Object> tempList= new ArrayList<>();  
        for(Object i:list){  
            if(!tempList.contains(i)){  
                tempList.add(i);  
            }  
        } 
        return tempList;
    }
}
