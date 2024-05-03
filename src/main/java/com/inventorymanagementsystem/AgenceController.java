package com.inventorymanagementsystem;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import atlantafx.base.util.Animations;
import com.inventorymanagementsystem.services.*;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTreeView;
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
import javafx.util.StringConverter;
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

public class AgenceController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//Affichage map
        WebEngine webengine;
        webengine = webView.getEngine();
        WebEngine webengine2;
        webengine2 = webViewClient.getEngine();

        url = this.getClass().getResource("map/index.html");
        webengine.load(url.toString());
        webengine2.load(url.toString());

//
        showAgenceData();
        initializeslider();
    }
    boolean ismapopen=false;
    @FXML
    public void agences_slider_panes(){

        double position = agences_slider.getValue();

        if (position >= 0 && position < 33) {
            agences_backshow_anchorpane.setVisible(true);
            agences_front_anchorpane.setVisible(false);
            agences_back_anchorpane.setVisible(false);
        } else if (position >= 34 && position < 66) {
            agences_backshow_anchorpane.setVisible(false);
            agences_front_anchorpane.setVisible(false);
            agences_back_anchorpane.setVisible(true);
        }
        else if (position >= 67 && position < 100) {
            agences_backshow_anchorpane.setVisible(false);
            agences_front_anchorpane.setVisible(true);
            agences_back_anchorpane.setVisible(false);
        }
        if (!ismapopen) show_agences_map();

        System.out.println("Main: "+agences_backshow_anchorpane.isVisible());
        System.out.println("Back: "+agences_back_anchorpane.isVisible());
        System.out.println("Front: "+agences_front_anchorpane.isVisible());
    }

    //ARTICLES-AGENCES
//agences
    @FXML
    private TableView<Agence> agence_table;
    @FXML
    private JFXListView<Agence> agence_listview;

    @FXML
    private TextField agence_tel_input;
    @FXML
    private TextField agence_nom_input;
    @FXML
    private TextField agence_adresse_input;
    @FXML
    private WebView webView;
    @FXML
    private WebView webViewClient;
    @FXML
    private TextField lat_input;

    @FXML
    private TextField lng_input;


    @FXML
    private Button add_agence_button;
    @FXML
    private Button delete_agence_button;
    @FXML
    private Button edit_agence_button;
    @FXML
    private Button translator_button;

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

    ArticleService articleService=new ArticleService();
    //AgenceService agenceService=new AgenceService(connection);
