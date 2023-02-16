package org.wise.com.domain.cache;

public sealed interface Cache<K, V> permits ConcurrentInMemoryCache {
    V get(K key);
    void put(K key, V value);
    void clear();
    boolean isEmpty();
}
