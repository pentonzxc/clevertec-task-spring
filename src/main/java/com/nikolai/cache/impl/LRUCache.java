package com.nikolai.cache.impl;

import com.nikolai.cache.Cache;
import com.nikolai.cache.Page;

import java.util.*;

public class LRUCache<K, V> implements Cache<K, V> {

    private final Map<K, V> heap;

    private final Deque<Page<K, V>> pages;

    private final int CACHE_SIZE;

    public LRUCache(int capacity) {
        this.heap = new HashMap<>();
        this.pages = new ArrayDeque<>();
        this.CACHE_SIZE = capacity;
    }

    @Override
    public V get(K key) {
        V value = heap.get(key);
        if (value == null) {
            return null;
        }
        update(new Page<>(key, value));
        return value;
    }

    @Override
    public void put(K key, V value) {
        var page = new Page<>(key, value);
        if (!heap.containsKey(key)) {
            if (pages.size() == CACHE_SIZE) {
                var lastPage = pages.removeLast();
                heap.remove(lastPage.getKey());
            }
        } else {
            removePage(page);
        }
        addPage(page);
        heap.put(key, value);
    }

    @Override
    public void remove(K key) {
        heap.remove(key);
        pages.removeIf((page) -> Objects.equals(key, page.getKey()));
    }

    private void update(Page<K, V> page) {
        removePage(page);
        addPage(page);
    }


    private void removePage(Page<K, V> page) {
        pages.remove(page);
    }

    private void addPage(Page<K, V> page) {
        pages.push(page);
    }
}
