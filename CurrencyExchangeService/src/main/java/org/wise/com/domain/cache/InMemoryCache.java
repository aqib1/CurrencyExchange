package org.wise.com.domain.cache;

import org.wise.com.domain.cache.exception.CacheKeyNotFoundException;

import java.util.HashMap;
import java.util.Map;

public non-sealed class InMemoryCache<K, V> implements Cache<K, V> {
    private final Map<K, V> cache;

    public InMemoryCache() {
        this.cache = new HashMap<>();
    }

    @Override
    public V get(K key) {
        if(!this.cache.containsKey(key)) {
            throw new CacheKeyNotFoundException(
                    String.format("Key %s not found in cache", key)
            );
        }

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
