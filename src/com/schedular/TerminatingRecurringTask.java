package com.schedular;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TerminatingRecurringTask implements RecurringTask {

    private final String command;
    private final int delay;
    private final long endTimeMillis;
    private TaskType taskType = TaskType.RECURRING;

    public TerminatingRecurringTask(int delay, long endTimeMillis, String command) {
        this.delay = delay;
        this.endTimeMillis = endTimeMillis;
        this.command = command;
    }

    @Override
    public int getDelay() {
        return delay;
    }

    @Override
    public java.util.Date scheduledTime() {
        return new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(delay));
    }

    @Override
    public TaskType getTaskType() {
        return taskType;
    }

    @Override
    public boolean isCompleted() {
        return System.currentTimeMillis() >= endTimeMillis;
    }

    @Override
    public void execute() {
        Executors.getExecutor().execute(this.command);
    }
}
