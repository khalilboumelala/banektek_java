package com.inventorymanagementsystem.services;

import com.inventorymanagementsystem.config.Database;
import com.inventorymanagementsystem.entity.Client;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientService {

    private static ClientService instance;

    static Connection connection;
    static PreparedStatement preparedStatement;

    public ClientService() {
        connection = Database.getInstance().connectDB();
    }

    public static ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }
        return instance;
    }

    @FXML
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `client`");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                clients.add(new Client(
                        resultSet.getInt("id"),
                        resultSet.getDate("dob").toLocalDate(),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getLong("cin"),
                        resultSet.getLong("num_tel"),
                        resultSet.getString("genre"),
                        resultSet.getString("pays"),
                        resultSet.getString("adresse"),
                        resultSet.getString("email"),
                        resultSet.getString("document"),
                        resultSet.getString("signature"),
                        resultSet.getString("profession"),
                        resultSet.getDate("date_creation").toLocalDate(),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getDate("last_login").toLocalDate(),
                        resultSet.getString("etat"),
                        resultSet.getString("photo")
                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) client : " + exception.getMessage());
        }
        return clients;
    }

    @FXML
    public Client get(int clientId) {
        Client client = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `client` WHERE id = ?");
            preparedStatement.setInt(1, clientId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                client = new Client(
                        resultSet.getInt("id"),
                        resultSet.getDate("dob").toLocalDate(),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getLong("cin"),
                        resultSet.getLong("num_tel"),
                        resultSet.getString("genre"),
                        resultSet.getString("pays"),
                        resultSet.getString("adresse"),
                        resultSet.getString("email"),
                        resultSet.getString("document"),
                        resultSet.getString("signature"),
                        resultSet.getString("profession"),
                        resultSet.getDate("date_creation").toLocalDate(),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getDate("last_login").toLocalDate(),
                        resultSet.getString("etat"),
                        resultSet.getString("photo")
                );
            }
        } catch (SQLException exception) {
            System.out.println("Error (get) client : " + exception.getMessage());
        }
        return client;
    }

    @FXML
    public static boolean add(Client client) {
        String request = "INSERT INTO `client`(`dob`, `nom`, `prenom`, `cin`, `num_tel`, `genre`, `pays`, `adresse`, `email`, `document`, `signature`, `profession`, `date_creation`, `username`, `password`, `last_login`, `etat`, `photo`) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setDate(1, java.sql.Date.valueOf(client.getDob()));
            preparedStatement.setString(2, client.getNom());
            preparedStatement.setString(3, client.getPrenom());
            preparedStatement.setLong(4, client.getCin());
            preparedStatement.setLong(5, client.getNumTel());
            preparedStatement.setString(6, client.getGenre());
            preparedStatement.setString(7, client.getPays());
            preparedStatement.setString(8, client.getAdresse());
            preparedStatement.setString(9, client.getEmail());
            preparedStatement.setString(10,"document");
            preparedStatement.setString(11,"signature");
            preparedStatement.setString(12, client.getProfession());
            preparedStatement.setDate(13, java.sql.Date.valueOf(client.getDateCreation()));
            preparedStatement.setString(14,client.getUsername());
            preparedStatement.setString(15,"password");
            preparedStatement.setDate(16, java.sql.Date.valueOf(client.getLastLogin()));
            preparedStatement.setString(17, "activer");
            preparedStatement.setString(18, client.getPhoto());

            preparedStatement.executeUpdate();
            System.out.println("Client added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) client : " + exception.getMessage());
        }
        return false;
    }

    @FXML
    public boolean edit(Client client) {
        String request = "UPDATE `client` SET `dob`=?, `nom`=?, `prenom`=?, `cin`=?, `num_tel`=?, `genre`=?, `pays`=?, `adresse`=?, `email`=?, `document`=?, `signature`=?, `profession`=?, `date_creation`=?, `username`=?, `password`=?, `last_login`=?, `etat`=?, `photo`=? WHERE `id`=?";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setDate(1, java.sql.Date.valueOf(client.getDob()));
            preparedStatement.setString(2, client.getNom());
            preparedStatement.setString(3, client.getPrenom());
            preparedStatement.setLong(4, client.getCin());
            preparedStatement.setLong(5, client.getNumTel());
            preparedStatement.setString(6, client.getGenre());
            preparedStatement.setString(7, client.getPays());
            preparedStatement.setString(8, client.getAdresse());
            preparedStatement.setString(9, client.getEmail());
            preparedStatement.setString(10,"document");
            preparedStatement.setString(11, "signature");
            preparedStatement.setString(12, client.getProfession());
            preparedStatement.setDate(13, java.sql.Date.valueOf(client.getDateCreation()));
            preparedStatement.setString(14, client.getUsername());
            preparedStatement.setString(15, client.getPassword());
            preparedStatement.setDate(16, java.sql.Date.valueOf(client.getLastLogin()));
            preparedStatement.setString(17, client.getEtat());
            preparedStatement.setString(18, client.getPhoto());
            preparedStatement.setInt(19, client.getId());

            preparedStatement.executeUpdate();
            System.out.println("Client edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) client : " + exception.getMessage());
        }
        return false;
    }

    @FXML
    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `client` WHERE `id` = ?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            System.out.println("Client deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) client : " + exception.getMessage());
        }
        return false;
    }
}
