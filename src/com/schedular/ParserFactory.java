package com.schedular;

import java.util.List;

/**
 * Parser Factory to create tasks based on the configured pattern.
 * Class has the List of available Parser.
 */
public class ParserFactory {

    private static final List<Parser> parsers = List.of(
            new DateTimeParser(),
            new MinutesParser()
    );

    /**
     * This method will take the pattern and return the ExecutableTask.
     * It will try to parse the pattern with all available parsers.
     * If none of the parsers can parse the pattern, it will return null.
     *
     * @param pattern The pattern to be parsed
     * @return ExecutableTask or null if no parser can parse the pattern
     */
    static ExecutableTask createTask(String pattern) {
        for (Parser parser : parsers) {
            try {
                ExecutableTask task = parser.apply(pattern);
                if (task != null) {
                    return task;
                }
            } catch (ParserException e) {
                // Handle the exception if needed
                // For now, we just ignore it and try the next parser
                System.err.println("Failed to Parse the " + pattern + " with Exception: " + e.getMessage());
            }
        }
        return null;
    }
}
