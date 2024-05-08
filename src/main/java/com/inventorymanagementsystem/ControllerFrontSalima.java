package com.inventorymanagementsystem;

//package com.inventorymanagementsystem;

import com.inventorymanagementsystem.config.Database;
import com.inventorymanagementsystem.entity.Notification;
import com.inventorymanagementsystem.entity.Reclamation;
import com.inventorymanagementsystem.entity.Reponse;
import com.inventorymanagementsystem.services.ReclamationService;
import com.inventorymanagementsystem.services.ReponseService;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebHistory;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.burningwave.core.assembler.StaticComponentContainer.Modules;

public class ControllerFrontSalima implements Initializable {

    private Connection connection;

    private Statement statement;

    private PreparedStatement preparedStatement;

    private ResultSet resultSet;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //display chart
        //chart();
        // Exports all modules to other modules
        Modules.exportAllToAll();

        // Charge la page dans webViewReponse
        //engine = webViewReponse.getEngine();
        //loadPage();

        showReclamationData();
        initializeReclamationListSelection();

        showReponseData();
        initializeReponseListSelection();

        connection = Database.getInstance().connectDB();
    }


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


    /*@FXML
    private WebView webViewReponse;*/
    //private WebEngine engine;
    @FXML
    private TextField textFieldLink;
    private WebHistory history;
    //private String homePage;
    private double webZoom;
    private String homePage;
    /*public DashboardController(TextField textFieldLink) {
        this.textFieldLink = textFieldLink;
    }*/

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
                Date utilDateCreation = new Date(selectedReclamation.getDate_reclamation().getTime());
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
    private TableView<Reponse> reponse_table;

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
    @FXML
    private DatePicker datePicker;
    @FXML
    private TableColumn<?, ?> table_reponse_date;
    @FXML
    private TableColumn<?, ?> table_reponse_message;
    @FXML
    private TableColumn<?, ?> table_reponse_id;

    public ObservableList<Reponse> listReponse() {
        ObservableList<Reponse> reponseList = FXCollections.observableArrayList();
        connection = Database.getInstance().connectDB();
        String sql = "SELECT * FROM REPONSE";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Reponse reponseData = new Reponse(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_reclamation_id "),
                        resultSet.getInt("id_agent_id "),
                        resultSet.getDate("date_reponse"),
                        resultSet.getString("message")
                );
                reponseList.add(reponseData); // Ajouter chaque client à la liste
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
        return reponseList;
    }


    public void showTableReponse(){
        ObservableList<Reponse> reponseList=listReponse();
        FXCollections.reverse(reponseList); //pour inverser
        table_reponse_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_reponse_date.setCellValueFactory(new PropertyValueFactory<>("date_reponse"));
        table_reponse_message.setCellValueFactory(new PropertyValueFactory<>("message"));


        reponse_table.setItems(reponseList);

    }



    /*@FXML
    public void searchByDate() {
        LocalDate date = datePicker.getValue();
        if (date != null) {
            Database database = Database.getInstance();
            List<Reponse> reponses = database.searchByDate(date);
            if (reponses != null) {
                // Effacer la listView existante avant d'ajouter de nouveaux éléments
                reponses_listview.getItems().clear();
                // Ajouter chaque objet Reponse à la listView
                reponses_listview.getItems().addAll(reponses);

                // Sélectionner le premier élément de la liste si elle n'est pas vide
                if (!reponses.isEmpty()) {
                    MultipleSelectionModel<Reponse> selectionModel = reponses_listview.getSelectionModel();
                    selectionModel.selectFirst();
                }
            }
        }
    }*/







    // supprimer
// Method to delete Reponse data


    // Method to show alert messages
    private void showAlertReponse(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }




    // Method to initialize the selection event for the list view
    private void initializeReponseListSelection() {
        reponses_listview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Call the selectReponseData method when a new item is selected
            //selectReponseData();
        });
        //reponses_listview.setCellFactory(new ReponseCellFactory());
        reponses_listview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //reponse_table.getSelectionModel();

    }

    @FXML
    private TextField filterField;
    ObservableList<Reponse> dataList;
    @FXML
    private TextField reponse_search;


   /* @FXML
    private WebEngine engine;

    @FXML
    public void loadPage() {
        engine.load("https://www.google.com");
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
        webViewReponse.setZoom(webZoom);
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
    }*/

    // chart reclamation
    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    /*public void chart() {
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
    }*/
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
                    VBox container = new VBox();
                    container.setSpacing(5); // Adjust spacing as needed

                    // Add FontAwesome icons and text for each attribute
                    FontAwesomeIconView messageIcon = new FontAwesomeIconView(FontAwesomeIcon.FILE);
                    messageIcon.setFill(Color.web("#3498db")); // Adjust icon color as needed
                    messageIcon.setSize("2em"); // Set icon size
                    Text messageText = new Text(" " + reponse.getMessage());

                    // Add date to the VBox container
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = dateFormat.format(reponse.getDate_reponse()); // Assuming getDateReponse() returns a Date object
                    Text dateText = new Text("Date: " + formattedDate);

                    // Add icons, text, and date to the VBox container
                    container.getChildren().addAll(messageIcon, messageText, dateText);

                    // Set the VBox container as the graphic for the list cell
                    setText(null); // Clear text
                    setGraphic(container);
                }
            }


        });
        reponses_listview.setItems(observableReponseList);
    }

}



//FIN REPONSE
