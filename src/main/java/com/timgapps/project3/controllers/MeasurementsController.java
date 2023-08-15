package com.timgapps.project3.controllers;

import com.timgapps.project3.dto.MeasurementDTO;
import com.timgapps.project3.models.Measurement;
import com.timgapps.project3.services.MeasurementService;
import com.timgapps.project3.util.NotCreatedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementService measurementService;

    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsController(MeasurementService measurementService, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
    }


    private ResponseEntity<HttpStatus> add(@RequestBody MeasurementDTO measurementDTO, BindingResult bindingResult) {
        // Получаем параметр с помощью аннотации @RequestBody, когда мы пришлем JSON в этот метод контроллера
        // @RequestBody автоматически сконвертирует его в объект класса MeasurementDTO
        // аннотация @Valid будет проверять на валидность наши данные measurement по аннотациям проверки в DTO

        // если в bindingResult есть какие-то ошибки, значит клиент прислал на сервер какие-то невалидные данные
        // и выкинем исключение
        if (bindingResult.hasErrors()) {
            // здесь ошибки валидации совместим в одну большую строку
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ")
                        .append(";");
            }
            // теперь когда мы подготовили сообщение об ошибке мы должны выбросить исклчение
            throw new NotCreatedException(errorMessage.toString());
        }
        //  теперь конвертируем DTO в модель
        measurementService.save(convertToMeasurement(measurementDTO));

        return ResponseEntity.ok(HttpStatus.OK);

    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        // modelMapper найдет все поля, которые совпадают по названию (например поле "name" совпадает с
        // полем "name" в модели
        return modelMapper.map(measurementDTO, Measurement.class);  // маппим measurementDTO в модель Measurement
    }
}
