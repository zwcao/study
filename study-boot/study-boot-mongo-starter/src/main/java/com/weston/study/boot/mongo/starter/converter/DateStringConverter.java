package com.weston.study.boot.mongo.starter.converter;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.logging.Logger;
import org.mongodb.morphia.logging.MorphiaLoggerFactory;
import org.mongodb.morphia.mapping.MappedField;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateStringConverter extends TypeConverter implements SimpleValueConverter {

    private static final Logger LOG = MorphiaLoggerFactory.get(DateStringConverter.class);

    /**
     * Creates the Converter.
     */
    public DateStringConverter() {
        this(Date.class);
    }

    protected DateStringConverter(final Class<?> clazz) {
        super(clazz);
    }

    @Override
    public Object decode(final Class<?> targetClass, final Object fromDBObject, final MappedField optionalExtraInfo) {
        if (fromDBObject == null) {
            return null;
        }

        if (fromDBObject instanceof Date) {
            return fromDBObject;
        }

        if (fromDBObject instanceof Number) {
            return new Date(((Number) fromDBObject).longValue());
        }

        if (fromDBObject instanceof String) {
            String fromDBObjectOfString = (String) fromDBObject;
            if (StringUtils.isBlank(fromDBObjectOfString)) {
                return null;
            }
            try {
                String formatStr = "yyyy-MM-dd HH:mm:ss";
                if (optionalExtraInfo != null) {
                    DateTimeFormat format = optionalExtraInfo.getAnnotation(DateTimeFormat.class);
                    formatStr = format == null ? formatStr : format.pattern();
                }
                return new SimpleDateFormat(formatStr).parse(fromDBObjectOfString);
            } catch (ParseException e) {
                LOG.error("Can't parse Date from: " + fromDBObjectOfString);
            }
        }

        return null;
        //throw new IllegalArgumentException("Can't convert to Date from " + fromDBObject);
    }

    @Override
    public Object encode(Object value, MappedField optionalExtraInfo) {
        if (value == null)
            return null;
        String formatStr = "yyyy-MM-dd HH:mm:ss";
        if (optionalExtraInfo != null) {
            DateTimeFormat format = optionalExtraInfo.getAnnotation(DateTimeFormat.class);
            formatStr = format == null ? formatStr : format.pattern();
        }
        return new SimpleDateFormat(formatStr).format(value);
    }

}
