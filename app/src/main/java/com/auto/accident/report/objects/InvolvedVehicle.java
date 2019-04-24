package com.auto.accident.report.objects;

/**
 * Created by myron on 3/12/2018.
 */

public class InvolvedVehicle {
    private int IV_ID;
    private int IV_AID;
    private String IV_TAG;
    private String IV_STATE;
    private String IV_EXPIRATION;
    private String IV_VIN;
    private String IV_YEAR;
    private String IV_MAKE;
    private String IV_MODEL;
    private int IV_CUID;
    private String IV_CDATE;
    private int IV_UUID;
    private String IV_UDATE;
    private String IV_TYPE;
    private String IV_PLATE_COUNTRY;


    public InvolvedVehicle() {

    }

    public InvolvedVehicle(int IV_ID, int IV_AID, String IV_TAG, String IV_STATE, String IV_EXPIRATION,
                           String IV_VIN, String IV_YEAR, String IV_MAKE, String IV_MODEL, int IV_CUID,
                           String IV_CDATE, int IV_UUID, String IV_UDATE, String IV_TYPE, String IV_PLATE_COUNTRY) {
        this.IV_ID = IV_ID;
        this.IV_AID = IV_AID;
        this.IV_TAG = IV_TAG;
        this.IV_STATE = IV_STATE;
        this.IV_EXPIRATION = IV_EXPIRATION;
        this.IV_VIN = IV_VIN;
        this.IV_YEAR = IV_YEAR;
        this.IV_MAKE = IV_MAKE;
        this.IV_MODEL = IV_MODEL;
        this.IV_CUID = IV_CUID;
        this.IV_CDATE = IV_CDATE;
        this.IV_UUID = IV_UUID;
        this.IV_UDATE = IV_UDATE;
        this.IV_TYPE = IV_TYPE;
        this.IV_PLATE_COUNTRY = IV_PLATE_COUNTRY;
    }

    public int getIV_ID() {
        return IV_ID;
    }

    public void setIV_ID(int IV_ID) {
        this.IV_ID = IV_ID;
    }

    public int getIV_AID() {
        return IV_AID;
    }

    public void setIV_AID(int IV_AID) {
        this.IV_AID = IV_AID;
    }

    public String getIV_TAG() {
        return IV_TAG;
    }

    public void setIV_TAG(String IV_TAG) {
        this.IV_TAG = IV_TAG;
    }

    public String getIV_STATE() {
        return IV_STATE;
    }

    public void setIV_STATE(String IV_STATE) {
        this.IV_STATE = IV_STATE;
    }

    public String getIV_EXPIRATION() {
        return IV_EXPIRATION;
    }

    public void setIV_EXPIRATION(String IV_EXPIRATION) {
        this.IV_EXPIRATION = IV_EXPIRATION;
    }

    public String getIV_VIN() {
        return IV_VIN;
    }

    public void setIV_VIN(String IV_VIN) {
        this.IV_VIN = IV_VIN;
    }

    public String getIV_YEAR() {
        return IV_YEAR;
    }

    public void setIV_YEAR(String IV_YEAR) {
        this.IV_YEAR = IV_YEAR;
    }

    public String getIV_MAKE() {
        return IV_MAKE;
    }

    public void setIV_MAKE(String IV_MAKE) {
        this.IV_MAKE = IV_MAKE;
    }

    public String getIV_MODEL() {
        return IV_MODEL;
    }

    public void setIV_MODEL(String IV_MODEL) {
        this.IV_MODEL = IV_MODEL;
    }

    public int getIV_CUID() {
        return IV_CUID;
    }

    public void setIV_CUID(int IV_CUID) {
        this.IV_CUID = IV_CUID;
    }

    public String getIV_CDATE() {
        return IV_CDATE;
    }

    public void setIV_CDATE(String IV_CDATE) {
        this.IV_CDATE = IV_CDATE;
    }

    public int getIV_UUID() {
        return IV_UUID;
    }

    public void setIV_UUID(int IV_UUID) {
        this.IV_UUID = IV_UUID;
    }

    public String getIV_UDATE() {
        return IV_UDATE;
    }

    public void setIV_UDATE(String IV_UDATE) {
        this.IV_UDATE = IV_UDATE;
    }

    public String getIV_TYPE() {
        return IV_TYPE;
    }

    public void setIV_TYPE(String IV_TYPE) {
        this.IV_TYPE = IV_TYPE;
    }

    public String getIV_PLATE_COUNTRY() {
        return IV_PLATE_COUNTRY;
    }

    public void setIV_PLATE_COUNTRY(String IV_PLATE_COUNTRY) {
        this.IV_PLATE_COUNTRY = IV_PLATE_COUNTRY;
    }
}
