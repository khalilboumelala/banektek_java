package com.inventorymanagementsystem.utils;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModalDialog extends Stage {
    public ModalDialog(Node content) {
        VBox container = new VBox();
        container.getChildren().addAll(content, createCloseButton());
        container.setPadding(new Insets(10));

        this.initModality(Modality.APPLICATION_MODAL);
        this.setScene(new javafx.scene.Scene(container));
    }

    private Button createCloseButton() {
        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(evt -> this.hide());
        return closeBtn;
    }
}
