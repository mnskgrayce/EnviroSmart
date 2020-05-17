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
public class CalculateAPOTest {
    public int medical;
    public int expectedResult;
    public String message;
    public int aqi;

    public CalculateAPOTest(int medical, int aqi, int expectedResult, String message){
        this.medical = medical;
        this.aqi = aqi;
        this.expectedResult = expectedResult;
        this.message = message;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] {
                {       //TCUNCM001
                        1, 1, 30,
                        "TCUNCM001 Context Manager calculates APO threshold (AQI = 1)"
                },
                {       //TCUNCM002
                        1, 50, 30,
                        "TCUNCM002 Context Manager calculates APO threshold (AQI = 50)"
                },
                {       //TCUNCM03
                        1, 51, 15,
                        "TCUNCM003 Context Manager calculates APO threshold (AQI = 51)"
                },
                {       //TCUNCM004
                        1, 100, 15,
                        "TCUNCM004 Context Manager calculates APO threshold (AQI = 100)"
                },
                {       //TCUNCM005
                        1, 101, 10,
                        "TCUNCM005 Context Manager calculates APO threshold (AQI = 101)"
                },
                {       //TCUNCM006
                        1, 150, 10,
                        "TCUNCM006 Context Manager calculates APO threshold (AQI = 150)"
                },
                {       //TCUNCM007
                        1, 0, 5,
                        "TCUNCM007 Context Manager calculates APO threshold (AQI = 0)"
                },
                {       //TCUNCM008
                        1, 500, 5,
                        "TCUNCM008 Context Manager calculates APO threshold (AQI = 500)"
                },
                {       //TCUNCM009
                        1, -500, 5,
                        "TCUNCM009 Context Manager calculates APO threshold (AQI = -500)"
                },
                {       //TCUNCM010
                        -5, 50, -150,
                        "TCUNCM010 Context Manager calculates APO threshold (AQI = 50, medical condition = -5)"
                },
                {       //TCUNCM011, fill dummy value, not actually used it
                        1, 0, 0,
                        "TCUNCM011 Context Manager calculates APO threshold (no data)"
                },
                {       //TCUNCM012, fill dummy value, not actually used it
                        1, 0, 0,
                        "TCUNCM012 Context Manager calculates APO threshold (null)"
                },
                {       //TCUNCM013
                        2, 50, 60,
                        "TCUNCM013 Context Manager calculates APO threshold (AQI = 50, medical condition = 2)"
                },
                {       //TCUNCM014
                        3, 50, 90,
                        "TCUNCM014 Context Manager calculates APO threshold (AQI = 50, medical condition = 3)"
                },

        };
        return Arrays.asList(data);
    }

    @Test
    public void APOtest() throws Exception{
        System.out.println(message);
        Method calculateapoThreshhold = (ContextManager.class).getDeclaredMethod("calculateapoThreshhold", User.class);
        calculateapoThreshhold.setAccessible(true);
        switch (message) {
            case "TCUNCM011 Context Manager calculates APO threshold (no data)":
                assertEquals(expectedResult, calculateapoThreshhold.invoke(null, new User()), message);
                break;
            case "TCUNCM012 Context Manager calculates APO threshold (null)":
                try {
                    assertNull(calculateapoThreshhold.invoke(null, (User) null), message);
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            default:
                int[] tempThreshold = new int[]{ 30, 35 };
                SensorData sensorData = new SensorData();
                sensorData.aqi = aqi;
                User user = new User(medical, tempThreshold, 50, 1, sensorData, 1, false, false);
                assertEquals(expectedResult, calculateapoThreshhold.invoke(null, user), message);
        }
    }
}