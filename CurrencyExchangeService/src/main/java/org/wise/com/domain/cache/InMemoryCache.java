package org.wise.com.domain.cache;

import org.wise.com.domain.cache.eviction.CacheEviction;

import java.util.Map;
import java.util.Optional;

public non-sealed class InMemoryCache<K, V> implements Cache<K, V> {
    private final Map<K, CacheEntry<V>> cache;
    private final CacheEviction<K> evictionPolicy;

    public InMemoryCache(CacheEviction<K> evictionPolicy, Map<K, CacheEntry<V>> cache) {
        this.cache = cache;
        this.evictionPolicy = evictionPolicy;
    }

    @Override
    public V get(K key) {
        var cacheEntry = this.cache.get(key);

        if(cacheEntry == null)
            return null;

        evictionPolicy.evict(key);
        return cacheEntry.getValue();
    }

    @Override
    public void put(K key, V value, Optional<Long> ttlMillis) {
        this.cache.put(key, new CacheEntry<>(value, ttlMillis.orElse(0L)));
        evictionPolicy.evict(key);
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
