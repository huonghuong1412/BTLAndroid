package com.example.btl.model;

import com.google.gson.annotations.SerializedName;

public class News {

    @SerializedName("title")
    private String title;
    @SerializedName("tag")
    private String tag;
    @SerializedName("time")
    private String time;
    @SerializedName("img")
    private String img;
    @SerializedName("link")
    private String link;

    public News() {
    }

    public News(String title, String tag, String time, String img, String link) {
        this.title = title;
        this.tag = tag;
        this.time = time;
        this.img = img;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
