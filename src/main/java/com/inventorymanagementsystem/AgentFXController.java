package com.inventorymanagementsystem;

import atlantafx.base.util.Animations;
import com.inventorymanagementsystem.entity.Agence;
import com.inventorymanagementsystem.entity.Agent;
import com.inventorymanagementsystem.entity.Client;
import com.inventorymanagementsystem.services.*;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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

public class AgentFXController {


    public AnchorPane agentAddPane;
    public Button add_agent_btn;
    public ImageView photo1_agent;
    public Label nom_agence_agent;
    public Button verif_agent_btn;
    public Button uploadButton;
    public Label agence_agent;
    public Button verrif_agent_btn;
    public CustomTextField nom_agent;
    public CustomTextField prenom_agent;
    public CustomTextField email_agent;
    public CustomTextField numero_agent;
    public ComboBox poste_agent;
    public AnchorPane agentsListPane;
    public ScrollPane agentCardScrollPane;
    public VBox agentCardVBox;
    public TextField agent_search;
    public AnchorPane agentEditPane;
    public Button edit_agent_btn;
    public ImageView photo1_agent_edit;
    public Label nomprenom_client_edit;
    public Label age_client_edit;
    public CustomTextField matricule_edit;
    public CustomTextField password_agent_edit;
    public Button upload_photo_edit;
    public CustomTextField nom_agent_edit;
    public CustomTextField prenom_agent_edit;
    public CustomTextField email_agent_edit;
    public CustomTextField numero_agent_edit;
    public ComboBox agence_agent_edit;
    public ComboBox poste_agent_edit;
    public Button verif_agent_btn_edit;
    public Label agence_client;
    public ComboBox agence_agent_add;
    public Map<Node, Agent> agentMap = new HashMap<>();
    public Button clients_btn;
    public Label nom_agent_session;
    double x,y;

    ////:session
    public String path_photo_add;
    public int id_agent_to_edit;
    public Button signout_btn;

    /////////fin session
    public void setAgentName(String agentName) {
        nom_agent_session.setText(agentName);
    }

    @FXML
    void movetoShow(){
        displayAgents(new ArrayList<>(AgentService.getInstance().getAll()));

        agentsListPane.setVisible(true);
        agentAddPane.setVisible(false);
        agentEditPane.setVisible(false);


    }
    @FXML
    void movetoAdd(){
        agentEditPane.setVisible(false);

        agentsListPane.setVisible(false);
        agentAddPane.setVisible(true);


    }
    @FXML
    void movetoEdit(){
        agentEditPane.setVisible(true);
        agentsListPane.setVisible(false);
        agentAddPane.setVisible(false);
    }
    @FXML
    public void movetoClients()
    {
        DashBoardService.moveFunction(clients_btn, "DashFX.fxml",getClass(), nom_agent_session);
    }
    public void clearDataAgent() {
        agence_agent_add.setValue(null);
        nom_agent.clear();
        prenom_agent.clear();
        email_agent.clear();
        numero_agent.clear();
        agence_agent_edit.setValue(null);
        poste_agent.setValue(null);
    }

    @FXML
    public void initialize() {
        movetoShow();
       add_agent_btn.setDisable(true);
        List<Agent> agents = AgentService.getInstance().getAll();
        displayAgents(agents);
        agent_search.textProperty().addListener((observable, oldValue, newValue) -> {
            List<Agent> filteredAgents = filterAgents(newValue);
            displayAgents(filteredAgents);
        });
        remplirComboBoxAgences();

    }
    private void remplirComboBoxAgences() {
        AgenceService agenceService = new AgenceService();
        List <Agence> listeAgences = agenceService.getAll();
        ObservableList<String> nomsAgences = FXCollections.observableArrayList();
        for (Agence agence : listeAgences) {
            nomsAgences.add(agence.getNom()+" | ID :" + agence.getId());

        }
        System.out.println(nomsAgences);
        agence_agent_add.setItems(nomsAgences);
        agence_agent_edit.setItems(nomsAgences);
    }

