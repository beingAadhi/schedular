package com.schedular;

import java.util.List;

/**
 * The Processor interface defines a method for processing InputPath and returning a list of scheduled tasks.
 * You can Introduce any Processes for Text, CSV, or any other format.
 */
public interface Processor {

    /**
     * Processes the input at the given path and returns a list of scheduled tasks in the File.
     *
     * @param inputPath the path to the input file
     * @return a list of scheduled tasks
     * @throws Exception if an error occurs during processing
     */
    List<ExecutableTask> process(String inputPath) throws Exception;
}
