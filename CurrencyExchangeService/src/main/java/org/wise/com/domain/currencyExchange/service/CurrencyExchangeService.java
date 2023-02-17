package org.wise.com.domain.currencyExchange.service;

import org.wise.com.domain.cache.ConcurrentInMemoryCache;
import org.wise.com.domain.currencyExchange.exception.CurrencyExchangeKeyNotFoundException;
import org.wise.com.domain.currencyExchange.record.CurrencyExchange;

import java.math.BigDecimal;
import java.util.Objects;

public class CurrencyExchangeService {
    private final ConcurrentInMemoryCache<String, CurrencyExchange>
            concurrentCurrencyExchangeCache;

    public CurrencyExchangeService() {
        this.concurrentCurrencyExchangeCache = new ConcurrentInMemoryCache<>();
    }

    public void saveCurrencyExchangeRate(
            CurrencyExchange exchangeRecord,
            long ttlMillis
    ) {
        concurrentCurrencyExchangeCache.put(exchangeRecord.generateKey(), exchangeRecord, ttlMillis);
    }

    public BigDecimal getCurrencyExchangeRate(String key) {
        var currencyExchange = concurrentCurrencyExchangeCache.get(key);
        if(Objects.isNull(currencyExchange)) {
            throw new CurrencyExchangeKeyNotFoundException(
                    String.format("Key %s not found in cache", key)
            );
        }
        return currencyExchange.rate();
    }

    public void clearAll() {
        this.concurrentCurrencyExchangeCache.clear();
    }
}
