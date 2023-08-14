package com.timgapps.project3.controllers;

import com.timgapps.project3.dto.SensorDTO;
import com.timgapps.project3.models.Sensor;
import com.timgapps.project3.services.SensorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private final SensorService sensorService;

    private final ModelMapper modelMapper;

    @Autowired
    public SensorsController(SensorService sensorService, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody SensorDTO sensorDTO,
                                             BindingResult bindingResult) {
        // помечаем параметр с помощью аннотации @RequestBody, когда мы пришлем JSON в этот метод контроллера
        // @RequestBody автоматически сконвертирует его в объект класса Person
        // аннотация @Valid будет проверять на валидность наш сенсор
        // по аннотации проверки в модели (Person)

        // если в bindingResult есть какие-то ошибки, значит клиент прислал нам какой-то невалидный сенсор
        // и выкинем исключение

        // TODO throw Exception

        // теперь конвертируем DTO в модель нашей сущности Sensor
        sensorService.save(convertToSensor(sensorDTO));
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        // modelMapper найдет все поля, которые совпадают по названию (например поле "name" оно совпадет с
        // полем "name" модели)
        return modelMapper.map(sensorDTO, Sensor.class);   // маппим SensorDTO в модель Sensor
        // ModelMapper берет на себя полностью маппинг между DTO и моделью
    }
}

