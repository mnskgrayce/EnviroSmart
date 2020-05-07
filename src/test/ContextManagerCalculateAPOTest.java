package test;

import helper.SensorData;
import helper.User;
import main.ContextManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContextManagerCalculateAPOTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test // UN001-UN010, UN013, UN014
    @DisplayName("APOThresholdShouldBeCalculated")
    // Change signature of ContextManager.calculateapoThreshold from private to public
    public void testCalculateAQIBoundary() {
        // Create some mock data
        int[] tempThreshold = new int[]{ 30, 35 };
        SensorData sensorData = new SensorData();
        sensorData.aqi = 50;    // Assign mock AQI to SensorData

        // Create a mock user
        User user = new User(3, tempThreshold, 50, 1, sensorData, 1, false, false);

        ContextManager cm = new ContextManager();
        assertEquals(90, cm.calculateapoThreshhold(user));
    }

    @Test // UN011
    @DisplayName("APOThresholdShouldBeZero")
    // Change signature of ContextManager.calculateapoThreshold from private to public
    public void testCalculateAQINoData() {
        User user = new User();
        ContextManager cm = new ContextManager();
        assertEquals(0, cm.calculateapoThreshhold(user));
    }

    @Test // UN012
    @DisplayName("ProgramShouldThrowNullException")
    // Change signature of ContextManager.calculateapoThreshold from private to public
    public void testCalculateAQINullUser() throws NullPointerException {
        try {
            User user = null;
            ContextManager cm = new ContextManager();
            cm.calculateapoThreshhold(user);
        } catch (NullPointerException e) { }
    }




}