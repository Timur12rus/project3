package com.timgapps.project3.dto;


// класс DTO (Data Transfer Object)
// специальный класс, который будет служить для общения с клиентом

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class MeasurementDTO {

    // здесь будем описывать те поля, которые будут приходить от клиента, и которые мы будем клиенту отправлять
    // добавим аннотации для проверки соответствия полей модели данным из запроса приходящего от клиента
    // DTO вообще никак не связан с базой данных, не помечен аннотацией @Entity, не помечен аннотацией @Table
    // и нет аннотации @Column
    // нет поля id, оно не приходит от клиента, ему его знать не нужно
    // id назначается сервером

    // DTO используется на уровне контроллера и мы не должны глубже заходить с DTO

    @NotEmpty
    @Size(min = -100, max = 100, message = "Value should be between -100 and 100")
    private Double value;

    @NotEmpty
    private Boolean raining;

    @NotNull
    private SensorDTO sensor;

    public boolean getRaining() {
        return raining;
    }

    public void setRaining(boolean isRaining) {
        this.raining = isRaining;
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
