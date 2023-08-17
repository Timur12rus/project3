package com.timgapps.project3.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "sensor")
public class Sensor implements Serializable {
    // реализуем интерфейс Serializable, потому что в классе Measurement, в котором мы ссылаемся на Sensor
    // мы в качестве ключа у Sensor'а используем поле name, т.е. мы выстраиваем связь "ОДИН КО МНОГИМ" не на
    // основании первичного ключа у Sensor'а (поле id), а на основании поля "name", нечислового значения
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotEmpty(message = "Название не должно быть пустым!")
    @Size(min = 3, max = 30, message = "Название сенсора должно быть от 3 до 30 символов")
    private String name;

    public Sensor() {
    }

    public Sensor(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
