
# Schedular Application

This is a Java application used to schedule or execute commands based on a defined pattern. 

## Features

- **One-Time Schedule**: Supports tasks that need to be executed once at a specific time.
- **Recurring Tasks**: Supports tasks that need to be executed repeatedly based on a minutes provide.

The application is designed to handle scheduling efficiently and provides flexibility for different types of tasks.


## Assumptions
- Terminal Commands to be executed are valid and can be run in the system's command line.
- recurring tasks by minutes should within a day 1440 minutes.
- Assuming the system time is in 24-hour format.
- Assuming to use Schedular provided by language since Java has a built-in Schedular.
- Assuming we might Implement more Parser's, Executor's in the Future.
- Assuming the system is not under heavy tasks, and the tasks are not resource-intensive. If required we increase the thread pool size.
