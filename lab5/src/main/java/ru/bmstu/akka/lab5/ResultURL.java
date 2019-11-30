package ru.bmstu.akka.lab5;

import java.io.Serializable;

public class ResultURL implements Serializable {
    private Long time;
    private TestURL test;

    public ResultURL(TestURL test, Long time) {
        this.time = time;
        this.test = test;
    }

    public Long getTime() {
        return time;
    }

    public TestURL getTest() {
        return test;
    }
}
