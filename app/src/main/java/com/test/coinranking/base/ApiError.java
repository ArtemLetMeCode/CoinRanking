package com.test.coinranking.base;

public class ApiError {

    private String errorName;
    private String errorKey;
    private int code = -1;
    private boolean isOffline;

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

    public String getErrorKey() {
        return errorKey;
    }

    public void setErrorKey(String errorKey) {
        this.errorKey = errorKey;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isOffline() {
        return isOffline;
    }

    public void setOffline(boolean offline) {
        isOffline = offline;
    }


}
