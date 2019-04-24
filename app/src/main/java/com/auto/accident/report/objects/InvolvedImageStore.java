package com.auto.accident.report.objects;

/**
 * Created by myron on 2/12/2018.
 */

public class InvolvedImageStore {
    private int AP_ID;
    private int AP_AID;
    private int AP_IPID;
    private String AP_CATEGORY;
    private String AP_PATH;
    private String AP_FILENAME;
    private int AP_CUID;
    private String AP_CDATE;
    private int AP_UUID;
    private String AP_UDATE;

    public InvolvedImageStore() {

    }
    // Result of generate Constructor


    public InvolvedImageStore(int AP_ID, int AP_AID, int AP_IPID, String AP_CATEGORY, String AP_PATH, String AP_FILENAME,
                              int AP_CUID, String AP_CDATE, int AP_UUID, String AP_UDATE) {
        this.AP_ID = AP_ID;
        this.AP_AID = AP_AID;
        this.AP_IPID = AP_IPID;
        this.AP_CATEGORY = AP_CATEGORY;
        this.AP_PATH = AP_PATH;
        this.AP_FILENAME = AP_FILENAME;
        this.AP_CUID = AP_CUID;
        this.AP_CDATE = AP_CDATE;
        this.AP_UUID = AP_UUID;
        this.AP_UDATE = AP_UDATE;
    }

    // Generate Setters

    public int getAP_ID() {
        return AP_ID;
    }

    public void setAP_ID(int AP_ID) {
        this.AP_ID = AP_ID;
    }

    public int getAP_AID() {
        return AP_AID;
    }

    public void setAP_AID(int AP_AID) {
        this.AP_AID = AP_AID;
    }

    public int getAP_IPID() {
        return AP_IPID;
    }

    public void setAP_IPID(int AP_IPID) {
        this.AP_IPID = AP_IPID;
    }

    public String getAP_CATEGORY() {
        return AP_CATEGORY;
    }

    public void setAP_CATEGORY(String AP_CATEGORY) {
        this.AP_CATEGORY = AP_CATEGORY;
    }

    public String getAP_PATH() {
        return AP_PATH;
    }

    public void setAP_PATH(String AP_PATH) {
        this.AP_PATH = AP_PATH;
    }

    // Generate Getters

    public String getAP_FILENAME() {
        return AP_FILENAME;
    }

    public void setAP_FILENAME(String AP_FILENAME) {
        this.AP_FILENAME = AP_FILENAME;
    }

    public int getAP_CUID() {
        return AP_CUID;
    }

    public void setAP_CUID(int AP_CUID) {
        this.AP_CUID = AP_CUID;
    }

    public String getAP_CDATE() {
        return AP_CDATE;
    }

    public void setAP_CDATE(String AP_CDATE) {
        this.AP_CDATE = AP_CDATE;
    }

    public int getAP_UUID() {
        return AP_UUID;
    }

    public void setAP_UUID(int AP_UUID) {
        this.AP_UUID = AP_UUID;
    }

    public String getAP_UDATE() {
        return AP_UDATE;
    }

    public void setAP_UDATE(String AP_UDATE) {
        this.AP_UDATE = AP_UDATE;
    }
}
