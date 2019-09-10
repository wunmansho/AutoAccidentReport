package com.auto.accident.report.model;

/**
 * Created by myron on 1/29/2018.
 */

public class PersistenceObj {

    private String PERSISTENCE_KEY;
    private String PERSISTENCE_VALUE;


    // this must be here to make this Dao Statement work PersistenceObj persistenceobj = new PersistenceObj();
    public PersistenceObj() {

    }

    // Generate Constructor

    public PersistenceObj(String PERSISTENCE_KEY, String PERSISTENCE_VALUE) {
        this.PERSISTENCE_KEY = PERSISTENCE_KEY;
        this.PERSISTENCE_VALUE = PERSISTENCE_VALUE;
    }

    // Generate Setters

    public String getPERSISTENCE_KEY() {
        return PERSISTENCE_KEY;
    }

    public void setPERSISTENCE_KEY(String PERSISTENCE_KEY) {
        this.PERSISTENCE_KEY = PERSISTENCE_KEY;
    }


    // Generate Getters

    public String getPERSISTENCE_VALUE() {
        return PERSISTENCE_VALUE;
    }

    public void setPERSISTENCE_VALUE(String PERSISTENCE_VALUE) {
        this.PERSISTENCE_VALUE = PERSISTENCE_VALUE;
    }
}
