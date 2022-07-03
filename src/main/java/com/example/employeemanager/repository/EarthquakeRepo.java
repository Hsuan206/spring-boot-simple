package com.example.employeemanager.repository;

import com.example.employeemanager.model.Earthquake;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EarthquakeRepo extends MongoRepository<Earthquake, EarthquakeRepo> {

}
