package com.datetimeservice.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeServiceUtils {
    static Logger logger = LoggerFactory.getLogger(DateTimeServiceUtils.class);
    public static LocalDateTime ldate1;
    public static LocalDateTime ldate2;
    public static ZonedDateTime zdate1;
    public static ZonedDateTime zdate2;
    public static long calculateDays(LocalDateTime date1, LocalDateTime date2){
        long daysBetween=0;
        daysBetween = Duration.between(date1, date2).toDays();
            if (daysBetween < 0) {
                daysBetween = Duration.between(date2, date1).toDays();
                logger.info("Days: " + daysBetween);
            }
        return daysBetween;
    }

    public static long calculateDaysWithTimezones(ZonedDateTime date1, ZonedDateTime date2) {
        long daysBetween;
        daysBetween = Duration.between(date1, date2).toDays();
        if (daysBetween < 0) {
            logger.info("The days between calculated is less than 0 hence switching the dates");
            daysBetween = Math.abs(daysBetween);
        }logger.info("Days: " + daysBetween);
        return daysBetween;
    }

    public static void setDateTimeFormat(String startDate, String endDate) {
        logger.info("setting the date and time format to be yyyy-mm-ddThh:mm:ss");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        ldate1 = LocalDateTime.parse(startDate, dtf);
        ldate2 = LocalDateTime.parse(endDate, dtf);
    }

    public static void setDateTimeZoneFormat(String startDate, String endDate) {
        DateTimeFormatter zdtf = DateTimeFormatter.ISO_DATE_TIME;
        logger.info("setting the date and time format to be yyyy-mm-ddThh:mm:ssZ where Z is the timezone offset");
        zdate1 = ZonedDateTime.parse(startDate,zdtf);
        zdate2 = ZonedDateTime.parse(endDate,zdtf);
    }

    public static long calculateConvertTo(long days, String convertTo) {
        long count=0;
        logger.info("Calculating " + convertTo + " in the " + days + " days ");
        if (convertTo.equals("seconds")) {
            count = days * 24 * 60 * 60;

        } else if (convertTo.equals("minutes")) {
            count = days * 24 * 60;
        } else if (convertTo.equals("hours")) {
            count = days * 24;
        } else if (convertTo.equals("years")) {
            count = days / 365;
        }
        return count;
    }

    public static void calculateWeekdays() {
        long daysBetween = calculateDays(ldate1,ldate2);

    }

}
