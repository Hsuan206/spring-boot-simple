package com.example.employeemanager.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"phaseId", "locId","matId","batchId"})
})
public class Config implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "phaseId")
    private String phaseId;
    @Column(name = "locId")
    private String locId;
    @Column(name = "matId")
    private String matId;
    @Column(name = "batchId")
    private String batchId;

    public Config(String phaseId, String locId, String matId, String batchId) {
        this.phaseId = phaseId;
        this.locId = locId;
        this.matId = matId;
        this.batchId = batchId;
    }

    public Config() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
