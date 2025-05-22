# Schedular Application

This is a Java application used to schedule or execute commands based on a defined pattern. 

## Features

- **One-Time Schedule**: Supports tasks that need to be executed once at a specific time.
- **Recurring Tasks**: Supports tasks that need to be executed repeatedly based on a minutes provide.
- **Terminating Recurring Tasks**: Supports recurring tasks (minute-based) that automatically stop executing after a specified end date and time.

The application is designed to handle scheduling efficiently and provides flexibility for different types of tasks.

## Input Formats

The application expects task definitions in the input file, one per line:

-   **One-Time Task:** `MM DD HH mm YYYY <command_to_execute>`
    *   Example: `30 20 11 05 2025 echo Process daily report`
-   **Simple Recurring Task (no end date):** `*/<minutes> <command_to_execute>`
    *   `<minutes>` should be between 1 and 1440.
    *   Example: `*/15 echo Check server status`
-   **Terminating Recurring Task (with end date):** `*/<minutes> <end_MM> <end_DD> <end_HH> <end_mm> <end_YYYY> <command_to_execute>`
    *   `<minutes>` should be between 1 and 1440.
    *   `<end_MM>`: End month (1-12)
    *   `<end_DD>`: End day (1-31, valid for the month/year)
    *   `<end_HH>`: End hour (0-23)
    *   `<end_mm>`: End minute (0-59)
    *   `<end_YYYY>`: End year (e.g., 2024)
    *   Example: `*/30 12 31 17 00 2024 echo Perform cleanup, ends 31 Dec 2024 at 17:00`

## Assumptions
- Terminal Commands to be executed are valid and can be run in the system's command line.
- recurring tasks by minutes should within a day 1440 minutes.
- Assuming the system time is in 24-hour format.
- Assuming to use Schedular provided by language since Java has a built-in Schedular.
- Assuming we might Implement more Parser's, Executor's in the Future.
- Assuming the system is not under heavy tasks, and the tasks are not resource-intensive. If required we increase the thread pool size.
- Assuming Test cases are not required. But I required can use JUnit and complete.