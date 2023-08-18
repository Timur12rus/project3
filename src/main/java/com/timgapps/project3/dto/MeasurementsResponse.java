package com.timgapps.project3.dto;

import com.timgapps.project3.services.MeasurementService;

import java.util.List;

// хорошим тоном является оборачивать список в какой-то внешний объект
public class MeasurementsResponse {
    private List<MeasurementDTO> measurements;

    public MeasurementsResponse(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }

    public List<MeasurementDTO> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }
}

