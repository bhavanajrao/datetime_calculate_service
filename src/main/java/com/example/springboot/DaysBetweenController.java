package com.example.springboot;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
public class DaysBetweenController implements ErrorController {
    @GetMapping(value = "/daysBetween", params = {"startDate", "endDate"})
    public String daysBetween(@RequestParam String startDate, @RequestParam String endDate ) {
        String daysBetweenDates = calculateDays(startDate,endDate);
        return "The total number of days between " + startDate + "and " + endDate+ "is " + daysBetweenDates ;
    }

    @GetMapping(value = "/daysBetween/convertedTo" , params = {"startDate", "endDate" , "convertTo"})
    public String convertTo(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String convertTo) {
        long difference_In_Time=0, difference=0, days ;

        try {
            String daysBet = calculateDays(startDate,endDate);
            days = Long.parseLong(daysBet);
            System.out.println("Number of days between startDate and endDate is " + days);
            if (convertTo.equals("seconds")) {
                difference = days * 24 * 60 * 60;

            } else if (convertTo.equals("minutes")) {
                difference = days * 24 * 60;
            } else if (convertTo.equals("hours")) {
                difference = days * 24;
            } else if (convertTo.equals("years")) {
                difference = days / 365;
            }else {
                return "Please give the convertTo value as seconds, minutes, hours or years";
            }
        }
        catch (DateTimeException de) {
            return "Please enter Date in dd-MM-yyyy:HH:mm:ss format";
        }
        return "Number of days between " + startDate + " to " + endDate + " is " + days + " which is converted to " + difference + " " +convertTo ;
    }

    @GetMapping("/error")
    public String errorPage() {
        return "Please enter the URI in {hostname}:{port}/daysBetween?date1={startDate}&date2={endDate} format where in " +
                "startDate and endDate is in dd-MM-yyyy:HH:mm:ss format";
    }

    public static String calculateDays(String startDate, String endDate){
        long daysBetween=0;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy:HH:mm:ss");

        String inputDate1 = startDate;
        String inputDate2 = endDate;

        try {

            LocalDateTime date1 = LocalDate.parse(inputDate1, dtf).atStartOfDay();
            LocalDateTime date2 = LocalDate.parse(inputDate2, dtf).atStartOfDay();
            daysBetween = Duration.between(date1, date2).toDays();
            if (daysBetween < 0) {
                daysBetween = Duration.between(date2,date1).toDays();
            }

            System.out.println ("Days: " + daysBetween);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    return String.valueOf(daysBetween);
    }

}
