package com.weston.study.boot.netty.web.starter.configuration;

import com.weston.study.boot.netty.web.starter.netty.iohandler.FilterLogginglHandler;
import com.weston.study.boot.netty.web.starter.netty.iohandler.HttpServerHandler;
import com.weston.study.boot.netty.web.starter.netty.iohandler.InterceptorHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

import javax.annotation.Resource;
import javax.net.ssl.SSLEngine;


@Configuration
@Slf4j
public class NettyHttpServer implements ApplicationListener<ApplicationStartedEvent> {

    @Value("${server.port}")
    private int port;

    @Resource
    private InterceptorHandler interceptorHandler;

    @Resource
    private HttpServerHandler httpServerHandler;

    @Override
    public void onApplicationEvent(@NonNull ApplicationStartedEvent event) {

        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childOption(NioChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(NioChannelOption.SO_REUSEADDR, true);
        bootstrap.childOption(NioChannelOption.SO_KEEPALIVE, false);
        bootstrap.childOption(NioChannelOption.SO_RCVBUF, 2048);
        bootstrap.childOption(NioChannelOption.SO_SNDBUF, 2048);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) {
//                // 支持SSL
//                ch.pipeline().addFirst(new ChannelInboundHandlerAdapter() {
//                    @Override
//                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                        // https数据流的第一位是十六进制“16”，转换成十进制就是22
//                        if (((ByteBuf) msg).getByte(0) == 22) {
//                            SelfSignedCertificate ssc = new SelfSignedCertificate();
//                            SslContext sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
//                            SSLEngine sslEngine = sslCtx.newEngine(UnpooledByteBufAllocator.DEFAULT);
//                            // 将处理 https 的处理程序添加至 HttpServerCodec 之前
//                            ctx.pipeline().addBefore("codec", "sslHandler", new SslHandler(sslEngine));
//                        } else if (((ByteBuf) msg).getByte(0) != 22) {
//                            // 删除 sslHandler
//                            ctx.pipeline().remove("sslHandler");
//                        }
//                        ctx.pipeline().remove(this);
//                        super.channelRead(ctx, msg);
//                    }
//                });
                ch.pipeline().addLast("codec", new HttpServerCodec());
                ch.pipeline().addLast("aggregator", new HttpObjectAggregator(1024 * 1024));
                ch.pipeline().addLast("logging", new FilterLogginglHandler());
                ch.pipeline().addLast("interceptor", interceptorHandler);
                ch.pipeline().addLast("bizHandler", httpServerHandler);
            }
        })
        ;
        ChannelFuture channelFuture = bootstrap.bind(port).syncUninterruptibly().addListener(future -> {
            log.info("Netty Http Server started on port {}", port);
        });
        channelFuture.channel().closeFuture().addListener(future -> {
            log.info("Netty Http Server Start Shutdown ............");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        });
    }

}
