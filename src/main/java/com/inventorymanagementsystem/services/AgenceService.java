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
                        resultSet.getInt("id_chef_id"),
                        resultSet.getDate("data_ajout"),
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
    public Agence getAgenceById(int id) {
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `agence` WHERE `id` = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Récupérer les données de l'agence depuis le ResultSet
                int agenceId = resultSet.getInt("id");
                String adresse = resultSet.getString("adresse");
                String nom = resultSet.getString("nom");
                long numTel = resultSet.getLong("num_tel");
                String etat = resultSet.getString("etat");
                int idChef = resultSet.getInt("id_chef_id");
                java.util.Date dateAjout = resultSet.getDate("data_ajout");
                String latitude = resultSet.getString("latitude");
                String longitude = resultSet.getString("longitude");
                // Instancier et retourner l'objet Agence avec les données récupérées
                return new Agence(agenceId, adresse, nom, numTel, etat, idChef, dateAjout, latitude, longitude);
            } else {
                // Aucune agence trouvée avec cet identifiant, retourner null
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de l'agence par ID : " + e.getMessage(), e);
        } finally {
            // Assurez-vous de fermer les ressources (preparedStatement, resultSet) dans le bloc finally
            // Ceci est crucial pour éviter les fuites de ressources et les problèmes de performance
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                // Gérer l'exception de fermeture de la preparedStatement si nécessaire
                System.out.println("Erreur lors de la fermeture de la preparedStatement : " + e.getMessage());
            }
        }
    }

}
