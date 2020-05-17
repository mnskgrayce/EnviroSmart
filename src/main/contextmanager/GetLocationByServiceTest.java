package main.contextManager;

import main.ContextManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class GetLocationByServiceTest {
    public List<String> expectedValues;
    public String searchKeyword;
    public String message;

    public GetLocationByServiceTest(List<String> expectedValues, String searchKeyword, String message){
        this.expectedValues = expectedValues;
        this.searchKeyword = searchKeyword;
        this.message = message;
    }

    @Before
    public void before() throws Exception {
        Field communicator = (ContextManager.class).getDeclaredField("communicator");
        communicator.setAccessible(true);
        communicator.set(null, com.zeroc.Ice.Util.initialize());

        Method iniLocationMapper = (ContextManager.class).getDeclaredMethod("iniLocationMapper");
        iniLocationMapper.setAccessible(true);
        iniLocationMapper.invoke(null);

        Method readCityInfo = (ContextManager.class).getDeclaredMethod("readCityInfo");
        readCityInfo.setAccessible(true);

        Field cityInfo = (ContextManager.class).getDeclaredField("cityInfo");
        cityInfo.setAccessible(true);
        cityInfo.set(null, readCityInfo.invoke(null));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                {
                        Arrays.asList("Vivo City Shopping Centre","Crescent Mall"), "restaurants",
                        "Get location by text correctly"
                },
                {
                        Arrays.asList("Vivo City Shopping Centre","Crescent Mall"), "shops",
                        "Get location by text correctly, only get indoor locations"
                },
                {
                        Arrays.asList("Vivo City Shopping Centre","Crescent Mall"), "RESTAURANTS",
                        "Get location by text upper case"
                },
                {
                        Arrays.asList("Vivo City Shopping Centre","Crescent Mall"), "ResTAuRaNts",
                        "Get location by text mixed upper and lower case"
                },
                {
                        Arrays.asList(), "",
                        "Get location by text empty text"
                },
                {
                        Arrays.asList(), "asskjfn122123iwnrgo",
                        "Get location by text not exist service"
                }
        };
        return Arrays.asList(data);
    }

    @Test
    public void getLocationByService() throws Exception {
        Method method = (ContextManager.class).getDeclaredMethod("getLocationsByService", String.class);
        method.setAccessible(true);
        System.out.println(message);
        assertEquals(expectedValues, method.invoke(null, searchKeyword));
    }
}
