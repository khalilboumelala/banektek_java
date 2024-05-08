package com.inventorymanagementsystem;

//import atlantafx.base.controls.ToggleSwitch;

import atlantafx.base.controls.Card;
import atlantafx.base.util.Animations;
import com.inventorymanagementsystem.config.Database;
import com.inventorymanagementsystem.utils.*;
import com.inventorymanagementsystem.entity.*;
import com.inventorymanagementsystem.services.*;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTreeView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.controlsfx.control.Rating;
import org.controlsfx.control.TaskProgressView;
import org.controlsfx.control.ToggleSwitch;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2MZ;


import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

import static org.burningwave.core.assembler.StaticComponentContainer.Modules;

public class DashboardController implements Initializable {

    public AnchorPane credit_pane;
    public Button credits_btn;
    private double x;
    private double y;

    public Client getClient_connecte() {
        return client_connecte;
    }

    public void setClient_connecte(Client client_connecte) {
        this.client_connecte = client_connecte;
        nom_agent_session.setText(client_connecte.getNom()+" "+client_connecte.getPrenom());

    }

    public Client client_connecte;




    @FXML
    private Label user;



    private Connection connection;

    private Statement statement;

    private PreparedStatement preparedStatement;

    private ResultSet resultSet;


//AGENCE





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
    private Button agences_btn;
    @FXML
    private AnchorPane comptes_pane;



