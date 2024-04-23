package com.inventorymanagementsystem.services;

import com.inventorymanagementsystem.config.Database;
import com.inventorymanagementsystem.entity.Compte;
import com.inventorymanagementsystem.entity.Virement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VirementService {

    private static VirementService instance;

    Connection connection;
    PreparedStatement preparedStatement;

    public VirementService() {
        connection = Database.getInstance().connectDB();
    }

    public static VirementService getInstance() {
        if (instance == null) {
            instance = new VirementService();
        }
        return instance;
    }


    public List<Virement> getAll() {
        List<Virement> listVirement = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `virement`");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println("data: "+ resultSet.getString("cin_emetteur")+ resultSet.getString("photo_cin"));
                listVirement.add(new Virement(
                        resultSet.getInt("id"),
                        new Compte(
                                resultSet.getInt("id_compte_emetteur_id"),
                                null, // You may need to fetch the Compte object separately
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null
                        ),
                        new Compte(
                                resultSet.getInt("id_compte_beneficiaire_id"),
                                null, // You may need to fetch the Compte object separately
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null
                        ),
                        resultSet.getString("cin_emetteur"),
                        resultSet.getString("photo_cin"),
                        resultSet.getDouble("montant"),
                        resultSet.getDate("date_emission"),
                        resultSet.getDate("date_approbation"),
                        resultSet.getString("etat"),
                        null // You may need to fetch the TypeVirement object separately
                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) virement : " + exception.getMessage());
        }
        return listVirement;
    }


    public boolean add(Virement virement) {
        String request = "INSERT INTO `virement`(`id_compte_emetteur_id`, `id_compte_beneficiaire_id`, `cin_emetteur`, `photo_cin`, `montant`, `date_emission`, `date_approbation`, `etat`) VALUES(21, 18, ?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);
            System.out.println();
      /*      preparedStatement.setInt(1, virement.getIdCompteEmetteur().getId());
            preparedStatement.setInt(2, virement.getIdCompteBeneficiaire().getId());*/
            preparedStatement.setString(1, virement.getCinEmetteur());
            preparedStatement.setString(2, virement.getPhotoCin());
            preparedStatement.setDouble(3, virement.getMontant());
            preparedStatement.setDate(4, new java.sql.Date(virement.getDateEmission().getTime()));
            preparedStatement.setDate(5, new java.sql.Date(virement.getDateApprobation().getTime()));
            preparedStatement.setString(6, virement.getEtat());

            preparedStatement.executeUpdate();
            System.out.println("Virement added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) virement : " + exception.getMessage());
        }
        return false;
    }


    public boolean edit(Virement virement) {
        String request = "UPDATE `virement` SET `cin_emetteur` = ?, `photo_cin` = ?, `montant` = ?, `date_emission` = ?, `date_approbation` = ?, `etat` = ? WHERE `id` = ?";
        try {
            preparedStatement = connection.prepareStatement(request);

            /*preparedStatement.setInt(1, virement.getIdCompteEmetteur().getId());
            preparedStatement.setInt(2, virement.getIdCompteBeneficiaire().getId());*/
            preparedStatement.setString(1, virement.getCinEmetteur());
            preparedStatement.setString(2, virement.getPhotoCin());
            preparedStatement.setDouble(3, virement.getMontant());
            preparedStatement.setDate(4, new java.sql.Date(virement.getDateEmission().getTime()));
            preparedStatement.setDate(5, new java.sql.Date(virement.getDateApprobation().getTime()));
            preparedStatement.setString(6, virement.getEtat());
            preparedStatement.setInt(7, virement.getId());

            preparedStatement.executeUpdate();
            System.out.println("Virement edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) virement : " + exception.getMessage());
        }
        return false;
    }


    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `virement` WHERE `id` = ?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            System.out.println("Virement deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) virement : " + exception.getMessage());
        }
        return false;
    }
}
