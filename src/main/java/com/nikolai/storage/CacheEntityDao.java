package com.nikolai.storage;


import com.nikolai.model.CacheEntity;

import java.util.HashMap;
import java.util.Map;

public class CacheEntityDao implements Dao {
    private final Map<Integer, CacheEntity> storage = new HashMap<>();

    public CacheEntity find(Integer id) {
        return storage.get(id);
    }

    public void remove(Integer id) {
        storage.remove(id);
    }

    public void save(CacheEntity cacheEntity) {
        if (cacheEntity.getId() == null) {
            throw new NullPointerException();
        }
        storage.put(cacheEntity.getId(), cacheEntity);
    }
}
