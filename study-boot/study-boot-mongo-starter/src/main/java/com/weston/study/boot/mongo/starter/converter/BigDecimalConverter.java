package com.weston.study.boot.mongo.starter.converter;

import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;
import org.mongodb.morphia.utils.ReflectionUtils;

import java.math.BigDecimal;
import java.util.List;

public class BigDecimalConverter extends TypeConverter implements SimpleValueConverter {

    public BigDecimalConverter() {
        super(BigDecimal.class);
    }

    @Override
    protected boolean isSupported(Class<?> c, MappedField optionalExtraInfo) {
        return BigDecimal.class.isAssignableFrom(c);
    }

    @Override
    public Object decode(Class<?> targetClass, Object fromDBObject, MappedField optionalExtraInfo) {
        if (fromDBObject == null) {
            return null;
        }
        if (fromDBObject instanceof String) {
            return new BigDecimal((String) fromDBObject);
        }
        if (fromDBObject instanceof Number) {
            return BigDecimal.valueOf(((Number) fromDBObject).doubleValue());
        }
        if (fromDBObject instanceof List) {
            final Class<?> type = targetClass.isArray() ? targetClass.getComponentType() : targetClass;
            return ReflectionUtils.convertToArray(type, (List<?>) fromDBObject);
        }
        return new BigDecimal(fromDBObject.toString());
    }

    @Override
    public Object encode(Object value, MappedField optionalExtraInfo) {
        if (value == null)
            return null;
        return ((BigDecimal)value).toPlainString();
    }

}
