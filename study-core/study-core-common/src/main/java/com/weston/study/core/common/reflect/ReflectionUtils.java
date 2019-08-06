package com.weston.study.core.common.reflect;

import com.weston.study.core.common.exception.Guard;
import com.weston.study.core.common.exception.SysException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.management.ReflectionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射工具类.
 * <p>
 * 提供访问私有变量,获取泛型类型Class, 提取集合中元素的属性, 转换字符串到对象等Util函数.
 */
public class ReflectionUtils {
    /**
     * 调用Getter方法.
     */
    public static Object invokeGetterMethod(Object obj, String propertyName) {
        String getterMethodName = "get" + StringUtils.capitalize(propertyName);
        return invokeMethod(obj, getterMethodName, new Class[]{}, new Object[]{});
    }

    /**
     * 调用Setter方法.使用value的Class来查找Setter方法.
     */
    public static void invokeSetterMethod(Object obj, String propertyName, Object value) {
        invokeSetterMethod(obj, propertyName, value, null);
    }

    /**
     * 调用Setter方法.
     *
     * @param propertyType 用于查找Setter方法,为空时使用value的Class替代.
     */
    public static void invokeSetterMethod(Object obj, String propertyName, Object value, Class<?> propertyType) {
        Class<?> type = propertyType != null ? propertyType : value.getClass();
        String setterMethodName = "set" + StringUtils.capitalize(propertyName);
        invokeMethod(obj, setterMethodName, new Class[]{type}, new Object[]{value});
    }

    /**
     * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
     */
    public static Object getFieldValue(final Object obj, final String fieldName) throws SysException{
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        }

