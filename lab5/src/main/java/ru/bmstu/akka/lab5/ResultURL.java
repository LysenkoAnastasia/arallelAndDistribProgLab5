package ru.bmstu.akka.lab5;

import java.io.Serializable;

public class ResultURL implements Serializable {
    private Integer time;
    private TestURL test;

    public ResultURL(TestURL test, Integer time) {
        this.time = time;
        this.test = test;
    }

    public Integer getTime() {
        return time;
    }

    public TestURL getTest() {
        return test;
    }
}
