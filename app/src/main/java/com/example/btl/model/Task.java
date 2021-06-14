package com.example.btl.model;

import java.io.Serializable;

public class Task implements Serializable {

    private Integer id;
    private String name;
    private Integer status;
    private Integer tagId;
    private String tagName;

    public Task() {
    }

    public Task(String name, Integer status, Integer tagId) {
        this.name = name;
        this.status = status;
        this.tagId = tagId;
    }

    public Task(Integer id, String name, Integer status, Integer tagId) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.tagId = tagId;
    }

    public Task(Integer id, String name, Integer status, String tagName) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.tagName = tagName;
    }

    public Task(Integer id, String name, Integer status, Integer tagId, String tagName) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.tagId = tagId;
        this.tagName = tagName;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
