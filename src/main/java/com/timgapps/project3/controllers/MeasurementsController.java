package com.timgapps.project3.controllers;

import com.timgapps.project3.dto.MeasurementDTO;
import com.timgapps.project3.dto.MeasurementsResponse;
import com.timgapps.project3.models.Measurement;
import com.timgapps.project3.services.MeasurementService;
import com.timgapps.project3.util.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

import static com.timgapps.project3.util.ErrorsUtil.returnErrorsToClient;

@Controller
@RequestMapping("/measurements")
public class MeasurementsController {

    private MeasurementService measurementService;
    private final MeasurementValidator measurementValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsController(MeasurementService measurementService, MeasurementValidator measurementValidator, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.measurementValidator = measurementValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    // хорошим тоном является оборачивать список в какой-то внешний объект
    // можно было бы сделать так:
//    public List<MeasurementDTO> getMeasurements() {
//        return measurementService.findAll().stream().map(this::convertToMeasurementDTO)
//                .collect(Collectors.toList());
//    }

    // но обернем список в внешний объект
    public MeasurementsResponse getMeasurements() {
        // обычно список из элементов оборачивается в один объект для пересылки
        return new MeasurementsResponse(measurementService.findAll().stream().map(this::convertToMeasurementDTO)
                .collect(Collectors.toList()));
    }

    // метод возвращает кол-во дождливых дней из БД
    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount() {
        return measurementService.findAll().stream().filter(Measurement::isRaining).count();
    }

    @PostMapping("/add")
    private ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
        // Получаем параметр с помощью аннотации @RequestBody, когда мы пришлем JSON в этот метод контроллера
        // @RequestBody автоматически сконвертирует его в объект класса MeasurementDTO
        // аннотация @Valid будет проверять на валидность наши данные measurement по аннотациям проверки в DTO

        Measurement measurementToAdd = convertToMeasurement(measurementDTO);
        measurementValidator.validate(measurementToAdd, bindingResult);
        // если в bindingResult есть какие-то ошибки, значит клиент прислал на сервер какие-то невалидные данные
        // и выкинем исключение
        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }
        //  теперь конвертируем DTO в модель
        measurementService.addMeasurement(measurementToAdd); // добавляем новое измерение в таблицу
        return ResponseEntity.ok(HttpStatus.OK);        // возвращаем клиенту статус ОК
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        // modelMapper найдет все поля, которые совпадают по названию (например поле "name" совпадает с
        // полем "name" в модели
        return modelMapper.map(measurementDTO, Measurement.class);  // маппим measurementDTO в модель Measurement
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    @ExceptionHandler
    // метод обрабатывает исключение(ловит)
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementException e) {
        // если исключение выброшено,
        // создаем наш response(объект, который мы хотим вернуть человеку (пользователю)
        MeasurementErrorResponse measurementErrorResponse = new MeasurementErrorResponse(
                "Measurement with this name wasn't found!",
                System.currentTimeMillis()
        );

        // В Http ответе тело ответа(response) и статус в загаловке
        return new ResponseEntity<>(measurementErrorResponse, HttpStatus.NOT_FOUND); // NOT_FOUND - 404 статус
    }
}
