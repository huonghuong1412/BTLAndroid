package com.example.btl.model;

import java.util.List;

public class Weather {

    private List<WeatherItem> weather;
    private Wind wind;
    private Sys sys;
    private Main main;
    private String name;

    public Weather() {
    }

    public Weather(List<WeatherItem> weather, Wind wind, Sys sys, Main main, String name) {
        this.weather = weather;
        this.wind = wind;
        this.sys = sys;
        this.main = main;
        this.name = name;
    }

    public List<WeatherItem> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherItem> weather) {
        this.weather = weather;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
