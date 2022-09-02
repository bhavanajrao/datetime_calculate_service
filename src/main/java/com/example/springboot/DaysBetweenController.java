package com.example.springboot;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@RestController
public class DaysBetweenController implements ErrorController {


    @GetMapping(value = "/daysBetween", params = {"startDate", "endDate"})
    public String daysBetween(@RequestParam String startDate, @RequestParam String endDate ) {
        String daysBetweenDates = calculateDays(startDate,endDate);
        return "The total number of days between " + startDate + " and " + endDate+ " is " + daysBetweenDates ;
    }

    @GetMapping(value = "/daysBetween/withTimezones", params = {"startDate", "endDate"})
    public String daysBetweenTimezones(@RequestParam String startDate, @RequestParam String endDate) {
        startDate=startDate.replace(" ", "+");
        endDate=endDate.replace(" ", "+");
        String daysBetweenDatesTimezone = calculateDaysWithTimezones(startDate,endDate);
        return "The total number of days between " + startDate + " and " + endDate+ " is " + daysBetweenDatesTimezone ;
    }


    @GetMapping("/error")
    public String errorPage() {
        return "Please enter the URI in {hostname}:{port}/daysBetween?date1={startDate}&date2={endDate} format where in " +
                "startDate and endDate is in dd-MM-yyyyTHHmm:ss format";
    }

    public static String calculateDaysWithTimezones(String startDate, String endDate) {
        DateTimeFormatter zdtf = DateTimeFormatter.ISO_DATE_TIME;
        long daysBetween=0;

        try {
            ZonedDateTime date1 = ZonedDateTime.parse(startDate,zdtf);
            ZonedDateTime date2 = ZonedDateTime.parse(endDate,zdtf);
            daysBetween = Duration.between(date1, date2).toDays();
            if (daysBetween < 0) {
                daysBetween = Duration.between(date2,date1).toDays();
            }

            System.out.println ("Days: " + daysBetween);

        }
        catch(DateTimeParseException de) {
            return "Please input the Dates in yyyy-mm-ddThh:mm:ss+/-hh:mm format (For egs: 2021-12-017T06:40:31+01:30 or 2021-12-017T06:40:31-01:30)";
        }
        return String.valueOf(daysBetween);
    }

    public static String calculateDays(String startDate, String endDate){
        long daysBetween=0;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        try {
            LocalDateTime date1 = LocalDate.parse(startDate, dtf).atStartOfDay();
            LocalDateTime date2 = LocalDate.parse(endDate, dtf).atStartOfDay();
            daysBetween = Duration.between(date1, date2).toDays();
            if (daysBetween < 0) {
                daysBetween = Duration.between(date2,date1).toDays();
            }

            System.out.println ("Days: " + daysBetween);

        }        catch(DateTimeParseException de) {
            return "Please input the Dates in yyyy-mm-ddThh:mm:ss format (For egs: 2021-12-017T06:40:31)";
        }
        return String.valueOf(daysBetween);
    }

}
