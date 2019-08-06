package com.weston.study.core.common.exception;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * 异常防护
 */
public class Guard {

    public static void notNull(Object argumentValue, String message) {
        notNull(argumentValue, null, message);
    }

    public static void notNull(Object argumentValue, String code, String message) {
        if (argumentValue == null) {
            fail(code, message);
        }
    }

    public static void allNotNull(String message, Object... objects) {
        if (objects == null) {
            fail(message);
        } else {
            for (int i = 0; i < objects.length; i++) {
                Object object = objects[i];
                if (object == null) {
                    fail(message);
                }
            }
        }
    }

    public static void notNullOrEmpty(String argumentValue, String message) {
        notNullOrEmpty(argumentValue, null, message);
    }

    public static void notNullOrEmpty(String argumentValue, String code, String message) {
        if (StringUtils.isEmpty(argumentValue)) {
            fail(code, message);
        }
    }

    public static void notNullOrEmpty(Collection<?> collection, String message) {
        notNullOrEmpty(collection, null, message);
    }

    public static void nullOrEmpty(Collection<?> collection, String message) {
        nullOrEmpty(collection, null, message);
    }

    public static void notNullOrEmpty(Collection<?> collection, String code, String message) {
        if (collection == null || collection.size() == 0) {
            fail(code, message);
        }
    }

    public static void nullOrEmpty(Collection<?> collection, String code, String message) {
        if (collection != null && collection.size() > 0) {
            fail(code, message);
        }
    }

    public static void hasOneAndOnlyOneRecord(Collection<?> collection, String messageTemplate, Object... args) {
        if (collection == null || collection.size() != 1) {
            fail(String.format(messageTemplate, args));
        }
    }

    public static void hasOneAndOnlyOneRecord(Collection<?> collection, String message) {
        if (collection == null || collection.size() != 1) {
            fail(message);
        }
    }

    public static <T extends Comparable<T>> void greaterThan(T value, T valueToCompare, String message) {
        greaterThan(value, valueToCompare, null, message);
    }

    public static <T extends Comparable<T>> void greaterThan(T value, T valueToCompare, String code, String message) {
        if (value == null || valueToCompare == null) {
            fail(code, message);
        }
        if (value.compareTo(valueToCompare) <= 0) {
            fail(code, message);
        }
    }

    public static <T extends Comparable<T>> void lessThan(T value, T valueToCompare, String message) {
        lessThan(value, valueToCompare, null, message);
    }

    public static <T extends Comparable<T>> void lessThan(T value, T valueToCompare, String code, String message) {
        if (value == null || valueToCompare == null) {
            fail(code, message);
        }
        if (value.compareTo(valueToCompare) >= 0) {
            fail(code, message);
        }
    }

    public static <T extends Comparable<T>> void equals(T value, T valueToCompare, String message) {
        equals(value, valueToCompare, null, message);
    }

    public static <T extends Comparable<T>> void equals(T value, T valueToCompare, String code, String message) {
        if (value == null || valueToCompare == null) {
            fail(code, message);
        }
        if (value.compareTo(valueToCompare) != 0) {
            fail(code, message);
        }
    }

    public static <T extends Comparable<T>> void lessThanOrEquals(T value, T valueToCompare, String message) {
        lessThanOrEquals(value, valueToCompare, null, message);
    }

    public static <T extends Comparable<T>> void lessThanOrEquals(T value, T valueToCompare, String code, String message) {
        if (value == null || valueToCompare == null) {
            fail(code, message);
        }
        if (value.compareTo(valueToCompare) > 0) {
            fail(code, message);
        }
    }

    public static <T extends Comparable<T>> void greaterThanOrEquals(T value, T valueToCompare, String message) {
        greaterThanOrEquals(value, valueToCompare, null, message);
    }

    public static <T extends Comparable<T>> void greaterThanOrEquals(T value, T valueToCompare, String code, String message) {
        if (value == null || valueToCompare == null) {
            fail(code, message);
        }
        if (value.compareTo(valueToCompare) < 0) {
            fail(code, message);
        }
    }

    public static void lessThanOrEquals(Collection<?> collection, int size, String message) {
        int colSize = 0;
        if (CollectionUtils.isNotEmpty(collection)) {
            colSize = collection.size();
        }
        if (colSize > size) {
            fail(message);
        }
    }

    public static void isTrue(boolean condition) {
        isTrue(condition, null, null);
    }

    public static void isTrue(boolean condition, String message) {
        isTrue(condition, null, message);

    }

    public static void isTrue(boolean condition, String code, String message) {
        if (!condition) {
            fail(code, message);
        }
    }

    public static void isFalse(boolean condition, String msg) {
        isFalse(condition, null, msg);
    }

    public static void isFalse(boolean condition, String code, String msg) {
        if (condition) {
            fail(code, msg);
        }
    }

    public static void fail(String message) {
        throw new BizException(message);
    }

    public static void fail(String code, String message) {
        throw new BizException(code, message);
    }
}