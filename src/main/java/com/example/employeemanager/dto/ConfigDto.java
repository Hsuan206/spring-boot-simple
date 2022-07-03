package com.example.employeemanager.dto;

public class ConfigDto {
    private String batch_id;
    private String loc_id;
    private String mat_id;
    private String phase_id;

    public String getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(String batch_id) {
        this.batch_id = batch_id;
    }

    public String getLoc_id() {
        return loc_id;
    }

    public void setLoc_id(String loc_id) {
        this.loc_id = loc_id;
    }

    public String getMat_id() {
        return mat_id;
    }

    public void setMat_id(String mat_id) {
        this.mat_id = mat_id;
    }

    public String getPhase_id() {
        return phase_id;
    }

    public void setPhase_id(String phase_id) {
        this.phase_id = phase_id;
    }
}
