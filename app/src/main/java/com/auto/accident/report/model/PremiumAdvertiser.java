package com.auto.accident.report.model;

/**
 * Created by myron on 7/9/2018.
 */

public class PremiumAdvertiser {

    private int PA_ID;
    private String PA_MENU;
    private String PA_ADVERTISER;
    private String PA_PHONE;
    private String PA_URL;
    private String PA_PATH;
    private String PA_FILENAME;

    public PremiumAdvertiser() {

    }

    public PremiumAdvertiser(int PA_ID, String PA_MENU, String PA_ADVERTISER, String PA_PHONE, String PA_URL, String PA_PATH, String PA_FILENAME) {
        this.PA_ID = PA_ID;
        this.PA_MENU = PA_MENU;
        this.PA_ADVERTISER = PA_ADVERTISER;
        this.PA_PHONE = PA_PHONE;
        this.PA_URL = PA_URL;
        this.PA_PATH = PA_PATH;
        this.PA_FILENAME = PA_FILENAME;
    }

    public int getPA_ID() {
        return PA_ID;
    }

    public String getPA_MENU() {
        return PA_MENU;
    }

    public String getPA_ADVERTISER() {
        return PA_ADVERTISER;
    }

    public String getPA_PHONE() {
        return PA_PHONE;
    }

    public String getPA_URL() {
        return PA_URL;
    }

    public String getPA_PATH() {
        return PA_PATH;
    }

    public String getPA_FILENAME() {
        return PA_FILENAME;
    }

    public void setPA_ID(int PA_ID) {
        this.PA_ID = PA_ID;
    }

    public void setPA_MENU(String PA_MENU) {
        this.PA_MENU = PA_MENU;
    }

    public void setPA_ADVERTISER(String PA_ADVERTISER) {
        this.PA_ADVERTISER = PA_ADVERTISER;
    }

    public void setPA_PHONE(String PA_PHONE) {
        this.PA_PHONE = PA_PHONE;
    }

    public void setPA_URL(String PA_URL) {
        this.PA_URL = PA_URL;
    }

    public void setPA_PATH(String PA_PATH) {
        this.PA_PATH = PA_PATH;
    }

    public void setPA_FILENAME(String PA_FILENAME) {
        this.PA_FILENAME = PA_FILENAME;
    }
}
