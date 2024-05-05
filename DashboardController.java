package com.inventorymanagementsystem;
//import okhttp3.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.sql.*;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

//import atlantafx.base.controls.ToggleSwitch;
import atlantafx.base.util.Animations;
import com.inventorymanagementsystem.services.*;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.transformation.FilteredList;
import javafx.event.Event;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.ToggleSwitch;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;


import atlantafx.base.controls.Card;
import com.inventorymanagementsystem.entity.*;
import com.inventorymanagementsystem.config.Database;
import com.inventorymanagementsystem.utils.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Rating;

import java.io.BufferedReader;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.inventorymanagementsystem.entity.Notification;
//

import java.io.IOException;

//

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXListCell;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

//import com.gluonhq.charm.glisten.control.DropdownButton;

import static org.burningwave.core.assembler.StaticComponentContainer.Modules;

public class DashboardController implements Initializable {

    private double x;
    private double y;



    @FXML
    private Label user;



    private Connection connection;

    private Statement statement;

    private PreparedStatement preparedStatement;

    private ResultSet resultSet;


//AGENCE


    @FXML
    private Button dashboard_btn;


    @FXML
    private AnchorPane dashboard_pane;


    @FXML
    private Button clients_btn;


    @FXML
    private AnchorPane clients_pane;



    @FXML
    private Button virements_btn;

    @FXML
    private AnchorPane virements_pane;
    @FXML
    private Button transaction_btn;

    @FXML
    private AnchorPane transaction_pane;

    @FXML
    private Button cartes_btn;


    @FXML
    private AnchorPane cartes_pane;



    //articles-agences
@FXML
private Button articles_btn;
    @FXML
    private Button comments_btn;
    @FXML
    private AnchorPane agences_pane;
    @FXML
    private AnchorPane left_pane;
    @FXML
    private AnchorPane content_pane;
    @FXML
    private AnchorPane articles_pane;
    @FXML
    private AnchorPane comments_pane;

    @FXML
    private AnchorPane articles_front_anchorpane;
    @FXML
    private AnchorPane articles_back_anchorpane;
    @FXML
    private ToggleSwitch frontbackarticlesswitch;

    @FXML
    private TextField lat_input;
    @FXML
    private TextField agence_tel_input;
    @FXML
    private TextField agence_nom_input;
    @FXML
    private TextField agence_adresse_input;
    @FXML
    private TextField lng_input;
    @FXML
    private TableView<Article> article_table;
    @FXML
    private JFXListView<Article> article_listview;

//
    @FXML
    private TableView<Agence> agence_table;
    @FXML
    private JFXListView<Agence> agence_listview;

    @FXML
    private WebView webView;
    @FXML
    private TableColumn<Agence, java.util.Date> agence_col_dateajout;
    @FXML
    private TableColumn<Agence, Integer> agence_col_id;
    @FXML
    private TableColumn<Agence, String> agence_col_longitude;
    @FXML
    private TableColumn<Agence, String> agence_col_latitude;

    @FXML
    private TableColumn<Agence, String> agence_col_adresse;

    @FXML
    private TableColumn<Agence,String> agence_col_nom;

    @FXML
    private TableColumn<Agence,String> agence_col_etat;
    @FXML
    private TableColumn<Agence, Long> agence_col_tel;

    @FXML
    TableColumn<Article, Date> article_col_datepub ;
    @FXML
    TableColumn<Article, String> article_col_image ;
    @FXML
    TableColumn<Article, String> article_col_titre ;
    @FXML
    TableColumn<Article, String> article_col_auteur ;
    @FXML
    TableColumn<Article, Boolean> article_col_checkbox ;
    @FXML
    TableColumn<Article, Integer> article_col_id ;

    @FXML
    private Button add_agence_button;
    @FXML
    private Button delete_agence_button;
    @FXML
    private Button edit_agence_button;
    @FXML
    private Button translator_button;
    @FXML
    private Button article_add_button;

    @FXML
    private Button article_upload_button;
    @FXML
    private TextField article_titre_input;
    @FXML
    private TextArea article_contenu_input;

    @FXML
    private Label article_upload_status;
    @FXML
    private Button agences_btn;
    //

    /////////::client //////:
    @FXML
    private TextField nom_client;
    @FXML
    private TextField prenom_client;
    @FXML
    private TextField mail_client;
    @FXML
    private TextField cin_client;
    @FXML
    private TextField genre_client;
    @FXML
    private TextField pays_client;
    @FXML
    private TextField piece_client;
    @FXML
    private TextField photo_client;

    @FXML
    private TextField tel_client;
    @FXML
    private TextField poste_client;
    @FXML
    private TextField dob_client;
    @FXML
    private TextField adresse_client;
    @FXML
    private TextField signature_client;
    @FXML
    private TextField pass_client;
    //////table client//////
    @FXML
    private TableColumn<?, ?> table_client_nom;
    @FXML
    private TableColumn<?, ?> table_client_id;
    @FXML
    private TableColumn<?, ?> table_client_cin;

    @FXML
    private TableColumn<?, ?> table_client_tel;

    @FXML
    private TableColumn<?, ?> table_client_mail;
    @FXML
    private TableView<Client> client_table;
    @FXML
    private TextField client_search;




    @FXML
    public void addClient() {
        if (nom_client.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir les données obligatoires.");
            alert.showAndWait();
            return;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        long cinLong = Long.parseLong(cin_client.getText());
        long telLong = Long.parseLong(tel_client.getText());
        try {
            connection = Database.getInstance().connectDB();
            String sql = "INSERT INTO client (dob, nom, prenom, cin, num_tel, genre, pays, adresse, email, document, signature, profession, date_creation, username, password, last_login, etat, photo) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, java.sql.Date.valueOf("2023-11-22"));
            preparedStatement.setString(2, nom_client.getText());
            preparedStatement.setString(3, prenom_client.getText());
            preparedStatement.setLong(4, cinLong);
            preparedStatement.setLong(5, telLong);
            preparedStatement.setString(6, genre_client.getText());
            preparedStatement.setString(7, pays_client.getText());
            preparedStatement.setString(8, adresse_client.getText());
            preparedStatement.setString(9, mail_client.getText());
            preparedStatement.setString(10, "cin");
            preparedStatement.setString(11, signature_client.getText());
            preparedStatement.setString(12, poste_client.getText());
            preparedStatement.setDate(13, java.sql.Date.valueOf("2023-11-22"));
            preparedStatement.setString(14, "username");
            preparedStatement.setString(15, "password");
            preparedStatement.setDate(16, java.sql.Date.valueOf("2023-11-22"));
            preparedStatement.setString(17, "activer");
            preparedStatement.setString(18, "ss");

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Client ajouté avec succès.");
                successAlert.showAndWait();

                ClearClientData();
                showTableClient();

            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Erreur lors de l'ajout du client.");
                errorAlert.showAndWait();
            }
        } catch (Exception e) {
            Alert exceptionAlert = new Alert(Alert.AlertType.ERROR);
            exceptionAlert.setTitle("Exception");
            exceptionAlert.setHeaderText(null);
            exceptionAlert.setContentText("Une exception s'est produite lors de l'ajout du client. Veuillez vérifier les données.");
            exceptionAlert.showAndWait();
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public ObservableList<Client> listClients() {
        ObservableList<Client> clientList = FXCollections.observableArrayList();
        connection = Database.getInstance().connectDB();
        String sql = "SELECT * FROM CLIENT";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Client clientData = new Client(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("profession"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("photo"),
                        resultSet.getDate("dob"),
                        resultSet.getLong("cin"),
                        resultSet.getString("adresse"),
                        resultSet.getString("etat"),
                        resultSet.getString("username"),
                        resultSet.getLong("num_tel"),
                        resultSet.getString("pays"),
                        resultSet.getString("document")
                );
                clientList.add(clientData); // Ajouter chaque client à la liste
            }

        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return clientList;
    }



    public void showTableClient(){
        ObservableList<Client> clientList=listClients();
        FXCollections.reverse(clientList); //pour inverser
        table_client_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_client_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        table_client_cin.setCellValueFactory(new PropertyValueFactory<>("cin"));
        table_client_tel.setCellValueFactory(new PropertyValueFactory<>("num"));
        table_client_mail.setCellValueFactory(new PropertyValueFactory<>("email"));

        client_table.setItems(clientList);



    }

    @FXML
    public void deleteClient(){
        if(client_table.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select client for deletion.");
            alert.showAndWait();
            return;
        }
        connection = Database.getInstance().connectDB();
        String sql="DELETE FROM CLIENT WHERE id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,client_table.getSelectionModel().getSelectedItem().getId());
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Client deleted successfully.");
                alert.showAndWait();
                ClearClientData();
                showTableClient();

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("No data present in the client table.");
                alert.showAndWait();
            }
        } catch (Exception err) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
    }
    public void ClearClientData(){
        nom_client.setText("");
        prenom_client.setText("");
        cin_client.setText("");
        tel_client.setText("");
        mail_client.setText("");
        adresse_client.setText("");
        dob_client.setText("");
        signature_client.setText("");
        photo_client.setText("");
        piece_client.setText("");
        pays_client.setText("");
        poste_client.setText("");
        genre_client.setText("");
        pass_client.setText("");
    }
    @FXML
    public void selectClient() {
        Client selectedClient = client_table.getSelectionModel().getSelectedItem();
        if (selectedClient == null) {
            // Aucun client sélectionné, rien à faire
            return;
        }

        // Remplir les champs de texte avec les données du client sélectionné
        nom_client.setText(selectedClient.getNom());
        prenom_client.setText(selectedClient.getPrenom());
        mail_client.setText(selectedClient.getEmail());
        cin_client.setText(String.valueOf(selectedClient.getCin()));
        genre_client.setText("homme");
        pays_client.setText(selectedClient.getPays());
        piece_client.setText(selectedClient.getDocument());
        photo_client.setText(selectedClient.getPhoto());
        tel_client.setText(String.valueOf(selectedClient.getNum()));
        poste_client.setText(selectedClient.getPoste());
        dob_client.setText(selectedClient.getDob().toString()); // Modifier en fonction de votre format de date
        adresse_client.setText(selectedClient.getAdresse());
        signature_client.setText(selectedClient.getDocument());
        pass_client.setText(selectedClient.getPassword());
    }
    @FXML
    public void updateClient() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Database.getInstance().connectDB();
            String sql = "UPDATE CLIENT SET nom=?, prenom=?, profession=?, email=?, password=?, photo=?, dob=?, cin=?, adresse=?, num_tel=?, pays=?, document=? WHERE id=?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nom_client.getText());
            preparedStatement.setString(2, prenom_client.getText());
            preparedStatement.setString(3, poste_client.getText());
            preparedStatement.setString(4, mail_client.getText());
            preparedStatement.setString(5, pass_client.getText());
            preparedStatement.setString(6, photo_client.getText());
            preparedStatement.setDate(7, Date.valueOf(dob_client.getText()));
            preparedStatement.setLong(8, Long.parseLong(cin_client.getText()));
            preparedStatement.setString(9, adresse_client.getText());

            preparedStatement.setLong(10, Long.parseLong(tel_client.getText()));
            preparedStatement.setString(11, pays_client.getText());
            preparedStatement.setString(12, "document/cin");
            preparedStatement.setInt(13,client_table.getSelectionModel().getSelectedItem().getId());

