package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeeksBetweenController {
    @GetMapping(value = "/weeksBetween", params = {"startDate", "endDate"})
    public String weeksBetween(@RequestParam String startDate, @RequestParam String endDate ) {
        String daysBetween = DaysBetweenController.calculateDays(startDate,endDate);
        if (daysBetween.contains("format")) {
            return daysBetween;
        }
        long daysBet = Long.parseLong(daysBetween);
        long weeksBet = daysBet/7;
        return "Full weeks between " + startDate + " to " + endDate + " is " + weeksBet;
    }

    @GetMapping(value = "/weeksBetween/withTimezones", params = {"startDate", "endDate"})
    public String weeksBetweenTimezones(@RequestParam String startDate, @RequestParam String endDate ) {
        startDate = startDate.replace(" ", "+");
        endDate = endDate.replace(" ", "+");
        String daysBetween = DaysBetweenController.calculateDaysWithTimezones(startDate,endDate);
        if (daysBetween.contains("format")) {
            return daysBetween;
        }
        long daysBet = Long.parseLong(daysBetween);
        long weeksBet = daysBet/7;
        return "Full weeks between " + startDate + " to " + endDate + " is " + weeksBet;
    }

}
