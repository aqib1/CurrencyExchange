package org.wise.com.domain.cache.exception;

public class CacheKeyNotFoundException extends RuntimeException {
    public CacheKeyNotFoundException(String message) {
        super(message);
    }
}
