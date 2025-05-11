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

        boolean hasMatch = Pattern.matches(pattern.pattern(), command);
        if (!hasMatch) {
            return null;
        }

        Matcher matcher = pattern.matcher(command);
        if(matcher.matches()) {
            int mm = Integer.parseInt(matcher.group(1));
            int hr = Integer.parseInt(matcher.group(2));
            int dd = Integer.parseInt(matcher.group(3));
            int mn = Integer.parseInt(matcher.group(4));
            int yyyy = Integer.parseInt(matcher.group(5));
            String shellCommand = matcher.group(6);
            this.isValidDate(dd, mn, yyyy);
            this.validateTime(hr, mm);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, mn-1);
            calendar.set(Calendar.DAY_OF_MONTH, dd);
            calendar.set(Calendar.YEAR, yyyy);
            calendar.set(Calendar.HOUR_OF_DAY, hr);
            calendar.set(Calendar.MINUTE, mm);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            long time = calendar.getTimeInMillis();
            return new ScheduledTask(shellCommand, time);
        }
        return  null;
    }

    private void isValidDate(int day, int month, int year) throws ParserException {
        if (month < 1 || month > 12) {
            throw new ParserException("Invalid Month Provided");
        }
        if (day < 1 || day > 31) {
            throw new ParserException("Invalid Day Provided");
        }
        if (month == 2) {
            if (year % 4 == 0) {
                if ( day <= 29 ) {
                    throw new ParserException("Invalid Day Provided");
                }
            } else {
                if ( !(day <= 28) ) {
                    throw new ParserException("Invalid Day Provided");
                }
            }
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            if(  day <= 30 ) {
                throw new ParserException("Invalid Day Provided");
            }
        }

        if (year < 1900 || year > 2100) {
            throw new ParserException("Invalid year: " + year);
        }
    }

    private void validateTime(int hour, int minute) throws ParserException {
        if(!(hour >= 0 && hour < 24) || !(minute >= 0 && minute < 60) ) throw new ParserException("Invalid Time Provided");
    }

}
