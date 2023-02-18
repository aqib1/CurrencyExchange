package org.wise.com.domain.currencyExchange;

import org.junit.jupiter.api.Test;
import org.wise.com.domain.cache.CacheEntry;
import org.wise.com.domain.cache.InMemoryCache;
import org.wise.com.domain.cache.eviction.CacheEviction;
import org.wise.com.domain.cache.eviction.LRUEviction;
import org.wise.com.domain.currencyExchange.exception.CurrencyExchangeKeyNotFoundException;
import org.wise.com.domain.currencyExchange.record.CurrencyExchange;
import org.wise.com.domain.currencyExchange.service.CurrencyExchangeService;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CurrencyExchangeServiceTest {
    private static final int MAX_SIZE = 20;
    private final CurrencyExchangeService currencyExchangeService;

    public CurrencyExchangeServiceTest() {
        Map<String, CacheEntry<CurrencyExchange>> concurrentMap = new ConcurrentHashMap<>();
        CacheEviction<String> eviction = new LRUEviction<>(concurrentMap, MAX_SIZE);
        InMemoryCache<String, CurrencyExchange> cache = new InMemoryCache<>(eviction, concurrentMap);
        this.currencyExchangeService = new CurrencyExchangeService(cache);
    }

    @Test
    public void afterEach() {
        this.currencyExchangeService.clearAll();
    }

    @Test
    public void save_WhenCurrencyExchangeRecordProvided_Success() {
        // given
        var currencyExchangeRecord = new CurrencyExchange(
                "USD",
                "PKR",
                BigDecimal.valueOf(281)
        );

        // when
        currencyExchangeService.saveCurrencyExchangeRate(currencyExchangeRecord, 1000);

        // then
        var exchangeRate = currencyExchangeService.getCurrencyExchangeRate(currencyExchangeRecord.generateKey());
        assertEquals(exchangeRate, BigDecimal.valueOf(281));
    }

    @Test
    public void get_CurrencyExchangeWhenKeyIsProvided_Success() {
        // given
        var currencyExchangeRecord = new CurrencyExchange(
                "USD",
                "PKR",
                BigDecimal.valueOf(281)
        );
        currencyExchangeService.saveCurrencyExchangeRate(currencyExchangeRecord, 1000);

        // when
        var exchangeRate = currencyExchangeService.getCurrencyExchangeRate(currencyExchangeRecord.generateKey());

        // then
        assertEquals(exchangeRate, BigDecimal.valueOf(281));
    }

    @Test
    public void get_CurrencyExchangeWhenKeyNotExists_ShouldThrowException() {
        // given
        var currencyExchangeRecord = new CurrencyExchange(
                "USD",
                "PKR",
                BigDecimal.valueOf(281)
        );
        currencyExchangeService.saveCurrencyExchangeRate(currencyExchangeRecord, 1000);

        // when & then
        assertThrows(CurrencyExchangeKeyNotFoundException.class,
                () -> currencyExchangeService.getCurrencyExchangeRate(
                        currencyExchangeRecord.generateKey("CAD", "PKR")
                ));
    }

    @Test
    public void get_CurrencyExchangeWhenKeyLimitExceed_ShouldThrowException() throws InterruptedException {
        // given
        for(int i = 0; i <= 22; i++) {
            var currencyExchangeRecord = new CurrencyExchange(
                    "USD" + i,
                    "PKR",
                    BigDecimal.valueOf(281)
            );
            currencyExchangeService.saveCurrencyExchangeRate(
                    currencyExchangeRecord,
                    0
            );
        }


        // when & then
        assertThrows(CurrencyExchangeKeyNotFoundException.class,
                () -> currencyExchangeService.getCurrencyExchangeRate(
                        "USD0"
                ));
    }
}
