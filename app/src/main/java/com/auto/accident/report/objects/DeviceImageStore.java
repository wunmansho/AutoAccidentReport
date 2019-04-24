package com.auto.accident.report.objects;

/**
 * Created by myron on 2/12/2018.
 */

public class DeviceImageStore {
    private int DP_ID;
    private int DP_DUID;
    private String DP_CATEGORY;
    private String DP_PATH;
    private String DP_FILENAME;
    private int DP_CUID;
    private String DP_CDATE;
    private int DP_UUID;
    private String DP_UDATE;

    public DeviceImageStore() {

    }
    // Result of generate Constructor


    public DeviceImageStore(int DP_ID, int DP_DUID, String DP_CATEGORY, String DP_PATH, String DP_FILENAME,
                            int DP_CUID, String DP_CDATE, int DP_UUID, String DP_UDATE) {
        this.DP_ID = DP_ID;
        this.DP_DUID = DP_DUID;
        this.DP_CATEGORY = DP_CATEGORY;
        this.DP_PATH = DP_PATH;
        this.DP_FILENAME = DP_FILENAME;
        this.DP_CUID = DP_CUID;
        this.DP_CDATE = DP_CDATE;
        this.DP_UUID = DP_UUID;
        this.DP_UDATE = DP_UDATE;
    }

    // Generate Setters

    public int getDP_ID() {
        return DP_ID;
    }

    public void setDP_ID(int DP_ID) {
        this.DP_ID = DP_ID;
    }

    public int getDP_DUID() {
        return DP_DUID;
    }

    public void setDP_DUID(int DP_DUID) {
        this.DP_DUID = DP_DUID;
    }

    public String getDP_CATEGORY() {
        return DP_CATEGORY;
    }

    public void setDP_CATEGORY(String DP_CATEGORY) {
        this.DP_CATEGORY = DP_CATEGORY;
    }

    public String getDP_PATH() {
        return DP_PATH;
    }

    public void setDP_PATH(String DP_PATH) {
        this.DP_PATH = DP_PATH;
    }

    public String getDP_FILENAME() {
        return DP_FILENAME;
    }

    // Generate Getters

    public void setDP_FILENAME(String DP_FILENAME) {
        this.DP_FILENAME = DP_FILENAME;
    }

    public int getDP_CUID() {
        return DP_CUID;
    }

    public void setDP_CUID(int DP_CUID) {
        this.DP_CUID = DP_CUID;
    }

    public String getDP_CDATE() {
        return DP_CDATE;
    }

    public void setDP_CDATE(String DP_CDATE) {
        this.DP_CDATE = DP_CDATE;
    }

    public int getDP_UUID() {
        return DP_UUID;
    }

    public void setDP_UUID(int DP_UUID) {
        this.DP_UUID = DP_UUID;
    }

    public String getDP_UDATE() {
        return DP_UDATE;
    }

    public void setDP_UDATE(String DP_UDATE) {
        this.DP_UDATE = DP_UDATE;
    }
}
