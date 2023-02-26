package com.nikolai.cache.config;


import com.nikolai.cache.Cache;
import com.nikolai.cache.factory.CacheFactory;
import com.nikolai.cache.factory.EntityCacheFactory;
import com.nikolai.cache.impl.EmptyCache;
import com.nikolai.model.CacheEntity;
import com.nikolai.service.CacheEntityServiceImpl;
import com.nikolai.service.CacheEntityService;
import com.nikolai.storage.CacheEntityDao;
import com.nikolai.storage.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.lang.reflect.Proxy;
import java.util.Objects;

@Configuration
public class CacheConfiguration {

    @Autowired
    Environment env;

    @Bean
    public CacheFactory<Integer, CacheEntity> entityCacheFactory() {
        return new EntityCacheFactory();
    }

    @Bean
    public Dao cacheEntityDao() {
        return new CacheEntityDao();
    }


    @Bean
    public CacheEntityService cacheEntityService() {
        var original = new CacheEntityServiceImpl(cacheEntityDao());
        Cache<?, ?> cache = buildCache(entityCacheFactory());

        if (cache instanceof EmptyCache) {
            return original;
        }

        return (CacheEntityService) Proxy.newProxyInstance(
                original.getClass().getClassLoader(),
                new Class[]{CacheEntityService.class},
                new CacheHandler(original, (Cache<Integer, CacheEntity>) cache)
        );
    }


    public Cache<?, ?> buildCache(CacheFactory<?, ?> cache) {
        String impl = env.getProperty("cache.impl");
        String size = env.getProperty("cache.size");
        if (Objects.isNull(impl) || Objects.isNull(size)) {
            return CacheFactory.emptyCache();
        }

        return cache.cache(
                CacheFactory.Implementation.valueOf(impl.toUpperCase()),
                Integer.parseInt(size)
        );
    }

}
