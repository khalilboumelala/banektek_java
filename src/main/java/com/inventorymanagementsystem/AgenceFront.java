package com.inventorymanagementsystem;

import com.inventorymanagementsystem.config.Database;
import com.inventorymanagementsystem.entity.Agence;
import com.inventorymanagementsystem.services.AgenceService;
import com.inventorymanagementsystem.services.ArticleService;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.gluonhq.charm.glisten.control.DropdownButton;


public class AgenceFront implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//Affichage map

        WebEngine webengine;
        webengine = webViewClient.getEngine();

        url = this.getClass().getResource("map/index.html");
        webengine.load(url.toString());
 //  webViewClient.setEventDispatcher();
     //   webViewClient.setsc

    }



    //ARTICLES-AGENCES

    @FXML
    private WebView webViewClient;














    boolean    agencesaffiches=false;
    public void showAgenceData() {
      // Assuming the table columns are properly defined as TableColumn<Agence, T>
        if (agencesaffiches) {
            webViewClient.getEngine().executeScript("removeAllMarkers() ");
            show_agences_map();
        }
        agencesaffiches=true;

    }
    public void show_agences_map() {
        ObservableList<Agence> agenceList =FXCollections.observableArrayList( AgenceService.getInstance().getAll());
        for (Agence agence : agenceList) {
            double lat = Double.parseDouble(agence.getLatitude());
            double lon = Double.parseDouble(agence.getLongitude());
            String adresse = agence.getNom();
            // Escape special characters in the address string and enclose it within quotes
            adresse = "'" + adresse.replace("'", "\\'") + "'";
            // Call the JavaScript function with properly formatted parameters
            webViewClient.getEngine().executeScript("createMarkers(" + lat + "," + lon + "," + adresse + ")");
        }
    }



    @FXML
    public void openepltwindow(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("PlotStocks.fxml"));
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

    private double x;
    private double y;

}
