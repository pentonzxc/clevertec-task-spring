package com.nikolai.storage;

import com.nikolai.model.CacheEntity;

public interface Dao {

    CacheEntity find(Integer id);

    void remove(Integer id);

    void save(CacheEntity cacheEntity);
}
