package com.schedular;

import java.util.Date;

/**
 * This interface represents a task that can be executed.
 * It provides methods to get the scheduled time, task type, completion status, and to execute the task.
 */
public interface ExecutableTask {
    /**
     * Get the scheduled time of the task.
     * @return Date object representing the scheduled time.
     */
    Date scheduledTime();

    /**
     * Get the type of the task.
     * @return TaskType enum representing the type of the task.
     */
    TaskType getTaskType();

    /**
     * Check if the task is completed.
     * @return true if the task is completed, false otherwise.
     */
    boolean isCompleted();

    /**
     * Execute the task.
     * This method should contain the logic to execute the task.
     */
    void execute();
}
