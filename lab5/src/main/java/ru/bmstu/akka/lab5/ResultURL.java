package ru.bmstu.akka.lab5;

import java.io.Serializable;

public class ResultURL implements Serializable {
    private String result;
    private Integer count;

    public ResultURL(String result, Integer count) {
        this.result = result;
        this.count = count;
    }

    public String getResult() {
        return result;
    }

    public Integer getCount() {
        return count;
    }
}
