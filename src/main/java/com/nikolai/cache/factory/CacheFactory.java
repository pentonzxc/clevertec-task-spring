package com.nikolai.cache.factory;

import com.nikolai.cache.Cache;
import com.nikolai.cache.impl.EmptyCache;

public interface CacheFactory<K, V> {

    Cache<K, V> cache(Implementation impl, int size);

    static EmptyCache emptyCache(){
        return new EmptyCache();
    }

    enum Implementation {
        LRU,
        LFU;
    }
}
