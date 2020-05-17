package main.unit.contextManager;

import com.zeroc.Ice.Current;
import main.ContextManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class SearchInfoTest {
    public String message;
    public String location;
    public String expectedResult;

    public SearchInfoTest(String message, String location, String expectedResult) {
        this.message = message;
        this.location = location;
        this.expectedResult = expectedResult;
    }

    @Before
    public void before() throws Exception {
        Method readCityInfo = (ContextManager.class).getDeclaredMethod("readCityInfo");
        readCityInfo.setAccessible(true);

        Field cityInfo = (ContextManager.class).getDeclaredField("cityInfo");
        cityInfo.setAccessible(true);
        cityInfo.set(null, readCityInfo.invoke(null));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] {
                { "Get Dam sem","Dam Sen Parklands", "The Dam Sen Parklands area was created as part of the rejuvenation of the industrial upgrade undertaken for World Expo 1988. The Parklands area is spacious with plenty of green and spaces for all ages. A big lake promenade stretches the area of Dam Sen Parklands."},
                { "Get HCM City","Ho Chi Minh City, Downtown", "The Ho Chi Minh City central business district (CBD), or 'the City' is located on a central point in district One. The point, known at its tip as Central Point, slopes upward to the north-west where 'the city' is bounded by parkland and the inner city suburb of District 3, District 4 and District 5."},
                { "Get Empty","", null},
                { "Get null",null, null},
                { "Get randomstring","yt3rrrh24zg", null}
        };
        return Arrays.asList(data);
    }

    @Test
    public void testSearchItems() throws Exception{
        System.out.println(message);
        ContextManager.ContextManagerWorkerI contextManagerWorkerI = new ContextManager.ContextManagerWorkerI();
        String results = contextManagerWorkerI.searchInfo(location, new Current());
        assertEquals(expectedResult, results);
    }
}
