package com.revolut.payment.exception;

import com.revolut.payment.model.InitiatePayment;

public class InitiatePaymentExceptionMessage {

    private int code;
    private String message;
    private InitiatePayment request;

    public InitiatePaymentExceptionMessage( InitiatePaymentException e ) {
        this.code = e.getCode();
        this.message = e.getMessage();
        this.request = e.getOriginalRequest();
    }

}