    @FXML
    private Button signout_btn;
    @FXML
    private Button comptes_btn;





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
        credit_pane.setVisible(false);
        //Button color to default
        comptes_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        clients_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        cartes_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        virements_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        agences_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        articles_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        comments_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        transaction_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        reclamation_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");
        credits_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.2),  rgba(255,106,239,0.2))");

    }
  public void  ActivateThisAnchor(AnchorPane AnchorTest,Button ButtonTest){

        disactivateAllPanes();
      Animations.fadeIn(AnchorTest, Duration.millis(1000)).playFromStart();
      AnchorTest.setVisible(true);
      ButtonTest.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(121,172,255,0.7),  rgba(255,106,239,0.7))");
      AnchorTest.toFront();
      AnchorTest.toFront();
      AnchorTest.toFront();
      AnchorTest.toFront();

    }
    String oldStyle ;
    public void activateAnchorPane(){


        comptes_btn.setOnMouseClicked(mouseEvent -> {
            content_pane.setStyle("-fx-background-color:   #1f375e; -fx-background-radius:  0 0;");
            ActivateThisAnchor(comptes_pane,comptes_btn);
        });
        credits_btn.setOnMouseClicked(mouseEvent -> {
            content_pane.setStyle("-fx-background-color:   #1f375e; -fx-background-radius:  0 0;");
            ActivateThisAnchor(credit_pane,credits_btn);
        });
        clients_btn.setOnMouseClicked(mouseEvent -> {
            content_pane.setStyle("-fx-background-color:   #1f375e; -fx-background-radius:  0 0;");
            ActivateThisAnchor(clients_pane,clients_btn);
        });
        cartes_btn.setOnMouseClicked(mouseEvent -> {
            content_pane.setStyle("-fx-background-color:   #1f375e; -fx-background-radius:  0 0;");
            ActivateThisAnchor(cartes_pane,cartes_btn);
        });
        virements_btn.setOnMouseClicked(mouseEvent -> {
            content_pane.setStyle("-fx-background-color:   #1f375e; -fx-background-radius:  0 0;");
            ActivateThisAnchor(virements_pane,virements_btn);
        });

        agences_btn.setOnMouseClicked(mouseEvent -> {
            content_pane.setStyle("-fx-background-color:   #1f375e; -fx-background-radius:  0 0;");
            ActivateThisAnchor(agences_pane,agences_btn);
            AgenceFront AgenceFront = loaderAgence.getController();
            AgenceFront.showAgenceData();


        });
        articles_btn.setOnMouseClicked(mouseEvent -> {

            content_pane.setStyle("-fx-background-color: lightgray; -fx-background-radius: 15;");

            ActivateThisAnchor(articles_pane,articles_btn);
        });
        comments_btn.setOnMouseClicked(mouseEvent -> {
            content_pane.setStyle("-fx-background-color: lightgray; -fx-background-radius: 15;");

            ActivateThisAnchor(comments_pane,comments_btn);
        });
        transaction_btn.setOnMouseClicked(mouseEvent -> {
            content_pane.setStyle("-fx-background-color:   #1f375e; -fx-background-radius:  0 0;");
            ActivateThisAnchor(transaction_pane,transaction_btn);
        });
        reclamation_btn.setOnMouseClicked(mouseEvent -> {
            content_pane.setStyle("-fx-background-color:   #1f375e; -fx-background-radius:  0 0;");
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

    FXMLLoader loaderAgence= new FXMLLoader(getClass().getResource("AgenceFront.fxml"));
    Pane AgenceLoadedPane;
    /*FXMLLoader loaderAgent= new FXMLLoader(getClass().getResource("AgentFX.fxml"));
    Pane AgentLoadedPane;*/

    FXMLLoader loaderClient= new FXMLLoader(getClass().getResource("MonCompte.fxml"));
    Pane ClientLoadedPane;

    FXMLLoader loaderCompte= new FXMLLoader(getClass().getResource("CompteFrontFX.fxml"));
    Pane CompteLoadedPane;

    FXMLLoader loaderCarte= new FXMLLoader(getClass().getResource("CarteFrontFX.fxml"));
    Pane CarteLoadedPane;
    FXMLLoader loaderVirement= new FXMLLoader(getClass().getResource("VirementFrontFX.fxml"));
    Pane VirementLoadedPane;
    FXMLLoader loaderTransaction= new FXMLLoader(getClass().getResource("TransactionFrontFX.fxml"));
    Pane TransactionLoadedPane;

    FXMLLoader loaderCredit= new FXMLLoader(getClass().getResource("CreditFrontFX.fxml"));
    Pane CreditLoadedPane;

    FXMLLoader loaderReclamation= new FXMLLoader(getClass().getResource("FrontSalima.fxml"));
    Pane ReclamationLoadedPane;

    List<Compte> comptelist;

    public List<Compte> getComptelist() {
        return comptelist;
    }

    public void setComptelist(List<Compte> comptelist) {
        this.comptelist = comptelist;
    }

    public void initializeAllFxml() {
        try {
            AgenceLoadedPane = loaderAgence.load();
            //  adjustPaneSize(AgenceLoadedPane);
            agences_pane.getChildren().add(AgenceLoadedPane);
            agences_pane.toFront();



            // For Client loader
            Task<Void> clientTask = new LoadFxmlTask(loaderClient);
           clientTask.setOnSucceeded(event -> {
                clients_pane.getChildren().add(((LoadFxmlTask) event.getSource()).getLoadedPane());
                clients_pane.toFront();
                // adjustPaneSize is not shown in the provided code, make sure to adjust the size if needed
            });
            new Thread(clientTask).start();

// For Compte loader

           // CompteFrontController compteController = loaderCompte.getController();
          //  compteController.setClient_connecte(client_connecte);
            Task<Void> compteTask = new LoadFxmlTask(loaderCompte);
            compteTask.setOnSucceeded(event -> {

                comptes_pane.getChildren().add(((LoadFxmlTask) event.getSource()).getLoadedPane());

            comptes_pane.toFront();
            CompteFrontController cl=((LoadFxmlTask) event.getSource()).getLoader().getController();
            cl.setComptes(comptelist);
            cl.displayCompte();


                // adjustPaneSize is not shown in the provided code, make sure to adjust the size if needed
            });
            new Thread(compteTask).start();

// For Carte loader
            Task<Void> carteTask = new LoadFxmlTask(loaderCarte);
            carteTask.setOnSucceeded(event -> {
                cartes_pane.getChildren().add(((LoadFxmlTask) event.getSource()).getLoadedPane());
             cartes_pane.toFront();
                CarteFrontController crl=((LoadFxmlTask) event.getSource()).getLoader().getController();

                crl.setComptes(comptelist);

                crl.movetoShow();
                crl.displayCarte();
                // adjustPaneSize is not shown in the provided code, make sure to adjust the size if needed
            });
            new Thread(carteTask).start();

// For Virement loader
            Task<Void> virementTask = new LoadFxmlTask(loaderVirement);
            virementTask.setOnSucceeded(event -> {
                virements_pane.getChildren().add(((LoadFxmlTask) event.getSource()).getLoadedPane());
               virements_pane.toFront();
                VirementFrontController vrl=((LoadFxmlTask) event.getSource()).getLoader().getController();

                vrl.setComptes(comptelist);

                vrl.movetoShow();
                //vrl.displayCarte();
                // adjustPaneSize is not shown in the provided code, make sure to adjust the size if needed
            });
            new Thread(virementTask).start();

// For Transaction loader
            Task<Void> transactionTask = new LoadFxmlTask(loaderTransaction);
            transactionTask.setOnSucceeded(event -> {
                transaction_pane.getChildren().add(((LoadFxmlTask) event.getSource()).getLoadedPane());
              transaction_pane.toFront();
                TransactionFrontController tcl=((LoadFxmlTask) event.getSource()).getLoader().getController();

                tcl.setComptes(comptelist);

                tcl.movetoShow();
                // adjustPaneSize is not shown in the provided code, make sure to adjust the size if needed
            });
            new Thread(transactionTask).start();

// For Credit loader
            Task<Void> creditTask = new LoadFxmlTask(loaderCredit);
            creditTask.setOnSucceeded(event -> {
                credit_pane.getChildren().add(((LoadFxmlTask) event.getSource()).getLoadedPane());
               credit_pane.toFront();
                CreditFrontController tcl=((LoadFxmlTask) event.getSource()).getLoader().getController();

                tcl.setComptes(comptelist);

                tcl.movetoShow();
                // adjustPaneSize is not shown in the provided code, make sure to adjust the size if needed
            });
            new Thread(creditTask).start();

            ReclamationLoadedPane = loaderReclamation.load();
            //  adjustPaneSize(AgenceLoadedPane);
            reclamation_pane.getChildren().add(ReclamationLoadedPane);
            reclamation_pane.toFront();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void adjustPaneSize(Pane pane) {
        double width = pane.getPrefWidth() * 0.01;
        double height = pane.getPrefHeight() * 0.01;
        pane.setPrefWidth(width);
        pane.setPrefHeight(height);
    }


@FXML
Label nom_agent_session;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Exports all modules to other modules
        Modules.exportAllToAll();
        disactivateAllPanes();
      //  setUsername();
        //showTableClient();


        initializeAllFxml();


        addarticlepanes();
        ActivateThisAnchor(clients_pane,clients_btn);

       // nom_agent_session.setText(client_connecte.getNom()+" "+client_connecte.getPrenom());





/*
        agence_listview.setExpanded(true);
        agence_listview.depthProperty().set(1);*/

        connection = Database.getInstance().connectDB();



        String[] command = {"wsl", "bash", "-c", "ollama run llama2-uncensored"};
         builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);
        try {
            process=builder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    ProcessBuilder builder;
    Process process;
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
            articlePane.setContenu(article.getContenu());
            articlePane.setTitre(article.getTitre());
            articlePane.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
            articlePane.setPrefWidth(articleWidth);

            articlePane.setPrefHeight(articleHeight);

            // Create ImageView for the image
            ImageView imageView = new ImageView(new Image("file:C:/" + article.getImage()));
            imageView.setFitWidth(articleWidth-0);
            imageView.setFitHeight(articleHeight-0);
            imageView.setOpacity(0.75);

            Text titleLabel = new Text(article.getTitre());
            Text contentText;
            String contenu = article.getContenu();
            if (contenu.length() > 300) {
                contentText = new Text(contenu.substring(0, 300));
            } else {
                contentText = new Text(contenu);
            }

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
            ///    currentRow.setAlignment(Pos.CENTER); // Align articles in the center horizontally
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
        articles_front_anchorpane.toFront();
        articles_pane.toFront();
        articlesContainer.toFront();
       // articlesContainer.toFront();
       // articles_pane.toFront();

    }

    @FXML
    AnchorPane mainAnchorpane;
    @FXML
    private TextArea add_comment_field ;
     @FXML
private     Rating article_rating;
    ArticlePane clonedPane = new ArticlePane();
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
                            String Contenu= getEntityProperty("article",articleId,"contenu");

                            articlePane.setContenu(Contenu);
                            addcommentpane(articlePane);
                        });
                    }
                }
            }
        }
    }

    @FXML
    JFXSlider comments_slider;




