package com.timgapps.project3.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

// класс DTO (Data Transfer Object)
// специальный класс, который будет служить для общения с клиентом
public class SensorDTO {
    // Здесь будем описывать те поля, которые будут приходить от клиента, и которые мы будем клиенту отправлять
    // Добавим аннотации для проверки соответствия полей модели данным из запроса приходящего от клиента
    // DTO вообще никак не связан с базой данных, не помечен аннотацией @Entity, не помечен аннотацией @Table
    // и нет аннотации @Column
    // нет поля id, это поле не приходит от клиента, и клиенту в принципе его знать не надо
    // id назначается только на сервере

    // DTO используется на уровне контроллера и мы не должны глубже заходить с DTO

    @NotEmpty(message = "Should not be empty")
    @Size(min = 3, max = 30, message = "Name should be between 2 and 30")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
