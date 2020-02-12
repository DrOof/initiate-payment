package com.revolut.payment.test;

import com.revolut.payment.model.InitiatePayment;
import com.revolut.payment.app.InitiatePaymentApp;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class InitiatePaymentAppTest {

    private static String AUTHORIZATION = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkJvYiIsImlhdCI6MTUxNjIzOTAyMn0.nj0xFBI8b8kG2UIttfbd4F8mymgMkcg0DTqpecfcTBk";

    @Before
    public void start() {
        InitiatePaymentApp.main( new String[]{} );
    }

    @Test
    public void testHandleInitiatePayment() {

        // create a payment
        String PAYMENT = "{ \"payment\": { \"from\": { \"name\": \"Bob\", \"BBAN\": \"1234\", \"currency\": \"USD\" }, \"to\": { \"name\": \"Allice\", \"BBAN\": \"1234\", \"currency\": \"USD\" }, \"consignment\": { \"amount\": 14, \"currency\": \"USD\" } } }";

        var res = given()
                .body( PAYMENT )
                .header( "Content-Type", ContentType.JSON )
                .header( "Accept", ContentType.JSON )
                .header( "Authorization", AUTHORIZATION )
                .post( "http://localhost:4567/payment" )
                .then();

        var result = res.extract().as( InitiatePayment.class );

        Assert.assertEquals( 200, res.extract().statusCode() );
        Assert.assertEquals( InitiatePayment.Status.AWAITING_AUTHORISATION, result.getStatus() );

    }

    @Test
    public void testHandleIdempotentInitiatePayment() {

        // create a payment
        String PAYMENT = "{ \"payment\": { \"from\": { \"name\": \"Bob\", \"BBAN\": \"4567\", \"currency\": \"USD\" }, \"to\": { \"name\": \"Allice\", \"BBAN\": \"4567\", \"currency\": \"USD\" }, \"consignment\": { \"amount\": 14, \"currency\": \"USD\" } } }";

        // prime with a initiate payment request
        var before = given().body( PAYMENT )
                .header( "Content-Type", ContentType.JSON )
                .header( "Accept", ContentType.JSON )
                .header( "Authorization", AUTHORIZATION )
                .post( "http://localhost:4567/payment" )
                .then();

        // try an identical initiate payment request
        var res = given().body( PAYMENT )
                .header( "Content-Type", ContentType.JSON )
                .header( "Accept", ContentType.JSON )
                .header( "Authorization", AUTHORIZATION )
                .post( "http://localhost:4567/payment" )
                .then();

        // this should give us a bad request
        Assert.assertEquals( 400, res.extract().statusCode() );

    }

    @Test
    public void testHandleUnauthorizedInitiatePayment() {

        // create a payment
        String PAYMENT = "{ \"payment\": { \"from\": { \"name\": \"Bob\", \"BBAN\": \"4321\", \"currency\": \"USD\" }, \"to\": { \"name\": \"Allice\", \"BBAN\": \"4321\", \"currency\": \"USD\" }, \"consignment\": { \"amount\": 14, \"currency\": \"USD\" } } }";

        // try an initiate payment request without authorization
        var res = given()
                .body( PAYMENT )
                .header( "Content-Type", ContentType.JSON )
                .header( "Accept", ContentType.JSON )
                // .header( "Authorization", AUTHORIZATION )
                .get( "http://localhost:4567/payment" )
                .then();

        // this should give us an unauthorized request
        Assert.assertEquals( res.extract().statusCode(), 401 );

    }

}
