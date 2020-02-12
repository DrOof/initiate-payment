package com.revolut.payment.task;

import com.revolut.payment.event.Event;

public class ProcessFailed implements Event {

    private ProcessInitiatePaymentTask target;

    public ProcessFailed( ProcessInitiatePaymentTask target ) {
        this.target = target;
    }

    @Override
    public ProcessInitiatePaymentTask getTarget() {
        return target;
    }
}
