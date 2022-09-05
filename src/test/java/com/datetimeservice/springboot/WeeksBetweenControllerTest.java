package com.datetimeservice.springboot;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeeksBetweenControllerTest {
    static Logger logger = LoggerFactory.getLogger(WeeksBetweenControllerTest.class);
    @Test
    public void testWeeksBetweenService() {
        WeeksBetweenController weeksBetweenController = new WeeksBetweenController();
        String result = weeksBetweenController.weeksBetween("2021-12-01T06:40:31", "2022-03-06T07:40:31");
        String expected = "Full weeks between 2021-12-01T06:40:31 to 2022-03-06T07:40:31 is 13";
        logger.info("Result is : " + result);
        logger.info("Expected is: "+ expected);
        Assert.assertEquals(expected,result);
    }

    @Test
    public void testWeeksBetweenTimezonesService() {
        WeeksBetweenController weeksBetweenController = new WeeksBetweenController();
        String result = weeksBetweenController.weeksBetweenTimezones("2021-12-01T06:40:31-05:30", "2022-03-07T06:40:31+05:30");
        String expected = "Full weeks between 2021-12-01T06:40:31-05:30 to 2022-03-07T06:40:31+05:30 is 13";
        logger.info("Result is : " + result);
        logger.info("Expected is: "+ expected);
        Assert.assertEquals(expected,result);
    }

}
