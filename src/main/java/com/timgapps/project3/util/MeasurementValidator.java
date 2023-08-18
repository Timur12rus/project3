package com.timgapps.project3.util;

import com.timgapps.project3.models.Measurement;
import com.timgapps.project3.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Measurement.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        // валидируем, что такого сенсора нет в базе данных
        Measurement measurement = (Measurement) o;

        // получаем сенсор у измерения, если его у него нет, то сразу выходим
        if (measurement.getSensor() == null) {
            return;
        }

        // если сенсор есть у измерения, то
        if (sensorService.findByName(measurement.getSensor().getName()).isEmpty()) {
            // если такого сенсора нет в таблице БД, то выдаем ошибку
            errors.rejectValue("sensor", "Нет зарегистрированного сенсора с таким именем! ");
        }
    }
}
