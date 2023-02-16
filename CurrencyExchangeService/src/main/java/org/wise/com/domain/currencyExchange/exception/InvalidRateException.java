package org.wise.com.domain.currencyExchange.exception;

public class InvalidRateException extends RuntimeException {
    public InvalidRateException(String message) {
        super(message);
    }
}