// ShakeX animation method for text fields
    private void shakeTextField(TextField textField) {
        TranslateTransition shake = new TranslateTransition(Duration.millis(100), textField);
        shake.setByX(10f);
        shake.setCycleCount(5);
        shake.setAutoReverse(true);
        shake.play();
    }
    private void shakeTextArea(TextArea textField) {
        TranslateTransition shake = new TranslateTransition(Duration.millis(100), textField);
        shake.setByX(10f);
        shake.setCycleCount(5);
        shake.setAutoReverse(true);
        shake.play();
    }
    @FXML
    public void addAgenceData() {
        // Check each text field individually
        boolean isAnyFieldEmpty = false;

        if (agence_nom_input.getText().isBlank()) {
            shakeTextField(agence_nom_input);
            agence_nom_input.setStyle("-fx-border-color: red;");
            isAnyFieldEmpty = true;
        }

        // Check if the input field is blank or contains non-digit characters
        if (agence_tel_input.getText().isBlank() || !agence_tel_input.getText().matches("\\d+")) {
            shakeTextField(agence_tel_input);
            agence_tel_input.setStyle("-fx-border-color: red;");
            isAnyFieldEmpty = true;
        }


        if (agence_adresse_input.getText().isBlank()) {
            shakeTextField(agence_adresse_input);
            agence_adresse_input.setStyle("-fx-border-color: red;");
            isAnyFieldEmpty = true;
        }

        if (lat_input.getText().isBlank() || !lat_input.getText().matches("\\d+")) {
            shakeTextField(lat_input);
            lat_input.setStyle("-fx-border-color: red;");
            isAnyFieldEmpty = true;
        }

        if (lng_input.getText().isBlank() || !lng_input.getText().matches("\\d+")) {
            shakeTextField(lng_input);
            lng_input.setStyle("-fx-border-color: red;");
            isAnyFieldEmpty = true;
        }

// Show overall alert if any field is empty
        if (isAnyFieldEmpty) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the mandatory data such as name, telephone number, address, latitude, and longitude.");
            alert.showAndWait();
        }

        // Show an alert message



        // Set parameters
        // Create an Agence instance
        Agence agence = new Agence(null, // id (assuming it's auto-generated)
                agence_adresse_input.getText(),
                agence_nom_input.getText(),
                Long.parseLong(agence_tel_input.getText()),
                "Normal", // Default value for etat
                null, // id_chef (assuming it's not provided here)
                new java.util.Date(), // Current date for date_ajout
                lat_input.getText(),
                lng_input.getText());


        boolean result = AgenceService.getInstance().add(agence);
        if (result ) {
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

    }






    public void clearAgenceData() {
        agence_nom_input.clear();
        agence_tel_input.clear();
        agence_adresse_input.clear();
        lat_input.clear();
        lng_input.clear();
        agence_listview.getSelectionModel().clearSelection();


    }

    @FXML
    private JFXSlider agences_slider;

    private void initializeslider(){

        // Set the formatter for the indicator text
        agences_slider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double value) {
                // Customize the format of the indicator text
                if (value >= 0 && value < 33) {
                    // Return the first string when the value is between 0 and 33
                    return "Main";
                } else if (value >= 34 && value <= 66) {
                    // Return the second string when the value is between 68 and 100
                    return "Back";
                }
                else if (value >= 67 && value <= 100) {
                    // Return the second string when the value is between 68 and 100
                    return "Front";
                }
                else {
                    // Return an empty string for other values
                    return "";
                }
            }

            @Override
            public Double fromString(String string) {
                // Not needed for this example
                return null;
            }
        });


    }
    boolean agences_open=false;

    public void showAgenceData() {
        ObservableList<Agence> agenceList =FXCollections.observableArrayList( AgenceService.getInstance().getAll());

        // Assuming the table columns are properly defined as TableColumn<Agence, T>
        if (agencesaffiches) {
            webViewClient.getEngine().executeScript("removeAllMarkers() ");
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
        agencesaffiches=true;
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

                    FontAwesomeIconView nameIcon = new FontAwesomeIconView(FontAwesomeIcon.BUILDING);
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
                    setOnMouseClicked(event -> {
                        if (event.getClickCount() == 2) {
                            System.out.println("hello");
                            agences_back_anchorpane.setVisible(true);
                            agences_backshow_anchorpane.setVisible(false);
                        }
                    });
                }
            }

        });
        agence_listview.setItems(agenceList);




    }

    boolean    agencesaffiches=false;


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
    private AnchorPane  agences_front_anchorpane;
    @FXML
    private AnchorPane  agences_backshow_anchorpane;
    @FXML
    private AnchorPane  agences_back_anchorpane;

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
    public void switchfrontbackagences(){

        if (agences_front_anchorpane.isVisible()) {
            agences_front_anchorpane.setVisible(false);
            agences_back_anchorpane.setVisible(true);
        }
        else
        {
            agences_back_anchorpane.setVisible(false);
            agences_front_anchorpane.setVisible(true);
        }
    }
    Integer agence_id;
    private Connection connection;

    private Statement statement;

    private PreparedStatement preparedStatement;

    private ResultSet resultSet;


    public void selectAgenceTableData(){
        int num=agence_listview.getSelectionModel().getSelectedIndex();
        Agence agenceData=agence_listview.getSelectionModel().getSelectedItem();
        if(num-1 < -1){
            return;
        }

        agence_nom_input.setText(agenceData.getNom());
        agence_tel_input.setText(String.valueOf(agenceData.getNumTel()));
        lat_input.setText(agenceData.getLatitude());
        lng_input.setText(agenceData.getLongitude());
        agence_adresse_input.setText(agenceData.getAdresse());
        agence_id=agenceData.getId();
        agences_slider.setValue(40);
        // System.out.println(agenceData.toString());
        webView.getEngine().executeScript("createMarker("+lat_input.getText()+","+lng_input.getText()+") ");

        //complete from here...
    }

    public void updateAgenceData(){
        boolean isAnyFieldEmpty = false;

        if (agence_nom_input.getText().isBlank()) {
            shakeTextField(agence_nom_input);
            agence_nom_input.setStyle("-fx-border-color: red;");
            isAnyFieldEmpty = true;
        }

        if (agence_tel_input.getText().isBlank()) {
            shakeTextField(agence_tel_input);
            agence_tel_input.setStyle("-fx-border-color: red;");
            isAnyFieldEmpty = true;
        }

        if (agence_adresse_input.getText().isBlank()) {
            shakeTextField(agence_adresse_input);
            agence_adresse_input.setStyle("-fx-border-color: red;");
            isAnyFieldEmpty = true;
        }

        if (lat_input.getText().isBlank()) {
            shakeTextField(lat_input);
            lat_input.setStyle("-fx-border-color: red;");
            isAnyFieldEmpty = true;
        }

        if (lng_input.getText().isBlank()) {
            shakeTextField(lng_input);
            lng_input.setStyle("-fx-border-color: red;");
            isAnyFieldEmpty = true;
        }

// Show overall alert if any field is empty
        if (isAnyFieldEmpty) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
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
                agence_listview.getItems().clear();
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
        if (agence_listview.getSelectionModel().isEmpty()) {
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
                //    webViewClient.getEngine().executeScript("removeAllMarkers() ");

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
