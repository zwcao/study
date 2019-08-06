package com.weston.study.core.common.reflect.invoker;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Get Field调用器
 */
public class GetFieldInvoker implements Invoker {
    private Field field;

    public GetFieldInvoker(Field field) {
        this.field = field;
    }

    @Override
    public Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException {
        return field.get(target);
    }

    public Field getField() {
        return field;
    }

    @Override
    public Class<?> getType() {
        return field.getType();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(this.getClass().isAssignableFrom(obj.getClass()))) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        GetFieldInvoker that = (GetFieldInvoker) obj;
        return this.field.equals(that.getField());
    }

    @Override
    public int hashCode() {
        return this.field.hashCode();
    }
}