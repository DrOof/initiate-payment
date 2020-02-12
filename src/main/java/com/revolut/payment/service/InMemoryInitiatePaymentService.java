package com.revolut.payment.service;

import com.revolut.payment.exception.InitiatePaymentNotFoundException;
import com.revolut.payment.model.InitiatePayment;
import com.revolut.payment.exception.InitiatePaymentIsIdempotentException;
import com.revolut.payment.task.ProcessFailed;
import com.revolut.payment.task.ProcessInitiatePaymentTask;
import com.revolut.payment.task.ProcessSuccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class InMemoryInitiatePaymentService implements InitiatePaymentService {

    private static final Logger logger = LoggerFactory.getLogger( InMemoryInitiatePaymentService.class );

    private HashMap<String, LinkedList<InitiatePayment>> queue = new HashMap<>();
    private ScheduledExecutorService ses = Executors.newScheduledThreadPool( 1 );

    /**
     * @inheritDoc
     */
    @Override
    public InitiatePayment create( String sub, InitiatePayment req ) throws InitiatePaymentIsIdempotentException {

        if ( isIdempotentInitiatePayment( sub, req ) ) {
            throw new InitiatePaymentIsIdempotentException( req );
        }

        if ( queue.get( sub ) == null ) {
            queue.put( sub, new LinkedList<>() );
        }

        queue.get( sub ).add( req );
        process( req );

        return req;

    }

    /**
     * @inheritDoc
     */
    @Override
    public List<InitiatePayment> read( String sub ) throws InitiatePaymentNotFoundException {

        List<InitiatePayment> result = queue.get( sub );

        if ( result == null ) {
            throw new InitiatePaymentNotFoundException( null );
        }

        return result;
    }

    /**
     * @inheritDoc
     */
    @Override
    public InitiatePayment read( String sub, String id ) throws InitiatePaymentNotFoundException {

        InitiatePayment result = null;
        try {
            result = queue.get( sub ).stream().filter( ( p ) -> p.getId().equals( id ) ).findFirst().get();
        } catch ( NoSuchElementException e ) {
            throw new InitiatePaymentNotFoundException( result );
        }

        return result;

    }

    /**
     * @inheritDoc
     */
    @Override
    public InitiatePayment update( String sub, String id, InitiatePayment.Status status ) throws InitiatePaymentNotFoundException {
        return update( read( sub, id ), status );
    }

    /**
     * @inheritDoc
     */
    public InitiatePayment update( InitiatePayment request, InitiatePayment.Status status ) {

        request.setStatus( status );
        process( request );

        return request;

    }

    /**
     * @inheritDoc
     */
    @Override
    public InitiatePayment delete( String sub, String id ) throws InitiatePaymentNotFoundException {
        return delete( read( sub, id ) );
    }

    /**
     *
     * @param request
     * @return
     */
    public InitiatePayment delete( InitiatePayment request ) {

        queue.get( request.getUser().getSub() ).remove( request );
        process( request );

        return request;

    }

    /**
     * If the payment has a similar hash, and is done within an hour of the previous one, it is considered the same payment.
     *
     * @param e
     * @return
     */
    private boolean isIdempotentInitiatePayment( String sub, InitiatePayment e ) {

        List<InitiatePayment> results = null;

        try {
            results = queue.get( sub ).stream()
                    .filter( ( event ) -> e.isIdempotentInitiatePayment( event ) )
                    .collect( Collectors.toList() );
        } catch ( Exception ex ) {
            //
        }

        return results != null;

    }



    /**
     * Process initiate payment event
     *
     * @param request
     * @return
     */
    private InitiatePayment process( InitiatePayment request ) {

        switch ( request.getStatus() ) {

            case AWAITING_AUTHORISATION:
                // ignore
                break;

            case AUTHORISED:

                // schedule immediately.
                // schedule after 3 hours.
                // schedule after 6 hours.
                // schedule after 12 hours.
                // schedule after 24 hours.
                var task = new ProcessInitiatePaymentTask( request );

                try {

                    task.addEventListener( ProcessFailed.class, this );
                    task.addEventListener( ProcessSuccess.class, this );

                } catch ( Exception e ) {
                    //
                }

                ses.schedule( task, 0, TimeUnit.SECONDS );

                break;

            case REJECTED:

                // ignore
                break;

            case CONSUMED:

                // For the sake of this test, rejected and consumed events are removed.
                // request = delete( request.getId() );
                break;

        }

        return request;

    }

    /**
     * Handle the event that a process initiate payment task fails
     *
     * @param e
     */
    public void handleProcessFailed( ProcessFailed e ) {
        logger.debug( e.getClass().getSimpleName() );
        logger.info( "processfailed: " + e.getTarget().toString() );
    }

    /**
     * Handle the event that a process initiate payment task succeeds
     *
     * @param e
     */
    public void handleProcessSuccess( ProcessSuccess e ) {
        update( e.getTarget().getRequest(), InitiatePayment.Status.CONSUMED );
        logger.info( "processsuccess: " + e.getTarget().toString() );
    }

}
