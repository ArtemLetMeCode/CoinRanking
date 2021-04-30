package com.test.coinranking.base;

import java.io.IOException;

public class NoConnectivityException extends IOException {

    private String message;

    public NoConnectivityException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
