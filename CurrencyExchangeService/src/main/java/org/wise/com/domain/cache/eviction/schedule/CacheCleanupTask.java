package org.wise.com.domain.cache.eviction.schedule;

import org.wise.com.domain.cache.CacheEntry;

import java.util.Map;

public class CacheCleanupTask<K, V> implements Runnable {
    private final Map<K, CacheEntry<V>> cache;

    public CacheCleanupTask(Map<K, CacheEntry<V>> cache) {
        this.cache = cache;
    }

    @Override
    public void run() {
        var keys = this.cache.keySet();
        for(var key: keys) {
            if(this.cache.get(key).isExpired()) {
                this.cache.remove(key);
            }
        }
    }
}
