package org.wise.com.domain.currencyExchange.record;

import org.wise.com.domain.currencyExchange.exception.InvalidRateException;

import java.math.BigDecimal;

public record CurrencyExchange(
        String fromCurrency,
        String toCurrency,
        BigDecimal rate
) {
    public CurrencyExchange {
        if(rate == null || rate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidRateException(
                    String.format("Invalid rate %s defined.", rate)
            );
        }
    }

    public String generateKey() {
        return fromCurrency
                .concat("->")
                .concat(toCurrency);
    }

    public String generateKey(String fromCurrency, String toCurrency) {
        return fromCurrency
                .concat("->")
                .concat(toCurrency);
    }
}
