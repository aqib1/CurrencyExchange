package org.wise.com.domain.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public non-sealed class ConcurrentInMemoryCache<K, V> implements Cache<K, V> {
    private final Map<K, CacheEntry<V>> cache;

    public ConcurrentInMemoryCache() {
        this.cache = new ConcurrentHashMap<>();
    }

    @Override
    public V get(K key) {
        var cacheEntry = this.cache.get(key);
        if(cacheEntry.isExpired()) {
            remove(key);
            return null;
        }

        return cacheEntry.getValue();
    }

    @Override
    public void put(K key, V value, long ttlMillis) {
        this.cache.put(key, new CacheEntry<>(value, ttlMillis));
    }

    @Override
    public void remove(K key) {
        this.cache.remove(key);
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
