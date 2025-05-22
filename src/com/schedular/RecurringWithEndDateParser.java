package com.schedular;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecurringWithEndDateParser implements Parser {

    private final Pattern pattern = Pattern.compile("^\\*/(\\d{1,4})\\s+(\\d{1,2})\\s+(\\d{1,2})\\s+(\\d{1,2})\\s+(\\d{1,2})\\s+(\\d{4})\\s+(.+)$");

    @Override
    public ExecutableTask apply(String command) throws ParserException {
        Matcher matcher = pattern.matcher(command);

        if (!matcher.matches()) {
            return null;
        }

        try {
            String minutesStr = matcher.group(1);
            String endMonthStr = matcher.group(2);
            String endDayStr = matcher.group(3);
            String endHourStr = matcher.group(4);
            String endMinuteStr = matcher.group(5);
            String endYearStr = matcher.group(6);
            String shellCommand = matcher.group(7);

            int minutes = Integer.parseInt(minutesStr);
            int endMonth = Integer.parseInt(endMonthStr);
            int endDay = Integer.parseInt(endDayStr);
            int endHour = Integer.parseInt(endHourStr);
            int endMinute = Integer.parseInt(endMinuteStr);
            int endYear = Integer.parseInt(endYearStr);

            if (minutes < 1 || minutes > 1440) {
                throw new ParserException("Minutes must be between 1 and 1440.");
            }

            validateDateTimeComponents(endDay, endMonth, endYear, endHour, endMinute);

            Calendar calendar = Calendar.getInstance();
            calendar.set(endYear, endMonth - 1, endDay, endHour, endMinute, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            long endTimeMillis = calendar.getTimeInMillis();

            return new TerminatingRecurringTask(minutes, endTimeMillis, shellCommand);

        } catch (NumberFormatException e) {
            throw new ParserException("Error parsing number from command: " + e.getMessage(), e);
        }
    }

    private void validateDateTimeComponents(int day, int month, int year, int hour, int minute) throws ParserException {
        if (year < 1970 || year > 2100) {
            throw new ParserException("Year must be between 1970 and 2100.");
        }
        if (month < 1 || month > 12) {
            throw new ParserException("Month must be between 1 and 12.");
        }
        if (day < 1 || day > daysInMonth(month, year)) {
            throw new ParserException("Invalid day for the given month and year.");
        }
        if (hour < 0 || hour > 23) {
            throw new ParserException("Hour must be between 0 and 23.");
        }
        if (minute < 0 || minute > 59) {
            throw new ParserException("Minute must be between 0 and 59.");
        }
    }

    private int daysInMonth(int month, int year) {
        if (month == 2) {
            return isLeapYear(year) ? 29 : 28;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        } else {
            return 31;
        }
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}
