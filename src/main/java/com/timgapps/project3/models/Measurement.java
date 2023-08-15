package com.timgapps.project3.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Measurement")
public class Measurement {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "raining")
    private boolean isRaining;

    @Column(name = "measurement_date_time")
    private LocalDateTime dateTime;

    @ManyToOne
    private Sensor sensor;

}
