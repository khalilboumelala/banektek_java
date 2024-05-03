package com.inventorymanagementsystem.services;

import com.inventorymanagementsystem.config.Database;
import com.inventorymanagementsystem.entity.Article;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleService {

    private static ArticleService instance;

    Connection connection;
    PreparedStatement preparedStatement;

    public ArticleService() {
        connection = Database.getInstance().connectDB();
    }

    public static ArticleService getInstance() {
        if (instance == null) {
            instance = new ArticleService();
        }
        return instance;
    }

    public List<Article> getAll() {
        List<Article> articleList = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `article`");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                articleList.add(new Article(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_agent_id"),
                        resultSet.getString("contenu"),
                        resultSet.getString("image"),
                        resultSet.getString("titre"),
                        resultSet.getDate("date_pub")
                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) article: " + exception.getMessage());
        }
        return articleList;
    }

    public Article get(int articleId) {
        Article article = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `article` WHERE `id` = ?");
            preparedStatement.setInt(1, articleId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                article = new Article(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_agent_id"),
                        resultSet.getString("contenu"),
                        resultSet.getString("image"),
                        resultSet.getString("titre"),
                        resultSet.getDate("date_pub")
                );
            }
        } catch (SQLException exception) {
            System.out.println("Error (get) article: " + exception.getMessage());
        }
        return article;
    }

    public boolean add(Article article) {
        String request = "INSERT INTO `article`(`id_agent_id`, `date_pub`, `contenu`, `image`, `titre`) VALUES (?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, 11);
            preparedStatement.setDate(2, new java.sql.Date(article.getDatePub().getTime()));
            preparedStatement.setString(3, article.getContenu());
            preparedStatement.setString(4, article.getImage());
            preparedStatement.setString(5, article.getTitre());

            preparedStatement.executeUpdate();
            System.out.println("Article added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) article: " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(Article article) {
        String request = "UPDATE `article` SET `id_agent_id`=?, `date_pub`=?, `contenu`=?, `image`=?, `titre`=? WHERE `id`=?";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, article.getIdAgent());
            preparedStatement.setDate(2, new java.sql.Date(article.getDatePub().getTime()));
            preparedStatement.setString(3, article.getContenu());
            preparedStatement.setString(4, article.getImage());
            preparedStatement.setString(5, article.getTitre());
            preparedStatement.setInt(6, article.getId());

            preparedStatement.executeUpdate();
            System.out.println("Article edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) article: " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `article` WHERE `id` = ?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            System.out.println("Article deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) article: " + exception.getMessage());
        }
        return false;
    }

}
