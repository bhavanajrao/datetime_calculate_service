package com.example.springboot;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@RestController
public class WeekdaysBetweenController implements ErrorController {
    long weekDays;
    @GetMapping(value="/weekdaysBetween/withTimezones", params = {"startDate", "endDate"})
    public String weekdaysBetweenTimezones(@RequestParam String startDate, @RequestParam String endDate) {
        weekDays=0;
        try {
            DateTimeFormatter zdtf = DateTimeFormatter.ISO_DATE_TIME;
            startDate = startDate.replace(" ", "+");
            endDate = endDate.replace(" ", "+");

            String inputDate1 = startDate;
            String inputDate2 = endDate;
            Date d1, d2;
            d1 = Date.from(ZonedDateTime.parse(inputDate1, zdtf).toInstant());
            d2 = Date.from(ZonedDateTime.parse(inputDate2, zdtf).toInstant());
            weekDays = getWeekDays(d1, d2);
        } catch (DateTimeParseException de){
            return "Please input the Dates in yyyy-mm-ddThh:mm:ss+/-hh:mm format (For egs: 2021-12-017T06:40:31+01:30 or 2021-12-017T06:40:31-01:30)" ;
        }
        System.out.println("Number of weekdays between " + startDate + " and " + endDate+ " is " + weekDays);
        return "Number of weekdays between " + startDate + " and " + endDate+ " is " + weekDays;
    }
    @GetMapping(value = "/weekdaysBetween", params = {"startDate","endDate"})
    public String weekdaysBetween(@RequestParam String startDate, @RequestParam String endDate ) {
        weekDays=0;
        Date d1, d2;
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            d1 = Date.from(LocalDateTime.parse(startDate,dtf).atZone(ZoneId.systemDefault()).toInstant());
            d2= Date.from(LocalDateTime.parse(endDate,dtf).atZone(ZoneId.systemDefault()).toInstant());
            weekDays = getWeekDays(d1, d2);
        }catch(DateTimeParseException de) {
            return "The StartDate is " + startDate + " and endDate is " + endDate + " Please input the Dates in yyyy-mm-ddThh:mm:ss format (For egs: 2021-12-017T06:40:31)";
        }

        System.out.println("Number of weekdays between " + startDate + " and " + endDate + " is " + weekDays);
        return "Number of weekdays between " + startDate + " and " + endDate + " is " + weekDays;

    }

    public long getWeekDays(Date d1, Date d2) {
        try {
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(d1);

            Calendar endCal = Calendar.getInstance();
            endCal.setTime(d2);

            if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
                startCal.setTime(d2);
                endCal.setTime(d1);
            }

            do {
                //excluding start date
                startCal.add(Calendar.DAY_OF_MONTH, 1);
                if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                    ++weekDays;
                }
            } while (startCal.getTimeInMillis() < endCal.getTimeInMillis());

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return weekDays;
    }

}
