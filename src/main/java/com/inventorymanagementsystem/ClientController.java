package com.inventorymanagementsystem;

import com.inventorymanagementsystem.config.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ClientController  implements Initializable {
    private Connection connection;
    private Statement statement;

    private PreparedStatement preparedStatement;

    private ResultSet resultSet;
    private TextField nom_client;
    private TextField prenom_client;
    private TextField mail_client;
    private TextField cin_client;
    private TextField genre_client;
    private TextField pays_client;
    private TextField piece_client;
    private TextField photo_client;

    private TextField tel_client;
    private TextField poste_client;
    private TextField dob_client;
    private TextField adresse_client;
    private TextField signature_client;
    private TextField pass_client;


    public void addClient(){
        if(nom_client.getText().isBlank()){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir les données obligatoires.");
            alert.showAndWait();
            return;
        }
        connection= Database.getInstance().connectDB();
        String sql = "INSERT INTO client (dob, nom, prenom, cin, num_tel, genre, pays, adresse, email, document, signature, profession, date_creation, username, password, last_login, etat, photo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // Préparez la déclaration
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Remplacez les ? avec les valeurs correspondantes
            preparedStatement.setDate(1, null);  // Exemple avec un datePicker pour la date de naissance
            preparedStatement.setString(2, "hamza");
            preparedStatement.setString(3, "naa");
            preparedStatement.setLong(4, 1111);
            preparedStatement.setLong(5, 1232222);
            preparedStatement.setString(6, "aaa");
            preparedStatement.setString(7,"aaa");
            preparedStatement.setString(8, "aaa");
            preparedStatement.setString(9, "aaa");
            preparedStatement.setString(10, "aaa");
            preparedStatement.setString(11, "aaa");
            preparedStatement.setString(12, "aaa");
            preparedStatement.setDate(13, null);  // Exemple pour la date de création
            preparedStatement.setString(14,"11/22/2023");
            preparedStatement.setString(15,"password");
            preparedStatement.setDate(16, null);  // Exemple pour la dernière connexion
            preparedStatement.setString(17, "sss");
            preparedStatement.setString(18, "ss");

            // Exécutez la requête
            preparedStatement.executeUpdate();

            // Fermez la connexion et la déclaration
            preparedStatement.close();
            connection.close();

            int result=preparedStatement.executeUpdate();
            if(result>0){
                //showBillingData();
               // billClearData();
            }else{
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill the mandatory data such as item number, quantity and price .");
                alert.showAndWait();
            }
        }catch (Exception err){
            err.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
