package com.schedular;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MinutesParser is responsible for parsing recurring task commands.
 * Checks if the input matches the pattern *<code>/&lt;minutes&gt; &lt;command&gt;</code>
 * and if matched creates a RecurringTask accordingly.
 */
public class MinutesParser implements Parser {

    private final Pattern pattern = Pattern.compile("^\\*/(\\d{1,4})\\s+(.+)$");

    public ExecutableTask apply(String interval) throws ParserException {

        boolean hasMatch = Pattern.matches(pattern.pattern(), interval);
        if (!hasMatch) {
            return null;
        }

        Matcher matcher = pattern.matcher(interval);

        if (matcher.matches()) {
            String minutes = matcher.group(1);
            String command = matcher.group(2);

            if( Integer.parseInt(minutes) < 1 || Integer.parseInt(minutes) > 1440) {
                throw new ParserException("Please provide a valid interval between 1 and 1440 minutes");
            }

            return new MinutesTask(Integer.parseInt(minutes), command);
        }
        return null;
    }
}
