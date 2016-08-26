package com.jogging.bhs.managers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by SPEED on 8/14/2016.
 */
public class GraphDatesManager {

    public GraphDatesManager ()
    {

    }

    public static Date getFirstDayOfPreviousMonth ()
    {
        Calendar aCalendar = Calendar.getInstance();
// add -1 month to current month
        aCalendar.add(Calendar.MONTH, -1);
// set DATE to 1, so first date of previous month
        aCalendar.set(Calendar.DATE, 1);

        Date  firstDateOfPreviousMonth = aCalendar.getTime();
        return firstDateOfPreviousMonth ;

    }

    public static Date getLastDayOfPreviousMonth ()
    {
        Calendar aCalendar = Calendar.getInstance();
        // add -1 month to current month
        aCalendar.add(Calendar.MONTH, -1);

        // set actual maximum date of previous month
        aCalendar.set(Calendar.DATE,     aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        //read it
        Date lastDateOfPreviousMonth = aCalendar.getTime();

        return lastDateOfPreviousMonth ;
    }

    public static Date getLastDayOfPrevious2Month ()
    {
        Calendar aCalendar = Calendar.getInstance();
        // add -1 month to current month
        aCalendar.add(Calendar.MONTH, -2);



        // set actual maximum date of previous month
        aCalendar.set(Calendar.DATE,     aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        //read it
        Date lastDateOfPreviousMonth = aCalendar.getTime();

        return lastDateOfPreviousMonth;
    }

    public static int GetCurrentYearWeekNumber ()
    {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.WEEK_OF_YEAR) ;


    }
    public static int GetCurrentMonthWeekNumber ()
    {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.WEEK_OF_MONTH) ;


    }

    public static Date getStartOFWeek(int enterWeek, int enterYear){


        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.WEEK_OF_YEAR, enterWeek);
        calendar.set(Calendar.YEAR, enterYear);

        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy"); // PST`
        Date startDate = calendar.getTime();
        String startDateInStr = formatter.format(startDate);
        System.out.println("...date..."+startDateInStr);
        return startDate ;

    }

    public static Date getEndOFWeek(int enterWeek, int enterYear){


        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.WEEK_OF_YEAR, enterWeek);
        calendar.set(Calendar.YEAR, enterYear);

        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy"); // PST`

        calendar.add(Calendar.DATE, 6);
        Date enddate = calendar.getTime();
        String endDaString = formatter.format(enddate);
        return enddate ;

    }
}
