package com.jogging.bhs.managers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ConvertDatesMilliseconds {

    public ConvertDatesMilliseconds()
    {

    }




    public static String getDate(long milliSeconds, String dateFormat)
    {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(milliSeconds);  //here your time in miliseconds
        String date = "" + cl.get(Calendar.DAY_OF_MONTH) + "/" + cl.get(Calendar.MONTH) + "/" + cl.get(Calendar.YEAR);
        String time = "" + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE) ;
        if (cl.get(Calendar.HOUR_OF_DAY )<9 )
        {
            time = "" +"0" +cl.get(Calendar.HOUR_OF_DAY) + ":" +cl.get(Calendar.MINUTE) ;
        }
        if (cl.get(Calendar.MINUTE)<9)
        {
            if (cl.get(Calendar.HOUR_OF_DAY )<9 )
            {
                time = "" +"0" +cl.get(Calendar.HOUR_OF_DAY) + ":" + "0"+cl.get(Calendar.MINUTE) ;
            }
            else {
                time = "" +cl.get(Calendar.HOUR_OF_DAY) + ":" + "0"+cl.get(Calendar.MINUTE) ;

            }

        }


        return date+"   "+time;
    }

    public static String getTime(long milliSeconds)
    {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(milliSeconds);  //here your time in miliseconds
        String time = "" + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE);

        if (cl.get(Calendar.HOUR_OF_DAY )<9 )
        {
            time = "" +"0" +cl.get(Calendar.HOUR_OF_DAY) + ":" +cl.get(Calendar.MINUTE) ;
        }
        if (cl.get(Calendar.MINUTE)<9)
        {
            if (cl.get(Calendar.HOUR_OF_DAY )<9 )
            {
                time = "" +"0" +cl.get(Calendar.HOUR_OF_DAY) + ":" + "0"+cl.get(Calendar.MINUTE) ;
            }
            else {
                time = "" +cl.get(Calendar.HOUR_OF_DAY) + ":" + "0"+cl.get(Calendar.MINUTE) ;

            }

        }

        return time ;
    }

    public static String getdate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");

        // Create a calendar object that will convert the date and time value in milliseconds to date.

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(milliSeconds);
//        return formatter.format(calendar.getTime());
    }

    public static  long ConvertDateToMillis  (Date givenDateString)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        Date mDate = null;
        try {
            mDate = sdf.parse(String.valueOf(givenDateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mDate.getTime();



    }
}
