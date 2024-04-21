package com.inventorymanagementsystem.services;

import com.inventorymanagementsystem.config.Database;
import com.inventorymanagementsystem.entity.Agence;
//import com.inventorymanagementsystem.utils.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AgenceService {

    private static AgenceService instance;

    Connection connection;
    PreparedStatement preparedStatement;

    public AgenceService() {
        connection = Database.getInstance().getConnection();
    }

    public static AgenceService getInstance() {
        if (instance == null) {
            instance = new AgenceService();
        }
        return instance;
    }

    public List<Agence> getAll() {
        List<Agence> listAgence = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `agence`");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listAgence.add(new Agence(
                        resultSet.getInt("id"),
                        resultSet.getString("adresse"),
                        resultSet.getString("nom"),
                        resultSet.getLong("num_tel"),
                        resultSet.getString("etat"),
                        resultSet.getInt("id_chef"),
                        resultSet.getDate("date_ajout"),
                        resultSet.getString("latitude"),
                        resultSet.getString("longitude")
                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) agence : " + exception.getMessage());
        }
        return listAgence;
    }

    public boolean add(Agence agence) {
        String request = "INSERT INTO `agence`(`adresse`, `nom`, `num_tel`, `etat`, `id_chef`, `date_ajout`, `latitude`, `longitude`) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setString(1, agence.getAdresse());
            preparedStatement.setString(2, agence.getNom());
            preparedStatement.setLong(3, agence.getNumTel());
            preparedStatement.setString(4, agence.getEtat());
            preparedStatement.setInt(5, agence.getIdChef());
            preparedStatement.setDate(6, new java.sql.Date(agence.getDateAjout().getTime()));
            preparedStatement.setString(7, agence.getLatitude());
            preparedStatement.setString(8, agence.getLongitude());

            preparedStatement.executeUpdate();
            System.out.println("Agence added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) agence : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(Agence agence) {
        String request = "UPDATE `agence` SET `adresse` = ?, `nom` = ?, `num_tel` = ?, `etat` = ?, `id_chef` = ?, `date_ajout` = ?, `latitude` = ?, `longitude` = ? WHERE `id` = ?";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setString(1, agence.getAdresse());
            preparedStatement.setString(2, agence.getNom());
            preparedStatement.setLong(3, agence.getNumTel());
            preparedStatement.setString(4, agence.getEtat());
            preparedStatement.setInt(5, agence.getIdChef());
            preparedStatement.setDate(6, new java.sql.Date(agence.getDateAjout().getTime()));
            preparedStatement.setString(7, agence.getLatitude());
            preparedStatement.setString(8, agence.getLongitude());
            preparedStatement.setInt(9, agence.getId());

            preparedStatement.executeUpdate();
            System.out.println("Agence edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) agence : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `agence` WHERE `id` = ?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            System.out.println("Agence deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) agence : " + exception.getMessage());
        }
        return false;
    }
}
