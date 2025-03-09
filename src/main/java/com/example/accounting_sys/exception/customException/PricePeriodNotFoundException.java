package com.example.accounting_sys.exception.customException;

public class PricePeriodNotFoundException extends RuntimeException{
    public PricePeriodNotFoundException(String message) {
        super(message);
    }
}
