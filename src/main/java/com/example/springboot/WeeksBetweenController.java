package com.example.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeeksBetweenController {

    Logger logger = LoggerFactory.getLogger(WeeksBetweenController.class);
    @GetMapping(value = "/weeksBetween", params = {"startDate", "endDate"})
    public String weeksBetween(@RequestParam String startDate, @RequestParam String endDate ) {
        logger.info("Calculating full weeks between startDate and endDate");
        logger.info("The startDate is " + startDate);
        logger.info("The endDate is "+ endDate);
        String daysBetween = DaysBetweenController.calculateDays(startDate,endDate);
        logger.info("The days between "+ startDate + " and endDate is " + daysBetween);
        if (daysBetween.contains("format")) {
            return daysBetween;
        }
        long daysBet = Long.parseLong(daysBetween);
        long weeksBet = daysBet/7;
        logger.info("Full weeks between "+ startDate + " and endDate is " + weeksBet);
        return "Full weeks between " + startDate + " to " + endDate + " is " + weeksBet;
    }

    @GetMapping(value = "/weeksBetween/withTimezones", params = {"startDate", "endDate"})
    public String weeksBetweenTimezones(@RequestParam String startDate, @RequestParam String endDate ) {
        logger.info("Calculating full weeks between startDate and endDate with timezone offset");
        startDate = startDate.replace(" ", "+");
        endDate = endDate.replace(" ", "+");
        logger.info("The startDate is " + startDate);
        logger.info("The endDate is "+ endDate);
        String daysBetween = DaysBetweenController.calculateDaysWithTimezones(startDate,endDate);
        logger.info("The days between "+ startDate + " and endDate is " + daysBetween);
        if (daysBetween.contains("format")) {
            return daysBetween;
        }
        long daysBet = Long.parseLong(daysBetween);
        long weeksBet = daysBet/7;
        logger.info("Full weeks between "+ startDate + " and endDate is " + weeksBet);
        return "Full weeks between " + startDate + " to " + endDate + " is " + weeksBet;
    }

}
