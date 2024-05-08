package com.inventorymanagementsystem;

import com.inventorymanagementsystem.entity.Client;
import com.inventorymanagementsystem.entity.Compte;
import com.inventorymanagementsystem.entity.Transaction;

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
public class TransactionFrontController {


    public Label nom_agent_session;



    public VBox clientCardVBox;
    public ScrollPane clientCardScrollPane;
    public TextField client_search;
    public AnchorPane clientListPane;
    public AnchorPane clientAddPane;
    public Map<StackPane, Transaction> transactionMap = new HashMap<StackPane, Transaction>();

    public void setAgentName(String agentName) {
        nom_agent_session.setText(agentName);
    }

    private List<Compte> comptes= new ArrayList<>();

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }
    @FXML
    void movetoShow() {
        displayTransaction();

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

        List<Transaction> transactionList = TransactionService.getInstance().getAll();
        displayTransaction(transactionList);
        client_search.textProperty().addListener((observable, oldValue, newValue) -> {
            List<Transaction> filteredTransaction = filterClients(newValue);
            displayTransaction(filteredTransaction);
    });*/
    }
    ///




    //
    private List<Transaction> filterClients(String searchText) {
        List<Transaction> transactionList = TransactionService.getInstance().getAll();
        List<Transaction> filteredTransaction = new ArrayList<>();
        for (Transaction transaction : transactionList) {
            String type = transaction.getType();
            Double montant = transaction.getMontant();
            String dateAsString = transaction.getDateTransaction().toString();
            if (type.toLowerCase().contains(searchText.toLowerCase())|| Double.toString(transaction.getMontant()).contains(searchText.toLowerCase())||
                    dateAsString.toLowerCase().contains(searchText.toLowerCase())) {
                filteredTransaction.add(transaction);
            }
        }
        return filteredTransaction;
    }

    List<Transaction> transactionList= new ArrayList<>();
    private void displayTransaction() {
        transactionList=TransactionService.getInstance().getByComptes(comptes);
       // System.out.println("transs: "+transactionList);
        // Effacer l'affichage actuel des clients
        clientCardVBox.getChildren().clear();

        // Afficher les clients filtrés
        for (int i = 0; i < transactionList.size(); i += 4) {
            // Créer une nouvelle rangée pour chaque groupe de cinq clients
            HBox clientRow = new HBox(10); // Espacement entre les cardviews
            clientRow.setPrefHeight(120); // Hauteur de chaque rangée
            clientRow.setPrefWidth(clientCardScrollPane.getWidth()); // Largeur de chaque rangée

            // Ajouter les cardviews pour chaque client dans la rangée actuelle
            for (int j = i; j < Math.min(i + 4, transactionList.size()); j++) {
                // Créer une StackPane pour chaque cardview

                StackPane clientCard = new StackPane();


                transactionMap.put(clientCard, transactionList.get(j));

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

// Ajouter les données du transaction au cardview
                Text transactionType = new Text("Type: " + transactionList.get(j).getType());
                transactionType.setStyle("-fx-font-size: 16px; -fx-fill: black; -fx-font-style: italic;");
                transactionType.setWrappingWidth(ELEMENT_WIDTH);
                StackPane.setAlignment(transactionType, Pos.TOP_LEFT);
                StackPane.setMargin(transactionType, new Insets(25, 0, 0, 10)); // 10 pixels de marge à gauche

                Text transactionDate = new Text("Date: " + String.valueOf(transactionList.get(j).getDateTransaction()));
                transactionDate.setStyle("-fx-font-size: 16px; -fx-fill: black; -fx-font-style: italic;");
                transactionDate.setWrappingWidth(ELEMENT_WIDTH);
                StackPane.setAlignment(transactionDate, Pos.TOP_LEFT);
                StackPane.setMargin(transactionDate, new Insets(50, 0, 0, 10)); // 10 pixels de marge à gauche

                Text transactionMontant = new Text("Montant: " + String.valueOf(transactionList.get(j).getMontant()));
                transactionMontant.setStyle("-fx-font-size: 16px; -fx-fill: black; -fx-font-style: italic;");
                transactionMontant.setWrappingWidth(ELEMENT_WIDTH);
                StackPane.setAlignment(transactionMontant, Pos.TOP_LEFT);
                StackPane.setMargin(transactionMontant, new Insets(75, 0, 0, 10)); // 10 pixels de marge à gauche

                clientCard.getChildren().addAll(background, transactionType, transactionDate, transactionMontant);


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


