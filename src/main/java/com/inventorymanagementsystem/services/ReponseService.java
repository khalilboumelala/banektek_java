package com.inventorymanagementsystem.services;

import com.inventorymanagementsystem.config.Database;
import com.inventorymanagementsystem.entity.Reclamation;
import com.inventorymanagementsystem.entity.Reponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class ReponseService {

    private static ReponseService instance;

    Connection connection;
    PreparedStatement preparedStatement;

    public ReponseService() {
        connection = Database.getInstance().connectDB();
    }

    public static ReponseService getInstance() {
        if (instance == null) {
            instance = new ReponseService();
        }
        return instance;
    }


    public List<Reponse> getAll() {
        List<Reponse> listReponse = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `reponse`");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listReponse.add(new Reponse(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_reclamation_id"),
                        resultSet.getInt("id_agent_id"),
                        resultSet.getDate("date_reponse"),
                        resultSet.getString("message")


                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) reponse : " + exception.getMessage());
        }
        return listReponse;
    }

    public Reponse get(int reponseId) {
        Reponse reponse = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `reponse` WHERE id = ?");
            preparedStatement.setInt(1, reponseId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                reponse = new Reponse(

                        resultSet.getInt("id"),
                        resultSet.getInt("id_reclamation_id"),
                        resultSet.getInt("id_agent_id"),
                        resultSet.getDate("date_reponse"),
                        resultSet.getString("message")
                );
            }
        } catch (SQLException exception) {
            System.out.println("Error (get) reponse : " + exception.getMessage());
        }
        return reponse;
    }


    public boolean add(Reponse reponse) {
        String request = "INSERT INTO `reponse`(`date_reponse`,`message`) VALUES(?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setDate(1, new java.sql.Date(reponse.getDate_reponse().getTime()));
            preparedStatement.setString(2, reponse.getMessage());

            preparedStatement.executeUpdate();
            System.out.println("Reponse added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) reponse : " + exception.getMessage());
        }
        return false;
    }


    public boolean edit(Reponse reponse) {
        String request = "UPDATE `reponse` SET /*`id_reclamation_id` = ?, `id_agent_id` = ?,*/ `date_reponse` = ?, `message` = ? WHERE `id` = ?";
        try {
            preparedStatement = connection.prepareStatement(request);

            //preparedStatement.setInt(1, reponse.getId_reclamation_id());
            //preparedStatement.setInt(2, reponse.getId_agent_id());
            preparedStatement.setDate(1, new java.sql.Date(reponse.getDate_reponse().getTime()));
            preparedStatement.setString(2, reponse.getMessage());
            preparedStatement.setInt(3, reponse.getId());

            preparedStatement.executeUpdate();
            System.out.println("Reponse edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) reponse : " + exception.getMessage());
        }
        return false;
    }



    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `reponse` WHERE `id` = ?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            System.out.println("Reponse deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) reponse : " + exception.getMessage());
        }
        return false;
    }

    public Reponse getReponseById(int selectedReponseId) {
        Reponse reponse = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `reponse` WHERE `id` = ?");
            preparedStatement.setInt(1, selectedReponseId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                reponse = new Reponse(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_reclamation_id"),
                        resultSet.getInt("id_agent_id"),
                        resultSet.getDate("date_reponse"),
                        resultSet.getString("message")
                );
            }
        } catch (SQLException exception) {
            System.out.println("Error (getReponseById) reponse : " + exception.getMessage());
        }
        return reponse;
    }

    // Méthode pour récupérer les réponses avec les informations de réclamation correspondantes
    public List<Reponse> getAllReponsesWithReclamationInfo() {
        List<Reponse> reponses = new ArrayList<>();
        try {
            // Exécutez la requête SQL de jointure entre les tables reponse et reclamation
            String sql = "SELECT r.*, re.* FROM reponse r LEFT JOIN reclamation re ON r.id_reclamation_id = re.id";
            preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Créez un objet Reclamation avec les données de la réclamation
                Reclamation reclamation = new Reclamation(
                        resultSet.getInt("re.id"),
                        resultSet.getString("re.type"),
                        resultSet.getString("re.description"),
                        resultSet.getInt("re.id_client_id"),
                        resultSet.getDate("re.date_reclamation"),
                        resultSet.getString("re.etat"),
                        resultSet.getString("re.document"),
                        resultSet.getString("re.email")
                );
                // Créez un objet Reponse avec les données de la réponse
                Reponse reponse = new Reponse(
                        resultSet.getInt("r.id"),
                        resultSet.getInt("r.id_reclamation_id"),
                        resultSet.getInt("r.id_agent_id"),
                        resultSet.getDate("r.date_reponse"),
                        resultSet.getString("r.message")
                );
                // Associez la réclamation à la réponse
                reponse.setReclamation(reclamation);
                // Ajoutez la réponse à la liste
                reponses.add(reponse);
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAllReponsesWithReclamationInfo) reponse : " + exception.getMessage());
        }
        return reponses;
    }


}
