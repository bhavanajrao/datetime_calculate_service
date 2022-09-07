package com.datetimeservice.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DateTimeException;

@RestController
public class WeeksBetweenController {

    Logger logger = LoggerFactory.getLogger(WeeksBetweenController.class);
    @GetMapping(value = "/weeksBetween", params = {"startDate", "endDate"})
    public String weeksBetween(@RequestParam String startDate, @RequestParam String endDate ) {
        long daysBetween=0;
        logger.info("Calculating full weeks between startDate and endDate");
        logger.info("The startDate is " + startDate);
        logger.info("The endDate is "+ endDate);
        try {
            DateTimeServiceUtils.setDateTimeFormat(startDate, endDate);
            daysBetween = DateTimeServiceUtils.calculateDays(DateTimeServiceUtils.ldate1, DateTimeServiceUtils.ldate2);
            logger.info("The days between " + startDate + " and endDate is " + daysBetween);
        }catch (DateTimeException de) {
            logger.error("There was a DateTime format parsing exception ", de);
            return "Please input the Dates in yyyy-mm-ddThh:mm:ss format (For egs: 2021-12-017T06:40:31)" + de.getMessage();
        }
        long weeksBet = daysBetween/7;
        logger.info("Full weeks between "+ startDate + " and endDate is " + weeksBet);
        return "Full weeks between " + startDate + " to " + endDate + " is " + weeksBet;
    }

    @GetMapping(value = "/weeksBetween/withTimezones", params = {"startDate", "endDate"})
    public String weeksBetweenTimezones(@RequestParam String startDate, @RequestParam String endDate ) {
        long daysBetween=0;
        logger.info("Calculating full weeks between startDate and endDate with timezone offset");
        startDate = startDate.replace(" ", "+");
        endDate = endDate.replace(" ", "+");
        logger.info("The startDate is " + startDate);
        logger.info("The endDate is "+ endDate);
        try {
            DateTimeServiceUtils.setDateTimeZoneFormat(startDate, endDate);
            daysBetween = DateTimeServiceUtils.calculateDaysWithTimezones(DateTimeServiceUtils.zdate1, DateTimeServiceUtils.zdate2);
            logger.info("The days between " + startDate + " and endDate is " + daysBetween);
        }catch (DateTimeException de) {
            logger.error("There was a DateTime format parsing exception ", de);
            return "Please input the Dates in yyyy-mm-ddThh:mm:ss[+|-]hh:mm format (For egs: 2021-12-017T06:40:31+01:30 or 2021-12-017T06:40:31-01:30)" + de.getMessage();
        }
        long weeksBet = daysBetween/7;
        logger.info("Full weeks between "+ startDate + " and endDate is " + weeksBet);
        return "Full weeks between " + startDate + " to " + endDate + " is " + weeksBet;
    }

}
