package main.low;

import main.WeatherAlarms;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

// For unit tests, we cover readWeatherConditions
class WeatherAlarmsReadConditionsTest {

    WeatherAlarms wa;
    Method readWeatherConditions;
    List<Integer> actual;

    @org.junit.jupiter.api.BeforeEach
    void setUp() throws NoSuchMethodException {
        this.wa = new WeatherAlarms();
        readWeatherConditions = (WeatherAlarms.class).getDeclaredMethod("readWeatherConditions");
        readWeatherConditions.setAccessible(true);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("WeatherConditionsShouldBeReadCorrectly")
    public void testReadWeatherConditions() throws Exception {
        List<Integer> expected = Arrays.asList(0, 1, 2, 3);
        actual = (List<Integer>) readWeatherConditions.invoke(wa);

        assertEquals(expected, actual);
    }
}
