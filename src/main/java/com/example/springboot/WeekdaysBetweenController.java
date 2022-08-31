package com.example.springboot;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RestController
public class WeekdaysBetweenController implements ErrorController {
    @GetMapping(value = "/weekdaysBetween", params = {"startDate","endDate"})
    public String weekdaysBetween(@RequestParam String startDate, @RequestParam String endDate ) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy:HH:mm:ss");
        long weekDays = 0;
        String inputDate1 = startDate;
        String inputDate2 = endDate;
        Date d1, d2;
        try {
            Calendar startCal = Calendar.getInstance();
            d1 =  sdf.parse(inputDate1);
            d2 =  sdf.parse(inputDate2);
            startCal.setTime(d1);

            Calendar endCal = Calendar.getInstance();
            endCal.setTime(d2);

            //Return 0 if start and end are the same
            if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
                return "No of weekdays between the dates are 0";
            }

            if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
                startCal.setTime(d2);
                endCal.setTime(d1);
            }

            do {
                //excluding start date
                startCal.add(Calendar.DAY_OF_MONTH, 1);
                if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                    ++weekDays;
                }
            } while (startCal.getTimeInMillis() < endCal.getTimeInMillis()); //excluding end date

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Number of weekdays between " + startDate + "and " + endDate+ "is " + weekDays);
        return "Number of weekdays between " + startDate + "and " + endDate+ "is " + weekDays;
    }

}
