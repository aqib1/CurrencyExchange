package org.wise.com.domain.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public non-sealed class ConcurrentInMemoryCache<K, V> implements Cache<K, V> {
    private final Map<K, V> cache;

    public ConcurrentInMemoryCache() {
        this.cache = new ConcurrentHashMap<>();
    }

    @Override
    public V get(K key) {
        return this.cache.get(key);
    }

    @Override
    public void put(K key, V value) {
        this.cache.put(key, value);
    }

    @Override
    public void clear() {
        this.cache.clear();
    }

    @Override
    public boolean isEmpty() {
        return this.cache.isEmpty();
    }
}
