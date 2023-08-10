package com.timgapps.project3.repositories;

import com.timgapps.project3.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


// Репозиторий дает нам доступ к базе данных и дает возможность работать с базой данных
@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
}
