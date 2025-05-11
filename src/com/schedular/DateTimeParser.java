package com.schedular;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * It is responsible for parsing a date string into a ScheduledTask object.
 * The command string should contain the date and time in the format "MM DD HH mm YYYY <command>".
 */
public class DateTimeParser implements Parser {

    private final Pattern pattern = Pattern.compile("^(\\d{1,2}) (\\d{1,2}) (\\d{1,2}) (\\d{2}) (\\d{4}) (.+)$");

    @Override
    public ExecutableTask apply(String command) throws ParserException {
        Matcher matcher = pattern.matcher(command);
        if (!matcher.matches()) {
            return null;
        }

        int minute = Integer.parseInt(matcher.group(1));
        int hour = Integer.parseInt(matcher.group(2));
        int day = Integer.parseInt(matcher.group(3));
        int month = Integer.parseInt(matcher.group(4));
        int year = Integer.parseInt(matcher.group(5));
        String shellCommand = matcher.group(6);

        validateDate(day, month, year);
        validateTime(hour, minute);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, minute, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return new ScheduledTask(shellCommand, calendar.getTimeInMillis());
    }

    private void validateDate(int day, int month, int year) throws ParserException {
        if (month < 1 || month > 12) {
            throw new ParserException("Invalid month: " + month);
        }
        if (day < 1 || day > daysInMonth(month, year)) {
            throw new ParserException("Invalid day: " + day);
        }
        if (year < 1900 || year > 2100) {
            throw new ParserException("Invalid year: " + year);
        }
    }

    private int daysInMonth(int month, int year) {
        return switch (month) {
            case 2 -> (isLeapYear(year)) ? 29 : 28; // February
            case 4, 6, 9, 11 -> // April, June, September, November
                    30;
            default -> // All other months
                    31;
        };
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
    }

    private void validateTime(int hour, int minute) throws ParserException {
        if (hour < 0 || hour >= 24 || minute < 0 || minute >= 60) {
            throw new ParserException("Invalid time: " + hour + ":" + minute);
        }
    }
}
