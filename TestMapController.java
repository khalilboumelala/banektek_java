/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventorymanagementsystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

/**
 * FXML Controller class
 *
 * @author Yassine
 */
public class TestMapController implements Initializable {

    public static double lon;
        public static double lat;

    @FXML
    private WebView webview;
    private WebEngine webengine;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        webengine = webview.getEngine();

        url = this.getClass().getResource("map/index.html");
        webengine.load(url.toString());

       
      
}

    @FXML
    private void tt() {
        System.out.println("test0");
        // Retrieve the latitude and longitude values from JavaScript
        Object result = webview.getEngine().executeScript("lat");
        System.out.println("test1");

        if (result instanceof Number) {
            double lat = ((Number) result).doubleValue();
            System.out.println("Lat: " + lat);
        } else {
            System.err.println("Error: Latitude is not a number");
        }

        result = webview.getEngine().executeScript("lon");

        if (result instanceof Number) {
            double lon = ((Number) result).doubleValue();
            System.out.println("Lon: " + lon);
        } else {
            System.err.println("Error: Longitude is not a number");
        }
    }

// JavaScript interface object
private class JavaApp {
  public void exit() {
    Platform.exit();
  }

    }
}
