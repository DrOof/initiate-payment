package com.revolut.payment.service;

import com.revolut.payment.exception.InitiatePaymentIsIdempotentException;
import com.revolut.payment.exception.InitiatePaymentNotFoundException;
import com.revolut.payment.model.InitiatePayment;

import java.util.List;

public interface InitiatePaymentService {

    /**
     * Create an initiate payment request
     *
     * @return
     */
    public InitiatePayment create( String userId, InitiatePayment req ) throws InitiatePaymentIsIdempotentException;

    /**
     * Read all initiate payment requests
     *
     * @return
     */
    public List<InitiatePayment> read( String userId );

    /**
     * Read an initiate payment request by id
     *
     * @param id
     * @return
     */
    public InitiatePayment read( String userId, String id ) throws InitiatePaymentNotFoundException;

    /**
     * Update the status of an initiate payment request by id
     *
     * @param id
     * @param status
     * @return
     */
    public InitiatePayment update( String userId, String id, InitiatePayment.Status status ) throws InitiatePaymentNotFoundException;

    /**
     * Remove an initiate payment request by id
     *
     * @param id
     * @return
     */
    public InitiatePayment delete( String userId, String id ) throws InitiatePaymentNotFoundException;

}
