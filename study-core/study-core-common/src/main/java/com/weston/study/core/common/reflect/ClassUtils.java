package com.weston.study.core.common.reflect;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * ClassUtils
 */
public class ClassUtils {

    public static Class<?> getBeanClazz(String classBeanName){
        try {
            return Class.forName(classBeanName);
        }
        catch (ClassNotFoundException e){
            return null;
        }
    }

    public static Class<?> getClass(Type type, int i) {
        if (type instanceof ParameterizedType) {
            return getGenericClass((ParameterizedType) type, i);  // 处理泛型类型
        } else if (type instanceof TypeVariable) {
            return getClass(((TypeVariable) type).getBounds()[0], 0); // 处理泛型擦拭对象
        } else {
            return (Class<?>) type; // class本身也是type，强制转型
        }
    }

    private static Class<?> getGenericClass(ParameterizedType parameterizedType, int i) {
        Object genericClass = parameterizedType.getActualTypeArguments()[i];
        if (genericClass instanceof ParameterizedType) { // 处理多级泛型
            return (Class<?>) ((ParameterizedType) genericClass).getRawType();
        } else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
            return (Class<?>) ((GenericArrayType) genericClass).getGenericComponentType();
        } else if (genericClass instanceof TypeVariable) { // 处理泛型擦拭对象
            return getClass(((TypeVariable) genericClass).getBounds()[0], 0);
        } else {
            return (Class<?>) genericClass;
        }
    }

}
