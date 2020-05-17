package main.unit.contextManager;

import com.zeroc.Ice.Current;
import helper.User;
import main.ContextManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class SearchItemsTest {
    public String message;
    public String location;
    public String[] expectedResult;
    private ContextManager.ContextManagerWorkerI contextManagerWorkerI = new ContextManager.ContextManagerWorkerI();

    public SearchItemsTest(String message, String location, String[] expectedResult) {
        this.message = message;
        this.location = location;
        this.expectedResult = expectedResult;
    }

    @Before
    public void before() throws Exception {
        Field communicatorField = (ContextManager.class).getDeclaredField("communicator");
        communicatorField.setAccessible(true);
        communicatorField.set(null, com.zeroc.Ice.Util.initialize());

        Method iniPreferenceWorker = (ContextManager.class).getDeclaredMethod("iniPreferenceWorker");
        iniPreferenceWorker.setAccessible(true);
        iniPreferenceWorker.invoke(null);

        Field weatherField = (ContextManager.class).getDeclaredField("currentWeather");
        weatherField.setAccessible(true);
        weatherField.set(null, 0);

        Method method = (ContextManager.class).getDeclaredMethod("readCityInfo");
        method.setAccessible(true);

        Field cityInfo = (ContextManager.class).getDeclaredField("cityInfo");
        cityInfo.setAccessible(true);
        cityInfo.set(null, method.invoke(null));

        contextManagerWorkerI.addUser("Jack", new Current());
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] {
                { "Get loc A", "A", new String[]{"Vivo City Shopping Centre"} },
                { "Get loc B", "B", new String[]{"Crescent Mall"} },
                { "Get loc E, expect empty", "E", new String[]{} },
                { "Get loc empty, expect empty", "", new String[]{} },
                { "Get loc null, expect empty", null, new String[]{} },
                { "Get loc blank, expect empty", " ", new String[]{} }
        };
        return Arrays.asList(data);
    }

    @Test
    public void testSearchItems() throws Exception{
        System.out.println(message);
        Field field = (ContextManager.class).getDeclaredField("users");
        field.setAccessible(true);
        LinkedHashMap<String, User> users = (LinkedHashMap<String, User>) field.get(null);
        users.get("Jack").sensorData.location = location;

        String[] results = contextManagerWorkerI.searchItems("Jack", new Current());

        assertArrayEquals(expectedResult, results);
    }
}

