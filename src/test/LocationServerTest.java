package test;

import main.LocationServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class LocationServerTest {

    LocationServer ls;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        this.ls = new LocationServer();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("LocationConfigFileShouldBeReadCorrectly")
    // IN001
    public void testReadConfig() {
        LinkedHashMap<String, String> expectedResults = new LinkedHashMap<>();

        expectedResults.put("A", "Indoor");
        expectedResults.put("B", "Indoor");
        expectedResults.put("C", "Outdoor");
        expectedResults.put("D", "Outdoor");

        assertEquals(expectedResults, ls.readConfig());
    }

    @Test
    @DisplayName("ConfigFileMissingShouldThrowException")
    // UN001
    // Config file must be missing
    public void testReadConfigException() throws FileNotFoundException {
        // FileNotFoundException must be thrown
        ls.readConfig();
    }

    @Test
    @DisplayName("TestReadConfigFileWhenEmpty")
    // IN002
    // LocationServerConfig must be empty
    public void testReadConfigEmpty() {
        LinkedHashMap<String, String> expectedResults = new LinkedHashMap<>();
        assertEquals(expectedResults, ls.readConfig());
    }

    @Test
    @DisplayName("TestReadConfigFileWithBadData")
    // IN003
    // LocationServerConfig:
    // sfshsh fsfhsoa[, 121019, fwwf
    public void testReadConfigBadData() throws ArrayIndexOutOfBoundsException {
        try {
            ls.readConfig();
        } catch (ArrayIndexOutOfBoundsException e) {
            assertEquals("Index 1 out of bounds for length 1", e.getMessage());
        }
    }
}