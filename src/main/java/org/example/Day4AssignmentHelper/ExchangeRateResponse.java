package org.example.Day4AssignmentHelper;

/**
 * Response object for exchange rate
 */
public final class ExchangeRateResponse {

    private final String source;
    private final double rate;

    public ExchangeRateResponse(String source, double rate) {
        this.source = source;
        this.rate = rate;
    }

    public String getSource() {
        return this.source;
    }

    public double getRate() {
        return this.rate;
    }

    @Override
    public String toString() {
        return "{source='" + source + "', rate=" + rate + "}";
    }
}
