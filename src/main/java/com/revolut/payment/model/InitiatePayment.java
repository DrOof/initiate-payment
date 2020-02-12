package com.revolut.payment.model;

import java.util.Date;
import java.util.UUID;

public class InitiatePayment {

    private static final int IDEMPOTENT_THRESHOLD = 10000;

    private final String id;
    private final Date timestamp;

    private User user;
    private Status status;
    private Payment payment;
    private int attempts = 0;

    public enum Status {
        AWAITING_AUTHORISATION,
        REJECTED,
        AUTHORISED,
        CONSUMED
    }

    public InitiatePayment() {

        id = UUID.randomUUID().toString();
        timestamp = new Date();
        status = Status.AWAITING_AUTHORISATION;

    }

    public InitiatePayment( User user, Payment payment ) {
        this();
        setPayment( payment );
        setUser( user );
    }

    public User getUser() {
        return user;
    }

    public void setUser( User user ) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus( Status status ) {
        this.status = status;
    }

    public void setPayment( Payment payment ) {
        this.payment = payment;
    }

    public Payment getPayment() {
        return payment;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getId() {
        return id;
    }

    public void attempt() {
        setAttempts( attempts++ );
    }

    public void setAttempts( int attempts ) {
        this.attempts = attempts;
    }

    public int getAttempts() {
        return attempts;
    }

    /**
     * Check whether another request is idem potent
     * ie. the details are identical and the request is done within the idempotent threshold
     *
     * @param e
     * @return
     */
    public boolean isIdempotentInitiatePayment( InitiatePayment e ) {

        long delta = timestamp.getTime() - e.getTimestamp().getTime();
        return (e.getPayment().hashCode() == payment.hashCode()) && (delta < IDEMPOTENT_THRESHOLD);

    }

}
