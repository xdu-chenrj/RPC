package org.common.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public final class SingletonFactory {
    private static final Map<String, Object> OBJECT_MAP = new ConcurrentHashMap<>();

    private SingletonFactory() {
    }

    public static <T> T getInstance(Class<T> tClass) {
        if (tClass == null) {
            throw new IllegalArgumentException();
        }
        String key = tClass.toString();
        if (OBJECT_MAP.containsKey(key)) {
            return tClass.cast(OBJECT_MAP.get(key));
        } else {
            return tClass.cast(OBJECT_MAP.computeIfAbsent(key, k -> {
                try {
                    return tClass.getDeclaredConstructor().newInstance();
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }));
        }
    }
}
