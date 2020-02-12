package com.revolut.payment.model;

import java.util.Objects;

public class Account {

    private String id;
    private String currency;
    private String name;
    private String BBAN;

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Account account = (Account) o;
        return Objects.equals( id, account.id ) &&
                Objects.equals( currency, account.currency ) &&
                Objects.equals( name, account.name ) &&
                Objects.equals( BBAN, account.BBAN );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id, currency, name, BBAN );
    }
}
