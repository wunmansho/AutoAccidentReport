package com.auto.accident.report.objects;

/**
 * Created by myron on 4/12/2018.
 */

public class AccidentNote {

    private int AN_ID;
    private int AN_AID;
    private int AN_IPID;
    private int AN_IVID;
    private String AN_APPATH;
    private String AN_SUBJECT;
    private String AN_NOTE;
    private int AN_CUID;
    private String AN_CDATE;
    private int AN_UUID;
    private String AN_UDATE;

    public AccidentNote() {

    }

    public AccidentNote(int AN_ID, int AN_AID, int AN_IPID, int AN_IVID, String AN_APPATH, String AN_SUBJECT, String AN_NOTE, int AN_CUID, String AN_CDATE, int AN_UUID, String AN_UDATE) {
        this.AN_ID = AN_ID;
        this.AN_AID = AN_AID;
        this.AN_IPID = AN_IPID;
        this.AN_IVID = AN_IVID;
        this.AN_APPATH = AN_APPATH;
        this.AN_SUBJECT = AN_SUBJECT;
        this.AN_NOTE = AN_NOTE;
        this.AN_CUID = AN_CUID;
        this.AN_CDATE = AN_CDATE;
        this.AN_UUID = AN_UUID;
        this.AN_UDATE = AN_UDATE;
    }

    public int getAN_ID() {
        return AN_ID;
    }

    public void setAN_ID(int AN_ID) {
        this.AN_ID = AN_ID;
    }

    public int getAN_AID() {
        return AN_AID;
    }

    public void setAN_AID(int AN_AID) {
        this.AN_AID = AN_AID;
    }

    public int getAN_IPID() {
        return AN_IPID;
    }

    public void setAN_IPID(int AN_IPID) {
        this.AN_IPID = AN_IPID;
    }

    public int getAN_IVID() {
        return AN_IVID;
    }

    public void setAN_IVID(int AN_IVID) {
        this.AN_IVID = AN_IVID;
    }

    public String getAN_APPATH() {
        return AN_APPATH;
    }

    public void setAN_APPATH(String AN_APPATH) {
        this.AN_APPATH = AN_APPATH;
    }

    public String getAN_SUBJECT() {
        return AN_SUBJECT;
    }

    public void setAN_SUBJECT(String AN_SUBJECT) {
        this.AN_SUBJECT = AN_SUBJECT;
    }

    public String getAN_NOTE() {
        return AN_NOTE;
    }

    public void setAN_NOTE(String AN_NOTE) {
        this.AN_NOTE = AN_NOTE;
    }

    public int getAN_CUID() {
        return AN_CUID;
    }

    public void setAN_CUID(int AN_CUID) {
        this.AN_CUID = AN_CUID;
    }

    public String getAN_CDATE() {
        return AN_CDATE;
    }

    public void setAN_CDATE(String AN_CDATE) {
        this.AN_CDATE = AN_CDATE;
    }

    public int getAN_UUID() {
        return AN_UUID;
    }

    public void setAN_UUID(int AN_UUID) {
        this.AN_UUID = AN_UUID;
    }

    public String getAN_UDATE() {
        return AN_UDATE;
    }

    public void setAN_UDATE(String AN_UDATE) {
        this.AN_UDATE = AN_UDATE;
    }
}
