package com.inventorymanagementsystem.services;

import com.inventorymanagementsystem.config.Database;
import com.inventorymanagementsystem.entity.Compte;
import com.inventorymanagementsystem.entity.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    private static TransactionService instance;

    Connection connection;
    PreparedStatement preparedStatement;

    public TransactionService() {
        connection = Database.getInstance().getConnection();
    }

    public static TransactionService getInstance() {
        if (instance == null) {
            instance = new TransactionService();
        }
        return instance;
    }


    public List<Transaction> getAll() {
        List<Transaction> listTransaction = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `transaction`");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listTransaction.add(new Transaction(
                        resultSet.getInt("id"),
                        new Compte(
                                resultSet.getInt("id_compte_id"),
                                null, // You may need to fetch the Compte object separately
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null
                        ),
                        resultSet.getString("type"),
                        resultSet.getDate("date_transaction"),
                        resultSet.getDouble("montant")
                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) transaction : " + exception.getMessage());
        }
        return listTransaction;
    }


    public boolean add(Transaction transaction) {
        String request = "INSERT INTO `transaction`(`id_compte`, `type`, `date_transaction`, `montant`) VALUES(?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, transaction.getIdCompte().getId());
            preparedStatement.setString(2, transaction.getType());
            preparedStatement.setDate(3, new java.sql.Date(transaction.getDateTransaction().getTime()));
            preparedStatement.setDouble(4, transaction.getMontant());

            preparedStatement.executeUpdate();
            System.out.println("Transaction added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) transaction : " + exception.getMessage());
        }
        return false;
    }


    public boolean edit(Transaction transaction) {
        String request = "UPDATE `transaction` SET `id_compte` = ?, `type` = ?, `date_transaction` = ?, `montant` = ? WHERE `id` = ?";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, transaction.getIdCompte().getId());
            preparedStatement.setString(2, transaction.getType());
            preparedStatement.setDate(3, new java.sql.Date(transaction.getDateTransaction().getTime()));
            preparedStatement.setDouble(4, transaction.getMontant());
            preparedStatement.setInt(5, transaction.getId());

            preparedStatement.executeUpdate();
            System.out.println("Transaction edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) transaction : " + exception.getMessage());
        }
        return false;
    }


    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `transaction` WHERE `id` = ?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            System.out.println("Transaction deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) transaction : " + exception.getMessage());
        }
        return false;
    }
}
