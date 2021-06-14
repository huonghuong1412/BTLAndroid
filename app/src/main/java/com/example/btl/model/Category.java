package com.example.btl.model;

import java.io.Serializable;

public class Category implements Serializable {
    private Integer id;
    private String name;
    private String description;
    private String userId;

    public Category() {
    }

    public Category(String name, String description, String userId) {
        this.name = name;
        this.description = description;
        this.userId = userId;
    }

    public Category(Integer id, String name, String description, String userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.userId = userId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
