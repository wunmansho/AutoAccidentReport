package com.auto.accident.report.objects;

/**
 * Created by myron on 12/3/2018.
 */

public class InvolvedMenu {
    int IM_ID;
    String IM_TYPE;
    String IM_DESC;
    String IM_PHON1 = "";
    String  IM_EMAIL = "";
    String IM_URL = "";
    String  IM_ICONURL = "";
    String  IM_CONFURL = "";


    public InvolvedMenu() {

    }

    public InvolvedMenu(int IM_ID, String IM_TYPE, String IM_DESC, String IM_PHON1, String IM_EMAIL, String IM_URL, String IM_ICONURL, String IM_CONFURL) {
        this.IM_ID = IM_ID;
        this.IM_TYPE = IM_TYPE;
        this.IM_DESC = IM_DESC;
        this.IM_PHON1 = IM_PHON1;
        this.IM_EMAIL = IM_EMAIL;
        this.IM_URL = IM_URL;
        this.IM_ICONURL = IM_ICONURL;
        this.IM_CONFURL = IM_CONFURL;
    }

    public void setIM_ID(int IM_ID) {
        this.IM_ID = IM_ID;
    }

    public void setIM_TYPE(String IM_TYPE) {
        this.IM_TYPE = IM_TYPE;
    }

    public void setIM_DESC(String IM_DESC) {
        this.IM_DESC = IM_DESC;
    }

    public void setIM_PHON1(String IM_PHON1) {
        this.IM_PHON1 = IM_PHON1;
    }

    public void setIM_EMAIL(String IM_EMAIL) {
        this.IM_EMAIL = IM_EMAIL;
    }

    public void setIM_URL(String IM_URL) {
        this.IM_URL = IM_URL;
    }

    public void setIM_ICONURL(String IM_ICONURL) {
        this.IM_ICONURL = IM_ICONURL;
    }

    public void setIM_CONFURL(String IM_CONFURL) {
        this.IM_CONFURL = IM_CONFURL;
    }

    public int getIM_ID() {
        return IM_ID;
    }

    public String getIM_TYPE() {
        return IM_TYPE;
    }

    public String getIM_DESC() {
        return IM_DESC;
    }

    public String getIM_PHON1() {
        return IM_PHON1;
    }

    public String getIM_EMAIL() {
        return IM_EMAIL;
    }

    public String getIM_URL() {
        return IM_URL;
    }

    public String getIM_ICONURL() {
        return IM_ICONURL;
    }

    public String getIM_CONFURL() {
        return IM_CONFURL;
    }
}
