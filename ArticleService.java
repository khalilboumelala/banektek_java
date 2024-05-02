package com.inventorymanagementsystem.services;

import java.sql.Connection;

public class ArticleService {

    private Connection connection;

    public ArticleService(Connection connection) {
        this.connection = connection;
    }
}
