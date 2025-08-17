package com.scheduler.util;

import com.scheduler.enums.Periodic;
import com.scheduler.enums.Week;
import org.springframework.stereotype.Component;

import java.time.Period;

@Component
public class CronGenerator {
    public String buildCronExpression(String type, int hour, int minute, String dayOfWeek, int dayOfMonth,int month) {
        final String pattern= String.valueOf(Periodic.valueOf(type.toUpperCase()));
        return switch (pattern) {
            case "DAY" -> String.format("0 %d %d * * ?", minute, hour);
            case "WEEK" ->
                    String.format("0 %d %d ? * %d", minute, hour, Integer.parseInt(String.valueOf(Week.valueOf(dayOfWeek))));  // 1 = Sunday, 2 = Monday, ...
            case "MONTH" -> String.format("0 %d %d %d * ?", minute, hour, dayOfMonth);
            case "YEAR" ->
                    String.format("0 %d %d %d %d ?", minute, hour, dayOfMonth, month); // January hardcoded for example
            default -> throw new IllegalArgumentException("Invalid type: " + type);
        };
    }
}
