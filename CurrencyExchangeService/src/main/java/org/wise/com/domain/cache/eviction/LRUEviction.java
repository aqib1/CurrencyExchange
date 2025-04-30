package org.wise.com.domain.cache.eviction;

import org.wise.com.domain.cache.CacheEntry;

import java.util.LinkedHashSet;
import java.util.Map;

public non-sealed class LRUEviction<K, V> implements CacheEviction<K> {
    private final LinkedHashSet<K> orderedKey;
    private final Map<K, CacheEntry<V>> cache;
    private final int maxSize;

    public LRUEviction(Map<K, CacheEntry<V>> cache, int maxSize) {
        this.cache = cache;
        this.maxSize = maxSize;
        this.orderedKey = new LinkedHashSet<>(this.cache.keySet());
    }

    @Override
    public synchronized void evict(K key) {
        refreshOrder(key);
        removeIfExceed();

    }

    private void removeIfExceed() {
        if (this.cache.size() >= maxSize) {
            var oldestKey = this.orderedKey.getFirst();
            orderedKey.remove(oldestKey);
            this.cache.remove(oldestKey);
        }
    }

    private void refreshOrder(K key) {
        this.orderedKey.remove(key);
        if(cache.containsKey(key)) {
            this.orderedKey.add(key);
        }
    }
}
