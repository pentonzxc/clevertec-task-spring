package com.nikolai.service;

import com.nikolai.cache.anno.CacheEvict;
import com.nikolai.cache.anno.CachePut;
import com.nikolai.model.CacheEntity;

public interface CacheEntityService {

    @CachePut
    CacheEntity find(Integer id);

    @CachePut
    void save(CacheEntity entity);

    @CacheEvict
    void remove(Integer id);
}
