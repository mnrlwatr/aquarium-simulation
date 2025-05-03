package org.fp.container;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class ApplicationContext {
    static ConcurrentMap<String,Object> dependencies = new ConcurrentHashMap<>();
    private ApplicationContext(){}

    public static Object getDependency(String key){
        return dependencies.get(key);
    }
    public static void addDependency(String key,Object value){
        dependencies.put(key,value);
    }
}
