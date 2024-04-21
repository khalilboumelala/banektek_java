package com.inventorymanagementsystem;


import com.inventorymanagementsystem.entity.Currency;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.converter.DoubleStringConverter;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class CurrencyController implements Initializable {

    @FXML
    private ComboBox<Currency> otherCurrencyComboBox;

    @FXML
    private ComboBox<String> tndCurrencyComboBox;

    @FXML
    private TextField othercurrencyammount;

    @FXML
    private TextField TndAmount;



    @FXML
    private Button convertButton;
    @FXML
    private Button closebutton;
    private final static DoubleStringConverter DOUBLE_STRING_CONVERTER = new DoubleStringConverter();

    private final static DecimalFormat CURRENCY_FORMAT = new DecimalFormat("#0.000");

    private final ObservableList<Currency> currencies = FXCollections.observableArrayList();

    private final ChangeListener<String> tndAmountChangeListener = (observable, oldValue, newValue) -> {
        try {
            convertAction(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        currencies.addAll(Currency.values());
     tndCurrencyComboBox.setDisable(true);
        String TND="TND";

        ArrayList<String> listtnd = new ArrayList<String>();
        listtnd.add(TND);
        tndCurrencyComboBox.setItems(FXCollections.singletonObservableList("TND"));
        otherCurrencyComboBox.setItems(currencies);
        tndCurrencyComboBox.getSelectionModel().selectFirst();
        otherCurrencyComboBox.getSelectionModel().selectLast();


      //  TndAmount.textProperty().addListener(tndAmountChangeListener);

    //    clearAction(null);

        try {
            responsebody= fetchExchangeRate();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        TndAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                convertAction(null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // You can perform any actions you want here when the text changes
        });
        otherCurrencyComboBox.setOnAction(event -> {
            try {
                convertAction(null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });

    }

    private final static HttpClient httpClient = HttpClient.newHttpClient();
    private final static ObjectMapper objectMapper = new ObjectMapper();
    public String responsebody;

    private String fetchExchangeRate() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://exchange-rate-api1.p.rapidapi.com/latest?base=TND"))
                .header("X-RapidAPI-Key", "7a9f9612aemsh9673ccae68eefcap16afb3jsn3ab3b4b815e5")
                .header("X-RapidAPI-Host", "exchange-rate-api1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        return response.body();
    }

    private Double parseExchangeRate(String responseBody, String currencyCode) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            JsonNode ratesNode = jsonNode.get("rates");
            if (ratesNode.has(currencyCode)) {
                return ratesNode.get(currencyCode).asDouble();
            } else {
                System.err.println("Currency code not found in the response.");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @FXML
    private void convertAction(ActionEvent actionEvent) throws IOException, InterruptedException {
        String inputCurrency = tndCurrencyComboBox.getValue();
        Currency outputCurrency = otherCurrencyComboBox.getValue();
        System.out.println("ouput currency: "+outputCurrency);
        double inputValue;
        if (!TndAmount.getText().isEmpty() && isNumeric(TndAmount.getText())) {
            inputValue = DOUBLE_STRING_CONVERTER.fromString(TndAmount.getText());
            // Fetch exchange rate asynchronously
            Double exchangeRateFuture = parseExchangeRate( responsebody,outputCurrency.toString());

                    double outputValue = inputValue * exchangeRateFuture;
                    othercurrencyammount.setText(CURRENCY_FORMAT.format(outputValue));


        }
    }
    @FXML
    private void convertActionReverse(ActionEvent actionEvent) throws IOException, InterruptedException {

        String inputCurrency = tndCurrencyComboBox.getValue();
        Currency outputCurrency = otherCurrencyComboBox.getValue();
        System.out.println("ouput currency: "+outputCurrency);
        double inputValue;
        if (!TndAmount.getText().isEmpty() && isNumeric(TndAmount.getText())) {
            inputValue = DOUBLE_STRING_CONVERTER.fromString(TndAmount.getText());
            // Fetch exchange rate asynchronously
            Double exchangeRateFuture = parseExchangeRate( responsebody,outputCurrency.toString());

            double outputValue = inputValue / exchangeRateFuture;
            TndAmount.setText(CURRENCY_FORMAT.format(outputValue));


        }
    }
    @FXML
    private void clearAction(ActionEvent actionEvent) {
        TndAmount.setText("");
        othercurrencyammount.setText("");
    }



    private static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void onExit(){
        closebutton.getScene().getWindow().hide();
    }


}
