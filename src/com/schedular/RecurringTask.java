package com.schedular;

/**
 * Add Recurring Capabilities to the ExecutableTask
 * This task will keep the Schedular thread alive infinitely since it is recurring nature
 * @see ExecutableTask
 */
public interface RecurringTask extends ExecutableTask {

    /**
     * Get the delay between two executions
     * @return delay in minutes
     */
    int getDelay();
}
