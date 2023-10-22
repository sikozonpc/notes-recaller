package com.tiagotaquelim.kindlerecaller.pojos;

public class LocationPojo {
    private String url;
    private int value;

    public LocationPojo(String url, int value) {
        this.url = url;
        this.value = value;
    }

    public LocationPojo() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
