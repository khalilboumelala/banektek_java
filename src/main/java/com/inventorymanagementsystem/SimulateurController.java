package com.inventorymanagementsystem;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.HashMap;
import java.util.Map;

public class SimulateurController implements Initializable {

    @FXML
    private Pane ResultPane;

    @FXML
    private TextField apporttextfield;

    @FXML
    private ComboBox<String> categoriecombobox;

    @FXML
    private Button clearbutton;

    @FXML
    private TextField dureetextfield;

    @FXML
    private TextField mensualiteautrestextfield;

    @FXML
    private TextField prixtextfield;

    @FXML
    private TextField revenutextfield;

    @FXML
    private Button simulatebutton;

    @FXML
    private Text monthly1;

    @FXML
    private Text frais;

    @FXML
    private Text finSol;

    // Map to store credit values for each category
    private final Map<String, Map<String, String>> creditValues = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate the creditValues map
        populateCreditValues();

        // Add items to the ComboBox
       // categoriecombobox.getItems().addAll("Crédit Agricole", "Crédit Automobile", "Crédit Commercial", "Crédit Étudiant");

        // Event listener for simulate button click
        simulatebutton.setOnAction(event -> simulateCredit());

        // Event listener for category selection change
        categoriecombobox.setOnAction(event -> {
            String selectedCategory = categoriecombobox.getValue();
            updateDuration(selectedCategory);
        });
    }

    private void populateCreditValues() {
        // Credit values for each category
        Map<String, String> creditAgricole = new HashMap<>();
        creditAgricole.put("maxPeriod", "10");
        creditAgricole.put("apportPropre", "20%");
        creditAgricole.put("plafond", "160 000");
        creditValues.put("Crédit Agricole", creditAgricole);

        Map<String, String> creditAutomobile = new HashMap<>();
        creditAutomobile.put("maxPeriod", "7");
        creditAutomobile.put("apportPropre", "Selon la puissance fiscale");
        creditAutomobile.put("plafond", "250 000");
        creditValues.put("Crédit Automobile", creditAutomobile);

        Map<String, String> creditCommercial = new HashMap<>();
        creditCommercial.put("maxPeriod", "3");
        creditCommercial.put("apportPropre", "250 DT / Personne");
        creditCommercial.put("plafond", "100 000");
        creditValues.put("Crédit Commercial", creditCommercial);

        Map<String, String> creditEtudiant = new HashMap<>();
        creditEtudiant.put("maxPeriod", "25");
        creditEtudiant.put("apportPropre", "0%");
        creditEtudiant.put("plafond", "Non Plafonné");
        creditValues.put("Crédit Étudiant", creditEtudiant);
    }

    private void simulateCredit() {
        double acquisitionPrice = Double.parseDouble(prixtextfield.getText());
        double minCapital = Double.parseDouble(apporttextfield.getText());
        double duration = Double.parseDouble(dureetextfield.getText()) * 12; // Convert duration to months
        double interestRate = 0.1; // Example interest rate

        // Calculate monthly payment
        double monthlyPayment = ((acquisitionPrice-minCapital) * interestRate) / (1 - Math.pow(1 + interestRate, -duration));

        // Calculate frais (1% of the acquisition price)
        double fraisValue = acquisitionPrice * 0.01;

        // Calculate fin_sol (acquisition price minus min capital)
        double finSolValue = acquisitionPrice - minCapital;

        // Update UI with new values
        monthly1.setText(String.format("%.2f DT", monthlyPayment));
        frais.setText(String.format("%.2f DT", fraisValue));
        finSol.setText(String.format("%.2f DT", finSolValue));
    }

    private void updateDuration(String selectedCategory) {
        if (creditValues.containsKey(selectedCategory)) {
            Map<String, String> selectedCreditValues = creditValues.get(selectedCategory);
            String maxPeriod = selectedCreditValues.get("maxPeriod");
            dureetextfield.setText(maxPeriod);
        } else {
            // Default duration if category not found
            dureetextfield.setText("");
        }
    }
}