@FXML
AnchorPane comment_anchorpane1;



 public void   addcommentpane(ArticlePane articlePane)
 {

     //comments_pane.getChildren().clear();

     comment_anchorpane1.getChildren().clear();
    clonedPane.getChildren().clear();
     clonedPane = cloneArticlePane(articlePane);


     // Retrieve comments for the article
     ObservableList<Commentaire> comments = listCommentsById( clonedPane.getArticleId());
     //  VBox commentContainer = createCommentContainer(comments);

     // Create Rating and TextArea for adding comments
     // Rating rating = new Rating();
     createCommentField();
     Button addButton = new Button("POST");


     addButton.setStyle("-fx-background-color:linear-gradient(to bottom right , #2f466b, #662d60);\n" +
             "-fx-background-radius:3px;\n" +
             "-fx-cursor:hand;\n" +
             "-fx-text-fill:#fff;");

     addButton.setLayoutX(467);
     addButton.setLayoutY(141);
     addButton.setOnAction(eventt -> addComment(articlePane.getArticleId()) );

     // Layout for adding comments and existing comments
     HBox addCommentAndCommentsHBox = new HBox(100);
     addCommentAndCommentsHBox.getChildren().addAll(add_comment_field,addButton);
     //    addCommentAndCommentsHBox.setPrefHeight(100); // Adjust the value as needed

     // Add the cloned article pane and comments layout to the comments_pane
     VBox vBox = new VBox(100); // No spacing between children
     vBox.getChildren().addAll(clonedPane, addCommentAndCommentsHBox);
     // Add the cloned article pane and comments layout to the comments_pane
     AnchorPane.setTopAnchor(vBox, 10.0); // Set the top margin of vBox
     AnchorPane.setLeftAnchor(vBox, 10.0); // Set the left margin of vBox
     //  AnchorPane.setTopAnchor(article_rating, 500.0); // Set the top margin of article_rating
     AnchorPane.setLeftAnchor(article_rating, 30.0); // Set the right margin of article_rating
     AnchorPane.setBottomAnchor(article_rating,5.0);

     article_rating.setVisible(true);
     comment_anchorpane1.getChildren().addAll(vBox,article_rating);

     // Activate the comments_pane
     ActivateThisAnchor(comments_pane, comments_btn);


 }

    public void addComment(Integer article_id) {
        // Check if any mandatory fields are blank
        // Here, you would typically check if the user has provided all necessary information for adding a comment to the article

        // Assuming that the user input for the comment is stored in a variable called "commentContent"
        if (add_comment_field.getText().equals("")) {
           Animations.shakeX( add_comment_field).playFromStart();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill the comment content.");
            alert.showAndWait();
            return;
        }

        // Create a Commentaire instance
        Commentaire commentaire = new Commentaire();
        commentaire.setArticleId(article_id);
        commentaire.setContenu(add_comment_field.getText()); // Assuming commentContent is the content of the comment
        commentaire.setDate(new java.util.Date()); // Set the current date for the comment
        commentaire.setNote((int) article_rating.getRating()); // Set the rating
        commentaire.setUserId(client_connecte.getId());
        // Call the service to add the comment to the database
        boolean result = CommentaireService.getInstance().add(commentaire);

        if (result) {
            // Success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Comment Added.");
            alert.showAndWait();
        } else {
            // Error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add the comment.");
            alert.showAndWait();
        }

       // comments_pane.getChildren().clear();
      //  ObservableList<Commentaire> comments = listCommentsById(article_id);

       addcommentpane(clonedPane.getOriginal());

    }

    VBox commentContainer;

    // Helper method to clone the article pane
    private ArticlePane cloneArticlePane(ArticlePane articlePane) {
      //  ArticlePane clonedPane = new ArticlePane();
        clonedPane.setStyle("-fx-border-width: 0;");
        clonedPane.setPrefSize(articlePane.getPrefWidth() + 100, articlePane.getPrefHeight() + 100);
        clonedPane.setArticleId(articlePane.getArticleId());
        clonedPane.setOriginal(articlePane);
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

                if (textcounter == 2) {
                    ScrollPane scrollPane = new ScrollPane();
                    TextArea Content= new TextArea(articlePane.getContenu());
                    Content.setEditable(false);
                    Content.setPrefColumnCount(30);
                    Content.setPrefRowCount(30);
                    Content.setWrapText(true);
                    scrollPane.setContent(Content);
                    scrollPane.setPrefSize(350, 250); // Adjust size as needed
                    scrollPane.setLayoutX(((Text) child).getLayoutX() + 400);
                    scrollPane.setLayoutY(((Text) child).getLayoutY() );
                    ObservableList<Commentaire> comments = listCommentsById(clonedPane.getArticleId()); // Replace with actual comments list
                    commentContainer = createCommentContainer(comments);

                    commentContainer.setLayoutX(((Text) child).getLayoutX() + 400);
                    commentContainer.setLayoutY(((Text) child).getLayoutY() + 350);
                    Label commentslabel = new Label("Comments:");
                    commentslabel.setLabelFor(scrollPane);
                    commentslabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
                    commentslabel.setLayoutX(((Text) child).getLayoutX() + 400);
                    commentslabel.setLayoutY(((Text) child).getLayoutY() + 250);
                    clonedPane.getChildren().addAll( scrollPane,commentslabel,commentContainer);
                } else {
                    clonedPane.getChildren().add(text);
                }
            }
        }

        // Create the comment container


        // Create the add comment field


        return clonedPane;
    }

    private Node createRatingStars(int rating) {
        HBox stars = new HBox(5); // Adjust spacing as needed

        // Add star icons based on the rating
        for (int i = 0; i < rating; i++) {
            FontIcon starIcon = new FontIcon(Material2MZ.STAR);
            starIcon.getStyleClass().add("star-icon");
            stars.getChildren().add(starIcon);
        }

        return stars;
    }
    private VBox createCommentContainer(ObservableList<Commentaire> comments) {
        if (comments.isEmpty()) {
            // If no comments are found, return an empty VBox
            return new VBox();
        }
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


                int rating = comment.getNote();
                Node ratingStars = createRatingStars(rating);


               // header.getStyleClass().add("comment-title");


                // Create a new Card for each comment
                Card commentCard = new Card();
                commentCard.getStyleClass().add("comment-card");

                Label titleLabel = new Label( fullname);
                titleLabel.getStyleClass().add("comment-title");

                Label descriptionLabel = new Label(fonction);
                descriptionLabel.getStyleClass().add("comment-description");

                HBox headerBox = new HBox(10, imageView, titleLabel, descriptionLabel,ratingStars);
                headerBox.setMaxHeight(0);
                headerBox.setPrefHeight(0);


                Text text = new Text(comment.getContenu());
                text.getStyleClass().add("comment-text");

                // Separator line between comment cards
                Separator separator = new Separator(Orientation.VERTICAL);
                separator.setPadding(new Insets(0, 10, 0, 10));

                // X icon for deleting comments
                HBox footer;
                //Integration User
                if (comment.getUserId()==0) {
                    FontIcon deleteIcon = new FontIcon(Material2AL.CLOSE);
                    deleteIcon.getStyleClass().add("delete-icon");
                    deleteIcon.setCursor(Cursor.HAND);

                    // Event listener for deleting comments (to be added later)
                    deleteIcon.setOnMouseClicked(event -> {
                        // Empty event listener for deleting comments
                        CommentaireService.getInstance().delete(comment.getId());
                        addcommentpane(clonedPane.getOriginal());
                        showAlertArticle(Alert.AlertType.INFORMATION, "Success", null, "Commentaire supprime avec succes.");
                    });

                    // Footer containing the X icon
                     footer = new HBox(10, deleteIcon);

                    footer.setAlignment(Pos.CENTER_LEFT);
                }
                else {
                     footer = new HBox(10);

                    footer.setAlignment(Pos.CENTER_LEFT);
                }
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
    private void createCommentField() {

        add_comment_field.setPrefRowCount(3);
        add_comment_field.setPrefColumnCount(24);
        add_comment_field.setPromptText("Add a comment");
add_comment_field.setVisible(true);

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

            closeDrawer();
        //    closeDrawer();
  //   content_pane.setLayoutX(content_pane.getLayoutX()-100);
        } else {
            // If drawer is closed, open it

            openDrawer();

           // content_pane.setLayoutX(content_pane.getLayoutX()+100);
        }
    }
@FXML

    private TaskProgressView articlegenerationtaskprogressview;

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






    int selectedArticle=0;
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

    private void showAlertArticle(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }






    private void validate(CheckBox checkBox,Article a, Event event) {
        // Validate here
        event.consume();

        checkBox.setSelected(!checkBox.isSelected());
        a.setSelected(true);
    }





    //RECLAMATION-REPONSES
    @FXML
    private AnchorPane reclamation_pane;

    @FXML
    private Button reclamation_btn;


    //


}
