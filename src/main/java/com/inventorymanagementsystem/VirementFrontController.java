package com.inventorymanagementsystem;

import com.inventorymanagementsystem.entity.Carte;
import com.inventorymanagementsystem.entity.Compte;
import com.inventorymanagementsystem.entity.Virement;

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
import org.controlsfx.control.textfield.CustomTextField;
import java.util.*;
import com.inventorymanagementsystem.services.*;
public class VirementFrontController {


    public Label nom_agent_session;



    public VBox clientCardVBox;
    public ScrollPane clientCardScrollPane;
    public TextField client_search;
    public AnchorPane clientListPane;
    public AnchorPane clientAddPane;
    public Map<StackPane, Virement> virementMap = new HashMap<StackPane, Virement>();

    public void setAgentName(String agentName) {
        nom_agent_session.setText(agentName);
    }
    List<Virement> virementList=new ArrayList<>();
    private List<Compte> comptes= new ArrayList<>();

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }

    @FXML
    void movetoShow() {
        displayVirement();

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
      /*  movetoShow();

        List<Virement> virementList = VirementService.getInstance().getAll();
        displayVirement(virementList);
        client_search.textProperty().addListener((observable, oldValue, newValue) -> {
            List<Virement> filteredVirement = filterClients(newValue);
            displayVirement(filteredVirement);
        });*/
    }
    private List<Virement> filterClients(String searchText) {
        List<Virement> virementList = VirementService.getInstance().getAll();
        List<Virement> filteredVirement = new ArrayList<>();
        for (Virement virement : virementList) {
            String etat = virement.getCinEmetteur();
            if (etat.toLowerCase().contains(searchText.toLowerCase())) {
                filteredVirement.add(virement);
            }
        }
        return filteredVirement;
    }


    private void displayVirement() {

        virementList=VirementService.getInstance().getByComptes(comptes);
        // Effacer l'affichage actuel des clients
        clientCardVBox.getChildren().clear();

        // Afficher les clients filtrés
        for (int i = 0; i < this.virementList.size(); i += 4) {
            // Créer une nouvelle rangée pour chaque groupe de cinq clients
            HBox clientRow = new HBox(10); // Espacement entre les cardviews
            clientRow.setPrefHeight(120); // Hauteur de chaque rangée
            clientRow.setPrefWidth(clientCardScrollPane.getWidth()); // Largeur de chaque rangée

            // Ajouter les cardviews pour chaque client dans la rangée actuelle
            for (int j = i; j < Math.min(i + 4, virementList.size()); j++) {
                // Créer une StackPane pour chaque cardview

                StackPane clientCard = new StackPane();


                virementMap.put(clientCard, virementList.get(j));

                clientCard.setPrefSize(200, 100); // Taille du cardview

                // Créer un rectangle pour le fond du cardview
                Rectangle background = new Rectangle(200, 100, Color.LIGHTGRAY);

                background.setArcWidth(20);
                background.setArcHeight(20);
                background.setStyle("-fx-fill: lightgray; -fx-stroke: black; -fx-stroke-width: 2px;");
                // Définir un effet agrandissant sur le survol et changer la couleur de fond
                clientCard.setOnMouseEntered(event -> {
                    if (background.getScaleX() != 1.1) { // Vérifier si la taille est différente
                        background.setScaleX(1.1);
                        background.setScaleY(1.1);
                        background.setFill(Color.CYAN);
                        background.setOpacity(0.2); // Réduire l'opacité pour rendre moins sensible
                    }
                });

// Réinitialiser la taille et la couleur lorsque la souris quitte
                clientCard.setOnMouseExited(event -> {
                    if (background.getScaleX() != 1.0) { // Vérifier si la taille est différente
                        background.setScaleX(1.0);
                        background.setScaleY(1.0);
                        background.setFill(Color.LIGHTGRAY);
                        background.setOpacity(1.0); // Rétablir l'opacité normale
                    }
                });

                // Définir une largeur fixe pour tous les éléments
                final double ELEMENT_WIDTH = 200.0;

// Ajouter les données du virement au cardview
                Text virementEtat = new Text("Etat: " + virementList.get(j).getEtat());
                virementEtat.setStyle("-fx-font-size: 16px; -fx-fill: black; -fx-font-style: italic;");
                virementEtat.setWrappingWidth(ELEMENT_WIDTH);
                StackPane.setAlignment(virementEtat, Pos.TOP_LEFT);
                StackPane.setMargin(virementEtat, new Insets(25, 0, 0, 10)); // 10 pixels de marge à gauche

                Text virementMontant = new Text("Montant: " + String.valueOf(virementList.get(j).getMontant()));
                virementMontant.setStyle("-fx-font-size: 16px; -fx-fill: black; -fx-font-style: italic;");
                virementMontant.setWrappingWidth(ELEMENT_WIDTH);
                StackPane.setAlignment(virementMontant, Pos.TOP_LEFT);
                StackPane.setMargin(virementMontant, new Insets(50, 0, 0, 10)); // 10 pixels de marge à gauche

                Text virementType = new Text("Type: " + String.valueOf(virementList.get(j).getType()));
                virementType.setStyle("-fx-font-size: 16px; -fx-fill: black; -fx-font-style: italic;");
                virementType.setWrappingWidth(ELEMENT_WIDTH);
                StackPane.setAlignment(virementType, Pos.TOP_LEFT);
                StackPane.setMargin(virementType, new Insets(75, 0, 0, 10)); // 10 pixels de marge à gauche

                clientCard.getChildren().addAll(background, virementEtat, virementMontant, virementType);


                Circle clip = new Circle();
                clip.setCenterX(50); // Centre X du cercle
                clip.setCenterY(40); // Centre Y du cercle
                clip.setRadius(40); // Rayon du cercle

                clientRow.getChildren().add(clientCard);
            }
            // Ajouter la rangée à la VBox principale
            clientCardVBox.getChildren().add(clientRow);
        }
    }
}


