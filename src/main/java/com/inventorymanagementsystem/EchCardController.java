package com.inventorymanagementsystem;

import com.inventorymanagementsystem.config.Database;
import com.inventorymanagementsystem.entity.Echeance;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EchCardController {
    @FXML
    private Pane Card;
    @FXML
    private Label datelb;
    @FXML
    private Label etatlb;
    @FXML
    private Label idlb;
    private int id;

    public void setData(Echeance ech) {
        datelb.setText(String.valueOf(ech.getDate()));
        etatlb.setText(ech.getEtat());
        idlb.setText(String.valueOf(ech.getId()));
        id = ech.getId();
    }

    public void Payer() {
        Connection cnx = Database.getInstance().connectDB();
        try {
            String sql1 = "UPDATE `echeance` SET `etat`=? WHERE `id`=?";
            PreparedStatement stm1 = cnx.prepareStatement(sql1);
            stm1.setString(1, "paye");
            stm1.setInt(2, id);
            stm1.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Echeance payee.");
            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
