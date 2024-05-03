package com.inventorymanagementsystem.utils;



import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Downloader implements Serializable {
    String key;

    public Downloader(String key) {
        this.key = key;
    }
    public String download(String symbol, StockTimeSeries type){
        String urlString = buildRequest(type.getFunction(),symbol);
        try {
            URL url = new URL ( urlString );
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            Scanner in = new Scanner(inputStream);
            StringBuilder answer = new StringBuilder();
            while (in.hasNext()){
                answer.append(in.nextLine());
            }
            System.out.println("Downloaded " + symbol + " in Downloader");
            return answer.toString();
        } catch (IOException e) {
            JavaFXException exception = new JavaFXException(e.getMessage());
            exception.showErrorDialog();
            e.printStackTrace();
        }

        return null;
    }
    private String buildRequest(String function, String symbol){
        String link = "https://www.alphavantage.co/query?function=" +
                function +
                "&symbol=" +
                symbol +
                "&interval=60min" + // Specify the desired timezone
                "&apikey=" + this.key;
        System.out.println("api: " + link);
        return link;
    }

}

