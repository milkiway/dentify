package com.junction.healthtech.models;

public class TimelineItem {
    public TimelineItem(String date, String title, String info) {
        this.date = date;
        this.title = title;
        this.info = info;
    }

    private String date;
    private String title;
    private String info;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
