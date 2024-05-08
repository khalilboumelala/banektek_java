package com.inventorymanagementsystem.services;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogWriter {

    public static void writeLog(String content) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime dateTime = LocalDateTime.now();
            // Formater la date selon le format
            String formattedDate = dateTime.format(formatter);

            // Ajouter la date et le tiret au début du contenu
            String formattedContent = "[" + formattedDate + "] - " + content  + "\n";

            String filePath="C:\\xampp\\htdocs\\BANEKTEK_FINAL_SYMFONY_JAVA\\public\\files\\log.txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            writer.write(formattedContent);
            writer.newLine(); // Ajouter une nouvelle ligne
            writer.close();
            System.out.println("Le contenu a été écrit avec succès dans le fichier : " + filePath);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier : " + e.getMessage());
        }
    }


}