            //  preparedStatement.setInt(12, Integer.parseInt(client_id.getText())); // Supposons que client_id est le champ ID du client à modifier

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Client updated successfully.");
                successAlert.showAndWait();
                ClearClientData();
                showTableClient();
                // Vous pouvez également actualiser ou recharger les données du tableau après la mise à jour
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error Message");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Failed to update client. Please check the provided data.");
                errorAlert.showAndWait();
            }
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void printPdfClient(){
        connection=Database.getInstance().connectDB();
        String  sql = "SELECT id, nom, num_tel FROM client";
        ;
        try{
            JasperDesign jasperDesign= JRXmlLoader.load(this.getClass().getClassLoader().getResourceAsStream("jasper-reports/customers.jrxml"));
            JRDesignQuery updateQuery=new JRDesignQuery();
            updateQuery.setText(sql);
            jasperDesign.setQuery(updateQuery);
            JasperReport jasperReport= JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null,connection);
            JasperViewer.viewReport(jasperPrint ,false);
        }catch (Exception err){
            err.printStackTrace();
        }
    }

    @FXML
    private void searchClient() {
        String searchText = client_search.getText().trim();

        // Vérifier si le champ de recherche n'est pas vide
        if (!searchText.isEmpty()) {
            // Créer un filtre pour rechercher dans la table
            FilteredList<Client> filteredData = new FilteredList<>(listClients(), p -> true);

            // Appliquer le filtre en fonction du texte de recherche
            filteredData.setPredicate(client -> {
                // Convertir l'ID en String pour la comparaison
                String idString = Integer.toString(client.getId());

                // Convertir le numéro de téléphone en String pour la comparaison
                String numTelString = Long.toString(client.getNum());

                // Convertir le texte de recherche en minuscules pour une comparaison insensible à la casse
                String lowerCaseFilter = searchText.toLowerCase();

                if (idString.contains(lowerCaseFilter)) {
                    return true; // Correspondance de l'ID
                } else if (client.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Correspondance du nom
                } else if (numTelString.contains(lowerCaseFilter)) {
                    return true; // Correspondance du numéro de téléphone
                }
                return false; // Aucune correspondance
            });

            // Mettre à jour la TableView avec la liste filtrée
            client_table.setItems(filteredData);
        } else {
            // Si le champ de recherche est vide, afficher tous les clients
            client_table.setItems(listClients());
        }
    }




    ///////fin client ////////:

    //

    @FXML
    private Button signout_btn;





    public void onExit(){
        System.exit(0);
    }



    public void disactivateAllPanes(){
        // Hide anchor pane
        dashboard_pane.setVisible(false);
        comptes_pane.setVisible(false);
        clients_pane.setVisible(false);
        cartes_pane.setVisible(false);
        virements_pane.setVisible(false);
        agences_pane.setVisible(false);
        articles_pane.setVisible(false);
        comments_pane.setVisible(false);
        transaction_pane.setVisible(false);
        reclamation_pane.setVisible(false);
        //Button color to default
        dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        comptes_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        clients_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        cartes_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        virements_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");

        agences_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        articles_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        comments_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        transaction_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        reclamation_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");

    }
  public void  ActivateThisAnchor(AnchorPane AnchorTest,Button ButtonTest){
        disactivateAllPanes();
      Animations.fadeIn(AnchorTest, Duration.millis(1000)).playFromStart();
      AnchorTest.setVisible(true);
      ButtonTest.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.7),  rgba(255,106,239,0.7))");
  }
    public void activateAnchorPane(){
        dashboard_btn.setOnMouseClicked(mouseEvent -> {
            ActivateThisAnchor(dashboard_pane,dashboard_btn);
        });
        comptes_btn.setOnMouseClicked(mouseEvent -> {
            ActivateThisAnchor(comptes_pane,comptes_btn);
        });
        clients_btn.setOnMouseClicked(mouseEvent -> {
            ActivateThisAnchor(clients_pane,clients_btn);
        });
        cartes_btn.setOnMouseClicked(mouseEvent -> {
            ActivateThisAnchor(cartes_pane,cartes_btn);
        });
        virements_btn.setOnMouseClicked(mouseEvent -> {
            ActivateThisAnchor(virements_pane,virements_btn);
        });

        agences_btn.setOnMouseClicked(mouseEvent -> {
            ActivateThisAnchor(agences_pane,agences_btn);
        });
        articles_btn.setOnMouseClicked(mouseEvent -> {

            ActivateThisAnchor(articles_pane,articles_btn);
        });
        comments_btn.setOnMouseClicked(mouseEvent -> {

            ActivateThisAnchor(comments_pane,comments_btn);
        });
        transaction_btn.setOnMouseClicked(mouseEvent -> {

            ActivateThisAnchor(transaction_pane,transaction_btn);
        });
        reclamation_btn.setOnMouseClicked(mouseEvent -> {

            ActivateThisAnchor(reclamation_pane,reclamation_btn);
        });
    }

    public void setUsername(){
        //user.setText(User.name.toUpperCase());
       // user.setText("banektek");
    }

    public void activateDashboard(){
        dashboard_pane.setVisible(true);
        comptes_pane.setVisible(false);
        clients_pane.setVisible(false);
        cartes_pane.setVisible(false);
        virements_pane.setVisible(false);
    }


    public void signOut(){
        signout_btn.getScene().getWindow().hide();
        try{
        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }

    }
    @FXML
    private WebView webViewReponse;
    private WebEngine engine;
    @FXML
    private TextField textFieldLink;
    //private WebEngine engine;
    private WebHistory history;
    //private String homePage;
    private double webZoom;
    private String homePage;
    /*public DashboardController(TextField textFieldLink) {
        this.textFieldLink = textFieldLink;
    }*/
    //afficher webView reponse


    @FXML
    public void loadPage() {
        engine.load("http://www.google.com");
        //engine.load("http://"+textFieldLink.getText());
    }
    public void refreshPage() {

        engine.reload();
    }

    public void zoomIn() {

        webZoom+=0.25;
        webViewReponse.setZoom(webZoom);
    }

    public void zoomOut() {

        webZoom-=0.25;
        webView.setZoom(webZoom);
    }

    public void displayHistory() {

        history = engine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();

        for(WebHistory.Entry entry : entries) {

            //System.out.println(entry);
            System.out.println(entry.getUrl()+" "+entry.getLastVisitedDate());
        }
    }

    public void back() {

        history = engine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();
        history.go(-1);

        textFieldLink.setText(entries.get(history.getCurrentIndex()).getUrl());
    }

    public void next() {

        history = engine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();
        history.go(1);

        textFieldLink.setText(entries.get(history.getCurrentIndex()).getUrl());
    }

    // chart reclamation
    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    public void chart() {
        try {
            Connection connection = Database.getInstance().connectDB();
            String chartSql = "SELECT date_reclamation, type FROM reclamation";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(chartSql);

            // Créer une structure de données pour stocker le nombre de réclamations par type à une date donnée
            Map<String, Map<String, Integer>> dataMap = new HashMap<>();

            // Parcourir les résultats de la requête
            while (resultSet.next()) {
                String date = resultSet.getString("date_reclamation");
                String type = resultSet.getString("type");

                // Mettre à jour la structure de données
                if (!dataMap.containsKey(date)) {
                    dataMap.put(date, new HashMap<>());
                }
                Map<String, Integer> typeCountMap = dataMap.get(date);
                typeCountMap.put(type, typeCountMap.getOrDefault(type, 0) + 1);
            }

            // Ajouter les données au graphique
            for (String date : dataMap.keySet()) {
                Map<String, Integer> typeCountMap = dataMap.get(date);
                for (String type : typeCountMap.keySet()) {
                    int count = typeCountMap.get(type);
                    XYChart.Series<String, Integer> series = new XYChart.Series<>();
                    series.setName(date);
                    series.getData().add(new XYChart.Data<>(type, count));
                    barChart.getData().add(series);
                }
            }

            // Fermer la connexion à la base de données
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //whatapp msg

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //display chart
        chart();
        // Exports all modules to other modules
        Modules.exportAllToAll();

        setUsername();
        showTableClient();

        // Affichage map
        WebEngine webengine;
        webengine = webView.getEngine();

        url = this.getClass().getResource("map/index.html");
        webengine.load(url.toString());

        // Initialisation de engine pour webViewReponse
        engine = webViewReponse.getEngine();

        // Charge la page dans webViewReponse
        loadPage();

        showAgenceData();
        showArticleData();
        addarticlepanes();

        showCompteData();
        initializeCompteListSelection();

        showReclamationData();
        initializeReclamationListSelection();

        showReponseData();
        initializeReponseListSelection();

        showCarteData();
        initializeCarteListSelection();

        showVirementData();
        initializeVirementListSelection();

        showTypeVirementData();
        initializeTypeVirementListSelection();

        showTransactionData();

        connection = Database.getInstance().connectDB();
    }


    //ARTICLES-AGENCES
//agence

    @FXML
    private void MapClick() {

        // Retrieve the latitude and longitude values from JavaScript
        Object latResult = webView.getEngine().executeScript("lat");
        Object lonResult = webView.getEngine().executeScript("lon");

        // Check if latitude and longitude values are not null and are valid numbers
        if (latResult != null && lonResult != null && latResult instanceof Number && lonResult instanceof Number) {
            double lat = ((Number) latResult).doubleValue();
            double lon = ((Number) lonResult).doubleValue();

            // Display latitude and longitude values
            lat_input.setText(String.valueOf(lat));
            lng_input.setText(String.valueOf(lon));
            System.out.println("lat: "+lat+" lon: "+lon);
            // Call JavaScript function to create marker
            webView.getEngine().executeScript("createMarker("+lat+","+lon+")");

            // Retrieve address and set it in the text field
            String address = returnAddressAndHandle(lat, lon);
            System.out.println(lat + " , " + lon + " , " + address);
            agence_adresse_input.setText(String.valueOf(address));
        } else {
            System.err.println("Error: Latitude or Longitude is null or not a number");
        }
    }


    public static String returnAddressAndHandle(double lat, double lon) {
        String address = null;
        try {
            // Create the URL for the reverse geocoding API
            String urlString = "https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=" + lat + "&lon=" + lon+"&accept-language=fr";
            URL url = new URL(urlString);

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse JSON response
                // Attempt to extract address from the 'display_name' field
                Pattern pattern = Pattern.compile("\"display_name\":\"(.*?)\"");
                Matcher matcher = pattern.matcher(response.toString());
                if (matcher.find()) {
                    address = matcher.group(1);
                }
            } else {
                System.err.println("Failed to fetch address. Response code: " + responseCode);
            }

            // Disconnect
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return address;
    }


    public void returntranslation() throws IOException, InterruptedException {
        /*HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://google-translate1.p.rapidapi.com/language/translate/v2/detect"))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("Accept-Encoding", "application/gzip")
                .header("X-RapidAPI-Key", "7a9f9612aemsh9673ccae68eefcap16afb3jsn3ab3b4b815e5")
                .header("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString("q=English%20is%20hard%2C%20but%20detectably%20so"))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());*/
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://currency-exchange.p.rapidapi.com/exchange?from=EUR&to=TND&q=1.0"))
                .header("X-RapidAPI-Key", "7a9f9612aemsh9673ccae68eefcap16afb3jsn3ab3b4b815e5")
                .header("X-RapidAPI-Host", "currency-exchange.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

    }

ArticleService articleService=new ArticleService(connection);
//AgenceService agenceService=new AgenceService(connection);
    public void addAgenceData() {

        if (agence_nom_input.getText().isBlank() || agence_tel_input.getText().isBlank() || agence_adresse_input.getText().isBlank() || lat_input.getText().isBlank() || lng_input.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the mandatory data such as name, telephone number, address, latitude, and longitude.");
            alert.showAndWait();

            return;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Database.getInstance().connectDB();
            String sql = "INSERT INTO Agence(adresse, nom, num_tel, etat, latitude, longitude, data_ajout) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            // Set parameters
            preparedStatement.setString(1, agence_adresse_input.getText());
            preparedStatement.setString(2, agence_nom_input.getText());
            preparedStatement.setString(3, agence_tel_input.getText());
            preparedStatement.setString(4, "Normal"); // Default value for etat
            preparedStatement.setString(6, lat_input.getText());
            preparedStatement.setString(7, lng_input.getText());
            preparedStatement.setDate(5, java.sql.Date.valueOf(LocalDate.now())); // Current date for data_ajout


            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                // Success message
                showAgenceData();
                clearAgenceData();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Failed to add agence data.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }





    public void clearAgenceData() {
        agence_nom_input.clear();
        agence_tel_input.clear();
        agence_adresse_input.clear();
        lat_input.clear();
        lng_input.clear();
    }

    public ObservableList<Agence> listAgenceData() {
        ObservableList<Agence> agenceList = FXCollections.observableArrayList();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getInstance().connectDB();
            String sql = "SELECT * FROM agence";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Agence agenceData = new Agence(
                        resultSet.getInt("id"),
                        resultSet.getString("adresse"),
                        resultSet.getString("nom"),
                        resultSet.getLong("num_tel"),
                        "Normal", // Set etat to "Normal" initially
                        0,
                        resultSet.getDate("data_ajout"), // Set data_ajout to the current date

                        resultSet.getString("latitude"),
                        resultSet.getString("longitude")
                );
                agenceList.add(agenceData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return agenceList;
    }

    public void showAgenceData() {
        ObservableList<Agence> agenceList = listAgenceData();

        // Assuming the table columns are properly defined as TableColumn<Agence,T>
        agence_col_dateajout.setCellValueFactory(new PropertyValueFactory<>("DateAjout"));
        agence_col_adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        agence_col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        agence_col_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        agence_col_tel.setCellValueFactory(new PropertyValueFactory<>("numTel"));
        agence_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        agence_col_longitude.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        agence_col_latitude.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        agence_table.setItems(agenceList);


       //
        agence_listview.setCellFactory(param -> new JFXListCell<>() {

            @Override
            protected void updateItem(Agence agence, boolean empty) {
                super.updateItem(agence, empty);
                if (empty || agence == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Create a VBox to hold the icons and text for each attribute
                    HBox container = new HBox();

                    container.setSpacing(5); // Adjust spacing as needed

                    // Add FontAwesome icons and text for each attribute
                    FontAwesomeIconView addressIcon = new FontAwesomeIconView(FontAwesomeIcon.MAP_MARKER);
                    addressIcon.setFill(Color.web("#3498db")); // Adjust icon color as needed
                    addressIcon.setSize("2em"); // Set icon size
                    Text addressText = new Text(" " + agence.getAdresse());

                    FontAwesomeIconView nameIcon = new FontAwesomeIconView(FontAwesomeIcon.ADDRESS_CARD);
                    nameIcon.setFill(Color.web("#e74c3c")); // Adjust icon color as needed
                    nameIcon.setSize("2em"); // Set icon size
                    Text nameText = new Text(" " + agence.getNom());


                    FontAwesomeIconView telephoneIcon = new FontAwesomeIconView(FontAwesomeIcon.PHONE);
                    telephoneIcon.setFill(Color.web("#2ecc71")); // Adjust icon color as needed
                    telephoneIcon.setSize("2em"); // Set icon size
                    Text telephoneText = new Text(" " + agence.getNumTel());


                    // Add icons and text to the VBox container
                    container.getChildren().addAll(nameIcon, nameText,addressIcon, addressText , telephoneIcon, telephoneText);
                    //container.getChildren().addAll();

                    // Set the VBox container as the graphic for the list cell
                    setText(null);
                    setGraphic(container);
                }
            }

        });
        agence_listview.setItems(agenceList);


    }


    public void show_agences_map()
    {ObservableList<Agence> agenceList = listAgenceData();


        double lat,lon;
        for (Agence agence : agenceList) {
            lat= Double.parseDouble(agence.getLatitude());
            lon= Double.parseDouble(agence.getLongitude());




            webView.getEngine().executeScript("createMarkers("+lat+","+lon+")");

        }

    }
   Integer agence_id;


    public void selectAgenceTableData(){
        int num=agence_table.getSelectionModel().getSelectedIndex();
        Agence agenceData=agence_table.getSelectionModel().getSelectedItem();
        if(num-1 < -1){
            return;
        }

        agence_nom_input.setText(agenceData.getNom());
        agence_tel_input.setText(String.valueOf(agenceData.getNumTel()));
        lat_input.setText(agenceData.getLatitude());
        lng_input.setText(agenceData.getLongitude());
        agence_adresse_input.setText(agenceData.getAdresse());
        agence_id=agenceData.getId();
        System.out.println(agenceData.toString());
        //complete from here..
    }

    public void updateAgenceData(){
        if (agence_nom_input.getText().isBlank() || agence_tel_input.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill the mandatory data such as name, phone number.");
            alert.showAndWait();
            return;
        }

        connection = Database.getInstance().connectDB();
        String sql = "UPDATE agence SET nom=?,num_tel=?,adresse=?,latitude=?,longitude=? WHERE id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, agence_nom_input.getText());
            preparedStatement.setString(2, agence_tel_input.getText());
            preparedStatement.setString(3, agence_adresse_input.getText());
            preparedStatement.setString(4, lat_input.getText());
            preparedStatement.setString(5, lng_input.getText());
            preparedStatement.setLong(6, agence_id);
            int result = preparedStatement.executeUpdate();


            if (result > 0) {
                // Refresh Agence data in the table
             //   clearAgenceData();
                agence_table.getItems().clear();
                showAgenceData();

                // Clear input fields if needed
                // agenceClearData();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Failed to update Agence data.");
                alert.showAndWait();
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public void deleteAgenceData(){
        if (agence_table.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select Agence for deletion.");
            alert.showAndWait();
            return;
        }
        connection = Database.getInstance().connectDB();
        String sql = "DELETE FROM agence WHERE id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,agence_id);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                // Refresh Agence data in the table
                webView.getEngine().executeScript("removeAllMarkers() ");
                showAgenceData();
                // Clear input fields if needed
                // agenceClearData();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("No data present in the Agence table.");
                alert.showAndWait();
            }
        } catch (Exception err) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
    }


    public void openexchangewindow(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("viewconverter.fxml"));
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }


    }

//Articles

    public ObservableList<Article> listArticles()
    {

        ObservableList<Article> articleList = FXCollections.observableArrayList();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getInstance().connectDB();
            String sql = "SELECT * FROM article";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Article articleData = new Article(
                        resultSet.getInt("id"),
                        resultSet.getString("contenu"),
                        resultSet.getString("titre"),
                        resultSet.getString("image")
                );
                articleList.add(articleData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return articleList;


    }
VBox articlesContainer;
    public void addarticlepanes(){
        ObservableList<Article> articles = listArticles();

         articlesContainer = new VBox(10); // Container to hold article rows with spacing of 10
        articlesContainer.setAlignment(Pos.CENTER); // Align articles in the center vertically

        double articleWidth = 250; // Width of the article pane
        double articleHeight = 250; // Height of the article pane
        int articlesPerRow = 0; // Number of articles added in the current row

        HBox currentRow = new HBox(10); // Initialize the first row with spacing of 10
        currentRow.setAlignment(Pos.CENTER); // Align articles in the center horizontally

        for(Article article : articles) {
            // Create a new pane for each article
            ArticlePane articlePane = new ArticlePane(article.getId()); // Assuming getId() returns the ID of the article
            articlePane.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
            articlePane.setPrefWidth(articleWidth);

            articlePane.setPrefHeight(articleHeight);

            // Create ImageView for the image
            ImageView imageView = new ImageView(new Image("file:C:/" + article.getImage()));
            imageView.setFitWidth(articleWidth-20);
            imageView.setFitHeight(articleHeight-20);
            imageView.setOpacity(0.35);

            Text titleLabel = new Text(article.getTitre());
            Text contentText = new Text(article.getContenu());

                // Adjust layout positions and wrapping width
            titleLabel.setLayoutY(20);
            titleLabel.setLayoutX(20);
            titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
            titleLabel.setWrappingWidth(articleWidth - 20); // Set the wrapping width for title

            contentText.setLayoutY(60); // Adjust position so it's below the title
            contentText.setLayoutX(10);
            contentText.setWrappingWidth(articleWidth - 20); // Set the wrapping width for content

            // Add nodes to the pane
            articlePane.getChildren().addAll(imageView, titleLabel, contentText);
            // Add the article pane to the current row
            currentRow.getChildren().add(articlePane);
            articlesPerRow++;

            // If the current row is full, add it to the articles container and start a new row
            if (articlesPerRow >= 3) {
                articlesContainer.getChildren().add(currentRow);
                currentRow = new HBox(10);
                currentRow.setAlignment(Pos.CENTER); // Align articles in the center horizontally
                articlesPerRow = 0;
            }
        }

        // If there are remaining articles in the last row, add it to the articles container
        if (!currentRow.getChildren().isEmpty()) {
            articlesContainer.getChildren().add(currentRow);
        }

        // Add padding to the articles container
        articlesContainer.setPadding(new Insets(10));

        // Set the container as the content of the Tab
        articles_front_anchorpane.getChildren().add(articlesContainer);

    }

    @FXML
    AnchorPane mainAnchorpane;

    @FXML
    private void list_article_comments() {
        for (Node node : articlesContainer.getChildren()) {
            if (node instanceof HBox) {
                HBox row = (HBox) node;
                for (Node articleNode : row.getChildren()) {
                    if (articleNode instanceof ArticlePane) {
                        ArticlePane articlePane = (ArticlePane) articleNode;

                        articlePane.setOnMouseClicked(event -> {
                            int articleId = articlePane.getArticleId();

                            // Clear existing content of comments_pane
                            comments_pane.getChildren().clear();

                            // Clone the clicked article pane
                            ArticlePane clonedPane = cloneArticlePane(articlePane);

                            // Retrieve comments for the article
                            ObservableList<Commentaire> comments = listCommentsById( clonedPane.getArticleId());
                            VBox commentContainer = createCommentContainer(comments);

                            // Create Rating and TextArea for adding comments
                            Rating rating = new Rating();
                            TextArea add_comment_field = createCommentField();

                            // Layout for adding comments and existing comments
                            HBox addCommentAndCommentsHBox = new HBox(100);
                            addCommentAndCommentsHBox.getChildren().addAll(add_comment_field);
                        //    addCommentAndCommentsHBox.setPrefHeight(100); // Adjust the value as needed

                            // Add the cloned article pane and comments layout to the comments_pane
                            VBox vBox = new VBox(100); // No spacing between children
                            vBox.getChildren().addAll(clonedPane, addCommentAndCommentsHBox);
                            comments_pane.getChildren().add(vBox);

                            // Activate the comments_pane
                            ActivateThisAnchor(comments_pane, comments_btn);
                        });
                    }
                }
            }
        }
    }

    // Helper method to clone the article pane
    private ArticlePane cloneArticlePane(ArticlePane articlePane) {
        ArticlePane clonedPane = new ArticlePane();
        clonedPane.setStyle("-fx-border-width: 0;");
        clonedPane.setPrefSize(articlePane.getPrefWidth() + 100, articlePane.getPrefHeight() + 100);
        clonedPane.setArticleId(articlePane.getArticleId());
        Integer textcounter=0;
        for (Node child : articlePane.getChildren()) {
            if (child instanceof ImageView) {
                ImageView imageView = new ImageView(((ImageView) child).getImage());
                imageView.setFitWidth(((ImageView) child).getFitWidth() + 150);
                imageView.setFitHeight(((ImageView) child).getFitHeight() + 200);
                imageView.setOpacity(((ImageView) child).getOpacity() + 0.4);
                clonedPane.getChildren().add(imageView);
            } else if (child instanceof Text) {
                Text text = new Text(((Text) child).getText());
                text.setLayoutX(((Text) child).getLayoutX() + 450);
                text.setLayoutY(((Text) child).getLayoutY() + 100);
                text.setStyle(((Text) child).getStyle());
                text.setWrappingWidth(((Text) child).getWrappingWidth());
                textcounter++;

                if (textcounter==2) {
                    ObservableList<Commentaire> comments = listCommentsById(clonedPane.getArticleId()); // Replace with actual comments list
                    VBox commentContainer = createCommentContainer(comments);

                    commentContainer.setLayoutX(((Text) child).getLayoutX() + 400);
                    commentContainer.setLayoutY(((Text) child).getLayoutY() + 350);
                    Label commentslabel=new Label("Comments:");
                    commentslabel.setLabelFor(commentContainer);
                    commentslabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
                    commentslabel.setLayoutX(((Text) child).getLayoutX() + 400);
                    commentslabel.setLayoutY(((Text) child).getLayoutY() + 300);
                    clonedPane.getChildren().addAll(text,commentslabel,commentContainer);

                }
                else
                clonedPane.getChildren().add(text);
            }
        }

        // Create the comment container


        // Create the add comment field


        return clonedPane;
    }

    private VBox createCommentContainer(ObservableList<Commentaire> comments) {
        int pageSize = 2; // Number of comments per page
        int pageCount = (int) Math.ceil((double) comments.size() / pageSize);

        Pagination pagination = new Pagination(pageCount, 0);
        pagination.setMaxPageIndicatorCount(5); // Adjust as needed
        pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
        pagination.setStyle("-fx-arrows-visible:false;");
        Animations.fadeIn(pagination , Duration.millis(2000));
        pagination.setPageFactory(index -> {
            HBox pageContainer = new HBox(10); // Adjust spacing as needed
            pageContainer.setAlignment(Pos.CENTER); // Align comments horizontally


            int startIndex = index * pageSize;
            int endIndex = Math.min(startIndex + pageSize, comments.size());

            for (int i = startIndex; i < endIndex; i++) {
                Commentaire comment = comments.get(i);
                String fullname = getEntityProperty("client", comment.getUserId(), "nom") + " " + getEntityProperty("client", comment.getUserId(), "prenom");
                String fonction = getEntityProperty("client",comment.getUserId(),"profession");
                String avatarimagepath = getEntityProperty("client", comment.getUserId(), "photo");
                String filePath = "C:/" + avatarimagepath; // Construct the file path

                Image avatar;
                File file = new File(filePath);
                if (file.exists()) { // Check if the file exists
                    avatar = new Image(file.toURI().toString());
                    // Now you can use the 'avatar' image object as needed
                } else {
                    avatar = new Image("file:///C:/clients_photos/avatar.png");
                    // Handle the case where the file doesn't exist
                }
                ImageView imageView = new ImageView(avatar);
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);





               // header.getStyleClass().add("comment-title");

            //    System.out.println("fullname: "+fullname+"foncition="+fonction);
                // Create a new Card for each comment
                Card commentCard = new Card();
                commentCard.getStyleClass().add("comment-card");

                Label titleLabel = new Label( fullname);
                titleLabel.getStyleClass().add("comment-title");

                Label descriptionLabel = new Label(fonction);
                descriptionLabel.getStyleClass().add("comment-description");

                HBox headerBox = new HBox(10, imageView, titleLabel, descriptionLabel);
                headerBox.setMaxHeight(0);
                headerBox.setPrefHeight(0);


                Text text = new Text(comment.getContenu());
                text.getStyleClass().add("comment-text");

                // Separator line between comment cards
                Separator separator = new Separator(Orientation.VERTICAL);
                separator.setPadding(new Insets(0, 10, 0, 10));

                // X icon for deleting comments
                FontIcon deleteIcon = new FontIcon(Material2AL.CLOSE);
                deleteIcon.getStyleClass().add("delete-icon");
                deleteIcon.setCursor(Cursor.HAND);

                // Event listener for deleting comments (to be added later)
                deleteIcon.setOnMouseClicked(event -> {
                    // Empty event listener for deleting comments
                    // Add your delete logic here
                });

                // Footer containing the X icon
                HBox footer = new HBox(10, deleteIcon);
                footer.setAlignment(Pos.CENTER_LEFT);

                commentCard.setHeader(headerBox);
               // commentCard.setHeader(header);
                commentCard.setBody(text);
                commentCard.setFooter(footer);

                pageContainer.getChildren().addAll(commentCard, separator);
            }

            return pageContainer;
        });

        VBox commentContainer = new VBox(10, pagination); // Add pagination control to comment container
        return commentContainer;
    }


    // Helper method to create the TextArea for adding comments
    private TextArea createCommentField() {
        TextArea add_comment_field = new TextArea();
        add_comment_field.setPrefRowCount(3);
        add_comment_field.setPrefColumnCount(24);
        add_comment_field.setPromptText("Add a comment");

        return add_comment_field;
    }
    //


    public ObservableList<Commentaire> listCommentsById(Integer id) {
        ObservableList<Commentaire> commentList = FXCollections.observableArrayList();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getInstance().connectDB();
            String sql = "SELECT * FROM commentaire WHERE article_id = " + id;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Commentaire commentaireData = new Commentaire(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("article_id"),
                        resultSet.getString("contenu"),
                        resultSet.getInt("note"),
                        resultSet.getDate("date")
                );
                commentList.add(commentaireData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return commentList;
    }

    // This method retrieves the specified property of an entity identified by its ID
    public static String getEntityProperty(String entityName, Integer id, String propertyName) {
        String propertyValue = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = Database.getInstance().connectDB(); // Connect to the database
            String sql = "SELECT " + propertyName + " FROM " + entityName + " WHERE id = " + id; // Construct the SQL query
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                // Retrieve the value of the specified property
                propertyValue = resultSet.getString(propertyName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return propertyValue;
    }

    private boolean isDrawerOpen = false;
    @FXML
    public void onDrawerButtonClick() {
        isDrawerOpen=left_pane.isVisible();
        if (isDrawerOpen) {
            // If drawer is open, close it
TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), content_pane);
            transition.setToX(0); // Move content pane to original position
            transition.play();
            closeDrawer();
            closeDrawer();
  //   content_pane.setLayoutX(content_pane.getLayoutX()-100);
        } else {
            // If drawer is closed, open it
            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), content_pane);
            transition.setToX(left_pane.getWidth()-80); // Move content pane to the right by the width of the left pane
            transition.play();
            openDrawer();

           // content_pane.setLayoutX(content_pane.getLayoutX()+100);
        }
    }

    private void openDrawer() {
        Animations.slideInLeft(left_pane, Duration.millis(1000)).playFromStart();
        left_pane.setVisible(true);
        // You can add animation/effects if needed
        isDrawerOpen = true;
    }

    private void closeDrawer() {
        Animations.slideOutLeft(left_pane, Duration.millis(1000)).playFromStart();

        PauseTransition pause = new PauseTransition(Duration.millis(1000)); // Adjust the duration of the delay as needed
        pause.setOnFinished(event -> {
            left_pane.setVisible(false);
            isDrawerOpen = false;
        });
        pause.play();
    }
