package com.revolut.payment.exception;

import com.revolut.payment.model.InitiatePayment;

public class InitiatePaymentIsIdempotentException extends InitiatePaymentException {

    public InitiatePaymentIsIdempotentException( InitiatePayment req ) {
        super( req );
    }

    @Override
    public String getLocalizedMessage() {
        return getMessage();
    }

    @Override
    public String getMessage() {
        return "Idempotent initiate payment request";
    }

    @Override
    public int getStatus() {
        return 400;
    }

    @Override
    public int getCode() {
        return 1001;
    }
}
