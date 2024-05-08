package com.inventorymanagementsystem;

import com.inventorymanagementsystem.entity.Client;
import com.inventorymanagementsystem.entity.Carte;

import com.inventorymanagementsystem.entity.Compte;
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
public class CarteFrontController {


    public Label nom_agent_session;



    public VBox clientCardVBox;
    public ScrollPane clientCardScrollPane;
    public TextField client_search;
    public AnchorPane clientListPane;
    public AnchorPane clientAddPane;
    public Map<StackPane, Carte> CarteMap = new HashMap<StackPane, Carte>();

    public void setAgentName(String agentName) {
        nom_agent_session.setText(agentName);
    }

    @FXML
    void movetoShow() {
        displayCarte();

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

    private List<Compte> comptes= new ArrayList<>();

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }

    List<Carte> CarteList=new ArrayList<>();


    @FXML
    public void initialize() {


      //
        //movetoShow();

      //  displayCarte(CarteList);

    }
    ///




    //
    private List<Carte> filterClients(String searchText) {
        List<Carte> carteList = CarteService.getInstance().getAll();
        List<Carte> filteredCarte = new ArrayList<>();
        for (Carte carte : carteList) {
            String type = carte.getType();
            Integer cvv = carte.getCvv();
            String plafond = carte.getPlafond();
            String etat = carte.getEtat();
            String dateAsString = carte.getDateEmission().toString();
            if (type.toLowerCase().contains(searchText.toLowerCase()) ||
                    cvv.toString().contains(searchText) ||
                    plafond.toLowerCase().contains(searchText.toLowerCase()) ||
                    etat.toLowerCase().contains(searchText.toLowerCase()) ||
                    dateAsString.toLowerCase().contains(searchText.toLowerCase())) {
                filteredCarte.add(carte);
            }
        }
        return filteredCarte;
    }



    public void displayCarte() {


        CarteList = CarteService.getInstance().getByComptes( comptes);
        //System.out.println("sizedisplay: "+CarteList.size());
        // Effacer l'affichage actuel des clients
        clientCardVBox.getChildren().clear();

        // Afficher les clients filtrés
        for (int i = 0; i < CarteList.size(); i += 4) {
            // Créer une nouvelle rangée pour chaque groupe de quatre clients
            HBox clientRow = new HBox(10); // Espacement entre les cardviews
            clientRow.setPrefHeight(120); // Hauteur de chaque rangée
            clientRow.setPrefWidth(clientCardScrollPane.getWidth()); // Largeur de chaque rangée

            // Ajouter les cardviews pour chaque client dans la rangée actuelle
            for (int j = i; j < Math.min(i + 4, CarteList.size()); j++) {
                // Créer une StackPane pour chaque cardview
                StackPane clientCard = new StackPane();
                clientCard.setPrefSize(200, 100); // Taille du cardview

                // Créer un rectangle pour le fond du cardview
                Rectangle background = new Rectangle(200, 100, Color.LIGHTGRAY);
                background.setArcWidth(20);
                background.setArcHeight(20);
                background.setStyle("-fx-fill: lightgray; -fx-stroke: black; -fx-stroke-width: 2px;");

                // Ajouter les données du Carte au cardview
                Text CarteType = new Text("Type: " + CarteList.get(j).getType());
                Text CarteCvv = new Text("CVV: " + String.valueOf(CarteList.get(j).getCvv()));
                Text CartePlafond = new Text("Plafond: " + String.valueOf(CarteList.get(j).getPlafond()));
                Text CarteEtat = new Text("Etat: " + String.valueOf(CarteList.get(j).getEtat()));
                Text CarteDate = new Text("DateEmission: " + String.valueOf(CarteList.get(j).getDateEmission()));

                // Organiser les éléments dans le cardview
                VBox cardContent = new VBox(5); // Espacement vertical entre les éléments
                cardContent.getChildren().addAll(CarteType, CarteCvv, CartePlafond, CarteEtat, CarteDate);
                cardContent.setAlignment(Pos.CENTER_LEFT); // Alignement à gauche

                clientCard.getChildren().addAll(background, cardContent);

                clientRow.getChildren().add(clientCard);
            }
            // Ajouter la rangée à la VBox principale
            clientCardVBox.getChildren().add(clientRow);
        }
    }}




