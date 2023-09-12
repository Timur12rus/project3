package com.timgapps.project3.dto;


// класс DTO (Data Transfer Object)
// специальный класс, который будет служить для общения с клиентом

import javax.validation.constraints.*;


public class MeasurementDTO {

    // здесь будем описывать те поля, которые будут приходить от клиента, и которые мы будем клиенту отправлять
    // добавим аннотации для проверки соответствия полей модели данным из запроса приходящего от клиента
    // DTO вообще никак не связан с базой данных, не помечен аннотацией @Entity, не помечен аннотацией @Table
    // и нет аннотации @Column
    // нет поля id, оно не приходит от клиента, ему его знать не нужно
    // id назначается сервером

    // DTO используется на уровне контроллера и мы не должны глубже заходить с DTO

    @NotNull
//    @Size(min = -100, max = 100, message = "Value should be between -100 and 100")
    @Min(-100)
    @Max(100)
    private Double value;

    @NotNull
    private Boolean raining;

    @NotNull
    private SensorDTO sensor;

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
