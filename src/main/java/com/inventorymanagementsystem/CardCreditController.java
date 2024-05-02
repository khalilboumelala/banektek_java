package com.inventorymanagementsystem;

import com.inventorymanagementsystem.config.Database;
import com.inventorymanagementsystem.entity.Credit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
//import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;

public class CardCreditController implements Initializable {
    @FXML
    private Pane Card;
    @FXML
    private ComboBox<String> etatcombo;
    @FXML
    private TextField montanttf;
    @FXML
    private TextField nom;
    @FXML
    private ProgressBar percbar;
    @FXML
    private TextField nomprenomtf;
    @FXML
    private Label perctf;
    @FXML
    private TextField ribtf;

    private int idcredit, id, dure, date_echeance, nb_echeances_restants;
    private float montant, taux, apport_propre, revenu_propre, montant_echeance;
    private String type, etat, rib;
    private Date date_debut;
    int IDCOMPTE;
    String NomClient, PrenomClient;

    ObservableList<String> Etats = FXCollections.observableArrayList("Suspendu", "Terminer", "En cours");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        etatcombo.setItems(Etats);
    }

    public void setData(Credit credit) {
        Connection cnx = Database.getInstance().connectDB();
        try {
            String sql1 = "SELECT `id_user_id`, `rib` FROM `compte` WHERE `id` LIKE '%" + credit.getId() + "%'";
            Statement stm1 = cnx.createStatement();
            ResultSet rs1 = stm1.executeQuery(sql1);
            while (rs1.next()) {
                rib = rs1.getString("rib");
                int iduser = rs1.getInt("id_user_id");
                ribtf.setText(rib);
                String sql2 = "SELECT * FROM `client` WHERE `id` LIKE '%" + iduser + "%'";
                Statement stm2 = cnx.createStatement();
                ResultSet rs2 = stm2.executeQuery(sql2);
                while (rs2.next()) {
                    nomprenomtf.setText(rs2.getString("nom") + " " + rs2.getString("prenom"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (credit.getEtat().equals("en cours")) {
            etatcombo.setValue("En cours");
            etatcombo.setStyle("-fx-border-color: #ff9100;");
        }
        if (credit.getEtat().equals("suspendu")) {
            etatcombo.setValue("Suspendu");
            etatcombo.setStyle("-fx-border-color: #ff0000;");
        }
        else {
            etatcombo.setValue("Terminer");
            etatcombo.setStyle("-fx-border-color: #00ff00;");
        }

        idcredit = credit.getIdcredit();
        id = credit.getId();
        dure = credit.getDure();
        date_echeance = credit.getDate_echeance();
        nb_echeances_restants = credit.getNb_echeances_restants();
        montant = credit.getMontant();
        taux = credit.getTaux();
        apport_propre = credit.getApport_propre();
        revenu_propre = credit.getRevenu_propre();
        montant_echeance = credit.getMontant_echeance();
        type = credit.getType();
        etat = credit.getEtat();
        date_debut = credit.getDate_debut();
        montanttf.setText(String.valueOf(montant_echeance));
        updateBar(credit.getIdcredit());
    }
    void updateBar(int CredId){
        Connection cnx = Database.getInstance().connectDB();
        try {
            String sql3 = "SELECT COUNT(*) AS total, SUM(CASE WHEN etat = 'paye' THEN 1 ELSE 0 END) AS payees FROM echeance WHERE id_credit_id = ?";
            PreparedStatement stm3 = cnx.prepareStatement(sql3);
            stm3.setInt(1, CredId);
            ResultSet rs3 = stm3.executeQuery();
            if (rs3.next()) {
                int totalEcheances = rs3.getInt("total");
                int payees = rs3.getInt("payees");
                if (totalEcheances > 0) {
                    double percentagePaid = (double) payees / totalEcheances;
                    percbar.setProgress(percentagePaid);
                    perctf.setText(percentagePaid*100 +"%");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void SupprimerCred() {
        Connection cnx = Database.getInstance().connectDB();
        try {
            String qry0 = "DELETE FROM `echeance` WHERE id_credit_id=?";
            PreparedStatement smt0 = cnx.prepareStatement(qry0);
            smt0.setInt(1, idcredit);
            smt0.executeUpdate();
            String qry = "DELETE FROM `credit` WHERE id=?";
            PreparedStatement smt = cnx.prepareStatement(qry);
            smt.setInt(1, idcredit);
            smt.executeUpdate();
            System.out.println("Suppression Effectue");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void ModifierCred() {
        Connection cnx = Database.getInstance().connectDB();
        try {
            String sql0 = "SELECT `id_user_id` FROM `compte` WHERE `rib` LIKE '%" + rib + "%'";
            Statement stm0 = cnx.createStatement();
            ResultSet rs0 = stm0.executeQuery(sql0);
            while (rs0.next()) {
                String sql1 = "SELECT `nom`, `prenom` FROM `client` WHERE `id` LIKE '%" + rs0.getString("id_user_id") + "%'";
                Statement stm1 = cnx.createStatement();
                ResultSet rs1 = stm1.executeQuery(sql1);
                IDCOMPTE = rs0.getInt("id_user_id");
                while (rs1.next()) {
                    nomprenomtf.setText(rs1.getString("nom") + " " + rs1.getString("prenom"));
                    NomClient = rs1.getString("nom");
                    PrenomClient = rs1.getString("prenom");
                    String sql2 = "UPDATE `credit` SET `id_compte_id`=?,`montant`=?,`etat`=? WHERE `id`=?";
                    PreparedStatement stm2 = cnx.prepareStatement(sql2);
                    stm2.setInt(1, id);
                    stm2.setFloat(2, montant);
                    if (etatcombo.getValue().equals("En cours")) {
                        stm2.setString(3, "en cours");
                    }
                    if (etatcombo.getValue().equals("Suspendu")) {
                        stm2.setString(3, "suspendu");
                    }
                    if (etatcombo.getValue().equals("Terminer")) {
                        stm2.setString(3, "terminer");
                    }
                    stm2.setInt(4, idcredit);
                    stm2.executeUpdate();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
















       /*
       try {
            Connection cnx = Database.getInstance().connectDB();
            String qry = "UPDATE `credit` SET `id_compte_id`=?,`montant`=?,`duree`=?,`etat`=?, WHERE `id`=?";
            PreparedStatement stm = cnx.prepareStatement(qry);
            try {
                String sql1 = "SELECT `id_user_id`,  FROM `compte` WHERE `rib` LIKE '%" + credit.getId() + "%'";
                Statement stm1 = cnx.createStatement();
                ResultSet rs1 = stm1.executeQuery(sql1);
                while (rs1.next()) {

                }

            stm.setString(1, evenement.getTitre());

            stm.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

       try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            DashboardController DC = loader.getController();
            if (type.equals("commercial")) {
                DC.type_credit.setValue("Crédit commercial");
                DC.taux_credit.setText("0.09");
            } else if (type.equals("agricole")) {
                DC.type_credit.setValue("Crédit agricole");
                DC.taux_credit.setText("0.07");
            } else if (type.equals("automobile")) {
                DC.type_credit.setValue("Crédit automobile");
                DC.taux_credit.setText("0.1");
            } else if (type.equals("consommation")) {
                DC.type_credit.setValue("Crédit à la consommation");
                DC.taux_credit.setText("0.12");
            } else if (type.equals("hypothecaire")) {
                DC.type_credit.setValue("Crédit hypothécaire");
                DC.taux_credit.setText("0.05");
            } else if (type.equals("etudiant")) {
                DC.type_credit.setValue("Crédit étudiant");
                DC.taux_credit.setText("0.11");
            }
            DC.apport_credit.setText(String.valueOf(apport_propre));
            DC.mecheance_credit.setText(String.valueOf(montant_echeance));
            DC.montant_credit.setText(String.valueOf(montant));
            //DC.date_credit.setValue(date_debut);
            DC.revenu_credit.setText(String.valueOf(revenu_propre));
            if(dure==12){
            DC.duree_credit.setValue("12 mois (1 année)");
            }if(dure==24){
                DC.duree_credit.setValue("24 mois (2 années)");
            }if(dure==36){
                DC.duree_credit.setValue("36 mois (3 année)");
            }if(dure==48){
                DC.duree_credit.setValue("48 mois (4 année)");
            }if(dure==60){
                DC.duree_credit.setValue("60 mois (5 année)");
            }if(dure==84){
                DC.duree_credit.setValue("84 mois (7 année)");
            }
            DC.ribtf.setText(rib);
            DC.dateEch.setValue(date_echeance);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
