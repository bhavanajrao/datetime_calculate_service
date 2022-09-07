package com.datetimeservice.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeParseException;

@RestController
public class DaysBetweenController implements ErrorController {
    static Logger logger = LoggerFactory.getLogger(DaysBetweenController.class);
    @GetMapping(value = "/daysBetween", params = {"startDate", "endDate"})
    public String daysBetween(@RequestParam String startDate, @RequestParam String endDate ) {
        long daysBetweenDates;
        logger.info("Calculating days between startDate and endDate");
        logger.info("The startDate is " + startDate);
        logger.info("The endDate is "+ endDate);
        try {
            DateTimeServiceUtils.setDateTimeFormat(startDate,endDate);
            daysBetweenDates = DateTimeServiceUtils.calculateDays(DateTimeServiceUtils.ldate1, DateTimeServiceUtils.ldate2);
            logger.info("The days between " + startDate + " and endDate is " + daysBetweenDates);
        }catch(DateTimeParseException de) {
            logger.error("There was a DateTime format parsing exception ", de);
            return "Please input the Dates in yyyy-mm-ddThh:mm:ss format (For egs: 2021-12-017T06:40:31)" + de.getMessage();
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
        long daysBetweenDatesTimezone;
        try {
            DateTimeServiceUtils.setDateTimeZoneFormat(startDate,endDate);
            daysBetweenDatesTimezone = DateTimeServiceUtils.calculateDaysWithTimezones(DateTimeServiceUtils.zdate1, DateTimeServiceUtils.zdate2);
            logger.info("The days between "+ startDate + " and endDate is " + daysBetweenDatesTimezone);
        }catch(DateTimeParseException de) {
            logger.error("There was a DateTime format parsing exception ", de);
            return "Please input the Dates in yyyy-mm-ddThh:mm:ss[+|-]hh:mm format (For egs: 2021-12-017T06:40:31+01:30 or 2021-12-017T06:40:31-01:30)" + de.getMessage();
        }
        return "The total number of days between " + startDate + " and " + endDate+ " is " + daysBetweenDatesTimezone ;
    }

}
