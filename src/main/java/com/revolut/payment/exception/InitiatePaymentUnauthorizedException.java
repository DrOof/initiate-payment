package com.revolut.payment.exception;

import com.revolut.payment.model.InitiatePayment;

public class InitiatePaymentUnauthorizedException extends InitiatePaymentException {

    public InitiatePaymentUnauthorizedException( InitiatePayment req ) {
        super( req );
    }

    @Override
    public String getLocalizedMessage() {
        return getMessage();
    }

    @Override
    public String getMessage() {
        return "Unauthorized initiate payment request exception";
    }

    @Override
    public int getStatus() {
        return 401;
    }

    @Override
    public int getCode() {
        return 1002;
    }
}
