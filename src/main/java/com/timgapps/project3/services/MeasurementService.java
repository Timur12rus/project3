package com.timgapps.project3.services;

import com.timgapps.project3.models.Measurement;
import com.timgapps.project3.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    @Transactional
    public void addMeasurement(Measurement measurement) {
        // перед тем как добавить это не полностью заполненое измерение, нужно
        // добавить в него текущую дату и время
        // и положить в этот объект объект Sensor
        enrichMeasurement(measurement);
        measurementRepository.save(measurement); // сохраняем объект в БД с помощью репозитория
    }

    private void enrichMeasurement(Measurement measurement) {
        // мы должны сами найти сенсор из БД по имени и вставить объект из Hibernate persistence context'а
        // потому что какой-то непонятный объект, который лежит в объекте measurement он будет пытаться сохраняться
        // в БД и будет ошибка, чтобы ошибки не было, мы должны название, которое пришло к нам в JSON'е
        // найти сенсор с этим названием и связать с объектом, который лежит в persistence context'е hibernat'а
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()).get());

        measurement.setDateTime(LocalDateTime.now());
    }
}
