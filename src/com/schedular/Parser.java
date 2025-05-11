package com.schedular;

/**
 * The interface for Parser's to match the pattern and create a Task.
 * <p>
 * Implementations of this interface can have different type of Patterns to Parse the command.
 * </p>
 * example @see DateTimeParser
 */
public interface Parser {

    /**
     * Apply the given command with their Pattern and returns a ScheduledTask.
     *
     * @param command the command to parse
     * @return a ScheduledTask if the command matches the parser's pattern, null otherwise
     * @throws ParserException if an error occurs during parsing
     */
    ExecutableTask apply(String command) throws ParserException;
}
