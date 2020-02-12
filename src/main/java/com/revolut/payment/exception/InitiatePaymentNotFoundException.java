package com.revolut.payment.exception;

import com.revolut.payment.model.InitiatePayment;

public class InitiatePaymentNotFoundException extends InitiatePaymentException {

    public InitiatePaymentNotFoundException( InitiatePayment req ) {
        super( req );
    }

    @Override
    public String getLocalizedMessage() {
        return getMessage();
    }

    @Override
    public String getMessage() {
        return "Not found initiate payment request exception";
    }

    @Override
    public int getCode() {
        return 1003;
    }

    @Override
    public int getStatus() {
        return 404;
    }

}
