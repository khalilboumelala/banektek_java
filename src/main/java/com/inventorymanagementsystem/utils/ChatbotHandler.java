        package com.inventorymanagementsystem.utils;

        import javafx.concurrent.Task;

        import java.io.BufferedReader;
        import java.io.BufferedWriter;
        import java.io.InputStreamReader;
        import java.io.OutputStreamWriter;
        import java.util.regex.Pattern;

        public class ChatbotHandler extends Task<String> {

            private final String promptText;

            Process process;
            ProcessBuilder builder;
            public ChatbotHandler(String promptText, Process process, ProcessBuilder builder) {
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

                // Start the process
                process = builder.start();

                // Write the instruction to the process
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                writer.write("jouez le rôle d'un bot conseiller personnel Banek, de Banektek, une banque numérique axée sur l'innovation et la technologie. voice la requette du client:");
                writer.flush();
                writer.close();

                // Read the response from the process
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null && !isCancelled()) {
                    line = ansiEscapeAndSpinners.matcher(line).replaceAll("");
                    if ((!line.trim().isEmpty())&& (!line.contains("Completed")) ){
                        output.append(line);//.append("\n");
                     //   System.out.println("line: "+line);
                       // System.out.println("output: "+output);
                        updateMessage("Banektek Ai is thinking...");  // Update progress message
                        updateMessage(line);
                    }
                }
              String stringoutput=  output.toString();
                stringoutput.replaceAll("\\s+", " ");
                stringoutput.replaceAll("\n", "");
                stringoutput.replace("Completed.","");
                System.out.println("string:"+stringoutput);

                // Wait for the process to complete
                int exitCode = process.waitFor();
                updateProgress(1.0, 1.0); // Ensure we set complete on finish

                // Close the reader
                reader.close();

                // Update the final message
                updateMessage("Completed.");  // Update final message

                // Check for any errors in the process execution
                if (exitCode != 0) {
                    throw new RuntimeException("Error executing the command, exit code " + exitCode);
                }

                // Return the output
                return stringoutput;
            }
        }
