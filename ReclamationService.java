package com.inventorymanagementsystem.services;

import com.inventorymanagementsystem.config.Database;
import com.inventorymanagementsystem.entity.Reclamation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReclamationService {


        //private static com.inventorymanagementsystem.services.ReclamationService instance;
        private static ReclamationService instance;

        Connection connection;
        PreparedStatement preparedStatement;

        public ReclamationService() {
            connection = Database.getInstance().connectDB();
        }

        //public static com.inventorymanagementsystem.services.ReclamationService getInstance() {
        public static ReclamationService getInstance() {
            if (instance == null) {
                instance = new ReclamationService();
            }
            return instance;
        }


        public List<Reclamation> getAll() {
            List<Reclamation> listReclamation = new ArrayList<>();
            try {
                preparedStatement = connection.prepareStatement("SELECT * FROM `reclamation`");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    listReclamation.add(new Reclamation(
                            resultSet.getInt("id"),
                            resultSet.getString("type"),
                            resultSet.getString("description"),
                            resultSet.getInt("id_client_id"),
                            resultSet.getDate("date_reclamation"),
                            resultSet.getString("etat"),
                            resultSet.getString("document"),
                            resultSet.getString("email")
                    ));
                }
            } catch (SQLException exception) {
                System.out.println("Error (getAll) reclamation : " + exception.getMessage());
            }
            return listReclamation;
        }

        public Reclamation get(int reclamationId) {
            Reclamation reclamation = null;
            try {
                preparedStatement = connection.prepareStatement("SELECT * FROM `reclamation` WHERE id = ?");
                preparedStatement.setInt(1, reclamationId);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    reclamation = new Reclamation(

                            resultSet.getInt("id"),
                            resultSet.getString("type"),
                            resultSet.getString("description"),
                            resultSet.getInt("id_client_id"),
                            resultSet.getDate("date_reclamation"),
                            resultSet.getString("etat"),
                            resultSet.getString("document"),
                            resultSet.getString("email")
                    );
                }
            } catch (SQLException exception) {
                System.out.println("Error (get) reclamation : " + exception.getMessage());
            }
            return reclamation;
        }


        public boolean add(Reclamation reclamation) {
            String request = "INSERT INTO `reclamation` (/*`id_client_id`,*/ `date_reclamation`, `type`, `description`, `etat`, `document`, `email`) VALUES (?, ?, ?, ?, ?, ?/*, ?*/)";
            try {
                preparedStatement = connection.prepareStatement(request);
                //preparedStatement.setInt(1, reclamation.getId_client_id());
                preparedStatement.setDate(1, new java.sql.Date(reclamation.getDate_reclamation().getTime()));
                preparedStatement.setString(2, reclamation.getType());
                preparedStatement.setString(3, reclamation.getDescription());
                preparedStatement.setString(4, reclamation.getEtat());
                preparedStatement.setString(5, reclamation.getDocument());
                preparedStatement.setString(6, reclamation.getEmail());
                preparedStatement.executeUpdate();
                System.out.println("Reclamation added");
                return true;
            } catch (SQLException exception) {
                System.out.println("Error (add) reclamation : " + exception.getMessage());
            }
            return false;
        }


        public boolean edit(Reclamation reclamation) {
            String request = "UPDATE `reclamation` SET `id_client_id` = ?, `date_reclamation` = ?, `type` = ?, `description` = ?, `etat` = ?, `document` = ?, `email` = ? WHERE `id` = ?";
            try {
                preparedStatement = connection.prepareStatement(request);
                preparedStatement.setInt(1, reclamation.getId_client_id());
                preparedStatement.setDate(2, new java.sql.Date(reclamation.getDate_reclamation().getTime()));
                preparedStatement.setString(3, reclamation.getType());
                preparedStatement.setString(4, reclamation.getDescription());
                preparedStatement.setString(5, reclamation.getEtat());
                preparedStatement.setString(6, reclamation.getDocument());
                preparedStatement.setString(7, reclamation.getEmail());
                preparedStatement.setInt(8, reclamation.getId());
                preparedStatement.executeUpdate();
                System.out.println("Reclamation edited");
                return true;
            }catch (SQLException exception) {
                System.out.println("Error (edit) reclamation : " + exception.getMessage());
            }
            return false;
        }


        public boolean delete(int id) {
            try {
                preparedStatement = connection.prepareStatement("DELETE FROM `reclamation` WHERE `id` = ?");
                preparedStatement.setInt(1, id);

                preparedStatement.executeUpdate();
                System.out.println("Reclamation deleted");
                return true;
            } catch (SQLException exception) {
                System.out.println("Error (delete) reclamation : " + exception.getMessage());
            }
            return false;
        }

        public Reclamation getReclamationById(int selectedReclamationId) {
            Reclamation reclamation = null;
            try {
                preparedStatement = connection.prepareStatement("SELECT * FROM `reclamation` WHERE `id` = ?");
                preparedStatement.setInt(1, selectedReclamationId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    reclamation = new Reclamation(
                            resultSet.getInt("id"),
                            resultSet.getString("type"),
                            resultSet.getString("description"),
                            resultSet.getInt("id_client_id"),
                            resultSet.getDate("date_reclamation"),
                            resultSet.getString("etat"),
                            resultSet.getString("document"),
                            resultSet.getString("email")
                    );
                }
            }  catch (SQLException exception) {
                System.out.println("Error (getReclamationById) reclamation : " + exception.getMessage());
            }
            return reclamation;
        }


}
