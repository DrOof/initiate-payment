package com.revolut.payment.task;

import com.revolut.payment.event.EventTarget;
import com.revolut.payment.model.InitiatePayment;

public class ProcessInitiatePaymentTask implements Runnable, EventTarget {

    private InitiatePayment request;

    public ProcessInitiatePaymentTask( InitiatePayment e ) {
        this.request = e;
    }

    @Override
    public void run() {

        request.attempt();
        process( request );

    }

    /**
     * @param e
     * @return
     */
    private InitiatePayment process( InitiatePayment e ) {

        // Attempt to settle the payment request against a core banking and/or financial messaging system.
        // step 1. debit from account
        // step 2. credit to account
        // step 3. store transaction on from account
        // step 4. store transaction on to account

        // just test fail or success
        // this.dispatchEvent( new ProcessFailed(), this );
        this.dispatchEvent( new ProcessSuccess( this ) );

        return e;

    }

    /**
     *
     * @return
     */
    public InitiatePayment getRequest() {
        return request;
    }
}
