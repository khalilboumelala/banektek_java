package com.inventorymanagementsystem.services;

import com.inventorymanagementsystem.config.Database;
import com.inventorymanagementsystem.entity.Carte;
import com.inventorymanagementsystem.entity.Compte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarteService {

    private static CarteService instance;

    Connection connection;
    PreparedStatement preparedStatement;

    public CarteService() {
        connection = Database.getInstance().connectDB();
    }

    public static CarteService getInstance() {
        if (instance == null) {
            instance = new CarteService();
        }
        return instance;
    }


    public List<Carte> getAll() {
        List<Carte> listCarte = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `carte`");

            ResultSet resultSet = preparedStatement.executeQuery();
CompteService compteService=CompteService.getInstance();
            while (resultSet.next()) {
                listCarte.add(new Carte(
                        resultSet.getInt("id"),
                      compteService.get( resultSet.getInt("num_compte_id"))
                             ,
                        resultSet.getDate("date_emission"),
                        resultSet.getDate("date_expiration"),
                        resultSet.getInt("cvv"),
                        resultSet.getString("plafond"),
                        resultSet.getString("type"),
                        resultSet.getString("etat")
                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) carte : " + exception.getMessage());
        }
        return listCarte;
    }

    public List<Carte> getByCompteId(int compte_id) {
        List<Carte> listCarte = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `carte` WHERE `num_compte_id` = ?");
            preparedStatement.setInt(1, compte_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            CompteService compteService=CompteService.getInstance();
            while (resultSet.next()) {
                listCarte.add(new Carte(
                        resultSet.getInt("id"),
                        compteService.get( resultSet.getInt("num_compte_id"))
                        ,
                        resultSet.getDate("date_emission"),
                        resultSet.getDate("date_expiration"),
                        resultSet.getInt("cvv"),
                        resultSet.getString("plafond"),
                        resultSet.getString("type"),
                        resultSet.getString("etat")
                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) carte : " + exception.getMessage());
        }
        return listCarte;
    }

    public List<Carte> getByComptes(List<Compte> comptes) {
        List<Carte> listCarte = new ArrayList<>();
        try {
            for (Compte c : comptes) {
                preparedStatement = connection.prepareStatement("SELECT * FROM `carte` WHERE `num_compte_id` = ?");
                preparedStatement.setInt(1, c.getId());
                ResultSet resultSet = preparedStatement.executeQuery();
                //System.out.println("query: "+preparedStatement.toString());

                while (resultSet.next()) {
                    Carte carte = new Carte(
                            resultSet.getInt("id"),
                            c,
                            resultSet.getDate("date_emission"),
                            resultSet.getDate("date_expiration"),
                            resultSet.getInt("cvv"),
                            resultSet.getString("plafond"),
                            resultSet.getString("type"),
                            resultSet.getString("etat")
                    );
                    listCarte.add(carte);
                }
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) carte : " + exception.getMessage());
        }
        return listCarte;
    }


    public boolean add(Carte carte) {
        String request = "INSERT INTO `carte`( `date_emission`, `date_expiration`, `cvv`, `plafond`, `type`, `etat`) VALUES( ?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

          //  preparedStatement.setInt(1, null);//TEST AVANT INTEGRATION
            preparedStatement.setDate(1, new java.sql.Date(carte.getDateEmission().getTime()));
            preparedStatement.setDate(2, new java.sql.Date(carte.getDateExpiration().getTime()));
            preparedStatement.setInt(3, carte.getCvv());
            preparedStatement.setString(4, carte.getPlafond());
            preparedStatement.setString(5, carte.getType());
            preparedStatement.setString(6, carte.getEtat());

            preparedStatement.executeUpdate();
            System.out.println("Carte added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) carte : " + exception.getMessage());
        }
        return false;
    }


    public boolean edit(Carte carte) {
        String request = "UPDATE `carte` SET `num_compte_id` = ?, `date_emission` = ?, `date_expiration` = ?, `cvv` = ?, `plafond` = ?, `type` = ?, `etat` = ? WHERE `id` = ?";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, carte.getNumCompte().getId());
            preparedStatement.setDate(2, new java.sql.Date(carte.getDateEmission().getTime()));
            preparedStatement.setDate(3, new java.sql.Date(carte.getDateExpiration().getTime()));
            preparedStatement.setInt(4, carte.getCvv());
            preparedStatement.setString(5, carte.getPlafond());
            preparedStatement.setString(6, carte.getType());
            preparedStatement.setString(7, carte.getEtat());
            preparedStatement.setInt(8, carte.getId());

            preparedStatement.executeUpdate();
            System.out.println("Carte edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) carte : " + exception.getMessage());
        }
        return false;
    }


    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `carte` WHERE `id` = ?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            System.out.println("Carte deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) carte : " + exception.getMessage());
        }
        return false;
    }
}
