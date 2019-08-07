package com.weston.study.boot.netty.web.starter.netty.handler;

import com.weston.study.boot.netty.web.starter.dto.Response;
import com.weston.study.boot.netty.web.starter.netty.http.NettyHttpRequest;

public interface IFunctionHandler<T> {
    Response<T> execute(NettyHttpRequest request);
}

