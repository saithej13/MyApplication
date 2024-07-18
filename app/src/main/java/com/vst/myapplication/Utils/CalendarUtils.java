package com.vst.myapplication.Utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CalendarUtils {
    public static final String DATE_STD_PATTERN = "yyyy-MM-dd";
    public static final String DATE_STD_PATTERN2 = "dd-MM-yyyy";
    public final static String DATE_PATTERN_CURRENT_DATE_LOG = "yyyy-MM-dd'T'HH:mm:ss";

    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    public static String getOrderPostDate()
    {
        String dateStr = null;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_STD_PATTERN, Locale.ENGLISH);
        dateStr = sdf.format(date);
        return dateStr;
    }
    //public static String getDateRoom()
    public static Date getDateRoom(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_STD_PATTERN, Locale.ENGLISH);
            return sdf.parse(date);
        } catch (ParseException e) {
            Log.d("ExceptionfromDate", e.getMessage());
            return null;
        }
    }
    public static Date getCrDateTimeRoom() {
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            return sdf.parse(String.valueOf(date));
        } catch (ParseException e) {
            Log.d("ExceptionfromDate", e.getMessage());
            return null;
        }
    }
    public static String getCurrentDateAsString()
    {
        String date = null;

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN,Locale.ENGLISH);
        date = sdf.format(new Date());

        return date;
    }
    public static String convertDateToFormattedString(String date) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            Date parsedDate = inputFormat.parse(date);
            if (parsedDate != null) {
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                return outputFormat.format(parsedDate);
            }
        } catch (ParseException e) {
            Log.d("ExceptionfromDate", e.getMessage());
        }
        return "N/A";
    }
    public static long getDatelong(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            Date parsedDate = sdf.parse(date);
            if (parsedDate != null) {
                return parsedDate.getTime();
            }
        } catch (ParseException e) {
            Log.d("ExceptionfromDate", e.getMessage());
        }
        return 0; // Return a default value in case of parse failure
    }
    public static String getPostDate(String date)
    {
        String dateStr = null;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_STD_PATTERN, Locale.ENGLISH);
        dateStr = sdf.format(date);
        return dateStr;
    }
    public static String getDatePattern3()
    {
        String dateStr = null;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_STD_PATTERN, Locale.ENGLISH);
        dateStr = sdf.format(date);
        Log.d("DashboardDAte",""+dateStr);
        return dateStr;
    }
    public static String getDatePattern2()
    {
        String dateStr = null;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_STD_PATTERN2, Locale.ENGLISH);
        dateStr = sdf.format(date);
        return dateStr;
    }
    public static String getTommorowDate()
    {
        String dateStr = null;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_STD_PATTERN, Locale.ENGLISH);
        dateStr = sdf.format(date);
        return dateStr;
    }
    public static String getOrderSummaryDate(int year, int month, int day)
    {
        month = month + 1;
        String date = (year < 10 ? "0"+year : year) + "-" +
                ((month) < 10 ? "0"+(month) : (month)) + "-" +
                (day < 10 ? "0"+day : day);

        return date;
    }
    public static int getDiffBtwDatesInDays(String startDate,String endDate)
    {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        calendar1.setTime(CalendarUtils.getDateFromString(startDate, CalendarUtils.DATE_STD_PATTERN));
        calendar2.setTime(CalendarUtils.getDateFromString(endDate, CalendarUtils.DATE_STD_PATTERN));

        long milliseconds1 = calendar1.getTimeInMillis();
        long milliseconds2 = calendar2.getTimeInMillis();

        long diff = milliseconds2 - milliseconds1;
        int diffDays = (int) (diff / (24 * 60 * 60 * 1000));

        return diffDays;
    }
    public static Date getDateFromString(String date,String pattern)
    {
        Date dateObj = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern,Locale.ENGLISH);
        try
        {
            if(!TextUtils.isEmpty(date))
                dateObj = sdf.parse(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return dateObj;
    }
    public static String getFormatedDatefromString2(String strDate) {
        String formattedDate = "N/A";
        if (!TextUtils.isEmpty(strDate)) {
            try {
                // Define the input date format pattern
                SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'Z yyyy", Locale.ENGLISH);
                // Set the time zone for input format if needed
                inputFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                // Parse the input date string
                Date date = inputFormat.parse(strDate);
                if (date != null) {
                    // Define the output date format pattern
                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
                    // Format the date into the desired format
                    formattedDate = outputFormat.format(date);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return formattedDate;
    }
    public static String getFormatedDatefromString(String strDate)
    {

        String formatedDate  = "N/A";
        if(!TextUtils.isEmpty(strDate))
        {
            try
            {
                formatedDate = strDate;
                LogUtils.errorLog(strDate, strDate);

                if(strDate.contains(" "))
                    strDate = strDate.replace(" ", "T");

                if(strDate.contains("T"))
                {
                    String arrDate[]= strDate.split("T")[0].split("-");
                    formatedDate = arrDate[0]+" "+getMonthFromNumber(StringUtils.getInt(arrDate[1]))+" "+arrDate[2];
                }
                else
                {
                    String arrDate[]= strDate.split("-");
                    formatedDate = arrDate[0]+" "+getMonthFromNumber(StringUtils.getInt(arrDate[1]))+" "+arrDate[2];
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                formatedDate =null;
            }
        }
        return formatedDate;
    }
    public static String getFormatedDatefromString3(String strDate)
    {
        //present format        tdate 06 Jul 2024 shift E
        //required format     tdate "2024-07-06" shift "E"
        //
        String formatedDate  = "N/A";
        if(!TextUtils.isEmpty(strDate))
        {
            try
            {
                formatedDate = strDate;
                LogUtils.errorLog(strDate, strDate);

                if(strDate.contains(" "))
                    strDate = strDate.replace(" ", "-");

                if(strDate.contains("T"))
                {
                    String arrDate[]= strDate.split("T")[0].split("-");
                    formatedDate = arrDate[2]+" "+getMonthFromString(arrDate[1])+" "+arrDate[0];
                }
                else
                {
                    String arrDate[]= strDate.split("-");
                    String s = String.valueOf(getMonthFromString(arrDate[1]));
                    if(s.length()==1)
                    {
                        formatedDate = arrDate[2]+"-0"+getMonthFromString(arrDate[1])+"-"+arrDate[0];
                    }
                    else
                    {
                        formatedDate = arrDate[2]+"-"+getMonthFromString(arrDate[1])+"-"+arrDate[0];
                    }

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                formatedDate =null;
            }
        }
        Log.d("formatedDate",""+formatedDate);
        return formatedDate;
    }
    public static String getFormatedDatefromString4(String strDate)
    {
        //present format        tdate 2024 Jul 06
        //required format     tdate "2024-07-06"
        //
        String formatedDate  = "N/A";
        if(!TextUtils.isEmpty(strDate))
        {
            try
            {
                formatedDate = strDate;
                LogUtils.errorLog(strDate, strDate);

                if(strDate.contains(" "))
                    strDate = strDate.replace(" ", "-");

                if(strDate.contains("T"))
                {
                    String arrDate[]= strDate.split("T")[0].split("-");
                    formatedDate = arrDate[2]+" "+getMonthFromString(arrDate[1])+" "+arrDate[0];
                }
                else
                {
                    String arrDate[]= strDate.split("-");
                    String s = String.valueOf(getMonthFromString(arrDate[1]));
                    if(s.length()==1)
                    {
                        if(arrDate[1].length()==1 && arrDate[2].length()==1)
                        {
                            formatedDate = arrDate[0]+"-0"+arrDate[1]+"-0"+arrDate[2];
                        }
                        else if(arrDate[1].length()==1){
                            formatedDate = arrDate[0] + "-0" + arrDate[1] + "-" + arrDate[2];
                        }
                        else if(arrDate[2].length()==1){
                            formatedDate = arrDate[0] + "-" + arrDate[1] + "-0" + arrDate[2];
                        }
                    }
                    else
                    {
                        formatedDate = arrDate[0]+"-"+arrDate[1]+"-"+arrDate[2];
                    }

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                formatedDate =null;
            }
        }
        Log.d("formatedDate",""+formatedDate);
        return formatedDate;
    }
    public static String getMonthFromNumber(int intMonth)
    {
        String strMonth = "";

        switch(intMonth)
        {
            case 1:
                strMonth = "Jan";break;
            case 2:
                strMonth = "Feb";break;
            case 3:
                strMonth = "Mar";break;
            case 4:
                strMonth = "Apr";break;
            case 5:
                strMonth = "May";break;
            case 6:
                strMonth = "Jun";break;
            case 7:
                strMonth = "Jul";break;
            case 8:
                strMonth = "Aug";break;
            case 9:
                strMonth = "Sep";break;
            case 10:
                strMonth = "Oct";break;
            case 11:
                strMonth = "Nov";break;
            case 12:
                strMonth = "Dec";break;
        }

        return strMonth;
    }
    public static int getMonthFromString(String Month)
    {
        int intMonth=0;

        switch(Month)
        {
            case "Jan":
                intMonth = 1;break;
            case "Feb":
                intMonth = 2;break;
            case "Mar":
                intMonth = 3;break;
            case "Apr":
                intMonth = 4;break;
            case "May":
                intMonth = 5;break;
            case "Jun":
                intMonth = 6;break;
            case "Jul":
                intMonth = 7;break;
            case "Aug":
                intMonth = 8;break;
            case "Sep":
                intMonth = 9;break;
            case "Oct":
                intMonth = 10;break;
            case "Nov":
                intMonth = 11;break;
            case "Dec":
                intMonth = 12;break;
        }

        return intMonth;
    }
    public static String getTodaydate()
    {
        return new SimpleDateFormat(DATE_PATTERN_CURRENT_DATE_LOG, new Locale("en")).format(new Date());
    }
    public static String getNextweekdate()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);
        Date nextWeekDate = calendar.getTime();
        return new SimpleDateFormat(DATE_PATTERN_CURRENT_DATE_LOG, new Locale("en")).format(nextWeekDate);
    }
}
