package com.inventorymanagementsystem.entity;

public enum Currency {
    TND("dinar"),
    USD("dollar"),
    GBP("pound"),
    EUR("euro"),
    JPY("yen"),
    QAR("riyal"),
    SAR("riyal"),
    CAD("dollar"),
    CHF("franc"),
    AED("dirham"),
    KWD("dinar"),
    BHD("dinar"),
    CNY("yuan");

    private String fullName;

    Currency(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getShortName() {
        return name();
    }
}
