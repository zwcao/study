package com.weston.study.boot.netty.web.starter.netty.exception;

public class IllegalPathNotFoundException extends Exception {
    public IllegalPathNotFoundException() {
        super("PATH NOT FOUND");
    }
}

