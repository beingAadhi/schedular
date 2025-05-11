package com.schedular;

import java.util.List;

public class SchedularApp {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Please provide a file path as an argument.");
            throw new IllegalArgumentException("Please provide a file path as an argument");
        }
        String filePath = args[0];
        List<ExecutableTask> tasks = new TextProcessor().process(filePath);
        Schedular schedular = new Schedular(tasks);
        schedular.schedule();
    }
}
