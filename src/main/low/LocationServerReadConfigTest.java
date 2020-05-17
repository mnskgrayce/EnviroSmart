package main.low;

import main.LocationServer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

// For unit tests, we cover readConfig
class LocationServerReadConfigTest {

    LocationServer ls;
    Method readConfig;
    LinkedHashMap<String, String> actual;

    @org.junit.jupiter.api.BeforeEach
    void setUp() throws Exception {
        this.ls = new LocationServer();
        readConfig = (LocationServer.class).getDeclaredMethod("readConfig");
        readConfig.setAccessible(true);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("LocationConfigFileShouldBeReadCorrectly")
    public void testReadConfig() throws Exception {
        actual = (LinkedHashMap<String, String>) readConfig.invoke(null);
        LinkedHashMap<String, String> expectedResults = new LinkedHashMap<>();
        expectedResults.put("A", "Indoor");
        expectedResults.put("B", "Indoor");
        expectedResults.put("C", "Outdoor");
        expectedResults.put("D", "Outdoor");
        assertEquals(expectedResults, actual);
    }

    @Test
    @DisplayName("TestReadConfigFileWhenEmpty")
    // LocationServerConfig must be empty
    public void testReadConfigEmpty() throws Exception {
        actual = (LinkedHashMap<String, String>) readConfig.invoke(null);
        LinkedHashMap<String, String> expectedResults = new LinkedHashMap<>();
        assertEquals(expectedResults, actual);
    }

    @Test
    @DisplayName("TestReadConfigFileWithBadData")
    // LocationServerConfig: "sfshsh fsfhsoa[, 121019, fwwf"
    public void testReadConfigBadData() {
        try {
            actual = (LinkedHashMap<String, String>) readConfig.invoke(null);
        } catch (ArrayIndexOutOfBoundsException | IllegalAccessException | InvocationTargetException e) {
            assertEquals("Index 1 out of bounds for length 1", e.getMessage());
        }
    }
}