package com.weston.study.core.common.callback;

@FunctionalInterface
public interface OnFinish<T> {

    void finish(T t);
}
