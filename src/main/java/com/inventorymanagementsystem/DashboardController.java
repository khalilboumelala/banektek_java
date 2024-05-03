package com.inventorymanagementsystem;

//import atlantafx.base.controls.ToggleSwitch;
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
    private TableView<Article> article_table;
    @FXML
    private JFXListView<Article> article_listview;
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





    ///////fin client ////////:

    //

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
@FXML
private AnchorPane comptes_pane;
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

    FXMLLoader loaderAgence= new FXMLLoader(getClass().getResource("Agence.fxml"));
    Pane AgenceLoadedPane;
    public void initializeAllFxml(){

        try {
            // Load the FXML file
           AgenceLoadedPane = loaderAgence.load();
            // Add the loaded Pane to secPane
            agences_pane.getChildren().add(AgenceLoadedPane);
            agences_pane.toFront();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Exports all modules to other modules
        Modules.exportAllToAll();

        setUsername();
        //showTableClient();


initializeAllFxml();

        showArticleData();
        addarticlepanes();

    /*    showCompteData();
        initializeCompteListSelection();

        showCarteData();
        initializeCarteListSelection();

        showVirementData();
        initializeVirementListSelection();

        showTypeVirementData();
        initializeTypeVirementListSelection();

        showTransactionData();
        initializeTransactionListSelection();
*/


        populateTreeView(comment_treeview);

/*
        agence_listview.setExpanded(true);
        agence_listview.depthProperty().set(1);*/

        connection = Database.getInstance().connectDB();

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
            articlePane.setContenu(article.getContenu());
            articlePane.setTitre(article.getTitre());
            articlePane.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
            articlePane.setPrefWidth(articleWidth);

            articlePane.setPrefHeight(articleHeight);

            // Create ImageView for the image
            ImageView imageView = new ImageView(new Image("file:C:/" + article.getImage()));
            imageView.setFitWidth(articleWidth-0);
            imageView.setFitHeight(articleHeight-0);
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
                            String titre=articlePane.getTitre();
                            String Contenu= articlePane.getContenu();
                            article_titre_input.setText(titre);
                            //
                            article_contenu_input.setPrefRowCount(10);
                            article_contenu_input.setWrapText(true);
                            article_contenu_input.setText(Contenu);

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
    public void comment_slider_panes(){

        double position = comments_slider.getValue();

        if (position >= 0 && position < 50) {
            comment_anchorpane1.setVisible(false);
            comment_anchorpane2.setVisible(true);
        } else if (position >= 51 && position < 100) {
            comment_anchorpane2.setVisible(false);
            comment_anchorpane1.setVisible(true);
        }

    }



@FXML
AnchorPane comment_anchorpane1;
    @FXML
    AnchorPane comment_anchorpane2;

    @FXML
    public void switchfrontbackcomments(){

        if (comment_anchorpane1.isVisible()) {
            comment_anchorpane1.setVisible(false);
            comment_anchorpane2.setVisible(true);
        }
        else
        {
            comment_anchorpane2.setVisible(false);
            comment_anchorpane1.setVisible(true);
        }
    }
@FXML
private    Button editButton ;
 public void   addcommentpane(ArticlePane articlePane)
 {
     comment_anchorpane1.getChildren().clear();
     clonedPane = cloneArticlePane(articlePane);


     // Retrieve comments for the article
     ObservableList<Commentaire> comments = listCommentsById( clonedPane.getArticleId());
     //  VBox commentContainer = createCommentContainer(comments);

     // Create Rating and TextArea for adding comments
     // Rating rating = new Rating();
     createCommentField();
     Button addButton = new Button("POST");
     editButton.setText("EDIT");

     addButton.setStyle("-fx-background-color:linear-gradient(to bottom right , #2f466b, #662d60);\n" +
             "-fx-background-radius:3px;\n" +
             "-fx-cursor:hand;\n" +
             "-fx-text-fill:#fff;");
     editButton.setStyle("-fx-background-color:linear-gradient(to bottom right , #2f466b, #662d60);\n" +
             "-fx-background-radius:3px;\n" +
             "-fx-cursor:hand;\n" +
             "-fx-text-fill:#fff;");
     addButton.setLayoutX(467);
     addButton.setLayoutY(141);
     addButton.setOnAction(eventt -> addComment(articlePane.getArticleId()) );
     editButton.setLayoutX(467);
     editButton.setLayoutY(190);
     editButton.setOnAction(eventt -> {
         ActivateThisAnchor(articles_pane,articles_btn) ;
         articles_back_anchorpane.setVisible(true);articles_front_anchorpane.setVisible(false);


     });
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
     AnchorPane.setLeftAnchor(editButton, 320.0); // Set the right margin of article_rating
     AnchorPane.setBottomAnchor(editButton,10.0);
     editButton.setVisible(true);
     article_rating.setVisible(true);
     comment_anchorpane1.getChildren().addAll(vBox,article_rating,editButton);

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
        populateTreeView(comment_treeview);
    }

    VBox commentContainer;

    // Helper method to clone the article pane
    private ArticlePane cloneArticlePane(ArticlePane articlePane) {
        ArticlePane clonedPane = new ArticlePane();
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

                if (textcounter==2) {
                    ObservableList<Commentaire> comments = listCommentsById(clonedPane.getArticleId()); // Replace with actual comments list
                     commentContainer = createCommentContainer(comments);

                    commentContainer.setLayoutX(((Text) child).getLayoutX() + 400);
                    commentContainer.setLayoutY(((Text) child).getLayoutY() + 350);
                    Label commentslabel=new Label("Comments:");
                    commentslabel.setLabelFor(commentContainer);
                    commentslabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
                    commentslabel.setLayoutX(((Text) child).getLayoutX() + 400);
                    commentslabel.setLayoutY(((Text) child).getLayoutY() + 300);
                    clonedPane.getChildren().addAll(text,commentslabel,commentContainer);

                }
                else clonedPane.getChildren().add(text);
            }
        }

        // Create the comment container


        // Create the add comment field


        return clonedPane;
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
                    CommentaireService.getInstance().delete(comment.getId());
                    addcommentpane(clonedPane.getOriginal());
                    showAlertArticle(Alert.AlertType.INFORMATION, "Success", null, "Commentaire supprime avec succes.");
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
    private void createCommentField() {

        add_comment_field.setPrefRowCount(3);
        add_comment_field.setPrefColumnCount(24);
        add_comment_field.setPromptText("Add a comment");
add_comment_field.setVisible(true);

    }
    //
@FXML
    JFXTreeView<Object> comment_treeview=new JFXTreeView<>();

    public void populateTreeView(JFXTreeView<Object> treeView) {
        // Clear existing items
      // treeView.getRoot().getChildren().clear();

        // Get articles from the database or wherever they are stored
        List<Article> articles = ArticleService.getInstance().getAll();

        // Create root node
        FontAwesomeIconView commentIcon = new FontAwesomeIconView(FontAwesomeIcon.COMMENT);
        commentIcon.setSize("24");
        commentIcon.setFill(Color.web("#FFA500")); // Set custom color for the icon (in this case, orange)

        TreeItem<Object> rootItem = new TreeItem<>("Commentaires", commentIcon);
        treeView.setRoot(rootItem);
        rootItem.setExpanded(true);

        // Populate tree with articles and their comments
        for (Article article : articles) {
            FontAwesomeIconView articleIcon = new FontAwesomeIconView(FontAwesomeIcon.BOOK);
            articleIcon.setSize("24");
            articleIcon.setFill(Color.web("#FFA500")); // Set custom color for the icon (in this case, orange)

           TreeItem articleItem = new TreeItem(article.getTitre(),articleIcon);

            // Get comments for this article
            List<Commentaire> comments = listCommentsById(article.getId());

            // Add comments as child nodes
            for (Commentaire comment : comments) {
               String nomuser=getEntityProperty("client",comment.getUserId(),"nom");
               String prenomuser=getEntityProperty("client",comment.getUserId(),"prenom");
                FontAwesomeIconView CommentIcon = new FontAwesomeIconView(FontAwesomeIcon.COMMENT);
                CommentIcon.setSize("12");
                CommentIcon.setFill(Color.web("#FFA500")); // Set custom color for the icon (in this case, orange)

                TreeItem commentItem = new TreeItem(comment.getContenu()+" By user " +nomuser+" "+prenomuser,CommentIcon);
                articleItem.getChildren().add(commentItem);
            }

            // Add article node to root
            rootItem.getChildren().add(articleItem);
        }
    }
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




    // Global variable to store the path of the uploaded image
    private String imagePath = "";
    @FXML
    private ImageView Photoarticle;

    @FXML
    private void uploadImageArticle(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File to Upload");
        fileChooser.setInitialDirectory(new File("C:/Users/khali/Downloads")); // Set initial directory to C:/Users/Downloads

        // Set file extension filters if needed
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            try {
                // Generate a UUID to use as the filename
                String uniqueFileName = UUID.randomUUID().toString().substring(0, 8);;
                // Get the file extension from the selected file
                String fileExtension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf("."));
                // Construct the filename with the UUID and file extension
                String fileName = uniqueFileName + fileExtension;
                // Define the destination directory
                File destinationDirectory = new File("C:/uploads/");
                // If the directory doesn't exist, create it
                if (!destinationDirectory.exists()) {
                    destinationDirectory.mkdirs();
                }
                // Construct the destination file path
                File destination = new File(destinationDirectory, fileName);
                // Copy the selected file to the destination
                Files.copy(selectedFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Update the global variable with the image path
                imagePath = "uploads/" + fileName;

                // Load the image into the ImageView
                Image image = new Image(destination.toURI().toString());
                Photoarticle.setImage(image);

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
    @FXML
    public void addOrUpdateArticleData() {
        // Input validation
        boolean isAnyArticleFieldEmpty = false;

        if (article_titre_input.getText().isBlank()) {
            shakeTextField(article_titre_input);
            article_titre_input.setStyle("-fx-border-color: red;");
            isAnyArticleFieldEmpty = true;
        }

        if (article_contenu_input.getText().isBlank()) {
            shakeTextArea(article_contenu_input);
            article_contenu_input.setStyle("-fx-border-color: red;");
            isAnyArticleFieldEmpty = true;
        }

// Show overall alert if any article field is empty
        if (isAnyArticleFieldEmpty) {
            showAlertArticle(Alert.AlertType.INFORMATION, "Message", null, "Veuillez remplir tous les champs.");
            return;
        }


        // Create Article object from FXML input fields
        Article article = new Article();
        article.setContenu(article_contenu_input.getText());
        article.setImage(imagePath); // Use the global variable for the image path
        article.setTitre(article_titre_input.getText());
        article.setDatePub(java.sql.Date.valueOf(LocalDate.now())); // Current date for date_pub


        // Determine if an existing article is selected
        selectedArticle = clonedPane.getArticleId();//article_listview.getSelectionModel().getSelectedItem();
        if (selectedArticle != 0) {
            // If an article is selected, update its data
            article.setId(selectedArticle); // Set the ID of the selected article
            article.setIdAgent(Integer.parseInt(getEntityProperty("article",selectedArticle,"id_agent_id")));
            if (Objects.equals(imagePath, ""))  article.setImage(getEntityProperty("article",selectedArticle,"image"));


            if (ArticleService.getInstance().edit(article)) {
                showAlertArticle(Alert.AlertType.INFORMATION, "Message", null, "Article updated successfully.");
                showArticleData();
                clearArticleData();
                articles_front_anchorpane.getChildren().clear();
                addarticlepanes();
            } else {
                showAlertArticle(Alert.AlertType.ERROR, "Error Message", null, "Failed to update article data.");
            }
        } else {
            // If no article is selected, add a new article
            if (ArticleService.getInstance().add(article)) {
                showAlertArticle(Alert.AlertType.INFORMATION, "Message", null, "Article added successfully.");
                showArticleData();
                clearArticleData();
            } else {
                showAlertArticle(Alert.AlertType.ERROR, "Error Message", null, "Failed to add article data.");
            }
        }
        addarticlepanes();
    }
    private void initializeslider(){

        // Set the formatter for the indicator text

        comments_slider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double value) {
                // Customize the format of the indicator text
                if (value >= 0 && value < 50) {
                    // Return the first string when the value is between 0 and 33
                    return "Back";
                } else if (value >= 51 && value <= 100) {
                    // Return the second string when the value is between 68 and 100
                    return "Front";
                } else {
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


    public void clearArticleData() {
        article_contenu_input.clear();
        article_titre_input.clear();
        article_listview.getSelectionModel().clearSelection();
        selectedArticle=0;
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
    public void showArticleData() {

     List<Article> listart=   ArticleService.getInstance().getAll();
       ObservableList<Article> articleList = FXCollections.observableArrayList(listart);


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
                    FontAwesomeIconView titleIcon = new FontAwesomeIconView(FontAwesomeIcon.BOOK);
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

    /*public void selectArticleTableData(){
      int num=article_listview.getSelectionModel().getSelectedIndex();
        Article articleData=article_listview.getSelectionModel().getSelectedItem();
        if(num-1 < -1){
            return;
        }

        article_titre_input.setText(articleData.getTitre());
      //
        article_contenu_input.setPrefRowCount(10);
        article_contenu_input.setWrapText(true);
        article_contenu_input.setText(articleData.getContenu());

    }*/

    public void deleteSelectedArticles() {
        System.out.println("hello");
       int selectedArticles = selectedArticle;//article_listview.getSelectionModel().getSelectedItem();
        if (selectedArticles!=0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select articles for deletion.");
            alert.showAndWait();
            return;
        }
ArticleService.getInstance().delete(selectedArticles);
        showArticleData();
        articles_front_anchorpane.getChildren().clear();
        addarticlepanes();
        clearArticleData();

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


}
