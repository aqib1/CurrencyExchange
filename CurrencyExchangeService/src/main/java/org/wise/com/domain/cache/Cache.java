package org.wise.com.domain.cache;

import java.util.Map;
import java.util.Optional;

public sealed interface Cache<K, V> permits InMemoryCache {
    V get(K key);
    void put(K key, V value, Optional<Long> ttlMillis);
    void remove(K key);
    void clear();
    boolean isEmpty();
}
