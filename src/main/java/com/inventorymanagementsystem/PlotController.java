package com.inventorymanagementsystem;


import com.inventorymanagementsystem.utils.MovingAvg;
import com.inventorymanagementsystem.utils.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.SearchableComboBox;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlotController {

    @FXML
    private RadioButton movingAvg;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private TableColumn<Stock, Button> buttonCol;
    @FXML
    private TableColumn<Stock, String> stockSymbolCol;
    @FXML
    private TableView<Stock> stocksTable = new TableView<>();
    @FXML
    private LineChart<Number, Number> plot;
    @FXML
    private TextField stockSymbol;
    @FXML
    private Button goToNotificationsButton;
   //private NotificationSetupScene notificationSetupScene;

    private final ObservableList<Stock> observableStocks = FXCollections.observableArrayList();
    private final HashMap<String, XYChart.Series<Number, Number>> currentSymbolsAndSeries = new HashMap<>();



    @FXML
    private SearchableComboBox StockSymbolChoicebox;
    //"2ZR8F4DQVX8QZ19L"    HVXSM2MLTDDO704S
    private final String apiKey = "HVXSM2MLTDDO704S";//HVXSM2MLTDDO704S

    public void initialize() {
        stocksTable.setEditable(true);
        plot.setAnimated(false);
        stockSymbolCol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        buttonCol.setCellFactory(ActionButtonTableCell.forTableColumn("Delete", (Stock s) ->
        {
            stocksTable.getItems().remove(s);
            deleteSeriesOnPlotUpdateCurrentMap(s);
            observableStocks.remove(s);
            return s;
        }));
        stocksTable.setItems(observableStocks);
      //  notificationSetupScene = new NotificationSetupScene();
        xAxis.setLabel("Time of Quotations [min]");
        yAxis.setLabel("Company Value [$]");
        plot.setTitle("Value in the Last 500 Minutes of Quotations");

        plot.setCreateSymbols(false);
        plot.setHorizontalGridLinesVisible(true);
        movingAvg.selectedProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean prev, Boolean curr) {
                if (curr) {
                    calculateAvg();
                } else {
                    ObservableList<XYChart.Series<Number, Number>> observableList = FXCollections.observableArrayList();
                    currentSymbolsAndSeries.keySet().forEach(s -> {
                        XYChart.Series<Number, Number> series = new XYChart.Series<>();//StockApp.getCurrentUser().restoreSeries(s);
                        currentSymbolsAndSeries.put(s, series);
                        observableList.add(series);
                    });
                    plot.setData(observableList);
                }
            }
        });

        String url = "https://www.alphavantage.co/query?function=TOP_GAINERS_LOSERS&apikey=" + apiKey;
        try {
            String jsonString = Jsoup.connect(url).ignoreContentType(true).execute().body();
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray mostActivelyTraded = jsonObject.getJSONArray("most_actively_traded");

            List<String> tickerSymbols = new ArrayList<>();
            for (int i = 0; i < mostActivelyTraded.length(); i++) {
                JSONObject tickerInfo = mostActivelyTraded.getJSONObject(i);
                String tickerSymbol = tickerInfo.getString("ticker");
                tickerSymbols.add(tickerSymbol);
            }

            // Populate the SearchableComboBox with ticker symbols
            StockSymbolChoicebox.getItems().addAll(tickerSymbols);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void deleteSeriesOnPlotUpdateCurrentMap(Stock s) {
        String symbol = s.getSymbol();
        XYChart.Series<Number, Number> series = currentSymbolsAndSeries.remove(symbol);
        plot.getData().remove(series);
        updateAxisRange();
    }


    @FXML
            private Button close;
    @FXML
            public void onExit() {

        close.getScene().getWindow().hide();
    }

    @FXML

    public void settextchoicebox(){
        StockSymbolChoicebox.setValue("test");


    }
    @FXML
    public void generateSeries() {

        String stockSymbolText = StockSymbolChoicebox.getValue().toString(); // Retrieve text from the editable portion
        if (stockSymbolText.isEmpty()) {
            // Handle case when no symbol is entered
            return;
        }

        try {
            if (currentSymbolsAndSeries.containsKey(stockSymbolText)) {
                throw new com.inventorymanagementsystem.utils.JavaFXException("Exception in javafx");
            }
        } catch (com.inventorymanagementsystem.utils.JavaFXException e) {
            e.showErrorDialog();
            return;
        }

        User user = new User("khh", "khalieel", "2ZR8F4DQVX8QZ19L"); //StockApp.getCurrentUser();
        XYChart.Series<Number, Number> series;
        try {
            series = user.downloadData(stockSymbolText);
            plot.getData().add(series);
            currentSymbolsAndSeries.put(stockSymbolText, series);
            observableStocks.add(new Stock(stockSymbolText));
            updateAxisRange();
        } catch (com.inventorymanagementsystem.utils.JavaFXException e) {
            e.showErrorDialog();
        }
    }

    private void updateAxisRange() {
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(findMinOfSeries(currentSymbolsAndSeries.values()));
        yAxis.setUpperBound(findMaxOfSeries(currentSymbolsAndSeries.values()));
        plot.setHorizontalGridLinesVisible(true);
    }

    private double findMaxOfSeries(Collection<XYChart.Series<Number, Number>> values) {
        double allMax = Double.MIN_VALUE;
        for (XYChart.Series<Number, Number> series : values) {
            Optional<XYChart.Data<Number, Number>> max = series.getData().stream().max(Comparator.comparingDouble(data -> data.getYValue().doubleValue()));
            if (max.isPresent()) {
                double maxVal = max.get().getYValue().doubleValue();
                if (maxVal > allMax) allMax = maxVal;
            }
        }
        return allMax > Double.MIN_VALUE ? Math.round(allMax * 1.001) : 0;
    }

    private double findMinOfSeries(Collection<XYChart.Series<Number, Number>> values) {
        double allMin = Double.MAX_VALUE;
        for (XYChart.Series<Number, Number> series : values) {
            Optional<XYChart.Data<Number, Number>> min = series.getData().stream().min(Comparator.comparingDouble(data -> data.getYValue().doubleValue()));
            if (min.isPresent()) {
                double minVal = min.get().getYValue().doubleValue();
                if (minVal < allMin) allMin = minVal;
            }
        }
        return allMin < Double.MAX_VALUE ? Math.round(allMin * 0.999) : 0;
    }

    public void goToNotifications(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) goToNotificationsButton.getScene().getWindow();
      //  notificationSetupScene.start(stage);
    }

    public void calculateAvg() {
        plot.getData().forEach(series -> {
            String stockName = series.getName();
            int N = 6;
            XYChart.Series<Number, Number> initialSeries = currentSymbolsAndSeries.get(stockName);
            ArrayList<Number> movingAvarage = MovingAvg.createAvgList(
                    initialSeries.getData().stream().map(XYChart.Data::getYValue).
                            collect(Collectors.toCollection(ArrayList::new)), N);
            for (int i = N; i < 100; i++) {
                series.getData().get(i).setYValue(movingAvarage.get(i - N));
            }
        });
    }

    public static class Stock {
        public final SimpleStringProperty symbol;

        public Stock(String symbol) {
            this.symbol = new SimpleStringProperty(symbol);
        }

        //Used in getting property for table DONT DELETE
        public String getSymbol() {
            return symbol.get();
        }
    }

    public static class ActionButtonTableCell<S> extends TableCell<S, Button> {

        private final Button actionButton;

        public ActionButtonTableCell(String label, Function<S, S> function) {
            this.getStyleClass().add("action-button-table-cell");

            this.actionButton = new Button(label);
            this.actionButton.setOnAction((ActionEvent e) -> function.apply(getCurrentItem()));
            this.actionButton.setMaxWidth(Double.MAX_VALUE);
        }

        public S getCurrentItem() {
            return getTableView().getItems().get(getIndex());
        }

        public static <S> Callback<TableColumn<S, Button>, TableCell<S, Button>> forTableColumn(String label, Function<S, S> function) {
            return param -> new ActionButtonTableCell<>(label, function);
        }

        @Override
        public void updateItem(Button item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(actionButton);
            }
        }
    }

}
