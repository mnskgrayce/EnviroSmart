package main.low;

import org.junit.jupiter.api.*;
import support.Sensor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SensorReadDataTest {
    Method readData;

    @BeforeEach
    void setUp() throws Exception {
        readData = (Sensor.class).getDeclaredMethod("readData");
        readData.setAccessible(true);
    }

    // General method to create sensors
    Sensor makeSensor(String username, String type) {
        Sensor s = new Sensor(username, type);
        return s;
    }

    @Test
    @DisplayName("Location values should be read correctly")
    public void testReadLocation() throws InvocationTargetException, IllegalAccessException {
        LinkedHashMap<String, Integer> expectedLoc = new LinkedHashMap<>();
        expectedLoc.put("A", 1);
        expectedLoc.put("C", 15);
        expectedLoc.put("D", 14);
        Sensor locationSensor = makeSensor("Jack", "Location");
        LinkedHashMap<String, Integer> actualLoc = (LinkedHashMap<String, Integer>) readData.invoke(locationSensor);
        assertEquals(expectedLoc, actualLoc);
    }

    @Test
    @DisplayName("Temperature values should be read correctly")
    public void testReadTemperature() throws InvocationTargetException, IllegalAccessException {
        LinkedHashMap<String, Integer> expectedTemp = new LinkedHashMap<>();
        expectedTemp.put("10", 5);
        expectedTemp.put("15", 3);
        expectedTemp.put("20", 4);
        Sensor sensor = makeSensor("Jack", "Temperature");
        LinkedHashMap<String, Integer> actualTemp = (LinkedHashMap<String, Integer>) readData.invoke(sensor);
        assertEquals(expectedTemp, actualTemp);
    }

    @Test
    @DisplayName("AQI values should be read correctly")
    public void testReadAQI() throws InvocationTargetException, IllegalAccessException {
        LinkedHashMap<String, Integer> expectedAQI = new LinkedHashMap<>();
        expectedAQI.put("200", 15);
        expectedAQI.put("90", 11);
        Sensor sensor = makeSensor("Jack", "AQI");
        LinkedHashMap<String, Integer> actualLoc = (LinkedHashMap<String, Integer>) readData.invoke(sensor);
        assertEquals(expectedAQI, actualLoc);
    }
}
