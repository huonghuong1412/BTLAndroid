package com.example.btl.model;

import java.util.List;

public class WeatherList {
    private List<WeatherDay> list;
    private City city;

    public WeatherList() {
    }

    public WeatherList(List<WeatherDay> list, City city) {
        this.list = list;
        this.city = city;
    }

    public List<WeatherDay> getList() {
        return list;
    }

    public void setList(List<WeatherDay> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
