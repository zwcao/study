package com.weston.study.core.common.helper.model;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

/**
 * 公共结构体
 */
public final class Struct {

    /**
     * 二元组
     *
     * @param <K>
     * @param <V>
     */
    public static class Pair<K, V> implements Serializable {
        private K k;
        private V t;
    }

    /**
     * 三元组
     */
    @Getter
    @Setter
    public static class Tuple3<A, B, C> implements Serializable {
        private A one;
        private B two;
        private C three;
    }

    /**
     * 四元组
     */
    @Getter
    @Setter
    public static class Tuple4<A, B, C, D> implements Serializable {
        private A one;
        private B two;
        private C three;
        private D four;
    }

}

