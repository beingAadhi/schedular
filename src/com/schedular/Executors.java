package com.schedular;


/*
 * This class provide the available executor's currently default to CommandLineExecutor
 */
public class Executors {

    private static  Executor defaultExecutor = new CommandLineExecutor();

    // Currently, this class only provides a default executor. You can extend this class to add more executors with type.
    public static Executor getExecutor() {
        return defaultExecutor;
    }
}
