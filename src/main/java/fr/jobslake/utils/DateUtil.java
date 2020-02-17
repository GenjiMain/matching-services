package fr.jobslake.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    public static final  String DATE_FORMAT = "ddMMyyyy" ;
    public static Date stringToDateFormat(String input) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT) ;
        return sdf.parse(input);
    }
    public static String dateToStringFormat(Date dateInput)
    {
        SimpleDateFormat formatter =  new SimpleDateFormat(DATE_FORMAT);
        return   formatter.format(dateInput) ;
    }
}