@FXML
    public void switchfrontbackarticles(){

        if (articles_front_anchorpane.isVisible()) {
            articles_front_anchorpane.setVisible(false);
            articles_back_anchorpane.setVisible(true);
        }
        else
        {
            articles_back_anchorpane.setVisible(false);
            articles_front_anchorpane.setVisible(true);
        }
}


    // Method to retrieve the ID of the last article added from the database
    private int getLastArticleIdFromDatabase() {
        int lastArticleId = 0;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getInstance().connectDB();
            String sql = "SELECT id FROM article ORDER BY id DESC LIMIT 1";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                lastArticleId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return lastArticleId;
    }

    // Global variable to store the path of the uploaded image
    private String imagePath = "";

    @FXML
    private void uploadImageArticle(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File to Upload");

        // Set file extension filters if needed
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            try {
                // Retrieve the articles from the database to get the last article ID
                int lastArticleId = getLastArticleIdFromDatabase();

                // Generate the new article ID by incrementing the last article ID
                int newArticleId = lastArticleId + 1;

                // Rename the file with the new article ID
                String fileName = "article_" + newArticleId+".png" ;
                File destination = new File("C:/uploads/" + fileName);
                Files.copy(selectedFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Update the global variable with the image path
                imagePath = "uploads/" + fileName;

                // Update the image path in the database or store it temporarily to be used when adding an article
                // For now, you can just print the path
                article_upload_status.setText(article_upload_status.getText() + " Success");
                article_upload_status.setStyle("-fx-text-fill: green;");
                System.out.println("Image uploaded and saved to: " + destination.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No file selected.");
        }
    }

    @FXML
    public void addArticleData() {
        if (article_titre_input.getText().isBlank() || article_contenu_input.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the mandatory data such as title and content.");
            alert.showAndWait();
            return;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Database.getInstance().connectDB();
            String sql = "INSERT INTO article(id_agent_id, contenu, image, titre, date_pub) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            // Set parameters
            preparedStatement.setInt(1, 11); // Assuming id_agent is 1
            preparedStatement.setString(2, article_contenu_input.getText());
            preparedStatement.setString(3, imagePath); // Use the global variable for the image path
            preparedStatement.setString(4, article_titre_input.getText());
            preparedStatement.setDate(5, java.sql.Date.valueOf(LocalDate.now())); // Current date for date_pub

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                // Success message
                showArticleData();
                //  clearArticleData();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Failed to add article data.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ObservableList<Article> listArticleData() {
        ObservableList<Article> articleList = FXCollections.observableArrayList();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getInstance().connectDB();
            String sql = "SELECT * FROM article";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                Article articleData = new Article(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_agent_id"),
                        resultSet.getString("contenu"),
                        resultSet.getString("image"),
                        resultSet.getString("titre"),
                        resultSet.getDate("date_pub")
                );
                articleList.add(articleData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return articleList;
    }


    private void validate(CheckBox checkBox,Article a, Event event) {
        // Validate here
        event.consume();

        checkBox.setSelected(!checkBox.isSelected());
        a.setSelected(true);
    }
    public void showArticleData() {
       ObservableList<Article> articleList = listArticleData();


        article_listview.setCellFactory(param -> new JFXListCell<>() {

            @Override
            protected void updateItem(Article article, boolean empty) {
                super.updateItem(article, empty);
                if (empty || article == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Create a VBox to hold the icons and text for each attribute
                    HBox container = new HBox();
                    container.setSpacing(5); // Adjust spacing as needed

                    // Add FontAwesome icons and text for each attribute
                    FontAwesomeIconView titleIcon = new FontAwesomeIconView(FontAwesomeIcon.ADDRESS_CARD);
                    titleIcon.setFill(Color.web("#e74c3c")); // Adjust icon color as needed
                    titleIcon.setSize("2em"); // Set icon size
                    Text titleText = new Text(" " + article.getTitre());

                    FontAwesomeIconView imageIcon = new FontAwesomeIconView(FontAwesomeIcon.IMAGE);
                    imageIcon.setFill(Color.web("#3498db")); // Adjust icon color as needed
                    imageIcon.setSize("2em"); // Set icon size
                    Text imageText = new Text(" " + article.getImage());

                    FontAwesomeIconView dateIcon = new FontAwesomeIconView(FontAwesomeIcon.CALENDAR_ALT);
                    dateIcon.setFill(Color.web("#2ecc71")); // Adjust icon color as needed
                    dateIcon.setSize("2em"); // Set icon size
                    Text dateText = new Text(" " + article.getDatePub());

                    // Add icons and text to the VBox container
                    container.getChildren().addAll(titleIcon, titleText, imageIcon, imageText, dateIcon, dateText);

                    // Set the VBox container as the graphic for the list cell
                    setText(null); // Clear text
                    setGraphic(container);
                }
            }

        });

        /*  Styles.toggleStyleClass(article_listview, Styles.BORDERED);
        Styles.toggleStyleClass(article_listview, Styles.STRIPED);
        article_listview.setEditable(true);*/
        article_listview.setItems(articleList);

    }

    public void selectArticleTableData(){
        int num=article_table.getSelectionModel().getSelectedIndex();
        Article articleData=article_table.getSelectionModel().getSelectedItem();
        if(num-1 < -1){
            return;
        }

        article_titre_input.setText(articleData.getTitre());
        article_contenu_input.setText(articleData.getContenu());


    }

    public void deleteSelectedArticles() {
        System.out.println("hello");
        ObservableList<Article> selectedArticles = article_table.getItems()
                .filtered(article -> article.isSelected().get());
        if (selectedArticles.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select articles for deletion.");
            alert.showAndWait();
            return;
        }

        connection = Database.getInstance().connectDB();
        String sql = "DELETE FROM article WHERE id=?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            for (Article article : selectedArticles) {
                preparedStatement.setInt(1, article.getId()); // Assuming 'getId()' returns article's ID
                preparedStatement.addBatch(); // Add delete query to batch

            }
            int[] results = preparedStatement.executeBatch(); // Execute batch
            int totalDeleted = Arrays.stream(results).sum(); // Total number of deleted rows

            if (totalDeleted > 0) {
                // Refresh article data in the table
                showArticleData();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("No data present in the article table.");
                alert.showAndWait();
            }
        } catch (SQLException err) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        } finally {
            // Close resources
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // Handle close exceptions
            }
        }
    }

    @FXML
    private Button comptes_btn;

    @FXML
    private AnchorPane comptes_pane;

    @FXML//COMPTE
    private AnchorPane compte_anchorpane;

    @FXML
    private Label compte_title;

    @FXML
    private TextField compte_type_input;

    @FXML
    private TextField compte_solde_input;

    @FXML
    private DatePicker compte_date_creation_input;

    @FXML
    private DatePicker compte_date_fermeture_input;

    @FXML
    private TextField compte_etat_input;

    @FXML
    private TextField compte_rib_input;

    @FXML
    private Button compte_add_button;

    @FXML
    private Button compte_clear_button;

    @FXML
    private JFXListView<Compte> comptes_listview;
    @FXML
    public void addOrUpdateCompteData() {
        // Input validation
        if (compte_type_input.getText().isBlank() || compte_solde_input.getText().isBlank() || compte_date_creation_input.getValue() == null) {
            showAlertCompte(Alert.AlertType.INFORMATION, "Message", null, "Please fill all mandatory data such as type, solde, and date de creation.");
            return;
        }

        // Create Compte object from FXML input fields
        Compte compte = new Compte();
        compte.setType(compte_type_input.getText());
        compte.setSolde(Double.parseDouble(compte_solde_input.getText()));
        compte.setDateCreation(java.sql.Date.valueOf(compte_date_creation_input.getValue()));
        compte.setDateFermeture(compte_date_fermeture_input.getValue() != null ? java.sql.Date.valueOf(compte_date_fermeture_input.getValue()) : null);
        compte.setEtat(compte_etat_input.getText());
        compte.setRib(compte_rib_input.getText());

        // Determine if an existing compte is selected
        Compte selectedCompte = comptes_listview.getSelectionModel().getSelectedItem();
        if (selectedCompte != null) {
            // If a compte is selected, update its data
            compte.setId(selectedCompte.getId()); // Set the ID of the selected compte
            compte.setIdAgence(selectedCompte.getIdAgence());
            compte.setIdUser(selectedCompte.getIdUser());
            if (CompteService.getInstance().edit(compte)) {
                showAlertCompte(Alert.AlertType.INFORMATION, "Message", null, "Compte updated successfully.");
                showCompteData();
                clearCompteData();
            } else {
                showAlertCompte(Alert.AlertType.ERROR, "Error Message", null, "Failed to update compte data.");
            }
        } else {
            // If no compte is selected, add a new compte
            if (CompteService.getInstance().add(compte)) {
                showAlertCompte(Alert.AlertType.INFORMATION, "Message", null, "Compte added successfully.");
                showCompteData();
                clearCompteData();
            } else {
                showAlertCompte(Alert.AlertType.ERROR, "Error Message", null, "Failed to add compte data.");
            }
        }
    }

    // Method to show Compte data
    public void showCompteData() {
        // Retrieve Compte data from database using CompteService
        List<Compte> compteList = CompteService.getInstance().getAll();

        ObservableList<Compte> observableCompteList = FXCollections.observableArrayList(compteList);

        // Assuming the listview is properly defined
        comptes_listview.getItems().clear();
        comptes_listview.setCellFactory(param -> new JFXListCell<>() {

            @Override
            protected void updateItem(Compte compte, boolean empty) {
                super.updateItem(compte, empty);
                if (empty || compte == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Create a VBox to hold the icons and text for each attribute
                    HBox container = new HBox();
                    container.setSpacing(5); // Adjust spacing as needed

                    // Add FontAwesome icons and text for each attribute
                    FontAwesomeIconView typeIcon = new FontAwesomeIconView(FontAwesomeIcon.FILE);
                    typeIcon.setFill(Color.web("#3498db")); // Adjust icon color as needed
                    typeIcon.setSize("2em"); // Set icon size
                    Text typeText = new Text(" " + compte.getType());

                    FontAwesomeIconView soldeIcon = new FontAwesomeIconView(FontAwesomeIcon.DOLLAR);
                    soldeIcon.setFill(Color.web("#e74c3c")); // Adjust icon color as needed
                    soldeIcon.setSize("2em"); // Set icon size
                    Text soldeText = new Text(" " + compte.getSolde());

                    FontAwesomeIconView etatIcon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE);
                    etatIcon.setFill(Color.web("#2ecc71")); // Adjust icon color as needed
                    etatIcon.setSize("2em"); // Set icon size
                    Text etatText = new Text(" " + compte.getEtat());

                    // Add icons and text to the VBox container
                    container.getChildren().addAll(typeIcon, typeText, soldeIcon, soldeText, etatIcon, etatText);

                    // Set the VBox container as the graphic for the list cell
                    setText(null); // Clear text
                    setGraphic(container);
                }
            }

        });
        comptes_listview.setItems(observableCompteList);
    }

    // Method to delete Compte data
    @FXML
    public void deleteCompteData() {
        // Input validation
        if (comptes_listview.getSelectionModel().isEmpty()) {
            showAlertCompte(Alert.AlertType.INFORMATION, "Message", null, "Please select a compte for deletion.");
            return;
        }

        // Confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete this compte?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        // Proceed with deletion if confirmed
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Retrieve selected compte from listview
            Compte selectedCompte = comptes_listview.getSelectionModel().getSelectedItem();
            // Delete Compte data using CompteService
            if (CompteService.getInstance().delete(selectedCompte.getId())) {
                showAlertCompte(Alert.AlertType.INFORMATION, "Message", null, "Compte deleted successfully.");
                showCompteData(); // Refresh Compte data in the listview
            } else {
                showAlertCompte(Alert.AlertType.ERROR, "Error Message", null, "Failed to delete compte data.");
            }
        }
    }

    // Method to show alert messages
    private void showAlertCompte(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    // Method to clear Compte data from input fields
    @FXML
    private void clearCompteData() {
        // Clear input fields
        compte_type_input.clear();
        compte_solde_input.clear();
        compte_date_creation_input.setValue(null);
        compte_date_fermeture_input.setValue(null);
        compte_etat_input.clear();
        compte_rib_input.clear();

        // Unset the selected compte
        comptes_listview.getSelectionModel().clearSelection();
    }
    // Method to select Compte data
    @FXML
    public void selectCompteData() {
        // Get the selected Compte from the listview
        Compte selectedCompte = comptes_listview.getSelectionModel().getSelectedItem();

        if (selectedCompte != null) {
            // Set the selected Compte's data to the input fields
            System.out.println(selectedCompte);
            compte_type_input.setText(selectedCompte.getType());
            compte_solde_input.setText(String.valueOf(selectedCompte.getSolde()));
            compte_etat_input.setText(selectedCompte.getEtat());
            compte_rib_input.setText(selectedCompte.getRib());

            // Convert java.sql.Date to LocalDate for creation date
            if (selectedCompte.getDateCreation() != null) {
                java.util.Date utilDateCreation = new java.util.Date(selectedCompte.getDateCreation().getTime());
                LocalDate dateCreation = utilDateCreation.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                compte_date_creation_input.setValue(dateCreation);
            } else {
                compte_date_creation_input.setValue(null);
            }

            // Convert java.sql.Date to LocalDate for fermeture date
            if (selectedCompte.getDateFermeture() != null) {
                java.util.Date utilDateFermeture = new java.util.Date(selectedCompte.getDateFermeture().getTime());
                LocalDate dateFermeture = utilDateFermeture.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                compte_date_fermeture_input.setValue(dateFermeture);
            } else {
                compte_date_fermeture_input.setValue(null);
            }
        }
    }

    // Method to initialize the selection event for the list view
    private void initializeCompteListSelection() {
        comptes_listview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Call the selectCompteData method when a new item is selected
            selectCompteData();
        });
    }
    //FIN COMPTES


    //CARTES

    @FXML
    private AnchorPane carte_back_anchorpane;

    @FXML
    private Label carte_label;

    @FXML
    private TextField carte_type_input;

    @FXML
    private DatePicker carte_date_emission_input;

    @FXML
    private DatePicker carte_date_expiration_input;

    @FXML
    private TextField carte_cvv_input;

    @FXML
    private TextField carte_plafond_input;

    @FXML
    private Button carte_add_button;

    @FXML
    private TextField carte_etat_input;

    @FXML
    private Button carte_clear_button;

    @FXML
    private ListView<Carte> carte_listview;
    // Method to add Carte data
    @FXML
    public void addOrUpdateCarteData() {
        // Input validation
        if (carte_type_input.getText().isBlank() || carte_cvv_input.getText().isBlank() || carte_date_emission_input.getValue() == null) {
            showAlertCarte(Alert.AlertType.INFORMATION, "Message", null, "Please fill all mandatory data such as type, cvv, and date d'émission.");
            return;
        }

        // Create Carte object from FXML input fields
        Carte carte = new Carte();
        // Set the Carte object with the input data
        carte.setType(carte_type_input.getText());
        carte.setCvv(Integer.parseInt(carte_cvv_input.getText()));
        carte.setDateEmission(java.sql.Date.valueOf(carte_date_emission_input.getValue()));
        carte.setDateExpiration(carte_date_expiration_input.getValue() != null ? java.sql.Date.valueOf(carte_date_expiration_input.getValue()) : null);
        carte.setPlafond(carte_plafond_input.getText());
        carte.setEtat(carte_etat_input.getText());

        // Determine if an existing carte is selected
        Carte selectedCarte = carte_listview.getSelectionModel().getSelectedItem();
        if (selectedCarte != null) {
            // If a carte is selected, update its data
            carte.setId(selectedCarte.getId()); // Set the ID of the selected carte
            carte.setNumCompte(selectedCarte.getNumCompte());
            if (CarteService.getInstance().edit(carte)) {
                showAlertCarte(Alert.AlertType.INFORMATION, "Message", null, "Carte updated successfully.");
                showCarteData();
                clearCarteData();
            } else {
                showAlertCarte(Alert.AlertType.ERROR, "Error Message", null, "Failed to update carte data.");
            }
        } else {
            // If no carte is selected, add a new carte
            if (CarteService.getInstance().add(carte)) {
                showAlertCarte(Alert.AlertType.INFORMATION, "Message", null, "Carte added successfully.");
                showCarteData();
                clearCarteData();
            } else {
                showAlertCarte(Alert.AlertType.ERROR, "Error Message", null, "Failed to add carte data.");
            }
        }
    }
    // Method to show Carte data
    public void showCarteData() {
        // Retrieve Carte data from database using CarteService
        List<Carte> carteList = CarteService.getInstance().getAll();

        ObservableList<Carte> observableCarteList = FXCollections.observableArrayList(carteList);

        // Assuming the listview is properly defined
        carte_listview.getItems().clear();
        carte_listview.setCellFactory(param -> new JFXListCell<>() {

            @Override
            protected void updateItem(Carte carte, boolean empty) {
                super.updateItem(carte, empty);
                if (empty || carte == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Create a VBox to hold the icons and text for each attribute
                    HBox container = new HBox();
                    container.setSpacing(5); // Adjust spacing as needed

                    // Add FontAwesome icons and text for each attribute
                    FontAwesomeIconView dateEmissionIcon = new FontAwesomeIconView(FontAwesomeIcon.CALENDAR);
                    dateEmissionIcon.setFill(Color.web("#3498db")); // Adjust icon color as needed
                    Text dateEmissionText = new Text(" Date Emission: " + carte.getDateEmission());

                    FontAwesomeIconView dateExpirationIcon = new FontAwesomeIconView(FontAwesomeIcon.CALENDAR);
                    dateExpirationIcon.setFill(Color.web("#e74c3c")); // Adjust icon color as needed
                    Text dateExpirationText = new Text(" Date Expiration: " + carte.getDateExpiration());

                    FontAwesomeIconView cvvIcon = new FontAwesomeIconView(FontAwesomeIcon.KEY);
                    cvvIcon.setFill(Color.web("#2ecc71")); // Adjust icon color as needed
                    Text cvvText = new Text(" CVV: " + carte.getCvv());

                    FontAwesomeIconView plafondIcon = new FontAwesomeIconView(FontAwesomeIcon.DOLLAR);
                    plafondIcon.setFill(Color.web("#3498db")); // Adjust icon color as needed
                    Text plafondText = new Text(" Plafond: " + carte.getPlafond());

                    FontAwesomeIconView typeIcon = new FontAwesomeIconView(FontAwesomeIcon.LIST);
                    typeIcon.setFill(Color.web("#e74c3c")); // Adjust icon color as needed
                    Text typeText = new Text(" Type: " + carte.getType());

                    FontAwesomeIconView etatIcon = new FontAwesomeIconView(FontAwesomeIcon.ADJUST);
                    etatIcon.setFill(Color.web("#2ecc71")); // Adjust icon color as needed
                    Text etatText = new Text(" Etat: " + carte.getEtat());


                    // Add icons and text to the VBox container
                    container.getChildren().addAll(dateEmissionIcon, dateEmissionText, dateExpirationIcon, dateExpirationText,
                            cvvIcon, cvvText, plafondIcon, plafondText, typeIcon, typeText,
                            etatIcon, etatText);

                    // Set the VBox container as the graphic for the list cell
                    setText(null); // Clear text
                    setGraphic(container);
                }
            }

        });
            carte_listview.setItems(observableCarteList);
    }

    // Method to delete Carte data
    public void deleteCarteData() {
        // Input validation
        if (carte_listview.getSelectionModel().isEmpty()) {
            showAlertCarte(Alert.AlertType.INFORMATION, "Message", null, "Please select a carte for deletion.");
            return;
        }

        // Confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete this carte?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        // Proceed with deletion if confirmed
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Retrieve selected carte from listview
            Carte selectedCarte = carte_listview.getSelectionModel().getSelectedItem();
            // Delete Carte data using CarteService
            if (CarteService.getInstance().delete(selectedCarte.getId())) {
                showAlertCarte(Alert.AlertType.INFORMATION, "Message", null, "Carte deleted successfully.");
                showCarteData(); // Refresh Carte data in the listview
            } else {
                showAlertCarte(Alert.AlertType.ERROR, "Error Message", null, "Failed to delete carte data.");
            }
        }
    }

    // Method to show alert messages
    private void showAlertCarte(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    // Method to clear Carte data from input fields
    // Method to clear Carte data from input fields and unset the selected carte
    @FXML
    private void clearCarteData() {
        // Clear input fields
        carte_type_input.clear();
        carte_cvv_input.clear();
        carte_date_emission_input.setValue(null);
        carte_date_expiration_input.setValue(null);
        carte_plafond_input.clear();
        carte_etat_input.clear();

        // Unset the selected carte
        carte_listview.getSelectionModel().clearSelection();
    }

    // Method to select Carte data
    public void selectCarteData() {
        // Get the selected Carte from the listview
        Carte selectedCarte = carte_listview.getSelectionModel().getSelectedItem();

        if (selectedCarte != null) {
            // Set the selected Carte's data to the input fields
            System.out.println(selectedCarte);
            carte_type_input.setText(selectedCarte.getType());
            carte_cvv_input.setText(String.valueOf(selectedCarte.getCvv()));
            carte_plafond_input.setText(selectedCarte.getPlafond());
            carte_etat_input.setText(selectedCarte.getEtat());

            // Convert java.sql.Date to LocalDate for emission date
            if (selectedCarte.getDateEmission() != null) {
                java.util.Date utilDateEmission = new java.util.Date(selectedCarte.getDateEmission().getTime());
                LocalDate dateEmission = utilDateEmission.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                carte_date_emission_input.setValue(dateEmission);
            } else {
                carte_date_emission_input.setValue(null);
            }

            // Convert java.sql.Date to LocalDate for expiration date
            if (selectedCarte.getDateExpiration() != null) {
                java.util.Date utilDateExpiration = new java.util.Date(selectedCarte.getDateExpiration().getTime());
                LocalDate dateExpiration = utilDateExpiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                carte_date_expiration_input.setValue(dateExpiration);
            } else {
                carte_date_expiration_input.setValue(null);
            }
        }
    }


    // Method to initialize the selection event for the list view
    private void initializeCarteListSelection() {
        carte_listview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Call the selectCarteData method when a new item is selected
            selectCarteData();
        });
    }

    //FIN CARTES

    //TYPE_VIREMENT
    @FXML
    private AnchorPane typeVirement_anchorpane;
    @FXML
    private TextField typeVirement_nom_input;

    @FXML
    private TextField typeVirement_frais_input;

    @FXML
    private Button typeVirement_add_button;

    @FXML
    private Button typeVirement_add_button1;

    @FXML
    private ListView<TypeVirement> typeVirement_listview;

    public void addTypeVirementData() {
        // Input validation
        if (typeVirement_nom_input.getText().isBlank() || typeVirement_frais_input.getText().isBlank()) {
            showalerttypevirement(Alert.AlertType.INFORMATION, "Message", null, "Please fill all mandatory data such as nom and frais.");
            return;
        }

        // Create TypeVirement object from FXML input fields
        TypeVirement typeVirement = new TypeVirement();
        typeVirement.setNom(typeVirement_nom_input.getText());
        typeVirement.setFrais(Double.parseDouble(typeVirement_frais_input.getText()));

        // Add TypeVirement data using TypeVirementService
        if (TypeVirementService.getInstance().add(typeVirement)) {
            showalerttypevirement(Alert.AlertType.INFORMATION, "Message", null, "TypeVirement added successfully.");
            showTypeVirementData();
            clearTypeVirementData();
        } else {
            showalerttypevirement(Alert.AlertType.ERROR, "Error Message", null, "Failed to add TypeVirement data.");
        }
    }

    // Method to show TypeVirement data
    public void showTypeVirementData() {
        // Retrieve TypeVirement data from database using TypeVirementService
        List<TypeVirement> typeVirementList = TypeVirementService.getInstance().getAll();
        ObservableList<TypeVirement> observableTypeVirementList = FXCollections.observableArrayList(typeVirementList);

        // Assuming the listview is properly defined
        // Clear existing items

        typeVirement_listview.setCellFactory(param -> new JFXListCell<>() {

            @Override
            protected void updateItem(TypeVirement typeVirement, boolean empty) {
                super.updateItem(typeVirement, empty);
                if (empty || typeVirement == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Create a VBox to hold the icons and text for each attribute
                    HBox container = new HBox();
                    container.setSpacing(5); // Adjust spacing as needed

                    // Add FontAwesome icons and text for each attribute
                    FontAwesomeIconView fraisIcon = new FontAwesomeIconView(FontAwesomeIcon.DOLLAR);
                    fraisIcon.setFill(Color.web("#3498db")); // Adjust icon color as needed
                    Text fraisText = new Text(" Frais: " + typeVirement.getFrais());

                    FontAwesomeIconView nomIcon = new FontAwesomeIconView(FontAwesomeIcon.TAG);
                    nomIcon.setFill(Color.web("#e74c3c")); // Adjust icon color as needed
                    Text nomText = new Text(" Nom: " + typeVirement.getNom());

                    // Add icons and text to the VBox container
                    container.getChildren().addAll(fraisIcon, fraisText, nomIcon, nomText);

                    // Set the VBox container as the graphic for the list cell
                    setText(null); // Clear text
                    setGraphic(container);
                }
            }

        });
        typeVirement_listview.setItems(observableTypeVirementList);
    }

    // Method to delete TypeVirement data
    public void deleteTypeVirementData() {
        // Input validation
        if (typeVirement_listview.getSelectionModel().isEmpty()) {
            showalerttypevirement(Alert.AlertType.INFORMATION, "Message", null, "Please select a TypeVirement for deletion.");
            return;
        }

        // Confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete this TypeVirement?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        // Proceed with deletion if confirmed
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Retrieve selected TypeVirement
            TypeVirement selectedTypeVirement = typeVirement_listview.getSelectionModel().getSelectedItem();
            // Delete TypeVirement data using TypeVirementService
            if (TypeVirementService.getInstance().delete(selectedTypeVirement.getId())) {
                showalerttypevirement(Alert.AlertType.INFORMATION, "Message", null, "TypeVirement deleted successfully.");
                showTypeVirementData(); // Refresh TypeVirement data in the listview
            } else {
                showalerttypevirement(Alert.AlertType.ERROR, "Error Message", null, "Failed to delete TypeVirement data.");
            }
        }
    }

    // Method to show alert messages
    private void showalerttypevirement(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    // Method to clear TypeVirement data from input fields
    private void clearTypeVirementData() {
        // Clear input fields
        typeVirement_nom_input.clear();
        typeVirement_frais_input.clear();
    }

    // Method to select TypeVirement data
    @FXML
    public void selectTypeVirementData() {
        // Get the selected TypeVirement from the listview
        TypeVirement selectedTypeVirement = typeVirement_listview.getSelectionModel().getSelectedItem();

        if (selectedTypeVirement != null) {
            // Set the selected TypeVirement's data to the input fields
            typeVirement_nom_input.setText(selectedTypeVirement.getNom());
            typeVirement_frais_input.setText(String.valueOf(selectedTypeVirement.getFrais()));
        }
    }

    // Method to initialize the selection event for the list view
    private void initializeTypeVirementListSelection() {
        typeVirement_listview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Call the selectTypeVirementData method when a new item is selected
            selectTypeVirementData();
        });
    }
    //FIN type
    @FXML
    public void switchvirementtype(){

        if (typeVirement_anchorpane.isVisible()) {
            typeVirement_anchorpane.setVisible(false);
            virement_anchorpane.setVisible(true);
        }
        else
        {
            typeVirement_anchorpane.setVisible(true);
            virement_anchorpane.setVisible(false);
        }
    }
    //VIREMENT

    @FXML
    private AnchorPane virement_anchorpane;

    @FXML
    private TextField virement_cinEmetteur_input;

    @FXML
    private TextField virement_photoCin_input;

    @FXML
    private TextField virement_montant_input;

    @FXML
    private DatePicker virement_dateEmission_input;

    @FXML
    private DatePicker virement_dateApprobation_input;

    @FXML
    private TextField virement_etat_input;

    @FXML
    private ComboBox<?> virement_type_combobox;

    @FXML
    private Button virement_add_button;

    @FXML
    private Label label_virement_cinEmetteur;

    @FXML
    private Label label_virement_photoCin;

    @FXML
    private Label label_virement_montant;

    @FXML
    private Label label_virement_dateEmission;

    @FXML
    private Label label_virement_dateApprobation;

    @FXML
    private Label label_virement_etat;

    @FXML
    private Label label_virement_type;

    @FXML
    private Button virement_add_button1;

    @FXML
    private JFXListView<Virement> virement_listview;

    @FXML
    private ToggleSwitch toggle_switch_virement_transaction;

    @FXML
    public void addOrUpdateVirementData() {
        // Input validation
        if (virement_cinEmetteur_input.getText().isBlank() || virement_photoCin_input.getText().isBlank() || virement_montant_input.getText().isBlank() || virement_dateEmission_input.getValue() == null || virement_dateApprobation_input.getValue() == null) {
            showalertvirement(Alert.AlertType.INFORMATION, "Message", null, "Please fill all mandatory data such as cin emetteur, photo cin, montant, date d'emission, and date d'approbation.");
            return;
        }

        // Create Virement object from FXML input fields
        Virement virement = new Virement();
        virement.setCinEmetteur(virement_cinEmetteur_input.getText());
        virement.setPhotoCin(virement_photoCin_input.getText());
        virement.setMontant(Double.parseDouble(virement_montant_input.getText()));
        virement.setDateEmission(java.sql.Date.valueOf(virement_dateEmission_input.getValue()));
        virement.setDateApprobation(java.sql.Date.valueOf(virement_dateApprobation_input.getValue()));
        virement.setEtat(virement_etat_input.getText());

        // Determine if an existing virement is selected
        Virement selectedVirement = virement_listview.getSelectionModel().getSelectedItem();
        if (selectedVirement != null) {
            // If a virement is selected, update its data
            virement.setId(selectedVirement.getId()); // Set the ID of the selected virement
            virement.setIdCompteBeneficiaire(selectedVirement.getIdCompteBeneficiaire());
            virement.setIdCompteEmetteur(selectedVirement.getIdCompteEmetteur());
            if (VirementService.getInstance().edit(virement)) {
                showalertvirement(Alert.AlertType.INFORMATION, "Message", null, "Virement updated successfully.");
                showVirementData();
                clearVirementData();
            } else {
                showalertvirement(Alert.AlertType.ERROR, "Error Message", null, "Failed to update virement data.");
            }
        } else {
            // If no virement is selected, add a new virement
            if (VirementService.getInstance().add(virement)) {
                showalertvirement(Alert.AlertType.INFORMATION, "Message", null, "Virement added successfully.");
                showVirementData();
                clearVirementData();
            } else {
                showalertvirement(Alert.AlertType.ERROR, "Error Message", null, "Failed to add virement data.");
            }
        }
    }

    // Method to show Virement data
    public void showVirementData() {
        // Retrieve Virement data from database using VirementService
        List<Virement> virementList = VirementService.getInstance().getAll();
   //     System.out.println(virementList);
        ObservableList<Virement> observableVirementList = FXCollections.observableArrayList(virementList);


        virement_listview.setCellFactory(param -> new JFXListCell<>() {

            @Override
            protected void updateItem(Virement virement, boolean empty) {
                super.updateItem(virement, empty);
                if (empty || virement == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Create a VBox to hold the icons and text for each attribute
                    HBox container = new HBox();
                    container.setSpacing(5); // Adjust spacing as needed

                    // Add FontAwesome icons and text for each attribute
                    FontAwesomeIconView idCompteEmetteurIcon = new FontAwesomeIconView(FontAwesomeIcon.USER);
                    idCompteEmetteurIcon.setFill(Color.web("#3498db")); // Adjust icon color as needed
                    String emetteur_lastname=getEntityProperty("client", Integer.valueOf(getEntityProperty("Compte",virement.getIdCompteEmetteur().getId(),"id_user_id")),"nom");
                    String emetteur_firstname=getEntityProperty("client", Integer.valueOf(getEntityProperty("Compte",virement.getIdCompteEmetteur().getId(),"id_user_id")),"prenom");
                    String emetteur_fullname=emetteur_firstname+" "+emetteur_lastname;
                    Text idCompteEmetteurText = new Text(" Compte Émetteur: " + emetteur_fullname);

                    FontAwesomeIconView idCompteBeneficiaireIcon = new FontAwesomeIconView(FontAwesomeIcon.USER);
                    idCompteBeneficiaireIcon.setFill(Color.web("#e74c3c")); // Adjust icon color as needed
                    String benefic_lastname=getEntityProperty("client", Integer.valueOf(getEntityProperty("Compte",virement.getIdCompteBeneficiaire().getId(),"id_user_id")),"nom");
                    String benefic_firstname=getEntityProperty("client", Integer.valueOf(getEntityProperty("Compte",virement.getIdCompteBeneficiaire().getId(),"id_user_id")),"prenom");
                    String benefic_fullname=benefic_firstname+" "+benefic_lastname;
                    Text idCompteBeneficiaireText = new Text(" Compte Bénéficiaire: " +benefic_fullname);

                    FontAwesomeIconView montantIcon = new FontAwesomeIconView(FontAwesomeIcon.DOLLAR);
                    montantIcon.setFill(Color.web("#2ecc71")); // Adjust icon color as needed
                    Text montantText = new Text(" Montant: " + virement.getMontant());

                    FontAwesomeIconView dateEmissionIcon = new FontAwesomeIconView(FontAwesomeIcon.CALENDAR_ALT);
                    dateEmissionIcon.setFill(Color.web("#3498db")); // Adjust icon color as needed
                    Text dateEmissionText = new Text(" Date Emission: " + virement.getDateEmission());

                    FontAwesomeIconView dateApprobationIcon = new FontAwesomeIconView(FontAwesomeIcon.CALENDAR);
                    dateApprobationIcon.setFill(Color.web("#e74c3c")); // Adjust icon color as needed
                    Text dateApprobationText = new Text(" Date Approbation: " + virement.getDateApprobation());

                    FontAwesomeIconView etatIcon = new FontAwesomeIconView(FontAwesomeIcon.ADJUST);
                    etatIcon.setFill(Color.web("#2ecc71")); // Adjust icon color as needed
                    Text etatText = new Text(" État: " + virement.getEtat());

                    // Add icons and text to the VBox container
                    container.getChildren().addAll(idCompteEmetteurIcon, idCompteEmetteurText, idCompteBeneficiaireIcon, idCompteBeneficiaireText,
                            montantIcon, montantText, dateEmissionIcon, dateEmissionText, dateApprobationIcon, dateApprobationText,
                            etatIcon, etatText);

                    // Set the VBox container as the graphic for the list cell
                    setText(null); // Clear text
                    setGraphic(container);
                }
            }

        });
        virement_listview.setItems(observableVirementList);
    }

    // Method to delete Virement data
    public void deleteVirementData() {
        // Input validation
        if (virement_listview.getSelectionModel().isEmpty()) {
            showalertvirement(Alert.AlertType.INFORMATION, "Message", null, "Please select a virement for deletion.");
            return;
        }

        // Confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete this virement?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        // Proceed with deletion if confirmed
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Retrieve selected virement information
            String selectedVirementInfo = "13";//virement_listview.getSelectionModel().getSelectedItem();
            // Extract virement id from selected information (you may need to parse it properly)
            int virementId = extractVirementId(selectedVirementInfo);
            // Delete Virement data using VirementService
            if (VirementService.getInstance().delete(virementId)) {
                showalertvirement(Alert.AlertType.INFORMATION, "Message", null, "Virement deleted successfully.");
                showVirementData(); // Refresh Virement data in the listview
            } else {
                showalertvirement(Alert.AlertType.ERROR, "Error Message", null, "Failed to delete virement data.");
            }
        }
    }

    // Method to show alert messages
    private void showalertvirement(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    // Method to clear Virement data from input fields
    private void clearVirementData() {
        // Clear input fields
        virement_cinEmetteur_input.clear();
        virement_photoCin_input.clear();
        virement_montant_input.clear();
        virement_dateEmission_input.setValue(null);
        virement_dateApprobation_input.setValue(null);
        virement_etat_input.clear();
    }
    // Method to select Virement data
    @FXML
    public void selectVirementData() {
        // Get the selected Virement from the listview
        Virement selectedVirement = virement_listview.getSelectionModel().getSelectedItem();

        if (selectedVirement != null) {
            // Set the selected Virement's data to the input fields
            System.out.println("test: "+selectedVirement.getCinEmetteur());
            virement_cinEmetteur_input.setText(selectedVirement.getCinEmetteur());
            virement_photoCin_input.setText(selectedVirement.getPhotoCin());
            virement_montant_input.setText(String.valueOf(selectedVirement.getMontant()));
            virement_etat_input.setText(selectedVirement.getEtat());

            // Convert java.sql.Date to LocalDate for emission date
            if (selectedVirement.getDateEmission() != null) {
                java.util.Date utilDateEmission = new java.util.Date(selectedVirement.getDateEmission().getTime());
                LocalDate dateEmission = utilDateEmission.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                virement_dateEmission_input.setValue(dateEmission);
            } else {
                virement_dateEmission_input.setValue(null);
            }

            // Convert java.sql.Date to LocalDate for approbation date
            if (selectedVirement.getDateApprobation() != null) {
                java.util.Date utilDateApprobation = new java.util.Date(selectedVirement.getDateApprobation().getTime());
                LocalDate dateApprobation = utilDateApprobation.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                virement_dateApprobation_input.setValue(dateApprobation);
            } else {
                virement_dateApprobation_input.setValue(null);
            }
        }
    }

    // Method to initialize the selection event for the list view
    private void initializeVirementListSelection() {
        virement_listview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Call the selectVirementData method when a new item is selected
            selectVirementData();
        });
    }

    // Method to extract Virement id from the selected information
    private int extractVirementId(String selectedVirementInfo) {
        // Implement logic to parse the virement id from the selected information
        // For example:
        // String[] parts = selectedVirementInfo.split(":");
        // return Integer.parseInt(parts[0].trim());
        return 0; // Placeholder, replace with actual logic
    }
    // FIN virement
    //TRANSACTION

    @FXML
    private AnchorPane transaction_anchorpane;

    @FXML
    private TextField transaction_type_input;

    @FXML
    private DatePicker transaction_date_input;

    @FXML
    private TextField transaction_montant_input;

    @FXML
    private Button transaction_add_button;

    @FXML
    private Button transaction_add_button1;

    @FXML
    private JFXListView<Transaction> transaction_listview;

    public void addTransactionData() {
        // Input validation
        if (transaction_type_input.getText().isBlank() || transaction_date_input.getValue() == null || transaction_montant_input.getText().isBlank()) {
            showalerttransaction(Alert.AlertType.INFORMATION, "Message", null, "Please fill all mandatory data such as type, date, and montant.");
            return;
        }

        // Create Transaction object from FXML input fields
        Transaction transaction = new Transaction();
        // Assuming you have methods to properly set these fields
        transaction.setType(transaction_type_input.getText());
        transaction.setDateTransaction(java.sql.Date.valueOf(transaction_date_input.getValue()));
        transaction.setMontant(Double.parseDouble(transaction_montant_input.getText()));

        // Add Transaction data using TransactionService
        if (TransactionService.getInstance().add(transaction)) {
            showalerttransaction(Alert.AlertType.INFORMATION, "Message", null, "Transaction added successfully.");
            showTransactionData();
        } else {
            showalerttransaction(Alert.AlertType.ERROR, "Error Message", null, "Failed to add transaction data.");
        }
    }

    // Method to show Transaction data
    public void showTransactionData() {
        // Retrieve Transaction data from database using TransactionService
        List<Transaction> transactionList = TransactionService.getInstance().getAll();
        ObservableList<Transaction> observableTransactionList = FXCollections.observableArrayList(transactionList);

        // Assuming the listview is properly defined
        // Clear existing items
        transaction_listview.setCellFactory(param -> new JFXListCell<>() {

            @Override
            protected void updateItem(Transaction transaction, boolean empty) {
                super.updateItem(transaction, empty);
                if (empty || transaction == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Create a VBox to hold the icons and text for each attribute
                    HBox container = new HBox();
                    container.setSpacing(5); // Adjust spacing as needed

                    // Add FontAwesome icons and text for each attribute
                    FontAwesomeIconView typeIcon = new FontAwesomeIconView(FontAwesomeIcon.LIST);
                    typeIcon.setFill(Color.web("#3498db")); // Adjust icon color as needed
                    Text typeText = new Text(" Type: " + transaction.getType());

                    FontAwesomeIconView dateIcon = new FontAwesomeIconView(FontAwesomeIcon.CALENDAR_ALT);
                    dateIcon.setFill(Color.web("#e74c3c")); // Adjust icon color as needed
                    Text dateText = new Text(" Date: " + transaction.getDateTransaction());

                    FontAwesomeIconView amountIcon = new FontAwesomeIconView(FontAwesomeIcon.DOLLAR);
                    amountIcon.setFill(Color.web("#2ecc71")); // Adjust icon color as needed
                    Text amountText = new Text(" Amount: " + transaction.getMontant());

                    // Add icons and text to the VBox container
                    container.getChildren().addAll(typeIcon, typeText, dateIcon, dateText, amountIcon, amountText);

                    // Set the VBox container as the graphic for the list cell
                    setText(null); // Clear text
                    setGraphic(container);
                }
            }

        });
        transaction_listview.setItems(observableTransactionList);

    }

    // Method to delete Transaction data
    public void deleteTransactionData() {
        // Input validation
        if (transaction_listview.getSelectionModel().isEmpty()) {
            showalerttransaction(Alert.AlertType.INFORMATION, "Message", null, "Please select a transaction for deletion.");
            return;
        }

        // Confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete this transaction?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        // Proceed with deletion if confirmed
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Retrieve selected transaction information
            String selectedTransactionInfo ="13";// transaction_listview.getSelectionModel().getSelectedItem();
            // Extract transaction id from selected information (you may need to parse it properly)
            int transactionId = extractTransactionId(selectedTransactionInfo);
            // Delete Transaction data using TransactionService
            if (TransactionService.getInstance().delete(transactionId)) {
                showalerttransaction(Alert.AlertType.INFORMATION, "Message", null, "Transaction deleted successfully.");
                showTransactionData(); // Refresh Transaction data in the listview
            } else {
                showalerttransaction(Alert.AlertType.ERROR, "Error Message", null, "Failed to delete transaction data.");
            }
        }
    }

    // Method to show alert messages
    private void showalerttransaction(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    // Method to extract Transaction id from the selected information
    private int extractTransactionId(String selectedTransactionInfo) {
        // Implement logic to parse the transaction id from the selected information
        // For example:
        // String[] parts = selectedTransactionInfo.split(":");
        // return Integer.parseInt(parts[0].trim());
        return 0; // Placeholder, replace with actual logic
    }
    //FIN transaction

    //RECLAMATION-REPONSES
    @FXML
    private AnchorPane reclamation_pane;
    @FXML
    private AnchorPane reclamation_anchorpane;
    @FXML
    private AnchorPane reponse_anchorpane;
    @FXML
    private Button reclamation_btn;
    @FXML
    private ChoiceBox<String> choiceBox_reclamation_type;
    @FXML
    private DatePicker reclamation_date_input;
    @FXML
    private TextField reclamation_description_input;

    @FXML
    private Button Add_Update_reclamation;

    @FXML
    private Button clear_reclamation_button;

    @FXML
    private JFXListView<Reclamation> reclamations_listview;

   @FXML
   private TextField reclamation_type_input;

   @FXML
   private TextField reclamation_document_input;
   @FXML
   private TextField reclamation_etat_input;
   @FXML
   private  TextField reclamation_email_input;


   /* @FXML
    private TextArea textInput;*/

    @FXML
    private TextField morseOutput;

    MorseCodeTranslator morseCodeTranslator = new MorseCodeTranslator();

    boolean morseToText = false;

    @FXML
    void updateMorseCode(KeyEvent event) {
        if(morseToText){
            morseOutput.setText(morseCodeTranslator.translateToText(reclamation_description_input.getText()));
        }else {
            morseOutput.setText(morseCodeTranslator.translateToMorse(reclamation_description_input.getText()));
        }
    }

    @FXML
    void switchStyle(ActionEvent event) {
        morseToText = !morseToText;
    }



    @FXML
    public void switchreclamationreponse(){

        if (reclamation_anchorpane.isVisible()) {
            reclamation_anchorpane.setVisible(false);
            reponse_anchorpane.setVisible(true);
        }
        else
        {
            reclamation_anchorpane.setVisible(true);
            reponse_anchorpane.setVisible(false);
        }
    }
    //




    public void addOrUpdateReclamationData() {
        // Input validation
        if (reclamation_type_input.getText().isBlank() || reclamation_date_input.getValue() == null || reclamation_description_input.getText().isBlank()) {
            showAlertReclamation(Alert.AlertType.INFORMATION, "Message", null, "Please fill all mandatory data such as type, date, and description.");
            return;
        }

        // Create Reclamation object from FXML input fields
        Reclamation reclamation = new Reclamation();
        reclamation.setType(reclamation_type_input.getText());
        reclamation.setDate_reclamation(reclamation_date_input.getValue() != null ? java.sql.Date.valueOf(reclamation_date_input.getValue()) : null);
        reclamation.setDescription(reclamation_description_input.getText());

        // Determine if an existing reclamation is selected
        Reclamation selectedReclamation = reclamations_listview.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            // If a reclamation is selected, update its data
            reclamation.setId(selectedReclamation.getId());
            reclamation.setId_client_id(selectedReclamation.getId_client_id());
            reclamation.setEtat(selectedReclamation.getEtat());
            reclamation.setDocument(selectedReclamation.getDocument());
            reclamation.setEmail(selectedReclamation.getEmail());

            if (ReclamationService.getInstance().edit(reclamation)) {
                showAlertReclamation(Alert.AlertType.INFORMATION, "Message", null, "Reclamation updated successfully.");
                showReclamationData();
                clearReclamationData();
                showNotification("Notification: reclamation updated");
                // Envoyer un message WhatsApp après la mise à jour de la réclamation
                //envoyerMessageWhatsApp(reclamation.getDescription());
            } else {
                showAlertReclamation(Alert.AlertType.ERROR, "Error Message", null, "Failed to update reclamation data.");
            }
        } else {
            // If no reclamation is selected, add a new reclamation
            if (ReclamationService.getInstance().add(reclamation)) {
                showAlertReclamation(Alert.AlertType.INFORMATION, "Message", null, "Reclamation added successfully.");
                showReclamationData();
                clearReclamationData();
                showNotification("Notification: reclamation added");
                // Envoyer un message WhatsApp après l'ajout de la réclamation
                //envoyerMessageWhatsApp(reclamation.getDescription());
            } else {
                showAlertReclamation(Alert.AlertType.ERROR, "Error Message", null, "Failed to add reclamation data.");
            }
        }
    }

    // Méthode pour envoyer un message WhatsApp
    /*private void envoyerMessageWhatsApp(String messageContent) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");

        // Remplacez les valeurs suivantes par les informations appropriées
        String fromNumber = "447860099299"; // Votre numéro WhatsApp
        String toNumber = "21696417944"; // Le numéro WhatsApp de destination
        String templateName = "message_test"; // Le nom de votre modèle de message WhatsApp

        // Construction du corps de la requête
        String requestBody = String.format("{\"messages\":[{\"from\":\"%s\",\"to\":\"%s\",\"content\":{\"templateName\":\"%s\",\"templateData\":{\"body\":{\"placeholders\":[\"%s\"]}}}}]}",
                fromNumber, toNumber, templateName, messageContent);
        RequestBody body = RequestBody.create(mediaType, requestBody);

        // Création de la requête
        Request request = new Request.Builder()
                .url("https://6g5nj5.api.infobip.com/whatsapp/1/message/template")
                .method("POST", body)
                .addHeader("Authorization", "App e3622c051d218c8aa375d599e1772741-41529839-4c3a-4d65-ba0c-4064778b1bea")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        // Envoi de la requête
        try {
            Response response = client.newCall(request).execute();
            // Gérer la réponse si nécessaire
            if (response.isSuccessful()) {
                // Le message a été envoyé avec succès
                System.out.println("Message WhatsApp envoyé avec succès !");
            } else {
                // Gérer les erreurs de l'API
                System.out.println("Erreur lors de l'envoi du message WhatsApp : " + response.code());
            }
        } catch (Exception e) {
            // Gérer les exceptions
            e.printStackTrace();
        }
    }*/

    private void showNotification(String message) {
        File file = new File("C:/Users/USER/Desktop/inventory-management-system-main final/src/main/java/com/inventorymanagementsystem/image/notification.png");
        Image image = new Image(file.toURI().toString());
        Notifications notifications = Notifications.create();
        notifications.graphic(new ImageView(image));
        notifications.text(message);
        notifications.title("Success Message");
        notifications.hideAfter(Duration.seconds(4));
        notifications.show();
    }






    public void showReclamationData() {
        // Retrieve Reclamation data from database using ReclamationService
        List<Reclamation> reclamationList = ReclamationService.getInstance().getAll();

        ObservableList<Reclamation> observableReclamationList = FXCollections.observableArrayList(reclamationList);

        // Assuming the listview is properly defined
        reclamations_listview.getItems().clear();
        reclamations_listview.setCellFactory(param -> new JFXListCell<>() {

            @Override
            protected void updateItem(Reclamation reclamation, boolean empty) {
                super.updateItem(reclamation, empty);
                if (empty || reclamation == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Create a VBox to hold the icons and text for each attribute
                    HBox container = new HBox();
                    container.setSpacing(5); // Adjust spacing as needed

                    // Add FontAwesome icons and text for each attribute
                    FontAwesomeIconView typeIcon = new FontAwesomeIconView(FontAwesomeIcon.FILE);
                    typeIcon.setFill(Color.web("#3498db")); // Adjust icon color as needed
                    typeIcon.setSize("2em"); // Set icon size
                    Text typeText = new Text(" " + reclamation.getType());


                    FontAwesomeIconView descriptionIcon = new FontAwesomeIconView(FontAwesomeIcon.DOLLAR);
                    descriptionIcon.setFill(Color.web("#e74c3c")); // Adjust icon color as needed
                    descriptionIcon.setSize("2em"); // Set icon size
                    Text soldeText = new Text(" " + reclamation.getDescription());

                    /*FontAwesomeIconView etatIcon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE);
                    etatIcon.setFill(Color.web("#2ecc71")); // Adjust icon color as needed
                    etatIcon.setSize("2em"); // Set icon size
                    Text etatText = new Text(" " + reclamation.getEtat());
                    */
                    // Add icons and text to the VBox container
                    container.getChildren().addAll(typeIcon, typeText, descriptionIcon, soldeText/*, etatIcon, etatText*/);

                    // Set the VBox container as the graphic for the list cell
                    setText(null); // Clear text
                    setGraphic(container);
                }
            }

        });
        reclamations_listview.setItems(observableReclamationList);
        Stage primaryStage = Application.getPrimaryStage();
        Notification notification = new Notification(primaryStage);

        // Notify user about the new publication
        Reclamation latestReclamation = observableReclamationList.get(observableReclamationList.size() - 1);
        //Notification notification = new Notification(stage);
        //notification.showNotification("New Reclamation", "New Reclamation ID: " + latestReclamation.getId() + ", Type: " + latestReclamation.getType());
    }


    // supprimer
