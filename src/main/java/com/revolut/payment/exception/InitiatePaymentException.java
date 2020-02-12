package com.revolut.payment.exception;

import com.revolut.payment.model.InitiatePayment;

public abstract class InitiatePaymentException extends Exception {

    private InitiatePayment req;

    public InitiatePaymentException( InitiatePayment req ) {
        super();
        this.req = req;
    }

    public abstract int getCode();

    public abstract int getStatus();

    public InitiatePayment getOriginalRequest() {
        return req;
    }

}
