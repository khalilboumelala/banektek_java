package com.inventorymanagementsystem;

import com.inventorymanagementsystem.entity.Compte;
import com.inventorymanagementsystem.entity.Credit;


import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.textfield.CustomTextField;
import java.util.*;
import com.inventorymanagementsystem.services.*;
public class CreditFrontController {


    public Label nom_agent_session;

    private List<Compte> comptes= new ArrayList<>();

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }




    public VBox clientCardVBox;
    public ScrollPane clientCardScrollPane;
    public TextField client_search;
    public AnchorPane clientListPane;
    public AnchorPane clientAddPane;
    public Map<StackPane, Credit> ComteMap = new HashMap<StackPane, Credit>();

    public void setAgentName(String agentName) {
        nom_agent_session.setText(agentName);
    }

    @FXML
    void movetoShow() {
        displayCredit();

        clientListPane.setVisible(true);
    }
    private double x;
    private double y;
    @FXML
    public void opensimulator(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("Loansimulator.fxml"));
            Scene scene = new Scene(root);
            Stage stage=new Stage();
            root.setOnMousePressed((event)->{
                x=event.getSceneX();
                y=event.getSceneY();
            });
            root.setOnMouseDragged((event)->{
                stage.setX(event.getScreenX()-x);
                stage.setY(event.getScreenY()-y);
            });
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        }catch (Exception err){
          /*  Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();*/
        }


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
        /*movetoShow();

        List<Credit> listCredit = CreditService.getInstance().getAll();
        displayCredit(listCredit);
        client_search.textProperty().addListener((observable, oldValue, newValue) -> {
            List<Credit> filteredCredit = filterClients(newValue);
            displayCredit(filteredCredit);
        });*/
    }
    ///




    //
    private List<Credit> filterClients(String searchText) {
        List<Credit> listCredit = CreditService.getInstance().getAll();
        List<Credit> filteredCredits = new ArrayList<>();
        for (Credit credit : listCredit) {
            Integer duree = credit.getDuree();
            Integer nbEcheancesRestants = credit.getNb_echeances_restants();
            Double apportPropre = credit.getApport_propre();
         //  Double revenuPropre = credit.getRevenu_propre();
            Double revenuPropre = credit.getrevenu_propre();
            String etat = credit.getEtat();

            // Convertir les valeurs numériques en chaînes de caractères pour la comparaison
            String dureeAsString = String.valueOf(duree);
            String nbEcheancesRestantsAsString = String.valueOf(nbEcheancesRestants);
            String apportPropreAsString = String.valueOf(apportPropre);
            String revenuPropreAsString = String.valueOf(revenuPropre);

            // Filtrer les crédits en fonction du texte de recherche
            if (dureeAsString.toLowerCase().contains(searchText.toLowerCase()) ||
                    nbEcheancesRestantsAsString.toLowerCase().contains(searchText.toLowerCase()) ||
                    apportPropreAsString.toLowerCase().contains(searchText.toLowerCase()) ||
                   revenuPropreAsString.toLowerCase().contains(searchText.toLowerCase()) ||
                    etat.toLowerCase().contains(searchText.toLowerCase())) {
                filteredCredits.add(credit);
            }
        }
        return filteredCredits;
    }





    List<Credit> listCredit=new ArrayList<>();
    private void displayCredit() {
listCredit=CreditService.getInstance().getByComptes(comptes);
        // Effacer l'affichage actuel des crédits
        clientCardVBox.getChildren().clear();

        // Afficher les crédits
        for (int i = 0; i < listCredit.size(); i += 4) {
            // Créer une nouvelle rangée pour chaque groupe de quatre crédits
            HBox creditRow = new HBox(10); // Espacement entre les cardviews
            creditRow.setPrefHeight(120); // Hauteur de chaque rangée
            creditRow.setPrefWidth(clientCardScrollPane.getWidth()); // Largeur de chaque rangée

            // Ajouter les cardviews pour chaque crédit dans la rangée actuelle
            for (int j = i; j < Math.min(i + 4, listCredit.size()); j++) {
                // Créer une StackPane pour chaque cardview
                StackPane creditCard = new StackPane();
                creditCard.setPrefSize(200, 100); // Taille du cardview

                // Créer un rectangle pour le fond du cardview
                Rectangle background = new Rectangle(200, 100, Color.LIGHTGRAY);
                background.setArcWidth(20);
                background.setArcHeight(20);
                background.setStyle("-fx-fill: lightgray; -fx-stroke: black; -fx-stroke-width: 2px;");

                // Ajouter les données du Crédit au cardview
                Text creditDuree = new Text("Duree: " + listCredit.get(j).getDuree());
                Text creditNbEcheancesRestants = new Text("Nb_echeances_restants: " + listCredit.get(j).getNb_echeances_restants());
                Text creditApportPropre = new Text("Apport_propre: " + listCredit.get(j).getApport_propre());
                //Text creditRevenuPropre = new Text("Revenu_propre: " + listCredit.get(j).getRevenu_propre());
                Text creditRevenuPropre = new Text("Revenu_propre:"+listCredit.get(j).getrevenu_propre());
                Text creditEtat = new Text("Etat: " + listCredit.get(j).getEtat());

                // Organiser les éléments dans le cardview
                VBox cardContent = new VBox(5); // Espacement vertical entre les éléments
                cardContent.getChildren().addAll(creditDuree, creditNbEcheancesRestants, creditApportPropre, creditRevenuPropre, creditEtat);
                cardContent.setAlignment(Pos.CENTER_LEFT); // Alignement à gauche

                creditCard.getChildren().addAll(background, cardContent);

                creditRow.getChildren().add(creditCard);
            }
            // Ajouter la rangée à la VBox principale
            clientCardVBox.getChildren().add(creditRow);
        }
    }

    }




