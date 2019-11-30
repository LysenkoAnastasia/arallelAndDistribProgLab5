package ru.bmstu.akka.lab5;

import java.io.Serializable;

public class TestURL implements Serializable {
    private String url;
    private Long count;

    public TestURL(String url, Long count) {
        this.url = url;
        this.count = count;
    }

    public String getUrl() {
        return url;
    }

    public Long getCount() {
        return count;
    }

}

