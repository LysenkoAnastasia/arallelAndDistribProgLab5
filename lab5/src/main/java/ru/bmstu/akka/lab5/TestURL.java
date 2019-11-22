package ru.bmstu.akka.lab5;

import java.io.Serializable;

public class TestURL implements Serializable {
    private String url;
    private Integer count;

    public TestURL(String url, Integer count) {
        this.url = url;
        this.count = count;
    }

    public String getUrl() {
        return url;
    }

    public Integer getCount() {
        return count;
    }
    
}

