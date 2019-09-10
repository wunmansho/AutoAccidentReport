package com.auto.accident.report.model;

/**
 * Created by myron on 6/5/2018.
 */

public class VehicleType {

    int VT_ID;
    String VT_TYPE;


    public VehicleType() {

    }

    public VehicleType(int VT_ID, String VT_TYPE, String VT_DESC) {
        this.VT_ID = VT_ID;
        this.VT_TYPE = VT_TYPE;

    }

    public int getVT_ID() {
        return VT_ID;
    }

    public void setVT_ID(int VT_ID) {
        this.VT_ID = VT_ID;
    }

    public String getVT_TYPE() {
        return VT_TYPE;
    }

    public void setVT_TYPE(String VT_TYPE) {
        this.VT_TYPE = VT_TYPE;
    }


}
