package com.inventorymanagementsystem;

import com.inventorymanagementsystem.entity.Reponse;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ReponseCellFactory implements Callback<ListView<Reponse>, ListCell<Reponse>> {

    @Override
    public ListCell<Reponse> call(ListView<Reponse> param) {
        return new ListCell<Reponse>() {
            @Override
            protected void updateItem(Reponse item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString()); // Vous pouvez ajuster cela selon la représentation de Reponse que vous voulez afficher
                }
            }
        };
    }
}

