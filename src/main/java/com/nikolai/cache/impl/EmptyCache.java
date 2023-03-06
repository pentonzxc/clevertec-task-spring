package com.nikolai.cache.impl;

import com.nikolai.cache.Cache;

public final class EmptyCache implements Cache<Object, Object> {

    @Override
    public Object get(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void put(Object key, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(Object key) {
        throw new UnsupportedOperationException();
    }
}
