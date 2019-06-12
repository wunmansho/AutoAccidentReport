package com.auto.accident.report.objects;

/**
 * Created by myron on 1/29/2018.   Another Comment
 */

public class AccidentId {
    private int AID_ID;
    private String AID_CCODE;
    private String AID_ADDRESS;
    private String AID_CITY;
    private String AID_STATE;
    private String AID_ZIP;
    private String AID_COUNTY;
    private String AID_LAT;
    private String AID_LON;

    private int AID_DUID;
    private String AID_RDATE;
    private String AID_ADATE;
    private String AID_ATIME;

    public AccidentId() {

    }

    public AccidentId(int AID_ID, String AID_CCODE, String AID_ADDRESS, String AID_CITY,
                      String AID_STATE, String AID_ZIP, String AID_COUNTY, String AID_LAT,
                      String AID_LON, int AID_DUID, String AID_RDATE, String AID_ADATE,
                      String AID_ATIME) {
        this.AID_ID = AID_ID;
        this.AID_CCODE = AID_CCODE;
        this.AID_ADDRESS = AID_ADDRESS;
        this.AID_CITY = AID_CITY;
        this.AID_STATE = AID_STATE;
        this.AID_ZIP = AID_ZIP;
        this.AID_COUNTY = AID_COUNTY;
        this.AID_LAT = AID_LAT;
        this.AID_LON = AID_LON;
        this.AID_DUID = AID_DUID;
        this.AID_RDATE = AID_RDATE;
        this.AID_ADATE = AID_ADATE;
        this.AID_ATIME = AID_ATIME;
    }

    public int getAID_ID() {
        return AID_ID;
    }

    public void setAID_ID(int AID_ID) {
        this.AID_ID = AID_ID;
    }

    public String getAID_CCODE() {
        return AID_CCODE;
    }

    public void setAID_CCODE(String AID_CCODE) {
        this.AID_CCODE = AID_CCODE;
    }

    public String getAID_ADDRESS() {
        return AID_ADDRESS;
    }

    public void setAID_ADDRESS(String AID_ADDRESS) {
        this.AID_ADDRESS = AID_ADDRESS;
    }

    public String getAID_CITY() {
        return AID_CITY;
    }

    public void setAID_CITY(String AID_CITY) {
        this.AID_CITY = AID_CITY;
    }

    public String getAID_STATE() {
        return AID_STATE;
    }

    public void setAID_STATE(String AID_STATE) {
        this.AID_STATE = AID_STATE;
    }

    public String getAID_ZIP() {
        return AID_ZIP;
    }

    public void setAID_ZIP(String AID_ZIP) {
        this.AID_ZIP = AID_ZIP;
    }

    public String getAID_COUNTY() {
        return AID_COUNTY;
    }

    public void setAID_COUNTY(String AID_COUNTY) {
        this.AID_COUNTY = AID_COUNTY;
    }

    public String getAID_LAT() {
        return AID_LAT;
    }

    public void setAID_LAT(String AID_LAT) {
        this.AID_LAT = AID_LAT;
    }

    public String getAID_LON() {
        return AID_LON;
    }

    public void setAID_LON(String AID_LON) {
        this.AID_LON = AID_LON;
    }

    public int getAID_DUID() {
        return AID_DUID;
    }

    public void setAID_DUID(int AID_DUID) {
        this.AID_DUID = AID_DUID;
    }

    public String getAID_RDATE() {
        return AID_RDATE;
    }

    public void setAID_RDATE(String AID_RDATE) {
        this.AID_RDATE = AID_RDATE;
    }

    public String getAID_ADATE() {
        return AID_ADATE;
    }

    public void setAID_ADATE(String AID_ADATE) {
        this.AID_ADATE = AID_ADATE;
    }

    public String getAID_ATIME() {
        return AID_ATIME;
    }

    public void setAID_ATIME(String AID_ATIME) {
        this.AID_ATIME = AID_ATIME;
    }
}