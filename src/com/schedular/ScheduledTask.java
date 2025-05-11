package com.schedular;

import java.util.Date;

/**
 * This class represents a task that is scheduled to run at a specific time.
 */
public class ScheduledTask implements ExecutableTask {
    private final String command;
    private final long scheduledTime;
    private boolean isCompleted = false;

    public ScheduledTask(String command, long scheduledTime) {
        this.command = command;
        this.scheduledTime = scheduledTime;
    }

    @Override
    public Date scheduledTime() {
        return new Date(scheduledTime);
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.ONCE;
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public void execute(){

        Executors.getExecutor().execute(this.command);
        // Mark the task as completed, once executed as it is a one-time task
        this.isCompleted = true;
    }
}
