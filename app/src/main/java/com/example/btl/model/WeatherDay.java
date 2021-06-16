package com.example.btl.model;

import java.util.List;

public class WeatherDay {
    private Integer dt;
    private Temp temp;
    private List<WeatherItem> weather;

    public WeatherDay() {
    }

    public WeatherDay(Integer dt, Temp temp, List<WeatherItem> weather) {
        this.dt = dt;
        this.temp = temp;
        this.weather = weather;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public List<WeatherItem> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherItem> weather) {
        this.weather = weather;
    }
}
