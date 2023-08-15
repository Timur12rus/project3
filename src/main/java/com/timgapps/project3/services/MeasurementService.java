package com.timgapps.project3.services;

import com.timgapps.project3.models.Measurement;
import com.timgapps.project3.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MeasurementService {
    private MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    @Transactional
    public void save(Measurement measurement) {
        enrichMeasurement(measurement);
    }

    private void enrichMeasurement(Measurement measurement) {
        // TODO enrichMeasurement
//        measurement
    }
}
