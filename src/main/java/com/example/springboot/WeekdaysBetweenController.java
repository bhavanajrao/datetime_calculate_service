package com.example.springboot;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;

@RestController
public class WeekdaysBetweenController implements ErrorController {
    long weekDays;
    Logger logger = LoggerFactory.getLogger(WeekdaysBetweenController.class);

    @GetMapping(value = "/weekdaysBetween", params = {"startDate","endDate"})
    public String weekdaysBetween(@RequestParam String startDate, @RequestParam String endDate ) {
        logger.info("Calculating weekdays between startDate and endDate");
        logger.info("The startDate is " + startDate);
        logger.info("The endDate is "+ endDate);
        weekDays=0;
        Date d1, d2;
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            logger.info("setting the date and time format to be yyyy-mm-ddThh:mm:ss ");
            d1 = Date.from(LocalDateTime.parse(startDate,dtf).atZone(ZoneId.systemDefault()).toInstant());
            d2= Date.from(LocalDateTime.parse(endDate,dtf).atZone(ZoneId.systemDefault()).toInstant());
            weekDays = getWeekDays(d1, d2);
        }catch(DateTimeParseException de) {
            logger.error("There was a DateTime format parsing exception ", de);
            return "The StartDate is " + startDate + " and endDate is " + endDate + " Please input the Dates in yyyy-mm-ddThh:mm:ss format (For egs: 2021-12-017T06:40:31)";
        }

        logger.info("Number of weekdays between " + startDate + " and " + endDate + " is " + weekDays);
        return "Number of weekdays between " + startDate + " and " + endDate + " is " + weekDays;

    }

    @GetMapping(value="/weekdaysBetween/withTimezones", params = {"startDate", "endDate"})
    public String weekdaysBetweenTimezones(@RequestParam String startDate, @RequestParam String endDate) {
        logger.info("Calculating weekdays between startDate and endDate with timezone offset");
        weekDays=0;
        startDate = startDate.replace(" ", "+");
        endDate = endDate.replace(" ", "+");
        logger.info("The startDate is " + startDate);
        logger.info("The endDate is "+ endDate);
        try {
            DateTimeFormatter zdtf = DateTimeFormatter.ISO_DATE_TIME;
            logger.info("setting the date and time format to be yyyy-mm-ddThh:mm:ssZ where Z is the timezone offset");
            Date d1, d2;
            d1 = Date.from(ZonedDateTime.parse(startDate, zdtf).toInstant());
            d2 = Date.from(ZonedDateTime.parse(endDate, zdtf).toInstant());
            weekDays = getWeekDays(d1, d2);
        } catch (DateTimeParseException de){
            logger.error("There was a DateTime format parsing exception " + de);
            return "Please input the Dates in yyyy-mm-ddThh:mm:ss+/-hh:mm format (For egs: 2021-12-017T06:40:31+01:30 or 2021-12-017T06:40:31-01:30)" ;
        }
        logger.info("Number of weekdays between " + startDate + " and " + endDate+ " is " + weekDays);
        return "Number of weekdays between " + startDate + " and " + endDate+ " is " + weekDays;
    }

    public long getWeekDays(Date d1, Date d2) {
        try {
                Calendar startCal = Calendar.getInstance();
                startCal.setTime(d1);

                Calendar endCal = Calendar.getInstance();
                endCal.setTime(d2);

            if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
                logger.info("endDate is smaller than startDate hence swapping the dates");
                startCal.setTime(d2);
                endCal.setTime(d1);
            }

            do {
                startCal.add(Calendar.DAY_OF_MONTH, 1);
                if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                    ++weekDays;
                }
            } while (startCal.getTimeInMillis() < endCal.getTimeInMillis());

        }
        catch (Exception e) {
            logger.error("There was an exception while calulating weekdays ", e);
            e.printStackTrace();
        }
        return weekDays;
    }

}
