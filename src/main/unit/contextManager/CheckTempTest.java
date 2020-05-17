package main.unit.contextManager;

import helper.SensorData;
import helper.User;
import main.ContextManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Parameterized.class)
public class CheckTempTest {
    public int[] threshold;
    public String message;

    public CheckTempTest(int[] threshold, String message){
        this.threshold = threshold;
        this.message = message;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] {
                {       //TCUNCM015
                        new int[]{30, 35},
                        "TCUNCM015 Context Manager checks temperature threshold (reached)"
                },
                {       //TCUNCM016
                        new int[]{40, 45},
                        "TCUNCM015 Context Manager checks temperature threshold (not reached)"
                },
                {       //TCUNCM017, fill dummy value, not actually used it
                        new int[]{0, 1},
                        "TCUNCM017 Context Manager checks temperature threshold (null user)"
                }

        };
        return Arrays.asList(data);
    }

    @Test
    public void testCheckTempReached() throws Exception{
        System.out.println(message);
        Method checkTempReach = (ContextManager.class).getDeclaredMethod("checkTempReached", User.class);
        checkTempReach.setAccessible(true);
        if (message.equals("TCUNCM017 Context Manager checks temperature threshold (null user)")){
            try {
                assertNull(checkTempReach.invoke(null, (User) null), message);
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            User user = new User();
            user.tempThreshholds = threshold;
            user.sensorData = new SensorData("Minesk", "A", 30, 50);
            if (message.equals("TCUNCM015 Context Manager checks temperature threshold (reached)"))
                assertTrue((Boolean) checkTempReach.invoke(null, user));
            else assertFalse((Boolean) checkTempReach.invoke(null, user));
        }
    }
}