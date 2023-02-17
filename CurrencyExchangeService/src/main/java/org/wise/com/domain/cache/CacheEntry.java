package org.wise.com.domain.cache;

import java.time.Instant;

public class CacheEntry<V> {
    private final V value;
    private final Instant timestamp;
    private final long ttlMillis;

    public CacheEntry(V value, long ttlMillis) {
        this.value = value;
        this.timestamp = Instant.now();
        this.ttlMillis = ttlMillis;
    }

    public V getValue() {
        return this.value;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(timestamp.plusMillis(ttlMillis));
    }
}
