package com.example.employeemanager.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ConfigPK implements Serializable {

    private String phaseId;
    private String locId;

    public ConfigPK(String phaseId, String locId) {
        this.phaseId = phaseId;
        this.locId = locId;
    }
    public ConfigPK() {

    }

    public String getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(String phaseId) {
        this.phaseId = phaseId;
    }

    public String getLocId() {
        return locId;
    }

    public void setLocId(String locId) {
        this.locId = locId;
    }

    // getter and setter ommitted
}

