//Compte:
/*
compte_listview.setCellFactory(param -> new JFXListCell<>() {

@Override
protected void updateItem(Compte compte, boolean empty) {
        super.updateItem(compte, empty);
        if (empty || compte == null) {
        setText(null);
        setGraphic(null);
        } else {
        // Create a VBox to hold the icons and text for each attribute
        VBox container = new VBox();
        container.setSpacing(5); // Adjust spacing as needed

        // Add FontAwesome icons and text for each attribute
        FontAwesomeIconView typeIcon = new FontAwesomeIconView(FontAwesomeIcon.FILE);
        typeIcon.setFill(Color.web("#3498db")); // Adjust icon color as needed
        typeIcon.setSize("2em"); // Set icon size
        Text typeText = new Text(" " + compte.getType());

        FontAwesomeIconView soldeIcon = new FontAwesomeIconView(FontAwesomeIcon.DOLLAR_SIGN);
        soldeIcon.setFill(Color.web("#e74c3c")); // Adjust icon color as needed
        soldeIcon.setSize("2em"); // Set icon size
        Text soldeText = new Text(" " + compte.getSolde());

        FontAwesomeIconView etatIcon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE);
        etatIcon.setFill(Color.web("#2ecc71")); // Adjust icon color as needed
        etatIcon.setSize("2em"); // Set icon size
        Text etatText = new Text(" " + compte.getEtat());

        // Add icons and text to the VBox container
        container.getChildren().addAll(typeIcon, typeText, soldeIcon, soldeText, etatIcon, etatText);

        // Set the VBox container as the graphic for the list cell
        setText(null); // Clear text
        setGraphic(container);
        }
        }

        });

//CARTE
carte_listview.setCellFactory(param -> new JFXListCell<>() {

    @Override
    protected void updateItem(Carte carte, boolean empty) {
        super.updateItem(carte, empty);
        if (empty || carte == null) {
            setText(null);
            setGraphic(null);
        } else {
            // Create a VBox to hold the icons and text for each attribute
            VBox container = new VBox();
            container.setSpacing(5); // Adjust spacing as needed

            // Add FontAwesome icons and text for each attribute
            FontAwesomeIconView dateEmissionIcon = new FontAwesomeIconView(FontAwesomeIcon.CALENDAR_ALT);
            dateEmissionIcon.setFill(Color.web("#3498db")); // Adjust icon color as needed
            Text dateEmissionText = new Text(" Date Emission: " + carte.getDateEmission());

            FontAwesomeIconView dateExpirationIcon = new FontAwesomeIconView(FontAwesomeIcon.CALENDAR_TIMES);
            dateExpirationIcon.setFill(Color.web("#e74c3c")); // Adjust icon color as needed
            Text dateExpirationText = new Text(" Date Expiration: " + carte.getDateExpiration());

            FontAwesomeIconView cvvIcon = new FontAwesomeIconView(FontAwesomeIcon.KEY);
            cvvIcon.setFill(Color.web("#2ecc71")); // Adjust icon color as needed
            Text cvvText = new Text(" CVV: " + carte.getCvv());

            FontAwesomeIconView plafondIcon = new FontAwesomeIconView(FontAwesomeIcon.DOLLAR_SIGN);
            plafondIcon.setFill(Color.web("#3498db")); // Adjust icon color as needed
            Text plafondText = new Text(" Plafond: " + carte.getPlafond());

            FontAwesomeIconView typeIcon = new FontAwesomeIconView(FontAwesomeIcon.LIST);
            typeIcon.setFill(Color.web("#e74c3c")); // Adjust icon color as needed
            Text typeText = new Text(" Type: " + carte.getType());

            FontAwesomeIconView etatIcon = new FontAwesomeIconView(FontAwesomeIcon.SIGN);
            etatIcon.setFill(Color.web("#2ecc71")); // Adjust icon color as needed
            Text etatText = new Text(" Etat: " + carte.getEtat());

            // Add icons and text to the VBox container
            container.getChildren().addAll(dateEmissionIcon, dateEmissionText, dateExpirationIcon, dateExpirationText,
                                             cvvIcon, cvvText, plafondIcon, plafondText, typeIcon, typeText,
                                             etatIcon, etatText);

            // Set the VBox container as the graphic for the list cell
            setText(null); // Clear text
            setGraphic(container);
        }
    }

});


//VIREMENT

//TRANSACTION

//TYPE_TRANS

*/
