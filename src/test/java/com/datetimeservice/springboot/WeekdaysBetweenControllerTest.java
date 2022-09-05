package com.datetimeservice.springboot;

import org.junit.jupiter.api.Test;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeekdaysBetweenControllerTest {

    static Logger logger = LoggerFactory.getLogger(WeekdaysBetweenControllerTest.class);
    @Test
    public void testWeekdaysBetweenService() {
        WeekdaysBetweenController weekdaysBetweenController = new WeekdaysBetweenController();
        String result = weekdaysBetweenController.weekdaysBetween("2021-12-01T06:40:31", "2022-03-06T07:40:31");
        String expected = "Number of weekdays between 2021-12-01T06:40:31 and 2022-03-06T07:40:31 is 68";
        logger.info("Result is : " + result);
        logger.info("Expected is: "+ expected);
        Assert.assertEquals(expected,result);
    }

    @Test
    public void testWeekdaysBetweenTimezonesService() {
        WeekdaysBetweenController weekdaysBetweenController = new WeekdaysBetweenController();
        String result = weekdaysBetweenController.weekdaysBetweenTimezones("2021-12-01T06:40:31-05:30", "2022-03-07T06:40:31+05:30");
        String expected = "Number of weekdays between 2021-12-01T06:40:31-05:30 and 2022-03-07T06:40:31+05:30 is 68";
        logger.info("Result is : " + result);
        logger.info("Expected is: "+ expected);
        Assert.assertEquals(expected,result);
    }


}