        Object result;
        try {
            result = field.get(obj);
        } catch (IllegalAccessException e) {
            throw new SysException(e);
        }
        return result;
    }

    /**
     * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
     */
    public static void setFieldValue(final Object obj, final String fieldName, final Object value) throws SysException {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
        }

        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            throw new SysException(e);
        }
    }

    /**
     * 循环向基类查找指定的field, 获取对象的DeclaredField, 并强制设置为可访问.
     * 如向基类查找Object仍无法找到, 返回null.
     */
    public static Field getAccessibleField(final Object obj, final String fieldName) {
        Guard.notNull(obj, "object不能为空");
        Guard.notNullOrEmpty(fieldName, "fieldName");
        return getAccessibleField(obj.getClass(), fieldName);
    }

    public static Field getAccessibleField(final Class<?> objClazz, final String fieldName) {
        Guard.notNull(objClazz, "objClazz");
        Guard.notNullOrEmpty(fieldName, "fieldName");
        for (Class<?> superClass = objClazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                continue;
            }
        }
        return null;
    }

    public static List<Field> getAllAccessibleFields(final Class<?> objClazz) {
        Guard.notNull(objClazz, "objClazz");
        List<Field> fieldList = new ArrayList<>();
        for (Class<?> superClass = objClazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field[] fieldArray = superClass.getDeclaredFields();
                for (Field fieldItem : fieldArray) {
                    fieldItem.setAccessible(true);
                    fieldList.add(fieldItem);
                }
            } catch (Exception e) {
                continue;
            }
        }
        return fieldList;
    }

    /**
     * 直接调用对象方法, 无视private/protected修饰符.
     * 用于一次性调用的情况.
     */
    public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
                                      final Object[] args) {
        Method method = getAccessibleMethod(obj, methodName, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
        }

        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * 循环向基类查找 获取对象的DeclaredMethod,并强制设置为可访问.
     * 如向上转型到Object仍无法找到, 返回null.
     * <p>
     * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
     */
    public static Method getAccessibleMethod(final Object obj, final String methodName,
                                             final Class<?>... parameterTypes) {
        Guard.notNull(obj, "object不能为空");

        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Method method = superClass.getDeclaredMethod(methodName, parameterTypes);
                method.setAccessible(true);
                return method;
            } catch (NoSuchMethodException e) {
                continue;
            }
        }
        return null;
    }

    /**
     * 循环向基类查找 获取对象的DeclaredMethod,并强制设置为可访问.
     * 如向上转型到Object仍无法找到, 返回null.
     * <p>
     * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
     */
    public static Method getAccessibleMethod(final Class<?> objClazz, final String methodName,
                                             final Class<?>... parameterTypes) {
        Guard.notNull(objClazz, "objClazz");

        for (Class<?> superClass = objClazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Method method;
                if (parameterTypes == null || parameterTypes.length == 0) {
                    method = findFirstMethodByName(objClazz.getDeclaredMethods(), methodName);
                } else {
                    method = superClass.getDeclaredMethod(methodName, parameterTypes);
                }
                method.setAccessible(true);
                return method;
            } catch (NoSuchMethodException e) {
                continue;
            }
        }
        return null;
    }

    /**
     * 通过反射, 获得Class定义中声明的父类的泛型参数的类型.
     * 如无法找到, 返回Object.class.
     * eg.
     * public UserRepository extends HibernateRepositry<User>
     *
     * @param clazz The class to introspect
     * @return the first generic declaration, or Object.class if cannot be determined
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射, 获得Class定义中声明的父类的泛型参数的类型.
     * 如无法找到, 返回Object.class.
     * <p>
     * 如public UserRepository extends HibernateRepositry<Long, User>
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     * @return the index generic declaration, or Object.class if cannot be determined
     */
    @SuppressWarnings("rawtypes")
    public static Class getSuperClassGenricType(final Class clazz, final int index) {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }

        return (Class) params[index];
    }

    public static Class<?>[] getSuperClassGenricTypes(final Class<?> clazz) {

        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return null;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (params == null || params.length == 0) {
            return null;
        }

        Class<?>[] classArray = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            if (!(params[i] instanceof Class)) {
                classArray[i] = Object.class;
            } else {
                classArray[i] = (Class<?>) params[i];
            }
        }
        return classArray;
    }

    public static Class<?> getInterfaceGenericType(final String className, final Class<?> interfaceClazz, final int index) {
        try {
            Class<?> clazz = Class.forName(className);
            return getInterfaceGenericType(clazz, interfaceClazz, 0);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * @param clazz
     * @param interfaceClazz
     * @return
     */
    public static Class<?> getInterfaceGenericType(final Class<?> clazz, final Class<?> interfaceClazz) {
        return getInterfaceGenericType(clazz, interfaceClazz, 0);
    }

    /**
     * @param clazz
     * @param interfaceClazz
     * @param index
     * @return
     */
    public static Class<?> getInterfaceGenericType(final Class<?> clazz, final Class<?> interfaceClazz, final int index) {

        Type[] interfaceGenTypeArray = clazz.getGenericInterfaces();

        if (interfaceGenTypeArray == null || interfaceGenTypeArray.length == 0) {
            return Object.class;
        }

        for (Type interfaceGenTypeItem : interfaceGenTypeArray) {

            if (!(interfaceGenTypeItem instanceof ParameterizedType)) {
                return Object.class;
            }

            ParameterizedType parameterizedInterfaceGenTypeItem = ((ParameterizedType) interfaceGenTypeItem);

            if (parameterizedInterfaceGenTypeItem.getRawType() != interfaceClazz) {
                continue;
            }

            Type[] params = parameterizedInterfaceGenTypeItem.getActualTypeArguments();

            if (index >= params.length || index < 0) {
                return Object.class;
            }

            if (!(params[index] instanceof Class)) {
                ParameterizedType parameterizedType = ((ParameterizedType) params[index]);
                if (!(parameterizedType.getRawType() instanceof Class)) {
                    return Object.class;
                } else {
                    return (Class<?>) parameterizedType.getRawType();
                }
            }

            return (Class<?>) params[index];
        }

        return Object.class;
    }

    public static Class<?>[] getInterfaceGenericTypes(final Class<?> clazz, final Class<?> interfaceClazz) {
        Type[] interfaceGenTypeArray = clazz.getGenericInterfaces();

        if (interfaceGenTypeArray == null || interfaceGenTypeArray.length == 0) {
            return null;
        }

        Class<?>[] classArray;

        for (Type interfaceGenTypeItem : interfaceGenTypeArray) {

            if (!(interfaceGenTypeItem instanceof ParameterizedType)) {
                continue;
            }

            ParameterizedType parameterizedInterfaceGenTypeItem = ((ParameterizedType) interfaceGenTypeItem);

            if (parameterizedInterfaceGenTypeItem.getRawType() != interfaceClazz) {
                continue;
            }

            Type[] params = parameterizedInterfaceGenTypeItem.getActualTypeArguments();
            classArray = new Class[params.length];

            for (int i = 0; i < params.length; i++) {
                if (!(params[i] instanceof Class)) {
                    ParameterizedType parameterizedType = ((ParameterizedType) params[i]);
                    if (!(parameterizedType.getRawType() instanceof Class)) {
                        classArray[i] = Object.class;
                    } else {
                        classArray[i] = (Class<?>) parameterizedType.getRawType();
                    }
                } else {
                    classArray[i] = (Class<?>) params[i];
                }
            }
            return classArray;
        }
        return null;
    }

    public static Class<?>[] getAllInterfaceTypes(Class objClazz) {
        Guard.notNull(objClazz, "objClazz");

        List<Class> interfaceClazzList = new ArrayList<>();
        for (Class<?> superClass = objClazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            Class<?>[] interfaceClazzArray = superClass.getInterfaces();
            if (interfaceClazzArray != null && interfaceClazzArray.length > 0) {
                for (Class<?> interfaceclazz : interfaceClazzArray) {
                    interfaceClazzList.add(interfaceclazz);
                }
            }
        }

        if (CollectionUtils.isEmpty(interfaceClazzList)) {
            return null;
        }

        Class[] classArray = new Class[interfaceClazzList.size()];
        return interfaceClazzList.toArray(classArray);
    }

    /**
     * 将反射时的checked exception转换为unchecked exceptions.
     */
    public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
        if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
                || e instanceof NoSuchMethodException) {
            return new IllegalArgumentException("Reflection Exception.", e);
        } else if (e instanceof InvocationTargetException) {
            return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
        } else if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException("Unexpected Checked Exception.", e);
    }

    /**
     * @param methods
     * @param methodName
     * @return
     */
    private static Method findFirstMethodByName(Method[] methods, String methodName) {
        if (methods == null || StringUtils.isEmpty(methodName)) {
            return null;
        }

        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }

        return null;
    }

}
