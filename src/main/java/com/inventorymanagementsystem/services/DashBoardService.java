package com.inventorymanagementsystem.services;

import com.inventorymanagementsystem.AgentFXController;
import com.inventorymanagementsystem.DashFXController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardService {
    public static void moveFunction(Button btn, String fxmlfile, Class<?> clazz,Label labelagent) {
        try {
                // Load the FXML file for AgentsFX.fxml
                FXMLLoader loader = new FXMLLoader(clazz.getResource(fxmlfile));
                Parent root = loader.load();
            String nomAgent = labelagent.getText();

            assignAgentName(loader, nomAgent);

            // Create a new scene with the loaded FXML content
                Scene scene = new Scene(root);

                // Get the current stage (assuming your application has only one stage)
                Stage stage = (Stage) btn.getScene().getWindow();

                // Set the new scene in the current stage
                stage.setScene(scene);
                stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle any potential exceptions here
        }
    }
    private static void assignAgentName(FXMLLoader loader,String nom) {
        Object controller = loader.getController();
        if (controller instanceof AgentFXController) {
            AgentFXController agentController = (AgentFXController) controller;
            agentController.setAgentName(nom);
        } else if (controller instanceof DashFXController) {
            DashFXController dashFXController = (DashFXController) controller;
            dashFXController.setAgentName(nom);
        }
    }
}
