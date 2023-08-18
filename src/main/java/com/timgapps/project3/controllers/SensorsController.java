package com.timgapps.project3.controllers;

import com.timgapps.project3.dto.SensorDTO;
import com.timgapps.project3.models.Sensor;
import com.timgapps.project3.services.SensorService;
import com.timgapps.project3.util.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.timgapps.project3.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorsController(SensorService sensorService, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO,
                                             BindingResult bindingResult) {
        // помечаем параметр с помощью аннотации @RequestBody, когда мы пришлем JSON в этот метод контроллера
        // @RequestBody автоматически сконвертирует его в объект класса sensorDTO
        // аннотация @Valid будет проверять на валидность наш сенсор
        // по аннотации проверки в DTO (SensorDTO)

        Sensor sensorToAdd = convertToSensor(sensorDTO);

        // валидируем(используем Spring validator), т.к. по условию не должно быть сенсоров с одинаковым
        // именем.
        // Мы хотим сразу отправлять ошибку, если клиент пытается добавить сенсор, который существует в таблице сенсоров
        sensorValidator.validate(sensorToAdd, bindingResult);

        // если в bindingResult есть какие-то ошибки, значит клиент прислал нам какой-то невалидный сенсор
        // и выкинем исключение (они есть либо при получении от клиента, либо при валидации в методе validate()
        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }
        // теперь конвертируем DTO в модель нашей сущности Sensor
        sensorService.register(sensorToAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        // modelMapper найдет все поля, которые совпадают по названию (например поле "name" оно совпадет с
        // полем "name" модели)
        return modelMapper.map(sensorDTO, Sensor.class);   // маппим SensorDTO в модель Sensor
        // ModelMapper берет на себя полностью маппинг между DTO и моделью
    }

    @ExceptionHandler
    ResponseEntity<MeasurementErrorResponse> handleException(MeasurementException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                // в ErrorResponse передается сообщение из нашего исключения MeasurementException
                e.getMessage(),
                System.currentTimeMillis()  // и текущее время в миллисекундах
        );
        // этот респонс здесь конструируется и возвращается обратно клиенту, со статусом "BAD_REQUEST" (400)
        // в HTTP ответе тело ответа (response) и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    // метод обрабатывает исключение
    private ResponseEntity<MeasurementErrorResponse> handleException(SensorNotFoundException e) {
        // создаем наш response(объект, который мы хотим вернуть человеку (пользователю)
        MeasurementErrorResponse measurementErrorResponse = new MeasurementErrorResponse(
                "Sensor with this name wasn't found!",
                System.currentTimeMillis()
        );

        // В Http ответе тело ответа(response) и статус в загаловке
        return new ResponseEntity<>(measurementErrorResponse, HttpStatus.NOT_FOUND); // NOT_FOUND - 404 статус
    }
}

