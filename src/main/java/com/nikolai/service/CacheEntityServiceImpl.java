package com.nikolai.service;

import com.nikolai.model.CacheEntity;
import com.nikolai.storage.Dao;


public class CacheEntityServiceImpl implements CacheEntityService {

    private final Dao dao;

    public CacheEntityServiceImpl(Dao dao) {
        this.dao = dao;
    }

    @Override
    public CacheEntity find(Integer id) {
        return dao.find(id);
    }

    @Override
    public void save(CacheEntity entity) {
        dao.save(entity);
    }

    @Override
    public void remove(Integer id) {
        dao.remove(id);
    }
}