// Method to delete Reclamation data
    @FXML
    public void deleteReclamationData() {
        // Input validation
        if (reclamations_listview.getSelectionModel().isEmpty()) {
            showAlertReclamation(Alert.AlertType.INFORMATION, "Message", null, "Please select a reclamation for deletion.");
            return;
        }

        // Confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete this reclamation?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        // Proceed with deletion if confirmed
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Retrieve selected Reclamation from listview
            Reclamation selectedReclamation = reclamations_listview.getSelectionModel().getSelectedItem();
            // Delete Reclamation data using ReclamationService
            if (ReclamationService.getInstance().delete(selectedReclamation.getId())) {
                showAlertReclamation(Alert.AlertType.INFORMATION, "Message", null, "Reclamation deleted successfully.");
                showReclamationData(); // Refresh Reclamation data in the listview
                showNotification("Notification: reclamation deleted"); // Affiche la notification de suppression
            } else {
                showAlertReclamation(Alert.AlertType.ERROR, "Error Message", null, "Failed to delete reclamation data.");
            }
        }
    }

    // Method to show alert messages
    private void showAlertReclamation(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    // Method to clear Reclamation data from input fields
    @FXML
    private void clearReclamationData() {
        // Clear input fields
        // Supprimer tous les éléments du ChoiceBox
        reclamation_type_input.clear();
        reclamation_date_input.setValue(null);
        reclamation_description_input.clear();


        // Unset the selected reclamation
        reclamations_listview.getSelectionModel().clearSelection();
    }



    // Method to select Reclamation data
    @FXML
    public void selectReclamationData() {
        // Get the selected Reclamation from the listview
        Reclamation selectedReclamation = reclamations_listview.getSelectionModel().getSelectedItem();

        if (selectedReclamation != null) {
            // Set the selected Reclamation's data to the input fields
            System.out.println(selectedReclamation);
            reclamation_type_input.setText(selectedReclamation.getType());
            reclamation_description_input.setText(selectedReclamation.getDescription());

            // Convert java.sql.Date to LocalDate for creation date
            if (selectedReclamation.getDate_reclamation() != null) {
                java.util.Date utilDateCreation = new java.util.Date(selectedReclamation.getDate_reclamation().getTime());
                LocalDate dateCreation = utilDateCreation.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                reclamation_date_input.setValue(dateCreation);
            } else {
                reclamation_date_input.setValue(null);
            }

        }
    }


    // Method to initialize the selection event for the list view
    private void initializeReclamationListSelection() {
        reclamations_listview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Call the selectCompteData method when a new item is selected
            selectReclamationData();
        });
    }
    /*public void initialize() {
        // Ajout des options au ChoiceBox
        choiceBox_reclamation_type.getItems().addAll(
                "Problème de connexion",
                "Problème de sécurité",
                "Problème de transaction"
        );

        // Définir une option par défaut si nécessaire
        choiceBox_reclamation_type.setValue("Problème de connexion");
    }*/

    //notification reclamation
    public void showNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .owner(stage) // Set the owner window
                .showInformation();
    }
    private Stage stage; // Reference to the stage

    // Method to set the stage
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    //FIN RECLAMATION



    //REPONSE
    /*@FXML
    private WebView webViewReponse;*/


    //private WebEngine engine;

    @FXML
    private DatePicker reponse_date_input;

    @FXML
    private TextField reponse_message;
    @FXML
    private JFXListView<Reponse> reponses_listview;

    @FXML
    private Button reponse_add_button;

    @FXML
    private Button clear_reponse_button;

    public void addOrUpdateReponseData() {
        // Input validation
        if (reponse_date_input.getValue() == null || reponse_message.getText().isBlank()) {
            showAlertReponse(Alert.AlertType.INFORMATION, "Message", null, "Please fill all mandatory data such as type, date, and description.");
            return;
        }

        // Create Reponse object from FXML input fields
        Reponse reponse = new Reponse();
        reponse.setDate_reponse(reponse_date_input.getValue() != null ? java.sql.Date.valueOf(reponse_date_input.getValue()) : null);
        reponse.setMessage(reponse_message.getText());

        // Determine if an existing reponse is selected
        Reponse selectedReponse = reponses_listview.getSelectionModel().getSelectedItem();
        if (selectedReponse != null) {
            // If a reponse is selected, update its data
            reponse.setId(selectedReponse.getId()); // Set the ID of the selected reponse
            reponse.setId_reclamation_id(selectedReponse.getId_reclamation_id());
            reponse.setId_agent_id(selectedReponse.getId_agent_id());
            reponse.setDate_reponse(selectedReponse.getDate_reponse());
            reponse.setMessage(selectedReponse.getMessage());
            if (ReponseService.getInstance().edit(reponse)) {
                showAlertReponse(Alert.AlertType.INFORMATION, "Message", null, "Reponse updated successfully.");
                showReponseData();
                clearReponseData();
                showNotification("Notification: reponse updated");
            } else {
                showAlertReponse(Alert.AlertType.ERROR, "Error Message", null, "Failed to update reponse data.");
            }
        } else {
            // If no reponse is selected, add a new reponse
            if (ReponseService.getInstance().add(reponse)) {
                showAlertReponse(Alert.AlertType.INFORMATION, "Message", null, "Reponse added successfully.");
                showReponseData();
                clearReponseData();
                showNotification("Notification: reponse added");
            } else {
                showAlertReponse(Alert.AlertType.ERROR, "Error Message", null, "Failed to add reponse data.");
            }
        }
    }



    public void showReponseData() {
        // Retrieve Reponse data from database using ReponseService
        List<Reponse> reponseList = ReponseService.getInstance().getAll();

        ObservableList<Reponse> observableReponseList = FXCollections.observableArrayList(reponseList);

        // Assuming the listview is properly defined

        reponses_listview.getItems().clear();
        reponses_listview.setCellFactory(param -> new JFXListCell<>() {

            @Override
            protected void updateItem(Reponse reponse, boolean empty) {
                super.updateItem(reponse, empty);
                if (empty || reponse == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Create a VBox to hold the icons and text for each attribute
                    HBox container = new HBox();
                    container.setSpacing(5); // Adjust spacing as needed

                    // Add FontAwesome icons and text for each attribute
                    FontAwesomeIconView messageIcon = new FontAwesomeIconView(FontAwesomeIcon.FILE);
                    messageIcon.setFill(Color.web("#3498db")); // Adjust icon color as needed
                    messageIcon.setSize("2em"); // Set icon size
                    Text messageText = new Text(" " + reponse.getMessage());


                    // Add icons and text to the VBox container
                    container.getChildren().addAll(messageIcon, messageText);

                    // Set the VBox container as the graphic for the list cell
                    setText(null); // Clear text
                    setGraphic(container);
                }
            }

        });
        reponses_listview.setItems(observableReponseList);
    }

    // supprimer
