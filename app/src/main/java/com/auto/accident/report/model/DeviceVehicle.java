package com.auto.accident.report.model;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
/**
 * Created by myron on 3/12/2018.
 */
@Entity(tableName = "device_vehicle_table")
public class DeviceVehicle {
    @PrimaryKey(autoGenerate = true)
    private int DV_ID;
    private String DV_TAG;
    private String DV_STATE;
    private String DV_EXPIRATION;
    private String DV_VIN;
    private String DV_YEAR;
    private String DV_MAKE;
    private String DV_MODEL;
    private int DV_CUID;
    private String DV_CDATE;
    private int DV_UUID;
    private String DV_UDATE;
    private String DV_TYPE;
    private String DV_PLATE_COUNTRY;


  //  public DeviceVehicle() {

   // }
   @Ignore
   public DeviceVehicle(String DV_TAG, String DV_STATE, String DV_EXPIRATION,
                        String DV_VIN, String DV_YEAR, String DV_MAKE, String DV_MODEL, int DV_CUID,
                        String DV_CDATE, int DV_UUID, String DV_UDATE, String DV_TYPE, String DV_PLATE_COUNTRY) {
       this.DV_TAG = DV_TAG;
       this.DV_STATE = DV_STATE;
       this.DV_EXPIRATION = DV_EXPIRATION;
       this.DV_VIN = DV_VIN;
       this.DV_YEAR = DV_YEAR;
       this.DV_MAKE = DV_MAKE;
       this.DV_MODEL = DV_MODEL;
       this.DV_CUID = DV_CUID;
       this.DV_CDATE = DV_CDATE;
       this.DV_UUID = DV_UUID;
       this.DV_UDATE = DV_UDATE;
       this.DV_TYPE = DV_TYPE;
       this.DV_PLATE_COUNTRY = DV_PLATE_COUNTRY;
   }

    public DeviceVehicle(int DV_ID, String DV_TAG, String DV_STATE, String DV_EXPIRATION,
                         String DV_VIN, String DV_YEAR, String DV_MAKE, String DV_MODEL, int DV_CUID,
                         String DV_CDATE, int DV_UUID, String DV_UDATE, String DV_TYPE, String DV_PLATE_COUNTRY) {
        this.DV_ID = DV_ID;
        this.DV_TAG = DV_TAG;
        this.DV_STATE = DV_STATE;
        this.DV_EXPIRATION = DV_EXPIRATION;
        this.DV_VIN = DV_VIN;
        this.DV_YEAR = DV_YEAR;
        this.DV_MAKE = DV_MAKE;
        this.DV_MODEL = DV_MODEL;
        this.DV_CUID = DV_CUID;
        this.DV_CDATE = DV_CDATE;
        this.DV_UUID = DV_UUID;
        this.DV_UDATE = DV_UDATE;
        this.DV_TYPE = DV_TYPE;
        this.DV_PLATE_COUNTRY = DV_PLATE_COUNTRY;
    }

    public int getDV_ID() {
        return DV_ID;
    }

    public void setDV_ID(int DV_ID) {
        this.DV_ID = DV_ID;
    }

    public String getDV_TAG() {
        return DV_TAG;
    }

    public void setDV_TAG(String DV_TAG) {
        this.DV_TAG = DV_TAG;
    }

    public String getDV_STATE() {
        return DV_STATE;
    }

    public void setDV_STATE(String DV_STATE) {
        this.DV_STATE = DV_STATE;
    }

    public String getDV_EXPIRATION() {
        return DV_EXPIRATION;
    }

    public void setDV_EXPIRATION(String DV_EXPIRATION) {
        this.DV_EXPIRATION = DV_EXPIRATION;
    }

    public String getDV_VIN() {
        return DV_VIN;
    }

    public void setDV_VIN(String DV_VIN) {
        this.DV_VIN = DV_VIN;
    }

    public String getDV_YEAR() {
        return DV_YEAR;
    }

    public void setDV_YEAR(String DV_YEAR) {
        this.DV_YEAR = DV_YEAR;
    }

    public String getDV_MAKE() {
        return DV_MAKE;
    }

    public void setDV_MAKE(String DV_MAKE) {
        this.DV_MAKE = DV_MAKE;
    }

    public String getDV_MODEL() {
        return DV_MODEL;
    }

    public void setDV_MODEL(String DV_MODEL) {
        this.DV_MODEL = DV_MODEL;
    }

    public int getDV_CUID() {
        return DV_CUID;
    }

    public void setDV_CUID(int DV_CUID) {
        this.DV_CUID = DV_CUID;
    }

    public String getDV_CDATE() {
        return DV_CDATE;
    }

    public void setDV_CDATE(String DV_CDATE) {
        this.DV_CDATE = DV_CDATE;
    }

    public int getDV_UUID() {
        return DV_UUID;
    }

    public void setDV_UUID(int DV_UUID) {
        this.DV_UUID = DV_UUID;
    }

    public String getDV_UDATE() {
        return DV_UDATE;
    }

    public void setDV_UDATE(String DV_UDATE) {
        this.DV_UDATE = DV_UDATE;
    }

    public String getDV_TYPE() {
        return DV_TYPE;
    }

    public void setDV_TYPE(String DV_TYPE) {
        this.DV_TYPE = DV_TYPE;
    }

    public String getDV_PLATE_COUNTRY() {
        return DV_PLATE_COUNTRY;
    }

    public void setDV_PLATE_COUNTRY(String DV_PLATE_COUNTRY) {
        this.DV_PLATE_COUNTRY = DV_PLATE_COUNTRY;
    }
}
