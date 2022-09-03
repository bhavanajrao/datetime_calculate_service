package com.datetimeservice.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@RestController
public class DaysBetweenController implements ErrorController {
    static Logger logger = LoggerFactory.getLogger(DaysBetweenController.class);
    @GetMapping(value = "/daysBetween", params = {"startDate", "endDate"})
    public String daysBetween(@RequestParam String startDate, @RequestParam String endDate ) {
        logger.info("Calculating days between startDate and endDate");
        logger.info("The startDate is " + startDate);
        logger.info("The endDate is "+ endDate);
        String daysBetweenDates = calculateDays(startDate,endDate);
        logger.info("The days between "+ startDate + " and endDate is " + daysBetweenDates);
        if (daysBetweenDates.contains("format")) {
            return daysBetweenDates;
        }
        return "The total number of days between " + startDate + " and " + endDate+ " is " + daysBetweenDates ;
    }

    @GetMapping(value = "/daysBetween/withTimezones", params = {"startDate", "endDate"})
    public String daysBetweenTimezones(@RequestParam String startDate, @RequestParam String endDate) {
        logger.info("Calculating days between startDate and endDate with timezone offset");
        startDate=startDate.replace(" ", "+");
        endDate=endDate.replace(" ", "+");
        logger.info("The startDate is " + startDate);
        logger.info("The endDate is "+ endDate);
        String daysBetweenDatesTimezone = calculateDaysWithTimezones(startDate,endDate);
        logger.info("The days between "+ startDate + " and endDate is " + daysBetweenDatesTimezone);
        if (daysBetweenDatesTimezone.contains("format")) {
            return daysBetweenDatesTimezone;
        }
        return "The total number of days between " + startDate + " and " + endDate+ " is " + daysBetweenDatesTimezone ;
    }


    @GetMapping("/error")
    public String errorPage() {
        logger.info("Invalid URL was typed");
        return "Please enter the URI in {hostname}:{port}/daysBetween?date1={startDate}&date2={endDate} format where in " +
                "startDate and endDate is in dd-MM-yyyyTHHmm:ss format";
    }

    public static String calculateDaysWithTimezones(String startDate, String endDate) {
        logger.info("Calculating number of Days between  " + startDate + " and " + endDate);
        DateTimeFormatter zdtf = DateTimeFormatter.ISO_DATE_TIME;
        logger.info("setting the date and time format to be yyyy-mm-ddThh:mm:ssZ where Z is the timezone offset");
        long daysBetween=0;

        try {
            ZonedDateTime date1 = ZonedDateTime.parse(startDate,zdtf);
            ZonedDateTime date2 = ZonedDateTime.parse(endDate,zdtf);
            daysBetween = Duration.between(date1, date2).toDays();
            logger.info("The daysbetween is " + daysBetween);
            if (daysBetween < 0) {
                logger.info("The days between calculated is less than 0 hence switching the dates");
                daysBetween = Duration.between(date2,date1).toDays();
            }

            logger.info("Days: " + daysBetween);

        }
        catch(DateTimeParseException de) {
            logger.error("There was a DateTime format parsing exception ", de);
            return "Please input the Dates in yyyy-mm-ddThh:mm:ss+/-hh:mm format (For egs: 2021-12-017T06:40:31+01:30 or 2021-12-017T06:40:31-01:30)";
        }
        return String.valueOf(daysBetween);
    }

    public static String calculateDays(String startDate, String endDate){
        logger.info("Calculating number of Days between  " + startDate + " and " + endDate);
        long daysBetween=0;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        logger.info("setting the date and time format to be yyyy-mm-ddThh:mm:ss");
        try {
            LocalDateTime date1 = LocalDate.parse(startDate, dtf).atStartOfDay();
            LocalDateTime date2 = LocalDate.parse(endDate, dtf).atStartOfDay();
            daysBetween = Duration.between(date1, date2).toDays();
            if (daysBetween < 0) {
                daysBetween = Duration.between(date2,date1).toDays();
            }

            logger.info("Days: " + daysBetween);

        }catch(DateTimeParseException de) {
            logger.error("There was a DateTime format parsing exception ", de);
            return "Please input the Dates in yyyy-mm-ddThh:mm:ss format (For egs: 2021-12-017T06:40:31)";
        }
        return String.valueOf(daysBetween);
    }

}
