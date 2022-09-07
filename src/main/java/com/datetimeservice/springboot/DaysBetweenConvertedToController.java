package com.datetimeservice.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DateTimeException;

@RestController
public class DaysBetweenConvertedToController {
    Logger logger = LoggerFactory.getLogger(DaysBetweenConvertedToController.class);

    @GetMapping(value = "/daysBetween/convertedTo" , params = {"startDate", "endDate" , "convertTo"})
    public String convertTo(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String convertTo) {
        long daysBet=0,difference;
        logger.info("The startDate is " + startDate);
        logger.info("The endDate is "+ endDate);
        if(convertTo.equals("seconds") || convertTo.equals("minutes") || convertTo.equals("hours") || convertTo.equals("years")) {
            logger.info("Convert the days between startDate " + startDate + " and endDate " + endDate + " to " + convertTo);
            try {
                DateTimeServiceUtils.setDateTimeFormat(startDate, endDate);
                logger.info("Calculating days between startDate and endDate");
                daysBet = DateTimeServiceUtils.calculateDays(DateTimeServiceUtils.ldate1, DateTimeServiceUtils.ldate2);
                logger.info("The days between startDate and endDate is " + daysBet);
                logger.info("Calculate Number of" + convertTo + " in the " + daysBet + " days ");
                difference = DateTimeServiceUtils.calculateConvertTo(daysBet, convertTo);
                logger.info("Number of days between " + startDate + " to " + endDate + " is " + daysBet + " which is converted to " + difference + " " + convertTo);
            } catch (DateTimeException de) {
                logger.error("There was a DateTime format parsing exception", de);
                return "Please enter Date in yyyy-MM-ddTHH:mm:ss format";
            }
            return "Number of days between " + startDate + " to " + endDate + " is " + daysBet + " which is converted to " + difference + " " + convertTo;
        }else {
            logger.error("The convertTo param was not passed properly");
            return "Please give the convertTo value as seconds, minutes, hours or years";
            }
        }

    @GetMapping(value = "/daysBetween/withTimezones/convertedTo", params= {"startDate", "endDate", "convertTo"})
    public String withTimezonesConvertTo(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String convertTo) {
        long daysBet = 0, difference;
        startDate = startDate.replace(" ", "+");
        endDate = endDate.replace(" ", "+");
        logger.info("The startDate is " + startDate);
        logger.info("The endDate is " + endDate);
        if (convertTo.equals("seconds") || convertTo.equals("minutes") || convertTo.equals("hours") || convertTo.equals("years")) {
            logger.info("Convert the days between startDate " + startDate + " and endDate " + endDate + " to " + convertTo);
            try {
                logger.info("Calculating days between startDate and endDate");
                DateTimeServiceUtils.setDateTimeZoneFormat(startDate, endDate);
                daysBet = DateTimeServiceUtils.calculateDaysWithTimezones(DateTimeServiceUtils.zdate1, DateTimeServiceUtils.zdate2);
                logger.info("The days between startDate and endDate is " + daysBet);
                logger.info("Calculate Number of" + convertTo + " in the " + daysBet + " days ");
                difference = DateTimeServiceUtils.calculateConvertTo(daysBet, convertTo);
                logger.info("Number of days between " + startDate + " to " + endDate + " is " + daysBet + " which is converted to " + difference + " " + convertTo);
            } catch (DateTimeException de) {
                logger.error("There was a DateTime format parsing exception", de);
                return "Please input the Dates in yyyy-mm-ddThh:mm:ss+/-hh:mm format (For egs: 2021-12-017T06:40:31+01:30 or 2021-12-017T06:40:31-01:30)";
            }
            return "Number of days between " + startDate + " to " + endDate + " is " + daysBet + " which is converted to " + difference + " " + convertTo;
        } else {
            logger.error("The convertTo param was not passed properly");
            return "Please give the convertTo value as seconds, minutes, hours or years";
        }
    }
}
