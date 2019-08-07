package com.weston.study.boot.netty.web.starter.netty.exception;

public class IllegalMethodNotAllowedException extends Exception {
    public IllegalMethodNotAllowedException() {
        super("METHOD NOT ALLOWED");
    }
}

