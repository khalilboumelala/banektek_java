package com.inventorymanagementsystem;

import com.inventorymanagementsystem.entity.Client;
import com.inventorymanagementsystem.entity.Compte;


import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.CustomTextField;
import java.util.*;
import com.inventorymanagementsystem.services.*;
public class CompteFrontController {


    public Label nom_agent_session;
public Client client_connecte;

    public Client getClient_connecte() {
        return client_connecte;
    }

    public void setClient_connecte(Client client_connecte) {
        this.client_connecte = client_connecte;
    }

    public VBox clientCardVBox;
    public ScrollPane clientCardScrollPane;
    public TextField client_search;
    public AnchorPane clientListPane;
    public AnchorPane clientAddPane;
    public Map<StackPane, Compte> ComteMap = new HashMap<StackPane, Compte>();

    public void setAgentName(String agentName) {
        nom_agent_session.setText(agentName);
    }

    @FXML
    void movetoShow() {
        displayCompte();

        clientListPane.setVisible(true);
    }

    @FXML
    void movetoAdd() {
        clientListPane.setVisible(false);

    }

    @FXML
    void movetoEdit() {

        clientListPane.setVisible(false);

    }



    @FXML
    public void initialize() {
       // movetoShow();
       // movetoShow();

       // List<Compte> comptes = CompteService.getInstance().getCompteByUserId(client_connecte.getId());
        //System.out.println(comptes);
       //
        displayCompte();
        System.out.println(comptes);
        Platform.runLater(() -> {
            Notifications.create()
                    .title("Hello")
                    .text("Bienvenue! Pensez à vous déconnecter pour votre sécurité.")
                    .hideAfter(Duration.seconds(10))
                    .show();
        });


    }
    ///





    //
    private List<Compte> filterClients(String searchText) {

        List<Compte> filteredComptes = new ArrayList<>();
        for (Compte compte : comptes) {
            String type = compte.getType();
            Double solde = compte.getSolde();
            String etat = compte.getEtat();
            String dateAsString = compte.getDateCreation().toString();
            if (solde.toString().toLowerCase().contains(searchText.toLowerCase()) ||
                    String.valueOf(solde).contains(searchText) ||
                    etat.toLowerCase().contains(searchText.toLowerCase()) ||
                    dateAsString.toLowerCase().contains(searchText.toLowerCase())) {
                filteredComptes.add(compte);
            }
        }
        return filteredComptes;
    }



private List<Compte> comptes= new ArrayList<>();

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }

    public void displayCompte() {

        // Effacer l'affichage actuel des clients
        clientCardVBox.getChildren().clear();

        // Afficher les clients filtrés
        for (int i = 0; i < comptes.size(); i += 4) {
            // Créer une nouvelle rangée pour chaque groupe de quatre clients
            HBox clientRow = new HBox(10); // Espacement entre les cardviews
            clientRow.setPrefHeight(120); // Hauteur de chaque rangée
            clientRow.setPrefWidth(clientCardScrollPane.getWidth()); // Largeur de chaque rangée

            // Ajouter les cardviews pour chaque client dans la rangée actuelle
            for (int j = i; j < Math.min(i + 4, comptes.size()); j++) {
                // Créer une StackPane pour chaque cardview
                StackPane clientCard = new StackPane();
                clientCard.setPrefSize(200, 100); // Taille du cardview

                // Créer un rectangle pour le fond du cardview
                Rectangle background = new Rectangle(200, 100, Color.LIGHTGRAY);
                background.setArcWidth(20);
                background.setArcHeight(20);
                background.setStyle("-fx-fill: lightgray; -fx-stroke: black; -fx-stroke-width: 2px;");

                // Ajouter les données du Compte au cardview
                Text compteType = new Text("Type: " + comptes.get(j).getType());
                Text compteSolde = new Text("Solde: " + String.valueOf(comptes.get(j).getSolde()));
                Text compteEtat = new Text("Etat: " + comptes.get(j).getEtat());
                Text compteDateCreation = new Text("Date de création: " + String.valueOf(comptes.get(j).getDateCreation()));

                // Organiser les éléments dans le cardview
                VBox cardContent = new VBox(5); // Espacement vertical entre les éléments
                cardContent.getChildren().addAll(compteType, compteSolde, compteEtat, compteDateCreation);                cardContent.setAlignment(Pos.CENTER_LEFT); // Alignement à gauche

                clientCard.getChildren().addAll(background, cardContent);

                clientRow.getChildren().add(clientCard);
            }
            // Ajouter la rangée à la VBox principale
            clientCardVBox.getChildren().add(clientRow);
        }
    }}




