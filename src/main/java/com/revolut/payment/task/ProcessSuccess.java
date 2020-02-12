package com.revolut.payment.task;

import com.revolut.payment.event.Event;

public class ProcessSuccess implements Event {

    private ProcessInitiatePaymentTask target;

    public ProcessSuccess( ProcessInitiatePaymentTask target ) {
        this.target = target;
    }

    @Override
    public ProcessInitiatePaymentTask getTarget() {
        return target;
    }

}
