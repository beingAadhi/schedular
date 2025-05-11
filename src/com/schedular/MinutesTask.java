package com.schedular;

import java.util.Date;

/*
 *This class represents a task that is scheduled to run at a fixed interval (in minutes).
 */
public class MinutesTask implements RecurringTask {
    private final String command;
    private final int delay;
    private TaskType taskType= TaskType.RECURRING;

    // this will always be false, since the task is recurring
    private boolean isCompleted = false;

    public MinutesTask(int delay, String command) {
        this.command = command;
        this.delay = delay;
    }

    @Override
    public int getDelay() {
        return delay;
    }

    @Override
    public Date scheduledTime() {
        return new Date(System.currentTimeMillis() + delay);
    }

    @Override
    public TaskType getTaskType() {
        return taskType;
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public void execute() {
       Executors.getExecutor().execute(this.command);
    }
}
