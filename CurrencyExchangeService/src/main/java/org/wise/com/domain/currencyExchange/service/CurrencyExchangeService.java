package org.wise.com.domain.currencyExchange.service;

import org.wise.com.domain.cache.InMemoryCache;
import org.wise.com.domain.currencyExchange.exception.CurrencyExchangeKeyNotFoundException;
import org.wise.com.domain.currencyExchange.record.CurrencyExchange;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public class CurrencyExchangeService {
    private final InMemoryCache<String, CurrencyExchange>
            currencyExchangeCache;

    public CurrencyExchangeService(
            InMemoryCache<String, CurrencyExchange> currencyExchangeCache
    ) {
        this.currencyExchangeCache = currencyExchangeCache;
    }

    public void saveCurrencyExchangeRate(
            CurrencyExchange exchangeRecord,
            long ttlMillis
    ) {
        currencyExchangeCache.put(exchangeRecord.generateKey(), exchangeRecord, Optional.of(ttlMillis));
    }

    public BigDecimal getCurrencyExchangeRate(String key) {
        var currencyExchange = currencyExchangeCache.get(key);
        if(Objects.isNull(currencyExchange)) {
            throw new CurrencyExchangeKeyNotFoundException(
                    String.format("Key %s not found in cache", key)
            );
        }
        return currencyExchange.rate();
    }

    public void clearAll() {
        this.currencyExchangeCache.clear();
    }
}
