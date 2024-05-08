package com.inventorymanagementsystem.utils;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class LoadFxmlTask extends Task<Void> {
    private final FXMLLoader loader;
    private final Pane pane;

    public FXMLLoader getLoader() {
        return loader;
    }

    public LoadFxmlTask(FXMLLoader loader) {
        this.loader = loader;
        this.pane = new Pane(); // You can change this if you have specific panes for each loader
    }

    @Override
    protected Void call() throws Exception {

        pane.getChildren().add(loader.load());
        return null;
    }

    public Pane getLoadedPane() {
        return pane;
    }
}
