package com.inventorymanagementsystem;

import com.inventorymanagementsystem.config.Database;
import com.inventorymanagementsystem.entity.Client;
import com.inventorymanagementsystem.services.CompteService;
import com.inventorymanagementsystem.services.LogWriter;
import com.inventorymanagementsystem.services.PasswordHasher;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
//import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
public class LoginController implements Initializable {
    @FXML
    private Label c_logo;

    @FXML
    private Label f_logo;

    @FXML
    private Button login_btn;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private AnchorPane login_form;


    private Connection connection;

    private Statement statement;

    private PreparedStatement preparedStatement;

    private ResultSet resultSet;
    private double x;
    private double y;

    private  CompteFrontController compteController;

    public CompteFrontController getCompteController() {
        return compteController;
    }

    public void setCompteController(CompteFrontController compteController) {
        this.compteController = compteController;
    }

    public void textfieldDesign(){
        if(username.isFocused()){
            username.setStyle("-fx-background-color:#fff;"+"-fx-border-width:2px");
            password.setStyle("-fx-background-color:transparent;"+"-fx-border-width:1px");
        }else if(password.isFocused()){
            username.setStyle("-fx-background-color:transparent;"+"-fx-border-width:1px");
            password.setStyle("-fx-background-color:#fff;"+"-fx-border-width:2px");

        }
    }

    public void dropShowAffect(){
        DropShadow original=new DropShadow(20, Color.valueOf("#ae44a5"));
        f_logo.setEffect(original);
        c_logo.setEffect(original);
    }

    public void onExit(){
        System.exit(0);
    }

    public void login(){
        connection = Database.getInstance().connectDB();
        String sql = "SELECT * FROM client WHERE username=?";
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username.getText());
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                LocalDate dob = resultSet.getDate("dob").toLocalDate();
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                long cin = resultSet.getLong("cin");
                long numTel = resultSet.getLong("num_tel");
                String genre = resultSet.getString("genre");
                String pays = resultSet.getString("pays");
                String adresse = resultSet.getString("adresse");
                String email = resultSet.getString("email");
                String document = resultSet.getString("document");
                String signature = resultSet.getString("signature");
                String profession = resultSet.getString("profession");
                LocalDate dateCreation = resultSet.getDate("date_creation").toLocalDate();
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                LocalDate lastLogin = resultSet.getDate("last_login").toLocalDate();
                String etat = resultSet.getString("etat");
                String photo = resultSet.getString("photo");

                // Create Client object using the constructor
                Client client = new Client(id, dob, nom, prenom, cin, numTel, genre, pays, adresse, email, document, signature, profession, dateCreation, username, password, lastLogin, etat, photo);

                //    System.out.println(passwordHashed);
              //  System.out.println(password.getText());
                //System.out.println(PasswordHasher.verifyPassword(password.getText(),passwordHashed));
                if (PasswordHasher.verifyPassword(this.password.getText(),password)) {
                    System.out.println(nom);
                    //////////////logs //////
                    String log = "Client [" + cin + "] S'EST CONNECTÉ";
                    LogWriter.writeLog(log);
                    /////////fin logs////////////////:
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Login Successful !");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("CompteFrontFX.fxml"));
                    Parent root = loader.load();
                    // clientController = loader.getController();
                    //clientController.setComptes(CompteService.getInstance().getCompteByUserId(client.getId()));
                  //  alert.showAndWait();
                }




                FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                Parent root = loader.load();
                DashboardController dashboardController = loader.getController();
                dashboardController.setClient_connecte(client);
              //  System.out.println("we are here::"+CompteService.getInstance().getCompteByUserId(client.getId()));
                dashboardController.setComptelist(CompteService.getInstance().getCompteByUserId(client.getId()));
              //  dashboardController.set


            //    System.out.println("client"+client);
                FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), login_btn.getScene().getRoot());
                fadeOut.setFromValue(1);
                fadeOut.setToValue(0);
                fadeOut.setOnFinished(event -> {
                    Stage stage = (Stage) login_btn.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), stage.getScene().getRoot());
                    fadeIn.setFromValue(0);
                    fadeIn.setToValue(1);
                    fadeIn.play();
                });
                fadeOut.play();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Wrong Username/Password");
                alert.showAndWait();
            }
        } catch (Exception err){
            err.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dropShowAffect();
    }
}