package com.weston.study.core.common.callback;

@FunctionalInterface
public interface OnException<T> {

    void exp(T t);
}
