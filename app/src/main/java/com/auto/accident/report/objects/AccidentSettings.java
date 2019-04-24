package com.auto.accident.report.objects;

/**
 * Created by myron on 4/28/2018.
 */

public class AccidentSettings {
    int AS_ID;
    int AS_DUID;
    String AS_SETTING;
    int AS_VALUE;


    public AccidentSettings() {

    }

    public AccidentSettings(int AS_ID, int AS_DUID, String AS_SETTING, int AS_VALUE) {
        this.AS_ID = AS_ID;
        this.AS_DUID = AS_DUID;
        this.AS_SETTING = AS_SETTING;
        this.AS_VALUE = AS_VALUE;
    }

    public int getAS_ID() {
        return AS_ID;
    }

    public void setAS_ID(int AS_ID) {
        this.AS_ID = AS_ID;
    }

    public int getAS_DUID() {
        return AS_DUID;
    }

    public void setAS_DUID(int AS_DUID) {
        this.AS_DUID = AS_DUID;
    }

    public String getAS_SETTING() {
        return AS_SETTING;
    }

    public void setAS_SETTING(String AS_SETTING) {
        this.AS_SETTING = AS_SETTING;
    }

    public int getAS_VALUE() {
        return AS_VALUE;
    }

    public void setAS_VALUE(int AS_VALUE) {
        this.AS_VALUE = AS_VALUE;
    }
}
