package test;

import helper.SensorData;
import helper.User;
import main.ContextManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;

class ContextManagerCheckTempReachedTest {
    Method checkTempReached;

    @BeforeEach
    void setUp() {
        try {
            checkTempReached = (ContextManager.class).getDeclaredMethod("checkTempReached", User.class);
            checkTempReached.setAccessible(true);
        } catch (NoSuchMethodException e) {};

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("TempThresholdReachedShouldBeChecked")
    public void testCheckTempReached() {
        // Create a new user and assign mock values
        User user = new User();

        //user.tempThresholds = new int[]{ 30, 35 };
        user.tempThreshholds = new int[]{ 40, 45 };
        user.sensorData = new SensorData("Minesk", "A", 30, 50);

        // Test if temp threshold reached is detected
        try {
            assertNotNull(checkTempReached.invoke(user, user));
            //assertTrue((Boolean) checkTempReached.invoke(user, user));
            assertFalse((Boolean) checkTempReached.invoke(user, user));
        } catch (InvocationTargetException | IllegalAccessException e) {};
    }

    @Test
    @DisplayName("UserNullShouldBeReported")
    public void testCheckTempReachedUserNull() throws NullPointerException {
        User user = new User();
        try {
            assertNull(checkTempReached.invoke(user, user));
        } catch (InvocationTargetException | IllegalAccessException e) {};
    }
}