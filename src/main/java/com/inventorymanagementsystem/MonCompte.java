package com.inventorymanagementsystem;

import com.inventorymanagementsystem.entity.Client;
import com.inventorymanagementsystem.services.ClientService;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MonCompte implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayProfil(ClientService.getInstance().get(4));
    }

    @FXML
VBox clientCardVBox;
    @FXML
    ScrollPane clientCardScrollPane;
    private void displayProfil(Client client) {

        // Effacer l'affichage actuel des clients
        clientCardVBox.getChildren().clear();

        // Afficher les clients filtrés

            // Créer une nouvelle rangée pour chaque groupe de cinq clients
            HBox clientRow = new HBox(10); // Espacement entre les cardviews
            clientRow.setPrefHeight(120); // Hauteur de chaque rangée
            clientRow.setPrefWidth(clientCardScrollPane.getWidth()); // Largeur de chaque rangée

            // Ajouter les cardviews pour chaque client dans la rangée actuelle

                // Créer une StackPane pour chaque cardview

                StackPane clientCard = new StackPane();



                clientCard.setPrefSize(550, 300); // Taille du cardview

                // Créer un rectangle pour le fond du cardview
                Rectangle background = new Rectangle(700, 300, Color.LIGHTGRAY);

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
                // Appliquer les mêmes effets aux enfants du rectangle

                // ImageView photoView = new ImageView(new Image("file:C:\\Users\\Mega-PC\\Desktop\\java2\\inventory-management-system-main\\src\\main\\resources\\com\\inventorymanagementsystem\\resources\\hamza.png"));
                //System.out.println(clients.get(j).getPhoto());
                ImageView photoView = new ImageView(new Image("file:C:\\xampp\\htdocs\\BANEKTEK_FINAL_SYMFONY_JAVA\\public\\"+client.getPhoto()));
                photoView.setFitWidth(100); // Largeur de la photo
                photoView.setFitHeight(80); // Hauteur de la photo
                photoView.setPreserveRatio(true);

                // Ajouter le nom du client au cardview
                Text clientName = new Text("Nom : "+ client.getNom());
                clientName.setStyle("-fx-font-size: 26px; -fx-fill:#003883; -fx-font-style: italic;");
                StackPane.setAlignment(clientName, Pos.TOP_LEFT);
                StackPane.setMargin(clientName, new Insets(25, 0, 0, 10)); // 10 pixels de marge à gauche
////////////////////////////////////////////////////////////
                Text clientPrenom = new Text("Prenom : " +client.getPrenom());
                clientPrenom.setStyle("-fx-font-size: 26px; -fx-fill: #003883; -fx-font-style: italic;");
                StackPane.setAlignment(clientPrenom, Pos.TOP_LEFT);
                StackPane.setMargin(clientPrenom, new Insets(75, 0, 0, 10)); // 10 pixels de marge à gauche
////////////////////////////////////////////////////////////
                Text clientCin = new Text("C.I.N : " + Long.toString(client.getCin()));
                clientCin.setStyle("-fx-font-size: 26px; -fx-fill: #003883; -fx-font-style: italic;");
                StackPane.setAlignment(clientCin, Pos.TOP_LEFT);
                StackPane.setMargin(clientCin, new Insets(125, 0, 0, 10)); // 10 pixels de marge à gauche
                ///////////////////////////////////////////////:
                Text clientEmail = new Text("E-Mail : "+client.getEmail());
                clientEmail.setStyle("-fx-font-size: 20px; -fx-fill: #003883; -fx-font-style: italic;");
                StackPane.setAlignment(clientEmail, Pos.TOP_LEFT);
                StackPane.setMargin(clientEmail, new Insets(175, 0, 0, 10)); // 10 pixels de marge à gauche
                ///////////////////////////////////////////////////////////


        Text clientAdresse = new Text("Adresse :" +client.getPrenom());
        clientAdresse.setStyle("-fx-font-size: 26px; -fx-fill: #d9007a; -fx-font-style: italic;");
        StackPane.setAlignment(clientAdresse , Pos.TOP_CENTER);
        StackPane.setMargin(clientAdresse, new Insets(75, 0, 0, 200)); // 20 pixels de marge à gauche
////////////////////////////////////////////////////////////
        Text clientProffessin = new Text("Proffession : " + Long.toString(client.getCin()));
        clientProffessin.setStyle("-fx-font-size: 26px; -fx-fill: #d9007a; -fx-font-style: italic;");
        StackPane.setAlignment(clientProffessin, Pos.TOP_CENTER);
        StackPane.setMargin(clientProffessin, new Insets(125, 0, 0, 200)); // 10 pixels de marge à gauche
        ///////////////////////////////////////////////:
        Text clientUsername = new Text("E-Mail : "+client.getEmail());
        clientUsername.setStyle("-fx-font-size: 20px; -fx-fill: #d9007a; -fx-font-style: italic;");
        StackPane.setAlignment(clientUsername, Pos.TOP_CENTER);
        StackPane.setMargin(clientUsername, new Insets(175, 0, 0, 200)); // 10 pixels de marge à gauche
        ///////////////////////////////////////////////////////////









        /////////////////////////////////////////
                // Ajouter la photo du client et le nom du client à la StackPane
                clientCard.getChildren().addAll(background, photoView, clientName,clientPrenom,clientCin,clientEmail,clientProffessin,clientAdresse,clientUsername);

                Circle clip = new Circle();
                clip.setCenterX(50); // Centre X du cercle
                clip.setCenterY(40); // Centre Y du cercle
                clip.setRadius(40); // Rayon du cercle

// Appliquer le clip à l'ImageView
                photoView.setClip(clip);

// Appliquer un effet d'arrondi
                photoView.setEffect(new DropShadow(5, Color.BLACK));
                // Aligner la photo du client dans le coin supérieur droit de la StackPane
                StackPane.setAlignment(photoView, Pos.TOP_RIGHT);
                StackPane.setMargin(photoView, new Insets(20, 10, 0, 0)); // 20 pixels de marge en haut

                // Ajouter la StackPane à la rangée actuelle
                clientRow.getChildren().add(clientCard);
        clientCardVBox.getChildren().add(clientRow);
            }

            // Ajouter la rangée à la VBox principale




}
