package com.weston.study.tools.poi.exception;

import java.math.BigDecimal;
import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 异常防护
 */
public class Guard {

    public static void notNull(Object argumentValue, String message) {
        if (argumentValue == null) {
            fail(message);
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
        if (StringUtils.isEmpty(argumentValue)) {
            fail(message);
        }
    }

    public static void notNullOrEmpty(Collection<?> collection, String message) {
        if (collection == null || collection.size() == 0) {
            fail(message);
        }
    }

    public static void nullOrEmpty(Collection<?> collection, String message) {
        if (collection != null && collection.size() > 0) {
            fail(message);
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

    public static void greaterThan(Integer value, Integer valueToCompare, String message) {
        if (value == null || valueToCompare == null) {
            fail(message);
        }
        if (value.compareTo(valueToCompare) <= 0) {
            fail(message);
        }
    }

    public static void greaterThanOrEquals(Integer value, Integer valueToCompare, String message) {
        if (value == null || valueToCompare == null) {
            fail(message);
        }
        if (value.compareTo(valueToCompare) < 0) {
            fail(message);
        }
    }

    public static void lessThan(Integer value, Integer valueToCompare, String message) {
        if (value == null || valueToCompare == null) {
            fail(message);
        }
        if (value.compareTo(valueToCompare) >= 0) {
            fail(message);
        }
    }

    public static void lessThanOrEquals(Integer value, Integer valueToCompare, String message) {
        if (value == null || valueToCompare == null) {
            fail(message);
        }
        if (value.compareTo(valueToCompare) > 0) {
            fail(message);
        }
    }

    public static void equals(Integer value, Integer valueToCompare, String message) {
        if (value == null || valueToCompare == null) {
            fail(message);
        }
        if (value.compareTo(valueToCompare) != 0) {
            fail(message);
        }
    }

    public static void greaterThan(BigDecimal value, BigDecimal valueToCompare, String message) {
        if (value == null || valueToCompare == null) {
            fail(message);
        }
        if (value.compareTo(valueToCompare) <= 0) {
            fail(message);
        }
    }

    public static void greaterThanOrEquals(BigDecimal value, BigDecimal valueToCompare, String message) {
        if (value == null || valueToCompare == null) {
            fail(message);
        }
        if (value.compareTo(valueToCompare) < 0) {
            fail(message);
        }
    }

    public static void lessThan(BigDecimal value, BigDecimal valueToCompare, String message) {
        if (value == null || valueToCompare == null) {
            fail(message);
        }
        if (value.compareTo(valueToCompare) >= 0) {
            fail(message);
        }
    }

    public static void lessThanOrEquals(BigDecimal value, BigDecimal valueToCompare, String message) {
        if (value == null || valueToCompare == null) {
            fail(message);
        }
        if (value.compareTo(valueToCompare) > 0) {
            fail(message);
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

    public static void equals(BigDecimal value, BigDecimal valueToCompare, String message) {
        if (value == null || valueToCompare == null) {
            fail(message);
        }
        if (value.compareTo(valueToCompare) != 0) {
            fail(message);
        }
    }

    public static void isTrue(boolean condition) {
        if (!condition) {
            fail(null);
        }
    }

    public static void isTrue(boolean condition, String message) {
        if (!condition) {
            fail(message);
        }
    }

    public static void isFalse(boolean condition, String msg) {
        if (condition) {
            fail(msg);
        }
    }

    public static void fail(String message) {
        throw new ApplicationBizException(message);
    }
}