    private List<Agent> filterAgents(String searchText) {
        List<Agent> agents = AgentService.getInstance().getAll();

        List<Agent> filteredAgents = new ArrayList<>();
        for (Agent agent : agents) {
           String nom_prenom = agent.getNom()+" "+agent.getPrenom();
            if (nom_prenom.toLowerCase().contains(searchText.toLowerCase()) || agent.getMatricule().toLowerCase().contains(searchText.toLowerCase())) {
                filteredAgents.add(agent);
            }

        }
        return filteredAgents;
    }
    private Agent getAgentFromPane(StackPane pane) {
        for (Map.Entry<Node, Agent> entry : agentMap.entrySet()) {
            if (entry.getKey() instanceof StackPane && entry.getKey().equals(pane)) {
                return entry.getValue();
            }
        }
        return null;
    }

    private void displayAgents(List<Agent> agents) {
        // Effacer l'affichage actuel des agents
        agentCardVBox.getChildren().clear();

        // Afficher les agents filtrés
        for (int i = 0; i < agents.size(); i +=4) {
            // Créer une nouvelle rangée pour chaque groupe de cinq agents
            HBox agentRow = new HBox(10); // Espacement entre les cardviews
            agentRow.setPrefHeight(120); // Hauteur de chaque rangée
            agentRow.setPrefWidth(agentCardScrollPane.getWidth()); // Largeur de chaque rangée

            // Ajouter les cardviews pour chaque client dans la rangée actuelle
            for (int j = i; j < Math.min(i + 4, agents.size()); j++) {
                // Créer une StackPane pour chaque cardview

                StackPane clientCard = new StackPane();
                agentMap.put(clientCard, agents.get(j));

                clientCard.setPrefSize(200, 100); // Taille du cardview

                // Créer un rectangle pour le fond du cardview
                Rectangle background = new Rectangle(200, 100, Color.LIGHTGRAY);
                background.setArcWidth(20);
                background.setArcHeight(20);
                background.setStyle("-fx-fill: lightgray; -fx-stroke: black; -fx-stroke-width: 2px;");
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
                ///
                ImageView photoView = new ImageView(new Image("file:C:\\xampp\\htdocs\\BANEKTEK_FINAL_SYMFONY_JAVA\\public\\"+agents.get(j).getPhoto()));
                photoView.setFitWidth(100); // Largeur de la photo
                photoView.setFitHeight(80); // Hauteur de la photo
                photoView.setPreserveRatio(true);
                Text clientName = new Text(agents.get(j).getNom());
                clientName.setStyle("-fx-font-size: 16px; -fx-fill: black; -fx-font-style: italic;");
                StackPane.setAlignment(clientName, Pos.TOP_LEFT);
                StackPane.setMargin(clientName, new Insets(25, 0, 0, 10)); // 10 pixels de marge à gauche
////////////////////////////////////////////////////////////
                Text clientPrenom = new Text(agents.get(j).getPrenom());
                clientPrenom.setStyle("-fx-font-size: 16px; -fx-fill: black; -fx-font-style: italic;");
                StackPane.setAlignment(clientPrenom, Pos.TOP_LEFT);
                StackPane.setMargin(clientPrenom, new Insets(40, 0, 0, 10)); // 10 pixels de marge à gauche
////////////////////////////////////////////////////////////
                Text agentMatricule = new Text("Matricule : " +agents.get(j).getMatricule());
                agentMatricule.setStyle("-fx-font-size: 10px; -fx-fill: black; -fx-font-style: italic;");
                StackPane.setAlignment(agentMatricule, Pos.TOP_LEFT);
                StackPane.setMargin(agentMatricule, new Insets(60, 0, 0, 10)); // 10 pixels de marge à gauche
                ///////////////////////////////////////////////:
                Text clientEmail = new Text(agents.get(j).getEmail());
                clientEmail.setStyle("-fx-font-size: 8px; -fx-fill: blue; -fx-font-style: italic;");
                StackPane.setAlignment(clientEmail, Pos.TOP_LEFT);
                StackPane.setMargin(clientEmail, new Insets(70, 0, 0, 10)); // 10 pixels de marge à gauche
                ///////////////////////////////////////////////////////////
                Button deleteButton = new Button("Supprimer");
                StackPane.setAlignment(deleteButton,Pos.CENTER_LEFT);
                StackPane.setMargin(deleteButton, new Insets(70, 0, 0, 10)); // 10 pixels de marge à gauche
               // deleteButton.setStyle("-fx-font-size: 10px; -fx-background-color: red; -fx-text-fill: white; -fx-font-style: bold;");
                deleteButton.getStyleClass().add("deleteButton");

                deleteButton.setOnAction(e -> {
                    // Afficher une boîte de dialogue de confirmation
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation de suppression");
                    alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cet agent ?");
                    alert.setContentText("Cette action est irréversible.");

                    // Attendre la réponse de l'utilisateur
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            StackPane agentpane = (StackPane) deleteButton.getParent();

                            // Obtenir la référence au client associé à cette StackPane
                            // Vous devrez ajouter une méthode pour obtenir le client à partir de la StackPane
                            Agent agentToDelete = getAgentFromPane(agentpane);

                            // Supprimer le client de la liste des agents
                            AgentService.getInstance().delete(agentToDelete.getId());

                            displayAgents(new ArrayList<>(AgentService.getInstance().getAll()));
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

                    StackPane agentPane = (StackPane) editButton.getParent();


                    Agent agentToEdit = getAgentFromPane(agentPane);
                    if(agentToEdit!=null){
                    remplirChampsAgentEdit(agentToEdit);
                    id_agent_to_edit= agentToEdit.getId();
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
                clientCard.getChildren().addAll(background, photoView, clientName,clientPrenom,agentMatricule,clientEmail,editButton,deleteButton);
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
                StackPane.setMargin(photoView, new Insets(20, 5, 0, 0)); // 20 pixels de marge en haut

                // Ajouter la StackPane à la rangée actuelle
                agentRow.getChildren().add(clientCard);
            }

            // Ajouter la rangée à la VBox principale
            agentCardVBox.getChildren().add(agentRow);
        }
    }



@FXML
    public void uploadPhoto(ActionEvent actionEvent) {
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
        photo1_agent.setImage(image);
        photo1_agent.setFitWidth(80);
        photo1_agent.setFitHeight(80);
        photo1_agent.setStyle("-fx-background-radius: 50px;");}}


    // Ajoutez ici les méthodes pour gérer les événements des éléments de l'interface utilisateur
@FXML
    public void verifier_agent(){


    add_agent_btn.setDisable(false);

}
    @FXML
    private void handleAddAgentButtonAction(ActionEvent event) {
        String poste = (String) poste_agent.getValue();

        String numero = numero_agent.getText();
        String email = email_agent.getText();
        String prenom = prenom_agent.getText();
        String nom = nom_agent.getText();

        String selectedAgence = (String) agence_agent_add.getValue();

// Diviser la chaîne en sous-chaînes en utilisant le séparateur " | ID :"
        String[] parts = selectedAgence.split("\\| ID :");

// parts[1] contiendra l'ID de l'agence sélectionnée
        int selectedAgenceId = Integer.parseInt(parts[1].trim());

        // Contrôles de saisie
        if (!validateNomPrenom(nom)) {
            setRedText(nom_agent);
            showAlert("Nom doit contenir uniquement des lettres alphabétiques");
            return;
        } else {
            resetTextFieldStyle(nom_agent);
        }
        if (!validateNomPrenom(prenom)) {
            setRedText(prenom_agent);
            showAlert("Prénom doit contenir uniquement des lettres alphabétiques");
            return;
        } else {
            resetTextFieldStyle(prenom_agent);
        }
        if (numero.isBlank() || !numero.matches("\\d+")) {
            setRedText(numero_agent);
            showAlert("Numéro de téléphone doit être un nombre");
            return;
        } else {
            resetTextFieldStyle(numero_agent);
        }
        if (email.isBlank() || !validateEmail(email)) {
            setRedText(email_agent);
            showAlert("Adresse email invalide");
            return;
        } else {
            resetTextFieldStyle(email_agent);
        }


        // Génération de mot de passe et nom d'utilisateur
        String password = Agent.generateStrongPassword(8);
        String username =   Agent.generateMatricule(nom, prenom);

        // Génération d'un nom de fichier photo aléatoire
        Random random = new Random();
        int randomNumber = random.nextInt(90) + 10;
        String fileName_photo = nom.toLowerCase() + "_" + prenom.toLowerCase() + "_" + randomNumber + ".png";

        // Création de l'objet Agent
        Agent agent = new Agent(0, selectedAgenceId, nom, prenom, poste, username, password, email, numero, "agents_photos/"+fileName_photo, "");
        System.out.println(fileName_photo);
        System.out.println("username : " + username);

        boolean ajoutReussi = AgentService.add(agent);
        if (ajoutReussi) {
            if (path_photo_add != null && !path_photo_add.isEmpty()) {

                String destinationDirectory = "C:/xampp/htdocs/BANEKTEK_FINAL_SYMFONY_JAVA/public/agents_photos/";
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setContentText("Agent ajouter avec succès !");
            alert.setHeaderText(null);
            alert.showAndWait();
            clearDataAgent();
            movetoShow();
        } else {
            // Code pour l'ajout échoué
        }
    }

    public void remplirChampsAgentEdit(Agent agent) {
        // Remplissage des champs de texte
        nom_agent_edit.setText(agent.getNom());
        prenom_agent_edit.setText(agent.getPrenom());
        email_agent_edit.setText(agent.getEmail());
        numero_agent_edit.setText(agent.getNumTel());

        // Remplissage des champs de ComboBox
        poste_agent_edit.setValue(agent.getPoste());
        Agence agence_edit=AgenceService.getInstance().getAgenceById(agent.getIdAgence());
        agence_agent_edit.setValue(agence_edit.getNom()+" | ID :"+agence_edit.getId());

        // Génération d'un nom d'utilisateur basé sur le nom et le prénom
        String username = Agent.generateMatricule(agent.getNom(), agent.getPrenom());
        matricule_edit.setText(username);
        // Remplissage du champ de date de naissance si nécessaire
        // dob_agent_edit.setValue(agent.getDob());
        // Remplissage de l'image du profil si nécessaire
        // photo1_agent_edit.setImage(new Image(agent.getPhoto()));
        photo1_agent_edit.setImage(new Image("file:C:\\xampp\\htdocs\\BANEKTEK_FINAL_SYMFONY_JAVA\\public\\"+agent.getPhoto()));

    }

    @FXML
    private void handleEditAgentButtonAction(ActionEvent event) {
        // Récupération des valeurs des champs de l'interface utilisateur
        String poste = (String) poste_agent_edit.getValue();
        String numero = numero_agent_edit.getText();
        String email = email_agent_edit.getText();
        String prenom = prenom_agent_edit.getText();
        String nom = nom_agent_edit.getText();
        String selectedAgence = (String) agence_agent_edit.getValue();

// Diviser la chaîne en sous-chaînes en utilisant le séparateur " | ID :"
        String[] parts = selectedAgence.split("\\| ID :");

// parts[1] contiendra l'ID de l'agence sélectionnée
        int selectedAgenceId = Integer.parseInt(parts[1].trim());
        // Contrôles de saisie
        if (!validateNomPrenom(nom)) {
            setRedText(nom_agent_edit);
            showAlert("Nom doit contenir uniquement des lettres alphabétiques");
            return;
        } else {
            resetTextFieldStyle(nom_agent_edit);
        }
        if (!validateNomPrenom(prenom)) {
            setRedText(prenom_agent_edit);
            showAlert("Prénom doit contenir uniquement des lettres alphabétiques");
            return;
        } else {
            resetTextFieldStyle(prenom_agent_edit);
        }
        if (numero.isBlank() || !numero.matches("\\d+")) {
            setRedText(numero_agent_edit);
            showAlert("Numéro de téléphone doit être un nombre");
            return;
        } else {
            resetTextFieldStyle(numero_agent_edit);
        }
        if (email.isBlank() || !validateEmail(email)) {
            setRedText(email_agent_edit);
            showAlert("Adresse email invalide");
            return;
        } else {
            resetTextFieldStyle(email_agent_edit);
        }

        // Génération de mot de passe et nom d'utilisateur
        String password = Agent.generateStrongPassword(8);
        String username =   Agent.generateMatricule(nom, prenom);

        // Création de l'objet Agent avec les données modifiées
        Agent agent = new Agent(id_agent_to_edit, selectedAgenceId, nom, prenom, poste, "", password, email, numero, "", "");

        // Appel de la méthode de mise à jour dans le service
        if (AgentService.edit(agent)) {
            System.out.println("Agent mis à jour avec succès !");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setContentText("Agent mis à jour avec succès !");
            alert.setHeaderText(null);
            alert.showAndWait();
            clearDataAgent();
            movetoShow();
        } else {
            System.out.println("Erreur lors de la mise à jour de l'agent.");
            // Code pour gérer le cas où la mise à jour échoue
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
