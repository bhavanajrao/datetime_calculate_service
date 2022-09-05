package com.datetimeservice.springboot;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaysBetweenConvertedToControllerTest {
    static Logger logger = LoggerFactory.getLogger(DaysBetweenConvertedToControllerTest.class);
    @Test
    public void testDaysBetweenConvertedToService() {
        DaysBetweenConvertedToController daysBetweenConvertedToController = new DaysBetweenConvertedToController();
        String result = daysBetweenConvertedToController.convertTo("2021-12-01T06:40:31", "2022-03-06T07:40:31", "seconds");
        String expected = "Number of days between 2021-12-01T06:40:31 to 2022-03-06T07:40:31 is 95 which is converted to 8208000 seconds";
        logger.info("Result is : " + result);
        logger.info("Expected is: "+ expected);
        Assert.assertEquals(expected,result);
    }

    @Test
    public void testDaysBetweenTimezonesConvertedToService() {
        DaysBetweenConvertedToController daysBetweenConvertedToController = new DaysBetweenConvertedToController();
        String result = daysBetweenConvertedToController.withTimezonesConvertTo("2021-12-01T06:40:31-05:30", "2022-03-06T07:40:31+05:30", "seconds");
        String expected = "Number of days between 2021-12-01T06:40:31-05:30 to 2022-03-06T07:40:31+05:30 is 94 which is converted to 8121600 seconds";
        logger.info("Result is : " + result);
        logger.info("Expected is: "+ expected);
        Assert.assertEquals(expected,result);
    }

}
