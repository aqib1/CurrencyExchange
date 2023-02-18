package org.wise.com.domain.cache.eviction;

public sealed interface CacheEviction<K>
        permits ScheduleEviction, LRUEviction {

    default void evict(K key) {}
}


