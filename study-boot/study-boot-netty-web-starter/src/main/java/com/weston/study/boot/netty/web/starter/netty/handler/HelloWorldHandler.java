package com.weston.study.boot.netty.web.starter.netty.handler;

import com.weston.study.boot.netty.web.starter.dto.Response;
import com.weston.study.boot.netty.web.starter.netty.annotation.NettyHttpHandler;
import com.weston.study.boot.netty.web.starter.netty.http.NettyHttpRequest;

@NettyHttpHandler(path = "/hello/world")
public class HelloWorldHandler implements IFunctionHandler<String> {

    @Override
    public Response<String> execute(NettyHttpRequest request) {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Response.ok("Hello World");
    }
}
