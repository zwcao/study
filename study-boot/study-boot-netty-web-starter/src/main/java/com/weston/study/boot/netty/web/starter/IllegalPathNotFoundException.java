package com.weston.study.boot.netty.web.starter;

public class IllegalPathNotFoundException extends Exception {
    public IllegalPathNotFoundException() {
        super("PATH NOT FOUND");
    }
}

