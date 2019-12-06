package ru.bmstu.akka.lab5;

import java.util.Optional;

public class TestUrlMsg {
    private ResultURL resultURL;

    public TestUrlMsg(ResultURL resultURL) {
        this.resultURL = resultURL;
    }

    public Optional<ResultURL> getResultURL() {
        if (resultURL.getTime() == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(resultURL);
        //return Optional.ofNullable(resultURL);
    }
}
