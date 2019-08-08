package com.weston.study.core.common.callback;

@FunctionalInterface
public interface OnFailure<T> {

    void failure(T t);
}
