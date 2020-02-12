package com.revolut.payment.model;

import java.util.Objects;

public class Consignment {

    private String currency;
    private Float amount;

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Consignment that = (Consignment) o;
        return Objects.equals( currency, that.currency ) &&
                Objects.equals( amount, that.amount );
    }

    @Override
    public int hashCode() {
        return Objects.hash( currency, amount );
    }
}
