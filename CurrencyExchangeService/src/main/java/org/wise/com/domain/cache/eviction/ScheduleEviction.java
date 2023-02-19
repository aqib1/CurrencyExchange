package org.wise.com.domain.cache.eviction;

import org.wise.com.domain.cache.CacheEntry;
import org.wise.com.domain.cache.eviction.schedule.CacheCleanupTask;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public non-sealed class ScheduleEviction<K, V> implements CacheEviction<K> {
    private final CacheCleanupTask<K, V> cleanupTask;
    private final int corePoolSize;
    private final int initialDelay;
    private final int period;
    private final TimeUnit timeUnit;
    private ScheduledExecutorService service;

    public ScheduleEviction(
            Map<K, CacheEntry<V>> cache,
            int corePoolSize,
            int initialDelay,
            int period,
            TimeUnit timeUnit
    ) {
        this.corePoolSize = corePoolSize;
        this.initialDelay = initialDelay;
        this.period = period;
        this.timeUnit = timeUnit;
        this.cleanupTask = new CacheCleanupTask<>(cache);
        initAndStartScheduler();
    }

    private void initAndStartScheduler() {
        service = Executors.newScheduledThreadPool(corePoolSize);
        service.scheduleAtFixedRate(
                cleanupTask,
                initialDelay,
                period,
                timeUnit
        );
    }

    public void shutDown() {
        if (service != null && !service.isShutdown()) {
            service.shutdown();
        }
    }
}
