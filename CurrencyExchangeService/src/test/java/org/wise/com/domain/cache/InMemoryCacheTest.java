package org.wise.com.domain.cache;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.wise.com.domain.cache.eviction.CacheEviction;
import org.wise.com.domain.cache.eviction.LRUEviction;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryCacheTest {
    private static final int MAX_SIZE = 20;
    public final InMemoryCache<String, String> inMemoryCache;

    public InMemoryCacheTest() {
        Map<String, CacheEntry<String>> concurrentMap = new ConcurrentHashMap<>();
        CacheEviction<String> eviction = new LRUEviction<>(concurrentMap, MAX_SIZE);
        this.inMemoryCache = new InMemoryCache<>(eviction, concurrentMap);
    }


    @AfterEach
    public void afterEach() {
        this.inMemoryCache.clear();
    }

    @Test
    public void put_WhenKeyAndValueProvided_ShouldStoreInCache() {
        // given
        this.inMemoryCache.put("test", "test", Optional.empty());

        // when
        var value = this.inMemoryCache.get("test");

        // then
        assertEquals(value, "test");
    }

    @Test
    public void get_WhenKeyIsProvided_ShouldReturnValue() {
        this.inMemoryCache.put("test", "test", Optional.empty());

        // when
        var value = this.inMemoryCache.get("test");

        // then
        assertEquals(value, "test");
    }

    @Test
    public void clear_ShouldClearCache() {
        // when
        this.inMemoryCache.put("test", "test", Optional.empty());

        this.inMemoryCache.clear();

        assertTrue(this.inMemoryCache.isEmpty());
    }

    @Test
    public void get_WhenKeyLimitExceeded_ShouldReturnNull() throws InterruptedException {
        for(int i = 0; i <= 22; i++) {
            this.inMemoryCache.put("test" + i, "test", Optional.empty());
        }
        TimeUnit.SECONDS.sleep(1);

        // when
        var value = this.inMemoryCache.get("test0");

        // then
        assertNull(value);
    }
}
