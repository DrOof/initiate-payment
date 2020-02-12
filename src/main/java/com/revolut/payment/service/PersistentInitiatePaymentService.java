package com.revolut.payment.service;

import com.revolut.payment.exception.InitiatePaymentIsIdempotentException;
import com.revolut.payment.exception.InitiatePaymentNotFoundException;
import com.revolut.payment.model.InitiatePayment;

import java.util.List;

// TODO: implement persistent service with jOOQ
public class PersistentInitiatePaymentService implements InitiatePaymentService {

    @Override
    public InitiatePayment create( String userId, InitiatePayment req ) throws InitiatePaymentIsIdempotentException {
        return null;
    }

    @Override
    public List<InitiatePayment> read( String userId ) {
        return null;
    }

    @Override
    public InitiatePayment read( String userId, String id ) throws InitiatePaymentNotFoundException {
        return null;
    }

    @Override
    public InitiatePayment update( String userId, String id, InitiatePayment.Status status ) throws InitiatePaymentNotFoundException {
        return null;
    }

    @Override
    public InitiatePayment delete( String userId, String id ) throws InitiatePaymentNotFoundException {
        return null;
    }

}
