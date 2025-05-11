package com.schedular;

/**
 * The interface for executing commands.
 * <p>
 * Implementations of this interface can provide different ways to execute the command.
 * </p>
 */
public interface Executor {

    /**
     * Just Execute the command and don't return anything.
     * Note: This method is not expected to throw any checked exceptions.
     *
     * @param command the command to execute
     */
   void execute(String command);

}
