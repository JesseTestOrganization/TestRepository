package com.edianjucai.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.edianjucai.common.Constant;

public class DateFormatUtils {

    public static String dateToString(Date date, String... dateFormat) {
        String format = null;
        if (dateFormat != null && dateFormat.length == 1) {
            format = dateFormat[0];
        } else {
            format = Constant.DATEFORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String strDate = sdf.format(date);
        return strDate;
    }
    
    public static Date StringToDate(String strDate, String... dateFormat) throws ParseException {
        String format = null;
        if (dateFormat != null && dateFormat.length == 1) {
            format = dateFormat[0];
        } else {
            format = Constant.DATEFORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = sdf.parse(strDate);
        return date;
    }
    
    public static Date calculationDate(Date date, int month) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.MONTH, month);
        return date = cl.getTime();
    } 
}
