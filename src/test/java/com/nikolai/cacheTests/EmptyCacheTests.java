package com.nikolai.cacheTests;

import com.nikolai.cache.impl.EmptyCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class EmptyCacheTests {
    static EmptyCache cache;

    @BeforeAll
    static void initGlobal() {
        cache = new EmptyCache();
    }


    @Test
    public void whenGet_thenThrowUnsupportedOperationException() {
        Assertions.assertThrows(UnsupportedOperationException.class , () -> cache.get(null));
    }


    @Test
    public void whenPut_thenThrowUnsupportedOperationException() {
        Assertions.assertThrows(UnsupportedOperationException.class , () -> cache.put(null , null));
    }


    @Test
    public void whenRemove_thenThrowUnsupportedOperationException() {
        Assertions.assertThrows(UnsupportedOperationException.class , () -> cache.remove(null));
    }


}
