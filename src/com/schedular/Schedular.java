package com.schedular;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Schedular class to manage the scheduling of tasks.
 * It uses a ScheduledExecutorService to schedule one-time and recurring tasks.
 */
public class Schedular {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    private final List<ExecutableTask> tasks;
    private final Map<ExecutableTask, ScheduledFuture<?>> recurringTaskFutures = new ConcurrentHashMap<>();

    // Default delay for one-time tasks in case of Execution time crossed already
    private static final int defaultDelay = 1000 * 60 ;

    public Schedular(List<ExecutableTask> tasks) {
        this.tasks = new CopyOnWriteArrayList<>(tasks);
    }

    public void schedule() {
        for (ExecutableTask task : tasks) {
            if (task.getTaskType() == TaskType.ONCE) {
                long delay = Math.max(defaultDelay, task.scheduledTime().getTime() - System.currentTimeMillis());
                System.out.println("Scheduling One-Time Task at " + ((delay == defaultDelay) ? new Date(new Date().getTime() + delay) : task.scheduledTime()));
                scheduler.schedule(() -> {
                    // This is the taskExecutor for one-time tasks
                    synchronized (tasks) { // Keep synchronization if tasks list is modified
                        System.out.println("Executing One-Time Task: " + task.getTaskType());
                        task.execute();
                        if(task.isCompleted()) tasks.remove(task);
                    }
                    checkAndShutdownScheduler();
                }, delay, TimeUnit.MILLISECONDS);
            } else if (task.getTaskType() == TaskType.RECURRING) {
                RecurringTask recurringTask = (RecurringTask) task;
                System.out.println("Scheduling Recurring Task with initial delay: " + recurringTask.getDelay() + " minutes, and period: " + recurringTask.getDelay() + " minutes");

                ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(() -> {
                    // This is the taskExecutor for recurring tasks
                    System.out.println("Executing Recurring Task: " + task.getTaskType());
                    task.execute(); // Execute the task first

                    // Check completion *after* execution
                    if (task.isCompleted()) {
                        System.out.println("Recurring task " + task.getTaskType() + " has completed its lifecycle. Removing and cancelling further executions.");
                        synchronized (tasks) { // Synchronize modifications to 'tasks' list
                           tasks.remove(task);
                        }
                        ScheduledFuture<?> selfFuture = recurringTaskFutures.remove(task); // Remove from map
                        if (selfFuture != null) {
                            selfFuture.cancel(false); // Cancel future executions
                        }
                    }
                    checkAndShutdownScheduler();
                }, recurringTask.getDelay(), recurringTask.getDelay(), TimeUnit.MINUTES); // Initial delay and period are both getDelay()

                recurringTaskFutures.put(task, future); // Store the future
            }
        }
    }

    /**
     * Shutdown the scheduler if all tasks are completed.
     * in case of recurring tasks, it will not Shut down the scheduler.
     * @see RecurringTask
     */
    private void checkAndShutdownScheduler() {
        synchronized (tasks) { // Ensure thread-safe access to 'tasks' list for the stream operation
            if (tasks.stream().allMatch(ExecutableTask::isCompleted)) {
                // This condition means all tasks *currently in the tasks list* are completed.
                // For TerminatingRecurringTasks, they should have removed themselves from 'tasks'
                // and cancelled their future.
                System.out.println("All tasks in the current list are marked as completed.");
                
                // Check if there are any futures that are not yet 'done' (completed/cancelled).
                // This is a safeguard. Ideally, recurringTaskFutures should be empty or all its futures should be 'done'
                // if the corresponding tasks were removed from the 'tasks' list.
                long activeRecurringCount = recurringTaskFutures.values().stream()
                                               .filter(future -> !future.isDone())
                                               .count();

                if (activeRecurringCount == 0) {
                    System.out.println("All recurring task schedules have finished. Shutting down scheduler.");
                    scheduler.shutdown();
                } else {
                    System.out.println(activeRecurringCount + " recurring tasks are still scheduled. Scheduler will not shut down yet.");
                }
            } else {
                // Optional: Log current task counts for debugging if not shutting down
                // System.out.println("Scheduler check: " + tasks.size() + " tasks remaining in list.");
            }
        }
    }
}
