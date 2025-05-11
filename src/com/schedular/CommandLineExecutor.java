package com.schedular;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * CommandLineExecutor responsible for executing commands in Terminal.
 */
public class CommandLineExecutor implements Executor {

    @Override
    public void execute(String command) {
        try {
            System.out.println("Executing command: " + command + " at " + new Date());
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            InputStream inputStream = process.getInputStream();
            byte[] outputBytes = inputStream.readAllBytes();

            // Convert bytes to String and print
            String output = new String(outputBytes);
            System.out.println("Output from Console: "  + output);
        } catch (IOException e) {
            System.err.println("Error executing command: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("InterruptedException " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
