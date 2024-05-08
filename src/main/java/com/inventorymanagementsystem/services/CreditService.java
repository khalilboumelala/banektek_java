package com.inventorymanagementsystem.services;

import com.inventorymanagementsystem.config.Database;
import com.inventorymanagementsystem.entity.Compte;
import com.inventorymanagementsystem.entity.Credit;
import com.inventorymanagementsystem.entity.Virement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreditService {

    private static CreditService instance;

    static Connection connection;
    static PreparedStatement preparedStatement;

    private CreditService() {
        connection = Database.getInstance().connectDB();
    }

    public static CreditService getInstance() {
        if (instance == null) {
            instance = new CreditService();
        }
        return instance;
    }

    public List<Credit> getAll() {
        List<Credit> listCredit = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `credit`");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listCredit.add(new Credit(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_compte_id"),
                        resultSet.getDouble("montant"),
                        resultSet.getDouble("taux"),
                        resultSet.getInt("duree"),
                        resultSet.getDate("date_debut"),
                        resultSet.getString("type"),
                        resultSet.getDouble("apport_propre"),
                        resultSet.getDouble("revenu_propre"),
                        resultSet.getDouble("montant_echeance"),
                        resultSet.getInt("date_echeance"),
                        resultSet.getString("etat"),
                        resultSet.getInt("nb_echeances_restants")
                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) credit: " + exception.getMessage());
        }
        return listCredit;
    }

    public List<Credit> getByComptes(List<Compte> comptes) {
        List<Credit> listCarte = new ArrayList<>();
        try {
            for (Compte c : comptes) {
                preparedStatement = connection.prepareStatement("SELECT * FROM `credit` WHERE `id_compte_id` = ? ");
                preparedStatement.setInt(1, c.getId());

                ResultSet resultSet = preparedStatement.executeQuery();
                //System.out.println("query: "+preparedStatement.toString());

                while (resultSet.next()) {
                   Credit credit = new Credit(
                            resultSet.getInt("id"),
                            resultSet.getInt("id_compte_id"),
                            resultSet.getDouble("montant"),
                            resultSet.getDouble("taux"),
                            resultSet.getInt("duree"),
                            resultSet.getDate("date_debut"),
                            resultSet.getString("type"),
                            resultSet.getDouble("apport_propre"),
                            resultSet.getDouble("revenu_propre"),
                            resultSet.getDouble("montant_echeance"),
                            resultSet.getInt("date_echeance"),
                            resultSet.getString("etat"),
                            resultSet.getInt("nb_echeances_restants")
                    );
                    listCarte.add(credit);
                }
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) carte : " + exception.getMessage());
        }
        return listCarte;
    }

}
