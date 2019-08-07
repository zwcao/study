package com.weston.study.boot.netty.web.starter;

public interface IFunctionHandler<T> {
    Response<T> execute(NettyHttpRequest request);
}

