package com.inventorymanagementsystem;
import atlantafx.base.util.Animations;
import com.inventorymanagementsystem.entity.Client;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.textfield.CustomTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.inventorymanagementsystem.services.*;

public class   DashFXController {

    public Button signout_btn;
    public Label nom_agent_session;
    double x,y;
    public DatePicker dob_client;
    public ComboBox proffession_client;
    public ComboBox sexe_client;
    public CustomTextField numero_client;
    public CustomTextField adresse_client;
    public CustomTextField email_client;
    public CustomTextField prenom_client;
    public CustomTextField nom_client;
    public CustomTextField cin_client;
    public ComboBox pays_client;
    public Button uploadButton;
    public ImageView photo1_client;
    public Button add_client_btn;
    public Button verif_client_btn;
    public VBox clientCardVBox;
    public ScrollPane clientCardScrollPane;
    public TextField client_search;
    public AnchorPane clientListPane;
    public AnchorPane clientAddPane;
    public Map<Node, Client> clientMap = new HashMap<>();
    public AnchorPane clientEditPane;
    public ComboBox proffession_client_edit;
    public CustomTextField username_client_edit;
    public Button upload_photo_edit;
    public CustomTextField password_client_edit;
    public ComboBox pays_client_edit;
    public Button edit_client_btn;
    public ImageView photo1_client_edit;
    public CustomTextField nom_client_edit;
    public CustomTextField cin_client_edit;
    public CustomTextField prenom_client_edit;
    public CustomTextField email_client_edit;
    public CustomTextField adresse_client_edit;
    public CustomTextField numero_client_edit;
    public ComboBox sexe_client_edit;
    public DatePicker dob_client_edit;
    public Button verif_client_btn_edit;
    public Label nomprenom_client_edit;
    public Label age_client_edit;
    public Label age_client;
    public Button clients_btn;
    public Button agents_btn;
    public Label fiche_username;
    public Label fiche_nom;
    public Label fiche_password;

    //////////SESSION
    Client test=new Client();
    int id_client_to_edit;
    String path_photo_add;
    String username_toadd;
    String password_toadd;

    ///////////////
    public void setAgentName(String agentName){
        nom_agent_session.setText(agentName);
    }
    @FXML
    void movetoShow(){
        displayClients(new ArrayList<>(ClientService.getInstance().getAll()));

        clientListPane.setVisible(true);
        clientAddPane.setVisible(false);
        clientEditPane.setVisible(false);


    }
    @FXML
    void movetoAdd(){
        clientEditPane.setVisible(false);

        clientListPane.setVisible(false);
        clientAddPane.setVisible(true);


    }
    @FXML
    void movetoEdit(){
        clientEditPane.setVisible(true);
        clientListPane.setVisible(false);
        clientAddPane.setVisible(false);
    }

    public void clearDataClient() {
        fiche_password.setText("Mot de passe");
        fiche_username.setText("Nom d'utilisateur");
        fiche_nom.setText("");
        pays_client.setValue(null);
        nom_client.clear();
        cin_client.clear();
        prenom_client.clear();
        email_client.clear();
        adresse_client.clear();
        numero_client.clear();
        sexe_client.setValue(null);
        proffession_client.setValue(null);
        dob_client.setValue(null);
    }
    @FXML
    public void initialize() {
        movetoShow();
       add_client_btn.setDisable(true);
        List<Client> clients = ClientService.getInstance().getAll();
        displayClients(clients);
        client_search.textProperty().addListener((observable, oldValue, newValue) -> {
            List<Client> filteredClients = filterClients(newValue);
            displayClients(filteredClients);
        });
        fillCountryComboBox();

    }
    private List<Client> filterClients(String searchText) {
        List<Client> clients = ClientService.getInstance().getAll();

        List<Client> filteredClients = new ArrayList<>();
        for (Client client : clients) {
           String nom_prenom = client.getNom()+" "+client.getPrenom();
            if (nom_prenom.toLowerCase().contains(searchText.toLowerCase()) || Long.toString(client.getCin()).contains(searchText.toLowerCase())) {
                filteredClients.add(client);
            }
        }
        return filteredClients;
    }
    private Client getClientFromPane(StackPane pane) {
        for (Map.Entry<Node, Client> entry : clientMap.entrySet()) {
            if (entry.getKey() instanceof StackPane && entry.getKey().equals(pane)) {
                return entry.getValue();
            }
        }
        return null;
    }

