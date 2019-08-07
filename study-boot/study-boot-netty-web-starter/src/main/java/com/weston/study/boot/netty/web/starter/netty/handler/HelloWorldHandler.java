package com.weston.study.boot.netty.web.starter.netty.handler;

import com.weston.study.boot.netty.web.starter.dto.Response;
import com.weston.study.boot.netty.web.starter.netty.annotation.NettyHttpHandler;
import com.weston.study.boot.netty.web.starter.netty.http.NettyHttpRequest;

@NettyHttpHandler(path = "/hello/world")
public class HelloWorldHandler implements IFunctionHandler<String> {

    @Override
    public Response<String> execute(NettyHttpRequest request) {
        return Response.ok("Hello World");
    }
}
