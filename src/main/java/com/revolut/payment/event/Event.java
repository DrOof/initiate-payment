package com.revolut.payment.event;

public interface Event {

    /**
     * Gets the target that dispatched the event.
     * @return
     */
    public Object getTarget();

}