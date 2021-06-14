package com.example.btl.model;

public class WeatherItem {
    private String main;
    private String description;

    public WeatherItem() {
    }

    public WeatherItem(String main, String description) {
        this.main = main;
        this.description = description;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
