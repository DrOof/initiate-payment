package com.revolut.payment.model;

public class User {

    private String name;
    private String iat;
    private String sub;

    public User( String iat, String name, String sub ) {
        this.iat = iat;
        this.name = name;
        this.sub = sub;
    }

    public String getSub() {
        return sub;
    }

}