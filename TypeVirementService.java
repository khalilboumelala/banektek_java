package com.inventorymanagementsystem.services;

import com.inventorymanagementsystem.config.Database;
import com.inventorymanagementsystem.entity.TypeVirement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TypeVirementService {

    private static TypeVirementService instance;

    Connection connection;
    PreparedStatement preparedStatement;

    public TypeVirementService() {
        connection = Database.getInstance().getConnection();
    }

    public static TypeVirementService getInstance() {
        if (instance == null) {
            instance = new TypeVirementService();
        }
        return instance;
    }


    public List<TypeVirement> getAll() {
        List<TypeVirement> listTypeVirement = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `type_virement`");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listTypeVirement.add(new TypeVirement(
                        resultSet.getInt("id"),
                        resultSet.getDouble("frais"),
                        resultSet.getString("nom")
                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) type virement : " + exception.getMessage());
        }
        return listTypeVirement;
    }


    public boolean add(TypeVirement typeVirement) {
        String request = "INSERT INTO `type_virement`(`frais`, `nom`) VALUES(?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setDouble(1, typeVirement.getFrais());
            preparedStatement.setString(2, typeVirement.getNom());

            preparedStatement.executeUpdate();
            System.out.println("TypeVirement added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) type virement : " + exception.getMessage());
        }
        return false;
    }


    public boolean edit(TypeVirement typeVirement) {
        String request = "UPDATE `type_virement` SET `frais` = ?, `nom` = ? WHERE `id` = ?";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setDouble(1, typeVirement.getFrais());
            preparedStatement.setString(2, typeVirement.getNom());
            preparedStatement.setInt(3, typeVirement.getId());

            preparedStatement.executeUpdate();
            System.out.println("TypeVirement edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) type virement : " + exception.getMessage());
        }
        return false;
    }


    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `type_virement` WHERE `id` = ?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            System.out.println("TypeVirement deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) type virement : " + exception.getMessage());
        }
        return false;
    }
}
