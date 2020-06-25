package com.auto.accident.report.model;

/**
 * Created by myron on 1/26/2018.
 */

public class InvolvedParty {
    private int IP_ID;
    private int IP_AID;
    private String IP_FNAME;
    private String IP_MI;
    private String IP_LNAME;
    private String IP_LICENSE;
    private String IP_LST;
    private String IP_ADDR1;
    private String IP_ADDR2;
    private String IP_CITY;
    private String IP_ST;
    private String IP_ZIP;
    private String IP_EMAIL;
    private String IP_PHON1;
    private String IP_PHON2;
    private String IP_PHON3;
    private String IP_PTYPE;
    private String IP_CNAM01;
    private String IP_PNUM01;
    private String IP_CNAM02;
    private String IP_PNUM02;
    private String IP_CNAM03;
    private String IP_PNUM03;
    private String IP_COMP;
    private int IP_CUID;
    private String IP_CDATE;
    private int IP_UUID;
    private String IP_UDATE;
    private String IP_LICENSE_COUNTRY;
    private String IP_RESIDENT_COUNTRY;
    private String IP_PHON1_COUNTRY;
    private String IP_PHON2_COUNTRY;
    private String IP_PHON3_COUNTRY;

    // this must be here to make this Dao Statement work InvolvedParty involvedparty = new InvolvedParty();
    public InvolvedParty() {

    }
    // Result of generate Constructor the InvolvedParty constructor

    public InvolvedParty(int IP_ID, int IP_AID, String IP_FNAME, String IP_MI, String IP_LNAME,
                         String IP_LICENSE, String IP_LST, String IP_ADDR1, String IP_ADDR2, String IP_CITY,
                         String IP_ST, String IP_ZIP, String IP_EMAIL, String IP_PHON1, String IP_PHON2,
                         String IP_PHON3, String IP_PTYPE, String IP_CNAM01, String IP_PNUM01, String IP_CNAM02,
                         String IP_PNUM02, String IP_CNAM03, String IP_PNUM03, String IP_COMP, int IP_CUID,
                         String IP_CDATE, int IP_UUID, String IP_UDATE, String IP_LICENSE_COUNTRY, String IP_RESIDENT_COUNTRY,
                         String IP_PHON1_COUNTRY, String IP_PHON2_COUNTRY, String IP_PHON3_COUNTRY) {
        this.IP_ID = IP_ID;
        this.IP_AID = IP_AID;
        this.IP_FNAME = IP_FNAME;
        this.IP_MI = IP_MI;
        this.IP_LNAME = IP_LNAME;
        this.IP_LICENSE = IP_LICENSE;
        this.IP_LST = IP_LST;
        this.IP_ADDR1 = IP_ADDR1;
        this.IP_ADDR2 = IP_ADDR2;
        this.IP_CITY = IP_CITY;
        this.IP_ST = IP_ST;
        this.IP_ZIP = IP_ZIP;
        this.IP_EMAIL = IP_EMAIL;
        this.IP_PHON1 = IP_PHON1;
        this.IP_PHON2 = IP_PHON2;
        this.IP_PHON3 = IP_PHON3;
        this.IP_PTYPE = IP_PTYPE;
        this.IP_CNAM01 = IP_CNAM01;
        this.IP_PNUM01 = IP_PNUM01;
        this.IP_CNAM02 = IP_CNAM02;
        this.IP_PNUM02 = IP_PNUM02;
        this.IP_CNAM03 = IP_CNAM03;
        this.IP_PNUM03 = IP_PNUM03;
        this.IP_COMP = IP_COMP;
        this.IP_CUID = IP_CUID;
        this.IP_CDATE = IP_CDATE;
        this.IP_UUID = IP_UUID;
        this.IP_UDATE = IP_UDATE;
        this.IP_LICENSE_COUNTRY = IP_LICENSE_COUNTRY;
        this.IP_RESIDENT_COUNTRY = IP_RESIDENT_COUNTRY;
        this.IP_PHON1_COUNTRY = IP_PHON1_COUNTRY;
        this.IP_PHON2_COUNTRY = IP_PHON2_COUNTRY;
        this.IP_PHON3_COUNTRY = IP_PHON3_COUNTRY;
    }


// Generate Setters

    public int getIP_ID() {
        return IP_ID;
    }

    public void setIP_ID(int IP_ID) {
        this.IP_ID = IP_ID;
    }

    public int getIP_AID() {
        return IP_AID;
    }

    public void setIP_AID(int IP_AID) {
        this.IP_AID = IP_AID;
    }

    public String getIP_FNAME() {
        return IP_FNAME;
    }

    public void setIP_FNAME(String IP_FNAME) {
        this.IP_FNAME = IP_FNAME;
    }

    public String getIP_MI() {
        return IP_MI;
    }

    public void setIP_MI(String IP_MI) {
        this.IP_MI = IP_MI;
    }

    public String getIP_LNAME() {
        return IP_LNAME;
    }

    public void setIP_LNAME(String IP_LNAME) {
        this.IP_LNAME = IP_LNAME;
    }

    public String getIP_LICENSE() {
        return IP_LICENSE;
    }

    public void setIP_LICENSE(String IP_LICENSE) {
        this.IP_LICENSE = IP_LICENSE;
    }

    public String getIP_LST() {
        return IP_LST;
    }

    public void setIP_LST(String IP_LST) {
        this.IP_LST = IP_LST;
    }

    public String getIP_ADDR1() {
        return IP_ADDR1;
    }

