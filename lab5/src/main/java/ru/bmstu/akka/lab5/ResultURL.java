package ru.bmstu.akka.lab5;

import java.io.Serializable;

public class ResultURL implements Serializable {
    private Integer time;
    private Integer count;

    public ResultURL(Integer time, Integer count) {
        this.time = time;
        this.count = count;
    }

    public Integer getTime() {
        return time;
    }

    public Integer getCount() {
        return count;
    }
}
