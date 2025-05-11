package com.schedular;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * It reads the file line by line, creates tasks based on the Parser available, and returns a list of ScheduledTask objects.
 * @see ParserFactory
 */
public class TextProcessor implements Processor {

    @Override
    public List<ExecutableTask> process(String filePath) throws Exception {

        Path path = Paths.get(filePath);
        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            throw new FileNotFoundException("File not found: " + filePath);
        }

        if (!Files.isReadable(path)) {
            throw new FileNotFoundException("File cannot be read: " + filePath);
        }

        List<ExecutableTask> tasks = new ArrayList<>();
        Files.readAllLines(path).stream()
                .filter(line -> !line.isBlank())
                .forEach(line -> {
                    ExecutableTask createdTask = ParserFactory.createTask(line);
                        if(createdTask != null) {
                            tasks.add(createdTask);
                        }
                });
        return tasks;
    }
}
