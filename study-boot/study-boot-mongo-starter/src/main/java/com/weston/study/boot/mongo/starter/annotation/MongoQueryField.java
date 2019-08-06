package com.weston.study.boot.mongo.starter.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MongoQueryField {

    String value() default "";

    Op op() default Op.eq;

    /**
     * 比较器
     */
    enum Op {
        eq,
        gt,
        gte,
        lt,
        lte,
        in,
        notin
    }
}
