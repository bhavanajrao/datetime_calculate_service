package com.datetimeservice.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DateTimeException;

@RestController
public class DaysBetweenConvertedToController {
    long  count;
    Logger logger = LoggerFactory.getLogger(DaysBetweenConvertedToController.class);
    @GetMapping(value = "/daysBetween/convertedTo" , params = {"startDate", "endDate" , "convertTo"})
    public String convertTo(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String convertTo) {
        logger.info("Converting output of Number of days between startDate "+ startDate + " and endDate " + endDate + " to "+ convertTo);
        logger.info("The startDate is " + startDate);
        logger.info("The endDate is "+ endDate);
        logger.info("Convert the days between startDate " + startDate + " and endDate " + endDate + " to " + convertTo);
        long days=0; String difference;
        try {
            logger.info("Calculating days between startDate and endDate");
             String daysBet = DaysBetweenController.calculateDays(startDate,endDate);
             logger.info("The days between startDate and endDate is " + daysBet);
             if (daysBet.contains("format")) {
                 logger.info("The date was not is right format, the format should be yyyy-mm-ddThh:mm:ss");
                return daysBet;
             }
             days = Long.parseLong(daysBet);
             logger.info("Calculate " + convertTo + " in the " + days + " days ");
             difference = calculate(days,convertTo);
             logger.info("Number of days between " + startDate + " to " + endDate + " is " + days + " which is converted to " + difference + " " + convertTo);
        }
        catch (DateTimeException de) {
            logger.error("There was a DateTime format parsing exception" , de);
            return "Please enter Date in yyyy-MM-ddTHH:mm:ss format";
        }
        if (difference.contains("convertTo")) {
            return difference;
        } else
            return "Number of days between " + startDate + " to " + endDate + " is " + days + " which is converted to " + difference + " " + convertTo ;
    }

    @GetMapping(value = "/daysBetween/withTimezones/convertedTo", params= {"startDate", "endDate", "convertTo"})
    public String withTimezonesConvertTo(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String convertTo) {
        long  days=0; String difference;
        startDate=startDate.replace(" ", "+");
        endDate=endDate.replace(" ", "+");
        logger.info("Converting output of Number of days between startDate "+ startDate + " and endDate " + endDate + " to "+ convertTo);
        logger.info("The startDate is " + startDate);
        logger.info("The endDate is "+ endDate);
        logger.info("Convert the days between startDate " + startDate + " and endDate " + endDate + " to " + convertTo);
        try {
            logger.info("Calculating days between startDate and endDate");
            String daysBet=DaysBetweenController.calculateDaysWithTimezones(startDate,endDate);
            logger.info("The days between startDate and endDate is " + daysBet);
            if (daysBet.contains("format")) {
                logger.info("The date was not is right format, the format should be yyyy-mm-ddThh:mm:ss+/-hh:mm");
                return daysBet;
            }
            days = Long.parseLong(daysBet);
            logger.info("Calculate " + convertTo + " in the " + days + " days ");
            difference = calculate(days, convertTo);
            logger.info("Number of days between " + startDate + " to " + endDate + " is " + days + " which is converted to " + difference + " " + convertTo);
        }catch (DateTimeException de) {
            logger.error("There was a DateTime format parsing exception" , de);
            return "Please input the Dates in yyyy-mm-ddThh:mm:ss+/-hh:mm format (For egs: 2021-12-017T06:40:31+01:30 or 2021-12-017T06:40:31-01:30)";
        }if (difference.contains("convertTo")) {
            return difference;
        } else {
            return "Number of days between " + startDate + " to " + endDate + " is " + days + " which is converted to " + difference + " " + convertTo;
        }
    }

    public String calculate(long days, String convertTo) {
        count=0;
        logger.info("Calculating " + convertTo + " in the " + days + " days ");
        if (convertTo.equals("seconds")) {
            count = days * 24 * 60 * 60;

        } else if (convertTo.equals("minutes")) {
            count = days * 24 * 60;
        } else if (convertTo.equals("hours")) {
            count = days * 24;
        } else if (convertTo.equals("years")) {
            count = days / 365;
        }else {
            logger.error("The converTo param was not passed properly");
            return "Please give the convertTo value as seconds, minutes, hours or years";
        }
        return String.valueOf(count);
    }
}
