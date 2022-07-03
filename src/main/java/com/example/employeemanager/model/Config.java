package com.example.employeemanager.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Config implements Serializable {

    @EmbeddedId
    private ConfigPK id;
    @Column(name = "MAT_ID")
    private String matId;
    @Column(name = "BATCH_ID")
    private String batchId;

    public Config(ConfigPK id, String matId, String batchId) {
        this.id = id;
        this.matId = matId;
        this.batchId = batchId;
    }


    public Config() {

    }

    public ConfigPK getId() {
        return id;
    }

    public void setId(ConfigPK id) {
        this.id = id;
    }
    public String getMatId() {
        return matId;
    }

    public void setMatId(String matId) {
        this.matId = matId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
}