    private void displayClients(List<Client> clients) {

        // Effacer l'affichage actuel des clients
        clientCardVBox.getChildren().clear();

        // Afficher les clients filtrés
        for (int i = 0; i < clients.size(); i +=4) {
            // Créer une nouvelle rangée pour chaque groupe de cinq clients
            HBox clientRow = new HBox(10); // Espacement entre les cardviews
            clientRow.setPrefHeight(120); // Hauteur de chaque rangée
            clientRow.setPrefWidth(clientCardScrollPane.getWidth()); // Largeur de chaque rangée

            // Ajouter les cardviews pour chaque client dans la rangée actuelle
            for (int j = i; j < Math.min(i + 4, clients.size()); j++) {
                // Créer une StackPane pour chaque cardview

                StackPane clientCard = new StackPane();


                clientMap.put(clientCard, clients.get(j));

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
                // Appliquer les mêmes effets aux enfants du rectangle

                // ImageView photoView = new ImageView(new Image("file:C:\\Users\\Mega-PC\\Desktop\\java2\\inventory-management-system-main\\src\\main\\resources\\com\\inventorymanagementsystem\\resources\\hamza.png"));
                //System.out.println(clients.get(j).getPhoto());
                ImageView photoView = new ImageView(new Image("file:C:\\xampp\\htdocs\\BANEKTEK_FINAL_SYMFONY_JAVA\\public\\"+clients.get(j).getPhoto()));
                photoView.setFitWidth(100); // Largeur de la photo
                photoView.setFitHeight(80); // Hauteur de la photo
                photoView.setPreserveRatio(true);

                // Ajouter le nom du client au cardview
                Text clientName = new Text(clients.get(j).getNom());
                clientName.setStyle("-fx-font-size: 16px; -fx-fill: black; -fx-font-style: italic;");
                StackPane.setAlignment(clientName, Pos.TOP_LEFT);
                StackPane.setMargin(clientName, new Insets(25, 0, 0, 10)); // 10 pixels de marge à gauche
////////////////////////////////////////////////////////////
                Text clientPrenom = new Text(clients.get(j).getPrenom());
                clientPrenom.setStyle("-fx-font-size: 16px; -fx-fill: black; -fx-font-style: italic;");
                StackPane.setAlignment(clientPrenom, Pos.TOP_LEFT);
                StackPane.setMargin(clientPrenom, new Insets(40, 0, 0, 10)); // 10 pixels de marge à gauche
////////////////////////////////////////////////////////////
                Text clientCin = new Text("C.I.N : " + Long.toString(clients.get(j).getCin()));
                clientCin.setStyle("-fx-font-size: 10px; -fx-fill: black; -fx-font-style: italic;");
                StackPane.setAlignment(clientCin, Pos.TOP_LEFT);
                StackPane.setMargin(clientCin, new Insets(60, 0, 0, 10)); // 10 pixels de marge à gauche
                ///////////////////////////////////////////////:
                Text clientEmail = new Text(clients.get(j).getEmail());
                clientEmail.setStyle("-fx-font-size: 8px; -fx-fill: blue; -fx-font-style: italic;");
                StackPane.setAlignment(clientEmail, Pos.TOP_LEFT);
                StackPane.setMargin(clientEmail, new Insets(70, 0, 0, 10)); // 10 pixels de marge à gauche
                ///////////////////////////////////////////////////////////
                Button deleteButton = new Button("Supprimer");
                StackPane.setAlignment(deleteButton,Pos.CENTER_LEFT);
                StackPane.setMargin(deleteButton, new Insets(70, 0, 0, 10)); // 10 pixels de marge à gauche
                //deleteButton.setStyle("-fx-font-size: 10px; -fx-background-color: red; -fx-text-fill: white; -fx-font-style: bold;");
                deleteButton.getStyleClass().add("deleteButton");

                deleteButton.setOnAction(e -> {
                    // Afficher une boîte de dialogue de confirmation
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation de suppression");
                    alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce client ?");
                    alert.setContentText("Cette action est irréversible.");

                    // Attendre la réponse de l'utilisateur
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            StackPane clientPane = (StackPane) deleteButton.getParent();

                            // Obtenir la référence au client associé à cette StackPane
                            // Vous devrez ajouter une méthode pour obtenir le client à partir de la StackPane
                            Client clientToDelete = getClientFromPane(clientPane);

                            // Supprimer le client de la liste des clients
                            ClientService.getInstance().delete(clientToDelete.getId());

                            displayClients(new ArrayList<>(ClientService.getInstance().getAll()));
                        }
                    });
                });

// Créer le bouton "Modifier"
                Button editButton = new Button("Modifier");
                StackPane.setAlignment(editButton,Pos.CENTER_LEFT);
                StackPane.setMargin(editButton, new Insets(70, 0, 0, 75)); // 10 pixels de marge à gauche
               // editButton.setStyle("-fx-font-size: 10px; -fx-background-color: orange;-fx-text-fill: white; -fx-font-style: bold;");
                editButton.getStyleClass().add("editButton");

