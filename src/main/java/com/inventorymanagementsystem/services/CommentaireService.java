package com.inventorymanagementsystem.services;

import com.inventorymanagementsystem.config.Database;
import com.inventorymanagementsystem.entity.Commentaire;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentaireService {

    private static CommentaireService instance;

    Connection connection;
    PreparedStatement preparedStatement;

    public CommentaireService() {
        connection = Database.getInstance().connectDB();
    }

    public static CommentaireService getInstance() {
        if (instance == null) {
            instance = new CommentaireService();
        }
        return instance;
    }

    public List<Commentaire> getAll() {
        List<Commentaire> commentaireList = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `commentaire`");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                commentaireList.add(new Commentaire(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("article_id"),
                        resultSet.getString("contenu"),
                        resultSet.getInt("note"),
                        resultSet.getTimestamp("date")
                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) commentaire: " + exception.getMessage());
        }
        return commentaireList;
    }

    public Commentaire get(int commentaireId) {
        Commentaire commentaire = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `commentaire` WHERE `id` = ?");
            preparedStatement.setInt(1, commentaireId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                commentaire = new Commentaire(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("article_id"),
                        resultSet.getString("contenu"),
                        resultSet.getInt("note"),
                        resultSet.getTimestamp("date")
                );
            }
        } catch (SQLException exception) {
            System.out.println("Error (get) commentaire: " + exception.getMessage());
        }
        return commentaire;
    }

    public boolean add(Commentaire commentaire) {
        String request = "INSERT INTO `commentaire`(`user_id`, `article_id`, `contenu`, `note`, `date`) VALUES (?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, 6);
            preparedStatement.setInt(2, commentaire.getArticleId());
            preparedStatement.setString(3, commentaire.getContenu());
            preparedStatement.setInt(4, commentaire.getNote());
            preparedStatement.setTimestamp(5, new java.sql.Timestamp(commentaire.getDate().getTime()));

            preparedStatement.executeUpdate();
            System.out.println("Commentaire added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) commentaire: " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(Commentaire commentaire) {
        String request = "UPDATE `commentaire` SET `user_id`=?, `article_id`=?, `contenu`=?, `note`=?, `date`=? WHERE `id`=?";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, 6);
            preparedStatement.setInt(2, commentaire.getArticleId());
            preparedStatement.setString(3, commentaire.getContenu());
            preparedStatement.setInt(4, commentaire.getNote());
            preparedStatement.setTimestamp(5, new java.sql.Timestamp(commentaire.getDate().getTime()));
            preparedStatement.setInt(6, commentaire.getId());

            preparedStatement.executeUpdate();
            System.out.println("Commentaire edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) commentaire: " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `commentaire` WHERE `id` = ?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            System.out.println("Commentaire deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) commentaire: " + exception.getMessage());
        }
        return false;
    }

}
