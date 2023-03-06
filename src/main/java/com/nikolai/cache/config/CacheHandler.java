package com.nikolai.cache.config;

import com.nikolai.cache.Cache;
import com.nikolai.cache.anno.CacheEvict;
import com.nikolai.cache.anno.CachePut;
import com.nikolai.model.CacheEntity;
import com.nikolai.service.CacheEntityService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CacheHandler implements InvocationHandler {
    private final CacheEntityService original;

    private final Cache<Integer, CacheEntity> cache;

    public CacheHandler(CacheEntityService original, Cache<Integer, CacheEntity> cache) {
        this.original = original;
        this.cache = cache;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        CacheEntity entity = null;

        if (isPutOperation(method.getAnnotation(CachePut.class))) {
            entity = cachePut(objects[0]);
        } else if (isRemoveOperation(method.getAnnotation(CacheEvict.class))) {
            cacheRemove(objects[0]);
        }

        return entity != null ? entity : method.invoke(original, objects);
    }


    private CacheEntity cachePut(Object arg) {
        CacheEntity entity = null;

        if (isKey(arg)) {
            entity = cache.get((Integer) arg);
        } else if (isValue(arg)) {
            cache.put(((CacheEntity) arg).getId(), (CacheEntity) arg);
        }

        return entity;
    }

    private void cacheRemove(Object arg) {
        cache.remove((Integer) arg);
    }



    private boolean isPutOperation(CachePut anno) {
        return anno != null;
    }

    private boolean isRemoveOperation(CacheEvict anno) {
        return anno != null;
    }

    private boolean isKey(Object obj) {
        return obj instanceof Integer;
    }

    private boolean isValue(Object obj) {
        return obj instanceof CacheEntity;
    }




}
