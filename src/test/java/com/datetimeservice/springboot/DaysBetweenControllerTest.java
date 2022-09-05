package com.datetimeservice.springboot;


import org.junit.jupiter.api.Test;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaysBetweenControllerTest {

    static Logger logger = LoggerFactory.getLogger(DaysBetweenControllerTest.class);

    @Test
    public void testDaysBetweenService() {
        DaysBetweenController daysBetweenController = new DaysBetweenController();
        String result = daysBetweenController.daysBetween("2021-12-01T06:40:31", "2022-03-01T06:40:31");
        logger.info("Result is :" + result);
        String expected = "The total number of days between 2021-12-01T06:40:31 and 2022-03-01T06:40:31 is 90";
        Assert.assertEquals(expected,result);
    }

    @Test
    public void testDaysBetweenTimezonesService() {
        DaysBetweenController daysBetweenController = new DaysBetweenController();
        String result = daysBetweenController.daysBetweenTimezones("2021-12-01T06:40:31-05:30", "2022-03-01T06:40:31+05:30");
        logger.info("Result is :" + result);
        String expected = "The total number of days between 2021-12-01T06:40:31-05:30 and 2022-03-01T06:40:31+05:30 is 89";
        Assert.assertEquals(expected,result);
    }


}
