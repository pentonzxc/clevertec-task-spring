package com.nikolai.cache.impl;

import com.nikolai.cache.Cache;
import com.nikolai.cache.Page;

import java.util.LinkedHashMap;
import java.util.Map;

/*
* Last Recently Cache
* Last Frequently Cache
* What is difference?*/
public class LFUCache<K, V> implements Cache<K, V> {

    private final Map<K, Page<Integer, V>> pages;

    private final int CACHE_SIZE;

    public LFUCache(int capacity) {
        this.pages = new LinkedHashMap<>();
        this.CACHE_SIZE = capacity;
    }

    @Override
    public V get(K key) {
        if (!pages.containsKey(key)) {
            return null;
        }
        var page = pages.get(key);
        increment(key);

        return page.getValue();
    }

    @Override
    public void put(K key, V value) {
        var page = pages.getOrDefault(key, null);
        if (page == null) {
            add(key, value);
        } else if (value != page.getValue()) {
            replace(key, value);
        } else {
            increment(key);
        }
    }

    @Override
    public void remove(K key) {
        pages.remove(key);
    }

    private void replace(K key, V value) {
        pages.replace(key, new Page<>(1, value));
    }


    private void add(K key, V value) {
        if (pages.size() == CACHE_SIZE) {
            pages.remove(minFrequencyKey());
        }
        pages.put(key, new Page<>(1, value));
    }


    private K minFrequencyKey() {
        K key = null;
        int min = Integer.MAX_VALUE;
        for (var entry : pages.entrySet()) {
            if (entry.getValue().getKey() < min) {
                key = entry.getKey();
                min = entry.getValue().getKey();
            }
        }

        return key;
    }


    private void increment(K key) {
        var page = pages.get(key);
        page.setKey(page.getKey() + 1);
    }

}
