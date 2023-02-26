package com.nikolai.cacheTests;

import com.nikolai.cache.impl.LFUCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LFUTests {
    LFUCache<Integer, Integer> cache;

    int capacity = 3;


    @BeforeEach
    public void init() {
        cache = new LFUCache<>(capacity);
    }


    @Test
    public void whenEmptyCache_thenGetReturnNull() {
        Assertions.assertNull(cache.get(1));
    }


    @Test
    public void whenPut_thenGetReturnElem() {
        int key = 1;
        int expectedElem = 10;

        cache.put(key, expectedElem);

        Assertions.assertEquals(expectedElem, cache.get(key));
    }


    @Nested
    class PutElementsMoreThanCapacity {
        int capacity = LFUTests.this.capacity;


        @Test
        public void whenElementsHaveSameFrequently_thenEvictFirstKey() {
            int evictedKey = 1;
            cache.put(evictedKey, 1);

            IntStream.range(evictedKey + 1, evictedKey + 1 + capacity).forEach((i) -> cache.put(i, i));

            Assertions.assertNull(cache.get(evictedKey));
        }

        @Test
        public void whenElementsHaveOthersFrequently_thenEvictLessFrequentlyKey() {
            int evictedKey = 1;
            cache.put(evictedKey, 1);

            IntStream.range(evictedKey + 1, evictedKey + 1 + capacity)
                    .forEach((i) -> IntStream.range(0, i).forEach((k) -> cache.put(i, i)));

            Assertions.assertNull(cache.get(evictedKey));
        }
    }

    @Test
    public void whenPutElements_thenGetReturnElem() {
        int key = 1;
        int expectedElem = 10;
        cache.put(key, expectedElem);

        IntStream.range(key + 1, key + capacity).forEach((i) -> cache.put(i, i));

        Assertions.assertEquals(expectedElem, cache.get(key));
    }


    @Test
    public void whenPutTwoElemWithEqualKeys_thenGetReturnLatestAddElem() {
        int key = 1;
        int replacedElem = 2;
        int expectedElem = 3;

        cache.put(key, replacedElem);
        cache.put(key, expectedElem);

        Assertions.assertEquals(expectedElem, cache.get(key));
    }


    @ParameterizedTest
    @MethodSource("com.nikolai.cacheTests.LRUTests#elements")
    public void whenPutNullKey_thenGetReturnElem(Integer expectedElem) {
        Integer key = null;
        cache.put(null, expectedElem);

        Assertions.assertEquals(expectedElem, cache.get(key));
    }


    @Test
    public void whenAdd_thenRemoveAndGetReturnNull() {
        int key = 2;
        cache.put(key, 10);
        cache.remove(key);

        Assertions.assertNull(cache.get(key));
    }


    @ParameterizedTest
    @MethodSource("com.nikolai.cacheTests.LRUTests#elements")
    public void whenElemNotExist_thenRemoveNotThrowException(Integer nonExistent) {
        Assertions.assertDoesNotThrow(() -> cache.remove(nonExistent));
    }


    private static Stream<Integer> elements() {
        return Stream.of(
                5,
                null
        );
    }
}
