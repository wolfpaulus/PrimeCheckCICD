package com.techcasita.lambda;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PrimeTest {

    @Test
    public void check() {
        assertEquals(1, Prime.check(7).getDivisor());
        assertEquals(3, Prime.check(9).getDivisor());
        assertEquals(7, Prime.check(49).getDivisor());
    }

    @Test
    public void handleRequest() throws Exception {

        final APIGatewayProxyRequestEvent requestEvent = new APIGatewayProxyRequestEvent();
        final String body = "{\"number\": 17}";
        requestEvent.setBody(body);

        final APIGatewayProxyResponseEvent responseEvent = new Prime().handleRequest(requestEvent, null);

        assertEquals(200, (int) responseEvent.getStatusCode());
        assertTrue(responseEvent.getBody().contains("\"answer\":\"Yes, 17 is a prime number, divisible only by itself and 1\""));
    }
}