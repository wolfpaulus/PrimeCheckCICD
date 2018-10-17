/*
 * PrimeResponse, a very basic AWS Lambda Function
 * @author <a href="mailto:wolf@paulus.com">Wolf Paulus</a>
 */
package com.techcasita.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PrimeResponse {
    private static final Logger log = LogManager.getLogger(PrimeResponse.class);
    private final String tag = "PrimeCheck P4";
    private final String answer;
    private final String timestamp = new Date().toString();
    private long n;
    private long d;
    public PrimeResponse(final long n, final long d) {
        this.n = n;
        this.d = d;
        answer = String.format("No, %d is not a prime number. %d is divisible by %d", n, n, d);
    }

    public PrimeResponse(final long n) {
        log.info("Found a prime number " + n);
        this.n = n;
        this.d = 1;
        answer = String.format("Yes, %d is a prime number, divisible only by itself and 1", n);
    }

    public PrimeResponse() {
        answer = "A prime number is a natural number greater than 1 that has no positive divisors other than 1 and itself.";
    }

    @Override
    public String toString() {
        return answer;
    }

    long getDivisor() {
        return d;
    }
}