                editButton.setOnAction(e -> {

                    StackPane clientPane = (StackPane) editButton.getParent();


                    Client clientToEdit = getClientFromPane(clientPane);
                    if(clientToEdit!=null){
                    remplirChampsClientEdit(clientToEdit);
                    id_client_to_edit= clientToEdit.getId();
                   movetoEdit();}



                });
                TranslateTransition shakeTransition = new TranslateTransition(Duration.millis(100), editButton);
                shakeTransition.setFromX(0);
                shakeTransition.setToX(-5);
                shakeTransition.setAutoReverse(true);
                shakeTransition.setCycleCount(4);
                editButton.setOnMouseEntered(event -> shakeTransition.play());

                // Arrêter l'effet de secousse lorsque la souris quitte le bouton
                editButton.setOnMouseExited(event -> shakeTransition.stop());
                shakeTransition.setNode(deleteButton);

               deleteButton.setOnMouseEntered(event -> shakeTransition.play());

                // Arrêter l'effet de secousse lorsque la souris quitte le bouton
              deleteButton.setOnMouseExited(event -> shakeTransition.stop());
                /////////////////////////////////////////
                // Ajouter la photo du client et le nom du client à la StackPane
                clientCard.getChildren().addAll(background, photoView, clientName,clientPrenom,clientCin,clientEmail);

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
            }

            // Ajouter la rangée à la VBox principale
            clientCardVBox.getChildren().add(clientRow);
        }
    }


    private void fillCountryComboBox() {
        ObservableList<String> countryList = FXCollections.observableArrayList();

        try {
            URL url = new URL("https://restcountries.com/v3.1/all"); // URL de l'API qui fournit la liste des pays
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            StringBuilder response = new StringBuilder();
            while ((output = br.readLine()) != null) {
                response.append(output);
            }

            conn.disconnect();

            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(response.toString());

            for (Object obj : jsonArray) {
                JSONObject countryObj = (JSONObject) obj;
                JSONObject countryNames = (JSONObject) countryObj.get("name");
                String countryName = (String) countryNames.get("common");
                countryList.add(countryName);
            }


            pays_client.setItems(countryList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

@FXML
    public void uploadPhoto(javafx.event.ActionEvent actionEvent) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choisir une photo");
    fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
    );

    Stage stage = (Stage) uploadButton.getScene().getWindow();
    File selectedFile = fileChooser.showOpenDialog(stage);

    // Logique pour traiter le fichier sélectionné (par exemple, afficher le chemin du fichier dans un label)
    if (selectedFile != null) {
        System.out.println("Photo sélectionnée : " + selectedFile.getAbsolutePath());
        Image image = new Image(selectedFile.toURI().toString());
        path_photo_add =selectedFile.toURI().toString();
        photo1_client.setImage(image);
        photo1_client.setFitWidth(80);
        photo1_client.setFitHeight(80);
        photo1_client.setStyle("-fx-background-radius: 50px;");}}


    // Ajoutez ici les méthodes pour gérer les événements des éléments de l'interface utilisateur
@FXML
    public void verifier_client(){
        if(dob_client!=null && !nom_client.getText().isBlank() && !prenom_client.getText().isBlank()){
            password_toadd=Client.generateStrongPassword(8);
            username_toadd = Client.generateUsername(nom_client.getText(),prenom_client.getText());
             Client c=new Client();
            c.setDob(dob_client.getValue());
            age_client.setText(Integer.toString(c.calculerAge()));
            fiche_nom.setText(prenom_client.getText().toUpperCase()+" "+nom_client.getText().toUpperCase());
            fiche_username.setText("Nom d'utilisateur : "+username_toadd);
            fiche_password.setText("Mot de passe :"+password_toadd);
            add_client_btn.setDisable(false);

        }



}
    @FXML
    private void handleAddClientButtonAction(ActionEvent event) {

        LocalDate dob = dob_client.getValue();
        String dobString = dob != null ? dob.toString() : null;

        String profession = (String) proffession_client.getValue();
        String sexe = (String) sexe_client.getValue();
        String numero = numero_client.getText();
        String adresse = adresse_client.getText();
        String email = email_client.getText();
        String prenom = prenom_client.getText();
        String nom = nom_client.getText();
        String cin = cin_client.getText();
        String pays = (String) pays_client.getValue();
        ////controle de saisie//////::
        if (!validateNomPrenom(nom_client.getText())) {
            setRedText(nom_client);
            showAlert("Nom doit contenir uniquement des lettres alphabétiques");
            return;
        } else {
            resetTextFieldStyle(nom_client);
        }
        if (!validateNomPrenom(prenom_client.getText())) {
            setRedText(prenom_client);
            showAlert("Prénom doit contenir uniquement des lettres alphabétiques");
            return;
        } else {
            resetTextFieldStyle(prenom_client);
        }
        if (numero_client.getText().isBlank() || !numero_client.getText().matches("\\d+")) {
            setRedText(numero_client);
            showAlert("Numéro de téléphone doit être un nombre");
            return;
        } else {
            resetTextFieldStyle(numero_client);
        }
        if (email_client.getText().isBlank() || !validateEmail(email_client.getText())) {
            setRedText(email_client);
            showAlert("Adresse email invalide");
            return;
        } else {
            resetTextFieldStyle(email_client);
        }
        if (cin_client.getText().isBlank() || !cin_client.getText().matches("\\d+")) {
            setRedText(cin_client);
            showAlert("Numéro de CIN doit être un nombre");
            return;
        } else {
            resetTextFieldStyle(cin_client);
        }

        //////////fin controle de saisie //////



        System.out.println("le mot de passe est :"+password_toadd);
        Random random = new Random();

// Génération d'un nombre aléatoire entre 10 et 99
        int randomNumber = random.nextInt(90) + 10;

// Concaténation du nombre aléatoire avec le nom de fichier
        String fileName_photo = nom.toLowerCase() + "_" + prenom.toLowerCase() + "_" + randomNumber + ".png";

        String passwordhash = PasswordHasher.hashPassword(password_toadd);
        Client client = new Client(0, dob, nom, prenom, Long.parseLong(cin), Long.parseLong(numero),
                sexe, pays, adresse, email, "", "", profession, LocalDate.now(), username_toadd, passwordhash, LocalDate.now(), "activer", "clients_photos/"+fileName_photo);
        System.out.println(fileName_photo);
        System.out.println("username : "+username_toadd);
        boolean ajoutReussi = ClientService.add(client);
        if (ajoutReussi) {

            if (path_photo_add != null && !path_photo_add.isEmpty()) {

                String destinationDirectory = "C:/xampp/htdocs/BANEKTEK_FINAL_SYMFONY_JAVA/public/clients_photos/";
           String destinationPath = destinationDirectory + fileName_photo;
                try {
                    URI uri = new URI(path_photo_add);
                    Path sourcePath = Paths.get(uri);
                    Files.copy(sourcePath, Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Photo copiée avec succès à : " + destinationPath);
                    fileName_photo = destinationPath; // Mettre à jour le chemin de la photo dans l'objet Client
                } catch (IOException | URISyntaxException e) {
                    System.out.println("Erreur lors de la copie de la photo : " + e.getMessage());
                }
            }


            System.out.println("Client ajouté avec succès !");
            clearDataClient();

            displayClients(new ArrayList<>(ClientService.getInstance().getAll()));

        } else {
            // Il y a eu une erreur lors de l'ajout du client
            System.out.println("Erreur lors de l'ajout du client.");
        }
    }

    @FXML
    public void movetoAgents()
    {
        DashBoardService.moveFunction(agents_btn, "AgentFX.fxml",getClass(),nom_agent_session);

    }
    public void remplirChampsClientEdit(Client client) {
        // Remplissage des champs de texte
        nom_client_edit.setText(client.getNom());
        prenom_client_edit.setText(client.getPrenom());
        email_client_edit.setText(client.getEmail());
        adresse_client_edit.setText(client.getAdresse());
        numero_client_edit.setText(String.valueOf(client.getNumTel()));
        cin_client_edit.setText(String.valueOf(client.getCin()));
        // Remplissage des champs de ComboBox
        pays_client_edit.setValue(client.getPays());
        sexe_client_edit.setValue(client.getGenre());
        proffession_client_edit.setValue(client.getProfession());
        username_client_edit.setText(client.getUsername());
        // Remplissage du champ de date de naissance
        dob_client_edit.setValue(client.getDob());
        nomprenom_client_edit.setText(client.getPrenom()+" "+client.getNom());
        age_client_edit.setText(Integer.toString(client.calculerAge()));
        photo1_client_edit.setImage(new Image("file:C:\\xampp\\htdocs\\BANEKTEK_FINAL_SYMFONY_JAVA\\public\\"+client.getPhoto()));
    }

    @FXML
    private void handleEditClientButtonAction(ActionEvent event) {
        LocalDate dob = dob_client_edit.getValue();
        String dobString = dob != null ? dob.toString() : null;
        String profession = (String) proffession_client_edit.getValue();
        String sexe = (String) sexe_client_edit.getValue();
        String numero = numero_client_edit.getText();
        String adresse = adresse_client_edit.getText();
        String email = email_client_edit.getText();
        String prenom = prenom_client_edit.getText();
        String nom = nom_client_edit.getText();
        String cin = cin_client_edit.getText();
        String pays = (String) pays_client_edit.getValue();
        String username = username_client_edit.getText();

        Client client = new Client(id_client_to_edit, dob, nom, prenom, Long.parseLong(cin), Long.parseLong(numero),
                sexe, pays, adresse, email, "", "", profession, LocalDate.now(), username, "password", LocalDate.now(), "activer", "");


        if (ClientService.getInstance().edit(client)) {
            System.out.println("Client mis à jour avec succès !");
           movetoShow();

        } else {
            System.out.println("Erreur lors de la mise a jour du client.");

        }
    }
    private void setRedText(TextField textField) {
        Animations.shakeX(textField).playFromStart();

        textField.setStyle("-fx-border-color: red;");
    }


    private void showAlert(String fieldName) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText("Le champ \"" + fieldName + "\" est vide. Veuillez le remplir.");

        alert.showAndWait();
    }
    private void resetTextFieldStyle(TextField textField) {
        textField.setStyle(""); // Réinitialise tous les styles
    }
    private boolean validateNomPrenom(String value) {
        // Vérifie si la valeur contient uniquement des lettres alphabétiques
        return Pattern.matches("[a-zA-Z]{3,}+", value);
    }

    private boolean validateEmail(String email) {
        // Vérifie si l'e-mail est valide en utilisant une regex
        return Pattern.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$", email);
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

}
