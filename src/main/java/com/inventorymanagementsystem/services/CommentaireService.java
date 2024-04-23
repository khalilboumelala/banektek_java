package com.inventorymanagementsystem.services;

import java.sql.Connection;

public class CommentaireService {

    private Connection connection;

    public CommentaireService(Connection connection) {
        this.connection = connection;
    }
}
