package com.inventorymanagementsystem.utils;

public class Interday extends StockTimeSeries{
    private Interval interval;

    public Interday(Downloader downloader, String symbol, Interval interval) {
        super(downloader,symbol,"TIME_SERIES_INTRADAY");
        this.interval = interval;
    }
}
