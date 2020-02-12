package com.revolut.payment.response;

import com.google.gson.Gson;
import spark.ResponseTransformer;

public class GsonResponseTransformer implements ResponseTransformer {

    private Gson gson = new Gson();

    @Override
    public String render( Object o ) throws Exception {
        return gson.toJson( o );
    }
}
