package com.inventorymanagementsystem.utils;

import javafx.concurrent.Task;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.regex.Pattern;

public class ArticleGeneration extends Task<String> {

    private final String promptText;

    Process process;
    ProcessBuilder builder;
    public ArticleGeneration(String promptText,Process process,ProcessBuilder builder) {
        this.promptText = promptText;
        this.process=process;
        this.builder=builder;
    }

    @Override
    protected String call() throws Exception {
        StringBuilder output = new StringBuilder();
        final Pattern ansiEscapeAndSpinners = Pattern.compile(
                "\\u001B(?:[@-Z\\\\-_]|\\[[0-?]*[ -/]*[@-~])|" +
                        "[⠙⠹⠸⠼⠴⠦⠇⠏⠋⠧]"
        );


        process=builder.start();

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String instruction = "Parle en français et écris un article sur le sujet suivant, qui ne dépasse pas 8-9 lignes: ";
        String fullPrompt = instruction + promptText;

        writer.write(fullPrompt + "\n");
        writer.flush();
        writer.close();

        double progress = 0.0;
        String line;
        while ((line = reader.readLine()) != null && !isCancelled()) {
            line = ansiEscapeAndSpinners.matcher(line).replaceAll("");
            if (!line.trim().isEmpty()) {
                output.append(line).append("\n");
                updateMessage("Banektek Ai is thinking...");  // Update progress message
                progress += 0.25; // Increment by an arbitrary small amount
                updateProgress(Math.min(1.0, progress), 1.0);  // Update progress
            }
        }


        int exitCode = process.waitFor();
        updateProgress(1.0, 1.0); // Ensure we set complete on finish
        updateMessage("Completed.");  // Update final message
        if (exitCode != 0) {
            throw new RuntimeException("Error executing the command, exit code " + exitCode);
        }

        return output.toString();
    }
}
