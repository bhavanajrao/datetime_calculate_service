package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DateTimeException;

@RestController
public class DaysBetweenConvertedToController {
    long  count;
    public String calculate(long days, String convertTo) {
        count=0;
        System.out.println("Number of days between startDate and endDate is " + days);
        if (convertTo.equals("seconds")) {
            count = days * 24 * 60 * 60;

        } else if (convertTo.equals("minutes")) {
            count = days * 24 * 60;
        } else if (convertTo.equals("hours")) {
            count = days * 24;
        } else if (convertTo.equals("years")) {
            count = days / 365;
        }else {
            return "Please give the convertTo value as seconds, minutes, hours or years";
        }
        return String.valueOf(count);
    }

    @GetMapping(value = "/daysBetween/withTimezones/convertedTo", params= {"startDate", "endDate", "convertTo"})
    public String withTimezonesConvertTo(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String convertTo) {
        long  days=0; String difference;
        startDate=startDate.replace(" ", "+");
        endDate=endDate.replace(" ", "+");
        try {
            String daysBet=DaysBetweenController.calculateDaysWithTimezones(startDate,endDate);
            if (daysBet.contains("format")) {
                return daysBet;
            }
            days = Long.parseLong(daysBet);
            difference = calculate(days, convertTo);
        }catch (DateTimeException de) {
            return "Please input the Dates in yyyy-mm-ddThh:mm:ss+/-hh:mm format (For egs: 2021-12-017T06:40:31+01:30 or 2021-12-017T06:40:31-01:30)";
        }if (difference.contains("convertTo")) {
            return difference;
        } else {
            return "Number of days between " + startDate + " to " + endDate + " is " + days + " which is converted to " + difference + " " + convertTo;
        }
    }

    @GetMapping(value = "/daysBetween/convertedTo" , params = {"startDate", "endDate" , "convertTo"})
    public String convertTo(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String convertTo) {
        long days=0; String difference;
        try {
             String daysBet = DaysBetweenController.calculateDays(startDate,endDate);
            if (daysBet.contains("format")) {
                return daysBet;
            }
             days = Long.parseLong(daysBet);
             difference= calculate(days,convertTo);

        }
        catch (DateTimeException de) {
            return "Please enter Date in yyyy-MM-ddTHH:mm:ss format";
        }
        if (difference.contains("convertTo")) {
            return difference;
        } else
            return "Number of days between " + startDate + " to " + endDate + " is " + days + " which is converted to " + difference + " " + convertTo ;
    }
}
