package org.example.day11.factory;

import org.example.day11.strategy.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SumStrategyFactoryTest {

    @Test
    void shouldReturnOddSumStrategy() {
        assertTrue(SumStrategyFactory.getStrategy(StrategyType.ODD) instanceof OddSumStrategy);
    }

    @Test
    void shouldReturnEvenSumStrategy() {
        assertTrue(SumStrategyFactory.getStrategy(StrategyType.EVEN) instanceof EvenSumStrategy);
    }

    @Test
    void shouldReturnPrimeSumStrategy() {
        assertTrue(SumStrategyFactory.getStrategy(StrategyType.PRIME) instanceof PrimeSumStrategy);
    }

    @Test
    void shouldReturnAllSumStrategyForAllType() {
        assertTrue(SumStrategyFactory.getStrategy(StrategyType.ALL) instanceof AllSumStrategy);
    }

    @Test
    void shouldDefaultToAllSumStrategyForUnknown() {
        assertTrue(SumStrategyFactory.getStrategy(null) instanceof AllSumStrategy);
    }

    @Test
    void shouldCoverPrivateConstructorUsingReflection() throws Exception {
        java.lang.reflect.Constructor<SumStrategyFactory> constructor =
                SumStrategyFactory.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
