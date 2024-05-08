package com.inventorymanagementsystem.entity;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

public class Notification {
    private Stage stage;

    public Notification(Stage stage) {
        this.stage = stage;
    }

    public void showNotification(String title, String message) {
        ImageView imageView = new ImageView("file:///C:/Users/USER/Desktop/inventory-management-system-main final/src/main/java/com/inventorymanagementsystem/uploads/notification.png");
        imageView.setFitWidth(50); // Set width of the icon
        imageView.setFitHeight(50); // Set height of the icon

        Notifications.create()
                .title(title)
                .text(message)
                .graphic(imageView)
                .owner(stage) // Set the owner window
                .darkStyle() // Use dark style (optional)
                .hideAfter(javafx.util.Duration.seconds(5)) // Hide after 5 seconds
                .show();
    }


}
