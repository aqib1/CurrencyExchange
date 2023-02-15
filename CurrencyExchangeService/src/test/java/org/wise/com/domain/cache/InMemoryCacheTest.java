package org.wise.com.domain.cache;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.wise.com.domain.cache.exception.CacheKeyNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryCacheTest {
    public final InMemoryCache<String, String> inMemoryCache;

    public InMemoryCacheTest() {
        this.inMemoryCache = new InMemoryCache<>();
    }


    @AfterEach
    public void afterEach() {
        this.inMemoryCache.clear();
    }

    @Test
    public void put_WhenKeyAndValueProvided_ShouldStoreInCache() {
        // given
        this.inMemoryCache.put("test", "test");

        // when
        var value = this.inMemoryCache.get("test");

        // then
        assertEquals(value, "test");
    }

    @Test
    public void get_WhenKeyIsProvided_ShouldReturnValue() {
        this.inMemoryCache.put("test", "test");

        // when
        var value = this.inMemoryCache.get("test");

        // then
        assertEquals(value, "test");
    }

    @Test
    public void get_WhenKeyNotExists_ShouldThrowException() {
        this.inMemoryCache.put("test", "test");
        assertThrows(
                CacheKeyNotFoundException.class,
                () -> this.inMemoryCache.get("Unknown")
        );
    }

    @Test
    public void clear_ShouldClearCache() {
        // when
        this.inMemoryCache.put("test", "test");

        this.inMemoryCache.clear();

        assertTrue(this.inMemoryCache.isEmpty());
    }
}
