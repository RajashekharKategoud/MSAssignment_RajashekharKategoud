package org.example.day11;

import org.example.day11.strategy.StrategyType;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainAppTest {

    @Test
    void shouldReturnCorrectStrategyForAllChoices() throws Exception {
        Method method = MainApp.class.getDeclaredMethod("mapChoiceToType", int.class);
        method.setAccessible(true); // ✅ Make private method accessible

        assertEquals(StrategyType.ALL, method.invoke(null, 1));
        assertEquals(StrategyType.ODD, method.invoke(null, 2));
        assertEquals(StrategyType.EVEN, method.invoke(null, 3));
        assertEquals(StrategyType.PRIME, method.invoke(null, 4));
        assertEquals(StrategyType.ALL, method.invoke(null, 99)); // default
    }

    @Test
    void shouldCoverPrivateConstructorUsingReflection() throws Exception {
        java.lang.reflect.Constructor<MainApp> constructor = MainApp.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
