package com.example.btl.model;

import java.util.List;

public class Weather {

    private List<WeatherItem> weather;
    private Wind wind;
    private Sys sys;
    private Main main;
    private Coord coord;
    private String name;
    private Integer dt;

    public Weather() {
    }

    public Weather(List<WeatherItem> weather, Wind wind, Sys sys, Main main, Coord coord, String name, Integer dt) {
        this.weather = weather;
        this.wind = wind;
        this.sys = sys;
        this.main = main;
        this.coord = coord;
        this.name = name;
        this.dt = dt;
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

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }
}
