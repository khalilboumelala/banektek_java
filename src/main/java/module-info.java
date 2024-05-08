module com.inventorymanagementsystem{

    requires java.base;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires jasperreports;
    requires org.burningwave.core;
    requires java.desktop;
    requires org.jsoup;
    requires barbecue;
    requires jdk.jsobject;
    requires jcef.win64.fat;
    requires pandomium;
    requires java.net.http;
    requires atlantafx.base;

    requires org.kordamp.ikonli.feather;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.material2;
    requires com.fasterxml.jackson.databind;
    requires de.jensd.fx.glyphs.fontawesome;
    requires com.jfoenix;
   // requires json.simple;
    requires javafx.fxml;
    requires jbcrypt;
    requires org.kordamp.ikonli.javafx;
    requires javax.mail.api;
    requires json.simple;
    requires jackson.core.asl;
    requires jackson.mapper.asl;
    requires org.json;
    requires javafx.media;
    requires twilio;


    opens com.inventorymanagementsystem to javafx.fxml;
    exports com.inventorymanagementsystem;
    exports com.inventorymanagementsystem.entity;
    opens com.inventorymanagementsystem.entity to javafx.fxml;
    exports com.inventorymanagementsystem.config;
    opens com.inventorymanagementsystem.config to javafx.fxml;
    exports com.inventorymanagementsystem.app;
    opens com.inventorymanagementsystem.app to javafx.fxml;
    exports com.inventorymanagementsystem.utils;
    opens com.inventorymanagementsystem.utils to javafx.fxml;
}