package com.inventorymanagementsystem;


import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import com.inventorymanagementsystem.services.PasswordHasher;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;



//

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
//


//
//import org.cef.browser.CefBrowser;
import org.panda_lang.pandomium.Pandomium;
import org.panda_lang.pandomium.wrapper.PandomiumClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


import static org.burningwave.core.assembler.StaticComponentContainer.Modules;

public class Application extends javafx.application.Application {

    private double x;
    private double y;
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {

       Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        //
        Scene scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource("/com/inventorymanagementsystem/css/fullpackstyling.css").toExternalForm());


        stage.setTitle("Banektek");
        root.setOnMousePressed((event)->{
            x=event.getSceneX();
            y=event.getSceneY();
        });
        root.setOnMouseDragged((event)->{
            stage.setX(event.getScreenX()-x);
            stage.setY(event.getScreenY()-y);
        });
     //       stage.setHeight(670);
        stage.setWidth(1200);
       // stage.setFullScreen(true);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();




    }


    public static Stage getPrimaryStage() {
        return primaryStage;
    }


    public static void main(String[] args) {
        launch();
    }
}