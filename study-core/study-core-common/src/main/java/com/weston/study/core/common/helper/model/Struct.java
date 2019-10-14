package com.weston.study.core.common.helper.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

/**
 * 公共结构体
 */
public final class Struct {

    /**
     * 元数据
     */
    @Getter
    @Setter
    public static class Tuple implements Serializable {
        private static Object[] array;

        protected Tuple build(Object... values) {
            if (values == null || values.length == 0) {
                throw new NullPointerException();
            }
            array = Arrays.copyOf(values, values.length);
            return this;
        }

        public int length() {
            return array == null ? 0 : array.length;
        }

        public Object[] toArray() {
            return Optional.ofNullable(array).orElse(null);
        }

        protected Object get(int index) {
            return array == null || index >= array.length ? null : array[index];
        }
    }

    /**
     * 一元组
     *
     * @param <T1>
     */
    public static class Tuple1<T1> extends Tuple {
        Tuple1() {
        }

        public static <T1> Tuple1 of(T1 t1) {
            return (Tuple1) new Tuple1().build(t1);
        }

        public T1 get1() {
            return (T1) get(0);
        }
    }


    /**
     * 二元组
     *
     * @param <T1>
     * @param <T2>
     */
    public static class Tuple2<T1, T2> extends Tuple1<T1> {
        Tuple2() {
        }

        public static <T1, T2> Tuple2 of(T1 t1, T2 t2) {
            return (Tuple2) new Tuple2().build(t1, t2);
        }

        public T2 get2() {
            return (T2) get(1);
        }
    }

    /**
     * 三元组
     *
     * @param <T1>
     * @param <T2>
     * @param <T3>
     */
    public static class Tuple3<T1, T2, T3> extends Tuple2<T1, T2> {
        Tuple3() {
        }

        public static <T1, T2, T3> Tuple3 of(T1 t1, T2 t2, T3 t3) {
            return (Tuple3) new Tuple3().build(t1, t2, t3);
        }

        public T3 get3() {
            return (T3) get(2);
        }
    }

    /**
     * 四元组
     *
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T4>
     */
    public static class Tuple4<T1, T2, T3, T4> extends Tuple3<T1, T2, T3> {
        Tuple4() {
        }

        public static <T1, T2, T3, T4> Tuple4 of(T1 t1, T2 t2, T3 t3, T4 t4) {
            return (Tuple4) new Tuple4().build(t1, t2, t3, t4);
        }

        public T4 get4() {
            return (T4) get(3);
        }
    }
}

