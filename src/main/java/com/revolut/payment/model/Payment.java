package com.revolut.payment.model;

import java.util.Objects;

public class Payment {

    private Account from;
    private Account to;
    private Consignment consignment;

    public Payment( Account from, Account to, Consignment consignment ) {

        this.from = from;
        this.to = to;
        this.consignment = consignment;

    }

    public Account getFrom() {
        return from;
    }

    public void setFrom( Account from ) {
        this.from = from;
    }

    public Account getTo() {
        return to;
    }

    public void setTo( Account to ) {
        this.to = to;
    }

    public Consignment getConsignment() {
        return consignment;
    }

    public void setConsignment( Consignment consignment ) {
        this.consignment = consignment;
    }

    @Override
    public boolean equals( Object o ) {

        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Payment payment = (Payment) o;

        return Objects.equals( from, payment.from ) &&
                Objects.equals( to, payment.to ) &&
                Objects.equals( consignment, payment.consignment );
    }

    @Override
    public int hashCode() {
        return Objects.hash( from, to, consignment );
    }
}
