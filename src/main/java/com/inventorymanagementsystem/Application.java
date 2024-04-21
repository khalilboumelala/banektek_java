package com.inventorymanagementsystem;


import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
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
import org.cef.browser.CefBrowser;
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
/*
    public void start(Stage stage) throws MalformedURLException {
        // Create web engine and view
         WebView webView = new WebView();
         WebEngine webEngine = webView.getEngine();
            String script= getClass().getResource("map.js").toString();
        //webEngine.executeScript(script);
        // Load the HTML file
        URL htmlFileUrl = new URL("http://localhost/map.html");//getClass().getResource("map.html");
        if (htmlFileUrl != null) {
            webEngine.load(htmlFileUrl.toExternalForm());
            webEngine.executeScript(script);
        } else {
            System.err.println("Failed to load map.html");
            // Handle the case where the HTML file is not found
            // You can show an error message or take appropriate action here
        }

        // Create scene
        stage.setTitle("Web Map");
        Scene scene = new Scene(webView, 500, 500);
        stage.setScene(scene);

        // Show stage
        stage.show();
    }

*/

    @Override
    public void start(Stage stage) throws IOException {
       // Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Banektek");
        root.setOnMousePressed((event)->{
            x=event.getSceneX();
            y=event.getSceneY();
        });
        root.setOnMouseDragged((event)->{
            stage.setX(event.getScreenX()-x);
            stage.setY(event.getScreenY()-y);
        });
     /*       stage.setHeight(670);
        stage.setWidth(1200);*/
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();




    }



    public static void main(String[] args) {
        launch();
    }
}