package com.schedular;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Schedular class to manage the scheduling of tasks.
 * It uses a ScheduledExecutorService to schedule one-time and recurring tasks.
 */
public class Schedular {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    private final List<ExecutableTask> tasks;

    // Default delay for one-time tasks in case of Execution time crossed already
    private static int defaultDelay = 1000 * 60 ;

    public Schedular(List<ExecutableTask> tasks) {
        this.tasks = new CopyOnWriteArrayList<>(tasks);
    }

    public void schedule() {
        for (ExecutableTask task : tasks) {
            Runnable taskExecutor = () -> {
                synchronized (tasks) {
                    System.out.println("Executing Task: " + task.getTaskType());
                    task.execute();
                    if(task.isCompleted()) tasks.remove(task);
                }
                checkAndShutdownScheduler();
            };

            if (task.getTaskType() == TaskType.ONCE) {
                long delay = Math.max(defaultDelay, task.scheduledTime().getTime() - System.currentTimeMillis());
                System.out.println("Scheduling One-Time Task at " + ((delay == defaultDelay) ? new Date(new Date().getTime() + delay) : task.scheduledTime()));
                scheduler.schedule(taskExecutor, delay, TimeUnit.MILLISECONDS);
            } else if (task.getTaskType() == TaskType.RECURRING) {
                RecurringTask recurringTask = (RecurringTask) task;
                System.out.println("Scheduling Recurring Task with delay: " + recurringTask.getDelay() + " minutes");
                scheduler.scheduleAtFixedRate(taskExecutor, recurringTask.getDelay(), recurringTask.getDelay(), TimeUnit.MINUTES);
            }
        }
    }

    /**
     * Shutdown the scheduler if all tasks are completed.
     * in case of recurring tasks, it will not Shut down the scheduler.
     * @see RecurringTask
     */
    private void checkAndShutdownScheduler() {
        if (tasks.stream().noneMatch(task -> !task.isCompleted())) {
            System.out.println("All tasks are completed.");
            scheduler.shutdown();
        }
    }
}
