package com.timgapps.project3.controllers;

import com.timgapps.project3.dto.SensorDTO;
import com.timgapps.project3.models.Sensor;
import com.timgapps.project3.services.SensorService;
import com.timgapps.project3.util.SensorErrorResponse;
import com.timgapps.project3.util.NotCreatedException;
import com.timgapps.project3.util.SensorNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        // @RequestBody автоматически сконвертирует его в объект класса sensorDTO
        // аннотация @Valid будет проверять на валидность наш сенсор
        // по аннотации проверки в DTO (SensorDTO)

        // если в bindingResult есть какие-то ошибки, значит клиент прислал нам какой-то невалидный сенсор
        // и выкинем исключение
        if (bindingResult.hasErrors()) {
            // здесь ошибки валидации мы совместим в одну большую строку
            // эту строку мы хотим отправить обратно клиенту, чтобы он посмотрел и смог исправить данные о сенсоре
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();  // добавим ошибки в List
            for (FieldError error : errors) {
                errorMessage.append(error.getField()) // на каком поле была получена ошибка
                        .append(" - ")
                        .append(";");
            }

            // теперь когда мы подготовили сообщение об ошибке
            // мы должны выбросить исключение и должны отправить сообщение с этой ошибкой
            throw new NotCreatedException(errorMessage.toString());
        }

        // теперь конвертируем DTO в модель нашей сущности Sensor
        sensorService.save(convertToSensor(sensorDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        // modelMapper найдет все поля, которые совпадают по названию (например поле "name" оно совпадет с
        // полем "name" модели)
        return modelMapper.map(sensorDTO, Sensor.class);   // маппим SensorDTO в модель Sensor
        // ModelMapper берет на себя полностью маппинг между DTO и моделью
    }

    @ExceptionHandler
    // метод обрабатывает исключение
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e) {
        // создаем наш response(объект, который мы хотим вернуть человеку (пользователю)
        SensorErrorResponse sensorErrorResponse = new SensorErrorResponse(
                "Sensor with this name wasn't found!",
                System.currentTimeMillis()
        );

        // В Http ответе тело ответа(response) и статус в загаловке
        return new ResponseEntity<>(sensorErrorResponse, HttpStatus.NOT_FOUND); // NOT_FOUND - 404 статус
    }

}

