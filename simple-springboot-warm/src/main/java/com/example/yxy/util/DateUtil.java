package com.example.yxy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static boolean checkDate(String st ,String et) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date startTime = sdf.parse(st);
        Date endTime = sdf.parse(et);
        Date nowCal = sdf.parse(sdf.format(calendar.getTime()));
        if(nowCal.after(startTime)  && nowCal.before(endTime)){
            return true;
        }else{
            return false;
        }
    }
}
