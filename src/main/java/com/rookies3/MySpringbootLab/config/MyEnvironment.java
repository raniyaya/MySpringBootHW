package com.rookies3.MySpringbootLab.config;

public class MyEnvironment {
    private String mode;

    public MyEnvironment(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
}