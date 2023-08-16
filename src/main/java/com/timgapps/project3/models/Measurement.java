package com.timgapps.project3.models;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "measurement")
public class Measurement {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "raining")
    @NotEmpty
    private boolean raining;

    @Column(name = "value")
    @Size(min = -100, max = 100, message = "Value should be between -100 and 100")
    @NotEmpty(message = "Should not be empty")
    private double value;

    @Column(name = "measurement_date_time")
    private LocalDateTime dateTime;

    @ManyToOne
    @NotEmpty(message = "Should not be empty")
    private Sensor sensor;

    public Measurement() {
    }

    public Measurement(boolean raining, double value, Sensor sensor) {
        this.raining = raining;
        this.value = value;
        this.sensor = sensor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
