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

}
