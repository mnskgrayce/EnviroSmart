package main.unit.contextManager;

import com.zeroc.Ice.Current;
import helper.User;
import main.ContextManager;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DeleteUserTest {
    public ContextManager.ContextManagerWorkerI cmw = new ContextManager.ContextManagerWorkerI();

    @Before
    public void before() throws Exception {
        Field communicatorField = (ContextManager.class).getDeclaredField("communicator");
        communicatorField.setAccessible(true);
        communicatorField.set(null, com.zeroc.Ice.Util.initialize());

        Method iniPreferenceWorker = (ContextManager.class).getDeclaredMethod("iniPreferenceWorker");
        iniPreferenceWorker.setAccessible(true);
        iniPreferenceWorker.invoke(null);

        Method iniLocationMapper = (ContextManager.class).getDeclaredMethod("iniLocationMapper");
        iniLocationMapper.setAccessible(true);
        iniLocationMapper.invoke(null);

        Method iniWeatherAlarmWorker = (ContextManager.class).getDeclaredMethod("iniWeatherAlarmWorker");
        iniWeatherAlarmWorker.setAccessible(true);
        iniWeatherAlarmWorker.invoke(null);

        Field weatherField = (ContextManager.class).getDeclaredField("currentWeather");
        weatherField.setAccessible(true);
        weatherField.set(null, 0);

        cmw.addUser("David", new Current());
        cmw.addUser("Jack", new Current());
    }

    @Test
    public void testDeleteSome() throws Exception{
        cmw.deleteUser("David", new Current());
        Field field = (ContextManager.class).getDeclaredField("users");
        field.setAccessible(true);
        LinkedHashMap<String, User> users = (LinkedHashMap<String, User>) field.get(null);
        assertFalse(users.containsKey("David"));
    }

    @Test
    public void testDeleteAll() throws Exception{
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        cmw.deleteUser("Jack", new Current());
        cmw.deleteUser("David", new Current());

        assertEquals("Context Manager has terminated!".trim(), outContent.toString().trim());
    }
}
