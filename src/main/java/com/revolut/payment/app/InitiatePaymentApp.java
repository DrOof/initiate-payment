package com.revolut.payment.app;

import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.revolut.payment.exception.*;
import com.revolut.payment.model.InitiatePayment;
import com.revolut.payment.model.User;
import com.revolut.payment.response.GsonResponseTransformer;
import com.revolut.payment.service.InitiatePaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.Base64;
import java.util.List;

import static spark.Spark.*;

public class InitiatePaymentApp {

    private static final Logger logger = LoggerFactory.getLogger( InitiatePaymentApp.class );

    private static InitiatePaymentService service;

    public static void main( String[] args ) {

        // Use injector to switch between service implementations
        Injector injector = Guice.createInjector( new InitiatePaymentAppModule() );
        service = injector.getInstance( InitiatePaymentService.class );

        // TODO: apply security
        before( ( req, res ) -> InitiatePaymentApp.authorise( req, res ) );

        // Set Content Type
        after( ( req, res ) -> res.type( "application/json" ) );

        // Handle errors
        exception( InitiatePaymentException.class, ( e, req, res ) -> InitiatePaymentApp.handleInitiatePaymentRequestException( e, req, res ) );

        // create
        post( "/payment", ( req, res ) -> InitiatePaymentApp.handleInitiatePayment( req, res ), new GsonResponseTransformer() );

        // list payments
        get( "/payment/:id", ( req, res ) -> InitiatePaymentApp.handleReadInitiatePayment( req, res ), new GsonResponseTransformer() );
        get( "/payment", ( req, res ) -> InitiatePaymentApp.handleReadAllInitiatePayment( req, res ), new GsonResponseTransformer() );

        // authorise / reject
        put( "/payment/:id/authorise", ( req, res ) -> InitiatePaymentApp.handleAuthoriseInitiatePayment( req, res ), new GsonResponseTransformer() );
        put( "/payment/:id/reject", ( req, res ) -> InitiatePaymentApp.handleRejectInitiatePayment( req, res ), new GsonResponseTransformer() );

    }

    /**
     * Authorise a request
     * ie. check a bearer token and inject the identity
     *
     * @param req
     * @param res
     * @return
     */
    public static void authorise( Request req, Response res ) throws InitiatePaymentUnauthorizedException {

        String result = req.headers( "Authorization" );
        if ( result == null ) {
            throw new InitiatePaymentUnauthorizedException( null );
        }

        User user = null;
        try {

            result = result.replace( "Bearer ", "" );
            result = result.split( "\\." )[1];
            result = new String( Base64.getDecoder().decode( result ) );

            user = new Gson().fromJson( result, User.class );
            req.attribute( "sub", user.getSub() );

        } catch ( Exception e ) {
            throw new InitiatePaymentUnauthorizedException( null );
        }

    }


    /**
     * Initiate a payment request
     *
     * @param req
     * @param res
     * @return
     */
    public static InitiatePayment handleInitiatePayment( Request req, Response res ) throws InitiatePaymentIsIdempotentException {


        return service.create( req.attribute( "sub" ), new Gson().fromJson( req.body(), InitiatePayment.class ) );
    }

    /**
     * Handles a scenario where a user reads an initiated payment by id
     *
     * @param req
     * @param res
     * @return
     */
    public static InitiatePayment handleReadInitiatePayment( Request req, Response res ) throws InitiatePaymentNotFoundException {
        return service.read( req.attribute( "sub" ), req.params( "id" ) );

    }

    /**
     * Handles a scenario where a user reads all initiated payments
     *
     * @param req
     * @param res
     * @return
     */
    public static List<InitiatePayment> handleReadAllInitiatePayment( Request req, Response res ) throws InitiatePaymentNotFoundException {
        return service.read( req.attribute( "sub" ) );
    }

    /**
     * Handles a scenario where a user authorises an initiate payment
     *
     * @param req
     * @param res
     * @return
     */
    public static InitiatePayment handleAuthoriseInitiatePayment( Request req, Response res ) throws InitiatePaymentNotFoundException {
        return service.update( req.attribute( "sub" ), req.params( "id" ), InitiatePayment.Status.AUTHORISED );
    }

    /**
     * Handles a scenario where a user rejects an initiate payment
     *
     * @param req
     * @param res
     * @return
     */
    public static InitiatePayment handleRejectInitiatePayment( Request req, Response res ) throws InitiatePaymentNotFoundException {
        return service.update( req.attribute( "sub" ), req.params( "id" ), InitiatePayment.Status.REJECTED );
    }

    /**
     * Handles an initiate payment exception
     *
     * @param e
     * @param req
     * @param res
     */
    public static void handleInitiatePaymentRequestException( InitiatePaymentException e, Request req, Response res ) {

        res.body( new Gson().toJson( new InitiatePaymentExceptionMessage( e ) ) );
        res.status( e.getStatus() );
        res.type( "application/json" );

    }

}