// Method to delete Reponse data
    @FXML
    public void deleteReonseData() {
        // Input validation
        if (reponses_listview.getSelectionModel().isEmpty()) {
            showAlertReponse(Alert.AlertType.INFORMATION, "Message", null, "Please select a Reponse for deletion.");
            return;
        }

        // Confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete this Reponsen?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        // Proceed with deletion if confirmed
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Retrieve selected Reponse from listview
            Reponse selectedReponse = reponses_listview.getSelectionModel().getSelectedItem();
            // Delete Reponse data using ReponseService
            if (ReponseService.getInstance().delete(selectedReponse.getId())) {
                showAlertReponse(Alert.AlertType.INFORMATION, "Message", null, "Reponse deleted successfully.");
                showReponseData(); // Refresh Reponse data in the listview
                showNotification("Notification: reponse deleted");
            } else {
                showAlertReponse(Alert.AlertType.ERROR, "Error Message", null, "Failed to delete reponse data.");
            }
        }
    }

    // Method to show alert messages
    private void showAlertReponse(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }



    // Method to clear Reponse data from input fields
    @FXML
    private void clearReponseData() {
        // Clear input fields
        reponse_date_input.setValue(null);
        reponse_message.clear();
        // Unset the selected reponse
        reponses_listview.getSelectionModel().clearSelection();
    }



    // Method to select Reponse data
    @FXML
    public void selectReponseData() {
        // Get the selected Reponse from the listview
        Reponse selectedReponse = reponses_listview.getSelectionModel().getSelectedItem();

        if (selectedReponse != null) {
            // Set the selected Reponse's data to the input fields
            System.out.println(selectedReponse);
            reponse_message.setText(selectedReponse.getMessage());

            // Convert java.sql.Date to LocalDate for creation date
            if (selectedReponse.getDate_reponse() != null) {
                java.util.Date utilDateCreation = new java.util.Date(selectedReponse.getDate_reponse().getTime());
                LocalDate dateCreation = utilDateCreation.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                reponse_date_input.setValue(dateCreation);
            } else {
                reponse_date_input.setValue(null);
            }

        }
    }


    // Method to initialize the selection event for the list view
    private void initializeReponseListSelection() {
        reponses_listview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Call the selectReponseData method when a new item is selected
            selectReponseData();
        });
    }

    //FIN REPONSE





}


