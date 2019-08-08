package com.weston.study.core.common.callback;

@FunctionalInterface
public interface OnSuccess<T> {

    void success(T t);
}
