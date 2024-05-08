package com.inventorymanagementsystem.services;

import com.inventorymanagementsystem.config.Database;
import com.inventorymanagementsystem.entity.Compte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompteService {

    private static CompteService instance;

    Connection connection;
    PreparedStatement preparedStatement;

    public CompteService() {
        connection = Database.getInstance().connectDB();
    }

    public static CompteService getInstance() {
        if (instance == null) {
            instance = new CompteService();
        }
        return instance;
    }


    public List<Compte> getAll() {
        List<Compte> listCompte = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `compte`");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listCompte.add(new Compte(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_user_id"),
                        resultSet.getInt("id_agence_id"),
                        resultSet.getString("type"),
                        resultSet.getDouble("solde"),
                        resultSet.getDate("date_creation"),

                        resultSet.getDate("date_fermeture"),
                        resultSet.getString("etat"),
                        resultSet.getString("rib")
                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) compte : " + exception.getMessage());
        }
        return listCompte;
    }

    public Compte get(int compteId) {
        Compte compte = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `compte` WHERE id = ?");
            preparedStatement.setInt(1, compteId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                compte = new Compte(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_user_id"),
                        resultSet.getInt("id_agence_id"),
                        resultSet.getString("type"),
                        resultSet.getDouble("solde"),
                        resultSet.getDate("date_creation"),
                        resultSet.getDate("date_fermeture"),
                        resultSet.getString("etat"),
                        resultSet.getString("rib")
                );
            }
        } catch (SQLException exception) {
            System.out.println("Error (get) compte : " + exception.getMessage());
        }
        return compte;
    }


    public boolean add(Compte compte) {
        String request = "INSERT INTO `compte`( `type`, `solde`, `date_creation`, `date_fermeture`, `etat`, `rib`) VALUES(?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            /*preparedStatement.setInt(1, compte.getIdUser());
            preparedStatement.setInt(2, compte.getIdAgence());*/
            preparedStatement.setString(1, compte.getType());
            preparedStatement.setDouble(2, compte.getSolde());
            preparedStatement.setDate(3, new java.sql.Date(compte.getDateCreation().getTime()));
            preparedStatement.setDate(4, new java.sql.Date(compte.getDateFermeture().getTime()));
            preparedStatement.setString(5, compte.getEtat());
            preparedStatement.setString(6, compte.getRib());

            preparedStatement.executeUpdate();
            System.out.println("Compte added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) compte : " + exception.getMessage());
        }
        return false;
    }


    public boolean edit(Compte compte) {
        String request = "UPDATE `compte` SET `id_user_id` = ?, `id_agence_id` =?, `type` = ?, `solde` = ?, `date_creation` = ?, `date_fermeture` = ?, `etat` = ?, `rib` = ? WHERE `id` = ?";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, compte.getIdUser());
            preparedStatement.setInt(2, compte.getIdAgence());
            preparedStatement.setString(3, compte.getType());
            preparedStatement.setDouble(4, compte.getSolde());
            preparedStatement.setDate(5, new java.sql.Date(compte.getDateCreation().getTime()));
            preparedStatement.setDate(6, new java.sql.Date(compte.getDateFermeture().getTime()));
            preparedStatement.setString(7, compte.getEtat());
            preparedStatement.setString(8, compte.getRib());
            preparedStatement.setInt(9, compte.getId());

            preparedStatement.executeUpdate();
            System.out.println("Compte edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) compte : " + exception.getMessage());
        }
        return false;
    }


    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `compte` WHERE `id` = ?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            System.out.println("Compte deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) compte : " + exception.getMessage());
        }
        return false;
    }

    public Compte getCompteById(int selectedCompteId) {
        Compte compte = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `compte` WHERE `id` = ?");
            preparedStatement.setInt(1, selectedCompteId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                compte = new Compte(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_user"),
                        resultSet.getInt("id_agence"),
                        resultSet.getString("type"),
                        resultSet.getDouble("solde"),
                        resultSet.getDate("date_creation"),
                        resultSet.getDate("date_fermeture"),
                        resultSet.getString("etat"),
                        resultSet.getString("rib")
                );
            }
        } catch (SQLException exception) {
            System.out.println("Error (getCompteById) compte : " + exception.getMessage());
        }
        return compte;
    }


    public List<Compte> getCompteByUserId(int UserId) {
        List<Compte> listCompte = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `compte` WHERE `id_user_id` = ?");
            preparedStatement.setInt(1, UserId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listCompte.add(new Compte(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_user_id"),
                        resultSet.getInt("id_agence_id"),
                        resultSet.getString("type"),
                        resultSet.getDouble("solde"),
                        resultSet.getDate("date_creation"),

                        resultSet.getDate("date_fermeture"),
                        resultSet.getString("etat"),
                        resultSet.getString("rib")
                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) compte : " + exception.getMessage());
        }
        return listCompte;
    }

}