    public void setIP_ADDR1(String IP_ADDR1) {
        this.IP_ADDR1 = IP_ADDR1;
    }

    public String getIP_ADDR2() {
        return IP_ADDR2;
    }

    public void setIP_ADDR2(String IP_ADDR2) {
        this.IP_ADDR2 = IP_ADDR2;
    }

    public String getIP_CITY() {
        return IP_CITY;
    }

    public void setIP_CITY(String IP_CITY) {
        this.IP_CITY = IP_CITY;
    }

    public String getIP_ST() {
        return IP_ST;
    }

    public void setIP_ST(String IP_ST) {
        this.IP_ST = IP_ST;
    }

    public String getIP_ZIP() {
        return IP_ZIP;
    }

    public void setIP_ZIP(String IP_ZIP) {
        this.IP_ZIP = IP_ZIP;
    }

    public String getIP_EMAIL() {
        return IP_EMAIL;
    }

    public void setIP_EMAIL(String IP_EMAIL) {
        this.IP_EMAIL = IP_EMAIL;
    }

    public String getIP_PHON1() {
        return IP_PHON1;
    }

    public void setIP_PHON1(String IP_PHON1) {
        this.IP_PHON1 = IP_PHON1;
    }

    public String getIP_PHON2() {
        return IP_PHON2;
    }

    public void setIP_PHON2(String IP_PHON2) {
        this.IP_PHON2 = IP_PHON2;
    }

    public String getIP_PHON3() {
        return IP_PHON3;
    }

    public void setIP_PHON3(String IP_PHON3) {
        this.IP_PHON3 = IP_PHON3;
    }

    public String getIP_PTYPE() {
        return IP_PTYPE;
    }


    // Generate Getters

    public void setIP_PTYPE(String IP_PTYPE) {
        this.IP_PTYPE = IP_PTYPE;
    }

    public String getIP_CNAM01() {
        return IP_CNAM01;
    }

    public void setIP_CNAM01(String IP_CNAM01) {
        this.IP_CNAM01 = IP_CNAM01;
    }

    public String getIP_PNUM01() {
        return IP_PNUM01;
    }

    public void setIP_PNUM01(String IP_PNUM01) {
        this.IP_PNUM01 = IP_PNUM01;
    }

    public String getIP_CNAM02() {
        return IP_CNAM02;
    }

    public void setIP_CNAM02(String IP_CNAM02) {
        this.IP_CNAM02 = IP_CNAM02;
    }

    public String getIP_PNUM02() {
        return IP_PNUM02;
    }

    public void setIP_PNUM02(String IP_PNUM02) {
        this.IP_PNUM02 = IP_PNUM02;
    }

    public String getIP_CNAM03() {
        return IP_CNAM03;
    }

    public void setIP_CNAM03(String IP_CNAM03) {
        this.IP_CNAM03 = IP_CNAM03;
    }

    public String getIP_PNUM03() {
        return IP_PNUM03;
    }

    public void setIP_PNUM03(String IP_PNUM03) {
        this.IP_PNUM03 = IP_PNUM03;
    }

    public String getIP_COMP() {
        return IP_COMP;
    }

    public void setIP_COMP(String IP_COMP) {
        this.IP_COMP = IP_COMP;
    }

    public int getIP_CUID() {
        return IP_CUID;
    }

    public void setIP_CUID(int IP_CUID) {
        this.IP_CUID = IP_CUID;
    }

    public String getIP_CDATE() {
        return IP_CDATE;
    }

    public void setIP_CDATE(String IP_CDATE) {
        this.IP_CDATE = IP_CDATE;
    }

    public int getIP_UUID() {
        return IP_UUID;
    }

    public void setIP_UUID(int IP_UUID) {
        this.IP_UUID = IP_UUID;
    }

    public String getIP_UDATE() {
        return IP_UDATE;
    }

    public void setIP_UDATE(String IP_UDATE) {
        this.IP_UDATE = IP_UDATE;
    }

    public String getIP_LICENSE_COUNTRY() {
        return IP_LICENSE_COUNTRY;
    }

    public void setIP_LICENSE_COUNTRY(String IP_LICENSE_COUNTRY) {
        this.IP_LICENSE_COUNTRY = IP_LICENSE_COUNTRY;
    }

    public String getIP_RESIDENT_COUNTRY() {
        return IP_RESIDENT_COUNTRY;
    }

    public void setIP_RESIDENT_COUNTRY(String IP_RESIDENT_COUNTRY) {
        this.IP_RESIDENT_COUNTRY = IP_RESIDENT_COUNTRY;
    }

    public String getIP_PHON1_COUNTRY() {
        return IP_PHON1_COUNTRY;
    }

    public void setIP_PHON1_COUNTRY(String IP_PHON1_COUNTRY) {
        this.IP_PHON1_COUNTRY = IP_PHON1_COUNTRY;
    }

    public String getIP_PHON2_COUNTRY() {
        return IP_PHON2_COUNTRY;
    }

    public void setIP_PHON2_COUNTRY(String IP_PHON2_COUNTRY) {
        this.IP_PHON2_COUNTRY = IP_PHON2_COUNTRY;
    }

    public String getIP_PHON3_COUNTRY() {
        return IP_PHON3_COUNTRY;
    }

    public void setIP_PHON3_COUNTRY(String IP_PHON3_COUNTRY) {
        this.IP_PHON3_COUNTRY = IP_PHON3_COUNTRY;
    }
}
