package com.timgapps.project3.services;

import com.timgapps.project3.models.Sensor;
import com.timgapps.project3.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    // метод будет принимать из контроллера от клиента объект класса Sensor и будет сохранять его в базу данных
    @Transactional
    public void save(Sensor sensor) {
//        enrichSensor(sensor);
        sensorRepository.save(sensor);
    }

    private void enrichSensor(Sensor sensor) {
        // на самом сервере добавляются поля
        // TODO enrichMethod for sensor
    }
}
