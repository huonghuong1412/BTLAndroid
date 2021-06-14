package com.example.btl.model;

import java.io.Serializable;

public class Tag implements Serializable {
    private Integer id;
    private String name;
    private String description;
    private String date;
    private String time;
    private Integer categoryId;
    private String categoryName;

    public Tag() {
    }

    public Tag(String name, String description, String date, String time, Integer categoryId) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.categoryId = categoryId;
    }

    public Tag(Integer id, String name, String description, String date, String time, Integer categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.categoryId = categoryId;
    }
    public Tag(Integer id, String name, String description, String date, String time, Integer categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }


    public Tag(Integer id, String name, String description, String date, String time, String categoryName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.categoryName = categoryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
