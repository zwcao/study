package com.weston.study.core.common.callback;

@FunctionalInterface
public interface DoFilter<T> {

    boolean filter(T t);
}
