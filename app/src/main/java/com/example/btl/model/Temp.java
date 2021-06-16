package com.example.btl.model;

public class Temp {
    private Double day, min, max;

    public Temp() {
    }

    public Temp(Double day, Double min, Double max) {
        this.day = day;
        this.min = min;
        this.max = max;
    }

    public Double getDay() {
        return day;
    }

    public void setDay(Double day) {
        this.day = day;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }
}
