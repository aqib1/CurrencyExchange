package org.wise.com.domain.cache;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

public class ConcurrentInMemoryCacheTest {
    public final ConcurrentInMemoryCache<String, String> concurrentInMemoryCache;

    public ConcurrentInMemoryCacheTest() {
        this.concurrentInMemoryCache = new ConcurrentInMemoryCache<>();
    }


    @AfterEach
    public void afterEach() {
        this.concurrentInMemoryCache.clear();
    }

    @Test
    public void put_WhenKeyAndValueProvided_ShouldStoreInCache() {
        // given
        this.concurrentInMemoryCache.put("test", "test", 1000);

        // when
        var value = this.concurrentInMemoryCache.get("test");

        // then
        assertEquals(value, "test");
    }

    @Test
    public void get_WhenKeyIsProvided_ShouldReturnValue() {
        this.concurrentInMemoryCache.put("test", "test", 1000);

        // when
        var value = this.concurrentInMemoryCache.get("test");

        // then
        assertEquals(value, "test");
    }

    @Test
    public void clear_ShouldClearCache() {
        // when
        this.concurrentInMemoryCache.put("test", "test", 1000);

        this.concurrentInMemoryCache.clear();

        assertTrue(this.concurrentInMemoryCache.isEmpty());
    }

    @Test
    public void get_WhenKeyIsExpired_ShouldReturnNull() throws InterruptedException {
        this.concurrentInMemoryCache.put("test", "test", 500);

        TimeUnit.SECONDS.sleep(1);

        // when
        var value = this.concurrentInMemoryCache.get("test");

        // then
        assertNull(value);
    }
}
