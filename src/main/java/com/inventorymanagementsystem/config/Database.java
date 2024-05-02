package com.inventorymanagementsystem.config;

import java.sql.*;

public class Database {

    public static Database databaseConnection;
    private Connection connection;

    private Database() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banektek", "root", "");
            System.out.println("Connexion etablie");

            //test
            /****
            PreparedStatement preparedStatement;
            try {
                preparedStatement = connection.prepareStatement("SELECT * FROM `article`");

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {

                    System.out.println(resultSet.getInt("id"));
                    System.out.println( resultSet.getString("titre"));    ;
                            System.out.println( resultSet.getString("image"));



                }
            } catch (SQLException exception) {
                System.out.println("Error (getAll) article : " + exception.getMessage());
            }
*/
            //


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Connection connectDB() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banektek", "root", "");
            System.out.println("Connexion etablie");
return connection;
            //test
            /*
            PreparedStatement preparedStatement;
            try {
                preparedStatement = connection.prepareStatement("SELECT * FROM `article`");

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {

                    System.out.println(resultSet.getInt("id"));
                    System.out.println( resultSet.getString("titre"));    ;
                            System.out.println( resultSet.getString("image"));



                }
            } catch (SQLException exception) {
                System.out.println("Error (getAll) article : " + exception.getMessage());
            }
*/
            //


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    public static Database getInstance() {
        if (databaseConnection == null) {
            databaseConnection = new Database();
        }
        return databaseConnection;
    }

    public Connection getConnection() {
        return connection;
    }
}
