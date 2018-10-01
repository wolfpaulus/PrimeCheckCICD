/*
 * Prime, a very basic AWS Lambda Function
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 */
package com.techcasita.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Lambda request handlers implement AWS Lambda Function application logic,
 * using plain old java objects as input and output.
 * However, since input and output is JSON encoded, AWS Lambda will perform (de-)serialization.
 */
public class Prime implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final Logger Log = LogManager.getLogger(Prime.class);

    /**
     * Check if the provided number is evenly divisible only by itself and one.
     *
     * @param n {@link Long}
     * @return {@link boolean} true, if the provided long is a prime number
     */
    static PrimeResponse check(final long n) {
        if (n < 2) {
            return new PrimeResponse();
        }
        if (n == 2 || n == 3) {
            return new PrimeResponse(n);
        }
        if (n % 2 == 0) {
            return new PrimeResponse(n, 2);
        }
        if (n % 3 == 0) {
            return new PrimeResponse(n, 3);
        }
        final long sqrtN = (long) Math.sqrt(n) + 1;
        for (long i = 6; i <= sqrtN; i += 6) {
            if (n % (i - 1) == 0) {
                return new PrimeResponse(n, i - 1);
            }
            if (n % (i + 1) == 0) {
                return new PrimeResponse(n, i + 1);
            }

        }
        return new PrimeResponse(n);
    }

    /**
     * Handles a Lambda Function request
     *
     * @param requestEvent The Lambda Function input stream
     * @param context      The Lambda execution environment context object.
     */
    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent requestEvent, final Context context) {
        final APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        try {
            final Gson gson = new Gson();
            final PrimeRequest request = gson.fromJson(requestEvent.getBody(), PrimeRequest.class);
            final PrimeResponse response = Prime.check(request.getNumber());
            final String body = gson.toJson(response);
            responseEvent.setBody(body);
            responseEvent.setStatusCode(200);
            Log.debug("handleRequest ok " + body );
        } catch (Exception ex) {
            Log.error(ex.toString());
            responseEvent.setBody(ex.toString());
            responseEvent.setStatusCode(500);
        }
        return responseEvent;
    }
}
