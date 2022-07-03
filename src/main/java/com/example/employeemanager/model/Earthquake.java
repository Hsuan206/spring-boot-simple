package com.example.employeemanager.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Document
public class Earthquake {
    @Id
    private EarthquakePK id;
    private String event;
    private String sentTime;
    private String description;
    private String originTime;
    private BigDecimal epicenterLon;
    private BigDecimal epicenterLat;
    private BigDecimal depth;
    private String magnitudeType;
    private BigDecimal magnitudeValue;

}
