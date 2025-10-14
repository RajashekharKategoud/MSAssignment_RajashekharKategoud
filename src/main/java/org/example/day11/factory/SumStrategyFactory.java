package org.example.day11.factory;

import org.example.day11.strategy.*;


public final class SumStrategyFactory {

    private SumStrategyFactory() { /* Utility class */ }

    public static SumStrategy getStrategy(StrategyType type) {
        switch (type) {
            case ODD:
                return new OddSumStrategy();
            case EVEN:
                return new EvenSumStrategy();
            case PRIME:
                return new PrimeSumStrategy();
            case ALL:
            default:
                return new AllSumStrategy();
        }
    }
}
