package com.revolut.payment.app;

import com.google.inject.AbstractModule;
import com.revolut.payment.service.InMemoryInitiatePaymentService;
import com.revolut.payment.service.InitiatePaymentService;

public class InitiatePaymentAppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind( InitiatePaymentService.class ).to( InMemoryInitiatePaymentService.class );
    }

}
