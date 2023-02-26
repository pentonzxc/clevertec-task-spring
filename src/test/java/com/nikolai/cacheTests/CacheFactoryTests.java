package com.nikolai.cacheTests;

import com.nikolai.cache.factory.CacheFactory;
import com.nikolai.cache.factory.EntityCacheFactory;
import com.nikolai.cache.impl.EmptyCache;
import com.nikolai.cache.impl.LRUCache;
import com.nikolai.model.CacheEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CacheFactoryTests {


    @Test
    public void whenEmptyCache_thenReturnCacheInstanceOfEmptyCache() {
        Assertions.assertTrue(CacheFactory.emptyCache() instanceof EmptyCache);
    }


    @Nested
    class EntityCacheFactoryTest {
        static EntityCacheFactory factory;

        @BeforeAll
        static void initGlobal() {
            factory = new EntityCacheFactory();
        }


        @Test
        public void whenImplementationLRU_thenReturnCacheInstanceOfLRUCache() {
            Assertions.assertTrue(factory.cache(CacheFactory.Implementation.LRU, 0) instanceof LRUCache<Integer, CacheEntity>);
        }

        @Test
        public void whenImplementationLFU_thenReturnCacheInstanceOfLFUCache() {
            Assertions.assertTrue(factory.cache(CacheFactory.Implementation.LFU, 0) instanceof LRUCache<Integer, CacheEntity>);
        }
    }
}
