package com.sb.auto.common.util;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

public class CollectionsUtil {

    /**
     * bean to map 함수
     * @param bean
     * @return
     */
    public static Map<String, Object> beanToMap(Object bean) {
        Map<String, Object> map = new HashMap<String, Object>();
        putValues(bean, map, null, true, false);
        return map;
    }

    /**
     * bean to map 함수 (key : camelCase, upperCase)
     * @param map
     * @return
     */
    public static Object mapToBean(Map<String, Object> map, Object bean) {
        mapToBean(bean, map, null, false, true);
        return bean;
    }

    /**
     * Map에 값 셋팅
     * @param bean
     * @param map
     * @param prefixOverrides 제거할 prefix 문자열 (ex. sch_)
     * @param keyUpperCase
     * @param camelCase
     * @return
     */
    private static void putValues(Object bean, Map<String, Object> map, String prefixOverrides, boolean keyUpperCase, boolean camelCase) {
        Class<?> cls = bean.getClass();
        for (Field field : cls.getDeclaredFields()) {
            field.setAccessible(true);

            Object value = null;
            String key;

            try {
                value = field.get(bean);
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            }

            key = field.getName();

            // 선행문자 제거
            if (prefixOverrides != null) {
                key = key.replaceFirst(prefixOverrides, "");
            }

            // key upperCase 및 underScore로 변경
            if (keyUpperCase) {
                key = key.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase().toUpperCase();
            }

            // key camelCase로 변경
            if (camelCase) {
                StringBuffer buffer = new StringBuffer();
                for (String token : key.toLowerCase().split("_")) {
                    buffer.append(StringUtils.capitalize(token));
                }
                key = StringUtils.uncapitalize(buffer.toString());
            }

            if (isValue(value)) {
                map.put(key, value);
            } else if (value instanceof BigDecimal) {
                map.put(key, value);
            } else {
                putValues(value, map, key, keyUpperCase, camelCase);
            }
        }
    }

    /**
     * Bean에 값 셋팅
     * @param bean
     * @param map
     * @param prefixOverrides 제거할 prefix 문자열 (ex. sch_)
     * @param keyUpperCase
     * @param camelCase
     * @return
     */
    private static Object mapToBean(Object bean, Map<String, Object> map, String prefixOverrides, boolean keyUpperCase, boolean camelCase) {

        String keyAttribute = null;
        String copyAttibute = null;
        String setMethodString = "set";
        String methodString = null;
        Iterator itr = map.keySet().iterator();
        while (itr.hasNext()) {

            keyAttribute = (String) itr.next();
            copyAttibute = keyAttribute;

            // 선행문자 제거
            if (prefixOverrides != null) {
                copyAttibute = copyAttibute.replaceFirst(prefixOverrides, "");
            }

            // key upperCase 및 underScore로 변경
            if (keyUpperCase) {
                copyAttibute = copyAttibute.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase().toUpperCase();
            }

            // key camelCase로 변경
            if (camelCase) {
                StringBuffer buffer = new StringBuffer();
                for (String token : copyAttibute.toLowerCase().split("_")) {
                    buffer.append(StringUtils.capitalize(token));
                }
                copyAttibute = StringUtils.uncapitalize(buffer.toString());
            }
            methodString = setMethodString + copyAttibute.substring(0, 1).toUpperCase() + copyAttibute.substring(1);
            try {
                Method[] methods = bean.getClass().getDeclaredMethods();
                for (int i = 0; i <= methods.length - 1; i++) {
                    if (methodString.equals(methods[i].getName())) {
                        methods[i].invoke(bean, map.get(keyAttribute));
                    }
                }
            } catch (SecurityException e) {
            } catch (IllegalAccessException e) {
            } catch (IllegalArgumentException e) {
            } catch (InvocationTargetException e) {
            }
        }
        return bean;
    }




    private static final Set<Class<?>> valueClasses = (Collections.unmodifiableSet(new HashSet<>(Arrays.asList(Object.class, String.class, Boolean.class,
            Character.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class))));

    private static boolean isValue(Object value) {
        return value == null || valueClasses.contains(value.getClass());
    }


}
