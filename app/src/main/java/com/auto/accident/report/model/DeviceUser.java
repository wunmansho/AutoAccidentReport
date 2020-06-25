package com.auto.accident.report.model;

/**
 * Created by myron on 1/26/2018.
 */

public class DeviceUser {
    private int DU_ID;
    private String DU_FNAME;
    private String DU_MI;
    private String DU_LNAME;
    private String DU_LICENSE;
    private String DU_LST;
    private String DU_ADDR1;
    private String DU_ADDR2;
    private String DU_CITY;
    private String DU_ST;
    private String DU_ZIP;
    private String DU_EMAIL;
    private String DU_PHON1;
    private String DU_PHON2;
    private String DU_PHON3;
    private String DU_PTYPE;
    private String DU_CNAM01;
    private String DU_PNUM01;
    private String DU_CNAM02;
    private String DU_PNUM02;
    private String DU_CNAM03;
    private String DU_PNUM03;
    private String DU_COMP;
    private int DU_CUID;
    private String DU_CDATE;
    private int DU_UUID;
    private String DU_UDATE;
    private String DU_LICENSE_COUNTRY;
    private String DU_RESIDENT_COUNTRY;
    private String DU_PHON1_COUNTRY;
    private String DU_PHON2_COUNTRY;
    private String DU_PHON3_COUNTRY;


    // this must be here to make this Dao Statement work DeviceUser deviceuser = new DeviceUser();
    public DeviceUser() {

    }
    // Result of generate Constructor


    public DeviceUser(int DU_ID, String DU_FNAME, String DU_MI, String DU_LNAME, String DU_LICENSE,
                      String DU_LST, String DU_ADDR1, String DU_ADDR2, String DU_CITY, String DU_ST,
                      String DU_ZIP, String DU_EMAIL, String DU_PHON1, String DU_PHON2, String DU_PHON3,
                      String DU_PTYPE, String DU_CNAM01, String DU_PNUM01, String DU_CNAM02, String DU_PNUM02,
                      String DU_CNAM03, String DU_PNUM03, String DU_COMP, int DU_CUID, String DU_CDATE,
                      int DU_UUID, String DU_UDATE, String DU_LICENSE_COUNTRY, String DU_RESIDENT_COUNTRY,
                      String DU_PHON1_COUNTRY, String DU_PHON2_COUNTRY, String DU_PHON3_COUNTRY) {
        this.DU_ID = DU_ID;
        this.DU_FNAME = DU_FNAME;
        this.DU_MI = DU_MI;
        this.DU_LNAME = DU_LNAME;
        this.DU_LICENSE = DU_LICENSE;
        this.DU_LST = DU_LST;
        this.DU_ADDR1 = DU_ADDR1;
        this.DU_ADDR2 = DU_ADDR2;
        this.DU_CITY = DU_CITY;
        this.DU_ST = DU_ST;
        this.DU_ZIP = DU_ZIP;
        this.DU_EMAIL = DU_EMAIL;
        this.DU_PHON1 = DU_PHON1;
        this.DU_PHON2 = DU_PHON2;
        this.DU_PHON3 = DU_PHON3;
        this.DU_PTYPE = DU_PTYPE;
        this.DU_CNAM01 = DU_CNAM01;
        this.DU_PNUM01 = DU_PNUM01;
        this.DU_CNAM02 = DU_CNAM02;
        this.DU_PNUM02 = DU_PNUM02;
        this.DU_CNAM03 = DU_CNAM03;
        this.DU_PNUM03 = DU_PNUM03;
        this.DU_COMP = DU_COMP;
        this.DU_CUID = DU_CUID;
        this.DU_CDATE = DU_CDATE;
        this.DU_UUID = DU_UUID;
        this.DU_UDATE = DU_UDATE;
        this.DU_LICENSE_COUNTRY = DU_LICENSE_COUNTRY;
        this.DU_RESIDENT_COUNTRY = DU_RESIDENT_COUNTRY;
        this.DU_PHON1_COUNTRY = DU_PHON1_COUNTRY;
        this.DU_PHON2_COUNTRY = DU_PHON2_COUNTRY;
        this.DU_PHON3_COUNTRY = DU_PHON3_COUNTRY;
    }

    public int getDU_ID() {
        return DU_ID;
    }

    public void setDU_ID(int DU_ID) {
        this.DU_ID = DU_ID;
    }

    public String getDU_FNAME() {
        return DU_FNAME;
    }

    public void setDU_FNAME(String DU_FNAME) {
        this.DU_FNAME = DU_FNAME;
    }

    public String getDU_MI() {
        return DU_MI;
    }

    public void setDU_MI(String DU_MI) {
        this.DU_MI = DU_MI;
    }

    public String getDU_LNAME() {
        return DU_LNAME;
    }

    public void setDU_LNAME(String DU_LNAME) {
        this.DU_LNAME = DU_LNAME;
    }

    public String getDU_LICENSE() {
        return DU_LICENSE;
    }

    public void setDU_LICENSE(String DU_LICENSE) {
        this.DU_LICENSE = DU_LICENSE;
    }

    public String getDU_LST() {
        return DU_LST;
    }

    public void setDU_LST(String DU_LST) {
        this.DU_LST = DU_LST;
    }

    public String getDU_ADDR1() {
        return DU_ADDR1;
    }

