package com.nikolai.cache;

import java.util.Objects;

public class Page<K, V> {
    private K key;

    private V value;


    public Page(K key, V value) {
        this.key = key;
        this.value = value;
    }


    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page<?, ?> page = (Page<?, ?>) o;
        return Objects.equals(key, page.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
