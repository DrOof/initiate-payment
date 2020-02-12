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

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getIat() {
        return iat;
    }

    public void setIat( String iat ) {
        this.iat = iat;
    }

    public String getSub() {
        return sub;
    }

    public void setSub( String sub ) {
        this.sub = sub;
    }
}