    public void setDU_ADDR1(String DU_ADDR1) {
        this.DU_ADDR1 = DU_ADDR1;
    }

    public String getDU_ADDR2() {
        return DU_ADDR2;
    }

    public void setDU_ADDR2(String DU_ADDR2) {
        this.DU_ADDR2 = DU_ADDR2;
    }

    public String getDU_CITY() {
        return DU_CITY;
    }

    public void setDU_CITY(String DU_CITY) {
        this.DU_CITY = DU_CITY;
    }

    public String getDU_ST() {
        return DU_ST;
    }

    public void setDU_ST(String DU_ST) {
        this.DU_ST = DU_ST;
    }

    public String getDU_ZIP() {
        return DU_ZIP;
    }

    public void setDU_ZIP(String DU_ZIP) {
        this.DU_ZIP = DU_ZIP;
    }

    public String getDU_EMAIL() {
        return DU_EMAIL;
    }

    public void setDU_EMAIL(String DU_EMAIL) {
        this.DU_EMAIL = DU_EMAIL;
    }

    public String getDU_PHON1() {
        return DU_PHON1;
    }

    public void setDU_PHON1(String DU_PHON1) {
        this.DU_PHON1 = DU_PHON1;
    }

    public String getDU_PHON2() {
        return DU_PHON2;
    }

    public void setDU_PHON2(String DU_PHON2) {
        this.DU_PHON2 = DU_PHON2;
    }

    public String getDU_PHON3() {
        return DU_PHON3;
    }

    public void setDU_PHON3(String DU_PHON3) {
        this.DU_PHON3 = DU_PHON3;
    }

    public String getDU_PTYPE() {
        return DU_PTYPE;
    }

    public void setDU_PTYPE(String DU_PTYPE) {
        this.DU_PTYPE = DU_PTYPE;
    }

    public String getDU_CNAM01() {
        return DU_CNAM01;
    }

    public void setDU_CNAM01(String DU_CNAM01) {
        this.DU_CNAM01 = DU_CNAM01;
    }

    public String getDU_PNUM01() {
        return DU_PNUM01;
    }

    public void setDU_PNUM01(String DU_PNUM01) {
        this.DU_PNUM01 = DU_PNUM01;
    }

    public String getDU_CNAM02() {
        return DU_CNAM02;
    }

    public void setDU_CNAM02(String DU_CNAM02) {
        this.DU_CNAM02 = DU_CNAM02;
    }

    public String getDU_PNUM02() {
        return DU_PNUM02;
    }

    public void setDU_PNUM02(String DU_PNUM02) {
        this.DU_PNUM02 = DU_PNUM02;
    }

    public String getDU_CNAM03() {
        return DU_CNAM03;
    }

    public void setDU_CNAM03(String DU_CNAM03) {
        this.DU_CNAM03 = DU_CNAM03;
    }

    public String getDU_PNUM03() {
        return DU_PNUM03;
    }

    public void setDU_PNUM03(String DU_PNUM03) {
        this.DU_PNUM03 = DU_PNUM03;
    }

    public String getDU_COMP() {
        return DU_COMP;
    }

    public void setDU_COMP(String DU_COMP) {
        this.DU_COMP = DU_COMP;
    }

    public int getDU_CUID() {
        return DU_CUID;
    }

    public void setDU_CUID(int DU_CUID) {
        this.DU_CUID = DU_CUID;
    }

    public String getDU_CDATE() {
        return DU_CDATE;
    }

    public void setDU_CDATE(String DU_CDATE) {
        this.DU_CDATE = DU_CDATE;
    }

    public int getDU_UUID() {
        return DU_UUID;
    }

    public void setDU_UUID(int DU_UUID) {
        this.DU_UUID = DU_UUID;
    }

    public String getDU_UDATE() {
        return DU_UDATE;
    }

    public void setDU_UDATE(String DU_UDATE) {
        this.DU_UDATE = DU_UDATE;
    }

    public String getDU_LICENSE_COUNTRY() {
        return DU_LICENSE_COUNTRY;
    }

    public void setDU_LICENSE_COUNTRY(String DU_LICENSE_COUNTRY) {
        this.DU_LICENSE_COUNTRY = DU_LICENSE_COUNTRY;
    }

    public String getDU_RESIDENT_COUNTRY() {
        return DU_RESIDENT_COUNTRY;
    }

    public void setDU_RESIDENT_COUNTRY(String DU_RESIDENT_COUNTRY) {
        this.DU_RESIDENT_COUNTRY = DU_RESIDENT_COUNTRY;
    }

    public String getDU_PHON1_COUNTRY() {
        return DU_PHON1_COUNTRY;
    }

    public void setDU_PHON1_COUNTRY(String DU_PHON1_COUNTRY) {
        this.DU_PHON1_COUNTRY = DU_PHON1_COUNTRY;
    }

    public String getDU_PHON2_COUNTRY() {
        return DU_PHON2_COUNTRY;
    }

    public void setDU_PHON2_COUNTRY(String DU_PHON2_COUNTRY) {
        this.DU_PHON2_COUNTRY = DU_PHON2_COUNTRY;
    }

    public String getDU_PHON3_COUNTRY() {
        return DU_PHON3_COUNTRY;
    }

    public void setDU_PHON3_COUNTRY(String DU_PHON3_COUNTRY) {
        this.DU_PHON3_COUNTRY = DU_PHON3_COUNTRY;
    }
}
