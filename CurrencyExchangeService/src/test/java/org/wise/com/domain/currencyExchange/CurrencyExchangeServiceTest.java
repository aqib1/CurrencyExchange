package org.wise.com.domain.currencyExchange;

import org.junit.jupiter.api.Test;
import org.wise.com.domain.currencyExchange.exception.CurrencyExchangeKeyNotFoundException;
import org.wise.com.domain.currencyExchange.record.CurrencyExchange;
import org.wise.com.domain.currencyExchange.service.CurrencyExchangeService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CurrencyExchangeServiceTest {
    private final CurrencyExchangeService currencyExchangeService;

    public CurrencyExchangeServiceTest() {
        this.currencyExchangeService = new CurrencyExchangeService();
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
        currencyExchangeService.saveCurrencyExchangeRate(currencyExchangeRecord);

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
        currencyExchangeService.saveCurrencyExchangeRate(currencyExchangeRecord);

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
        currencyExchangeService.saveCurrencyExchangeRate(currencyExchangeRecord);

        // when & then
        assertThrows(CurrencyExchangeKeyNotFoundException.class,
                () -> currencyExchangeService.getCurrencyExchangeRate(
                        currencyExchangeRecord.generateKey("CAD", "PKR")
                ));
    }
}
