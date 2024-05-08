package com.inventorymanagementsystem.services;

import com.inventorymanagementsystem.config.Database;
//import com.inventorymanagementsystem.entity.Compte;
import com.inventorymanagementsystem.entity.Compte;
import com.inventorymanagementsystem.entity.Transaction;
import com.inventorymanagementsystem.entity.Virement;

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

    public TransactionService() { connection = Database.getInstance().connectDB();
    }

    public static TransactionService getInstance() {
        if (instance == null) {
            instance = new TransactionService();
        }
        return instance;
    }


    public List<Transaction> getAll() {
        //    connection = Database.getInstance().getConnection();
        List<Transaction> listTransaction = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = Database.getInstance().connectDB();
            preparedStatement = connection.prepareStatement("SELECT * FROM `transaction`");

            resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                listTransaction.add(new Transaction(
                        resultSet.getInt("id"),

                        resultSet.getString("type"),
                        resultSet.getDate("date_transaction"),
                        resultSet.getDouble("montant")
                ));

            }
            System.out.println("trans: "+listTransaction);
        } catch (SQLException exception) {
            System.out.println("Error (getAll) transaction : " + exception.getMessage());
        }
        return listTransaction;
    }


    public List<Transaction> getByComptes(List<Compte> comptes) {
        List<Transaction> listCarte = new ArrayList<>();
        try {
            for (Compte c : comptes) {
                preparedStatement = connection.prepareStatement("SELECT * FROM `transaction` WHERE (`id_compte_id` = ?) ");
                preparedStatement.setInt(1, c.getId());

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Transaction tr = new Transaction(
                            resultSet.getInt("id"),
                            resultSet.getString("type"),
                            resultSet.getDate("date_transaction"),
                            resultSet.getDouble("montant"),
                            resultSet.getInt("id_compte_id")
                    );
                    listCarte.add(tr);
                }
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) transaction : " + exception.getMessage());
        }
        return listCarte;
    }



    public boolean add(Transaction transaction) {
        String request = "INSERT INTO `transaction`( `type`, `date_transaction`, `montant`) VALUES( ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);


            preparedStatement.setString(1, transaction.getType());
            preparedStatement.setDate(2, new java.sql.Date(transaction.getDateTransaction().getTime()));
            preparedStatement.setDouble(3, transaction.getMontant());

            preparedStatement.executeUpdate();
            System.out.println("Transaction added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) transaction : " + exception.getMessage());
        }
        return false;
    }


    public boolean edit(Transaction transaction) {
        String request = "UPDATE `transaction` SET  `type` = ?, `date_transaction` = ?, `montant` = ? WHERE `id` = ?";
        try {
            preparedStatement = connection.prepareStatement(request);


            preparedStatement.setString(1, transaction.getType());
            preparedStatement.setDate(2, new java.sql.Date(transaction.getDateTransaction().getTime()));
            preparedStatement.setDouble(3, transaction.getMontant());
            preparedStatement.setInt(4, transaction.getId());

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
