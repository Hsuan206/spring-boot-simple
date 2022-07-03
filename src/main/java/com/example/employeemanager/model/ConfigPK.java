package com.example.employeemanager.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ConfigPK implements Serializable {
    @Column(name = "PHASE_ID")
    private String phaseId;
    @Column(name = "LOC_ID")
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

