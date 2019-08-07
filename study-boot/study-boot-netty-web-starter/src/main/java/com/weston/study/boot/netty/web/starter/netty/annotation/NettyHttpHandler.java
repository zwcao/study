package com.weston.study.boot.netty.web.starter.netty.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface NettyHttpHandler {
    /**
     * 请求路径
     * @return
     */
    String path() default "";

    /**
     * 支持的提交方式
     * @return
     */
    String method() default "GET";

    /**
     * path和请求路径是否需要完全匹配。 如果是PathVariable传参数，设置为false
     * @return
     */
    boolean equal() default true;
}
