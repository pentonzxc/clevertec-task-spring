package com.nikolai.cache.factory;

import com.nikolai.cache.Cache;
import com.nikolai.cache.impl.LFUCache;
import com.nikolai.cache.impl.LRUCache;
import com.nikolai.model.CacheEntity;

public class EntityCacheFactory implements CacheFactory<Integer, CacheEntity> {

    @Override
    public Cache<Integer, CacheEntity> cache(Implementation impl, int size) {
        if (impl == Implementation.LFU) {
            return new LFUCache<>(size);
        }
        return new LRUCache<>(size);
    }
}
