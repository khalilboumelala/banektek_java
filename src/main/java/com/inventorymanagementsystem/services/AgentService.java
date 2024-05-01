package com.inventorymanagementsystem.services;



import com.inventorymanagementsystem.config.Database;
import com.inventorymanagementsystem.entity.Agent;
import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AgentService {

    private static AgentService instance;

    static Connection connection;
    static PreparedStatement preparedStatement;

    public AgentService() {
        connection = Database.getInstance().connectDB();
    }

    public static AgentService getInstance() {
        if (instance == null) {
            instance = new AgentService();
        }
        return instance;
    }
    @FXML
    public List<Agent> getAll() {
        List<Agent> agents = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `agent`");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                agents.add(new Agent(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_agence_id"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("poste"),
                        resultSet.getString("matricule"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("num_tel"),
                        resultSet.getString("photo"),
                        resultSet.getString("faceid")
                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) agent : " + exception.getMessage());
        }
        return agents;
    }
    @FXML
    public Agent get(int agentId) {
        Agent agent = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `agent` WHERE id = ?");
            preparedStatement.setInt(1, agentId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                agent = new Agent(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_agence_id"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("poste"),
                        resultSet.getString("matricule"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("num_tel"),
                        resultSet.getString("photo"),
                        resultSet.getString("faceid")
                );
            }
        } catch (SQLException exception) {
            System.out.println("Error (get) agent : " + exception.getMessage());
        }
        return agent;
    }
    @FXML
    public static boolean add(Agent agent) {
        String request = "INSERT INTO `agent`(`id_agence_id`, `nom`, `prenom`, `poste`, `matricule`, `password`, `email`, `num_tel`, `photo`, `faceid`) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, agent.getIdAgence());
            preparedStatement.setString(2, agent.getNom());
            preparedStatement.setString(3, agent.getPrenom());
            preparedStatement.setString(4, agent.getPoste());
            preparedStatement.setString(5, agent.getMatricule());
            preparedStatement.setString(6, agent.getPassword());
            preparedStatement.setString(7, agent.getEmail());
            preparedStatement.setString(8, agent.getNumTel());
            preparedStatement.setString(9, agent.getPhoto());
            preparedStatement.setString(10, agent.getFaceId());

            preparedStatement.executeUpdate();
            System.out.println("Agent added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) agent : " + exception.getMessage());
        }
        return false;
    }
    @FXML
    public static boolean edit(Agent agent) {
        String request = "UPDATE `agent` SET `id_agence_id`=?, `nom`=?, `prenom`=?, `poste`=?, `matricule`=?, `password`=?, `email`=?, `num_tel`=?, `photo`=?, `faceid`=? WHERE `id`=?";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1,agent.getIdAgence());
            preparedStatement.setString(2, agent.getNom());
            preparedStatement.setString(3, agent.getPrenom());
            preparedStatement.setString(4, agent.getPoste());
            preparedStatement.setString(5, agent.getMatricule());
            preparedStatement.setString(6, agent.getPassword());
            preparedStatement.setString(7, agent.getEmail());
            preparedStatement.setString(8, agent.getNumTel());
            preparedStatement.setString(9, agent.getPhoto());
            preparedStatement.setString(10, agent.getFaceId());
            preparedStatement.setInt(11, agent.getId());

            preparedStatement.executeUpdate();
            System.out.println("Agent edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) agent : " + exception.getMessage());
        }
        return false;
    }
    @FXML
    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `agent` WHERE `id` = ?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            System.out.println("Agent deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) agent : " + exception.getMessage());
        }
        return false;
    }
}
