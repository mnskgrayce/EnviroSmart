package main.unit.contextManager;

import main.ContextManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import support.LocationDetails;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CityInfoTest {
    public int index;
    public String name;
    public String location;
    public String info;
    public List<String> services = new ArrayList<>();

    public CityInfoTest(int index, String name, String location, String info, List<String> services) {
        this.index = index;
        this.name = name;
        this.location = location;
        this.info = info;
        this.services = services;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] expectedResult = new Object[][] {
            {
                0,
                "Vivo City Shopping Centre",
                "A",
                "Vivo City Shopping Centre is a major regional shopping centre in the southern suburb of Ho Chi Minh City, " +
                "Vietnam. It is the second largest shopping centre in the southern suburbs of Ho Chi Minh City, by gross " +
                "area, and contains the only H&M store in that region.",
                Arrays.asList("cinema", "restaurants", "pool", "shops", "bowling")
            },
            {
                1,
                "Crescent Mall",
                "B",
                "Crescent Mall Shopping Centre is located 10km South of the Ho Chi Minh City central business district(CBD) " +
                "and includes Banana Republic, Baskin Robins, CGV Cinema, Bobapop and over 130 specialty stores.",
                Arrays.asList("cinema", "restaurants", "shops")
            },
            {
                2,
                "Dam Sen Parklands",
                "C",
                "The Dam Sen Parklands area was created as part of the rejuvenation of the industrial upgrade undertaken " +
                "for World Expo 1988. The Parklands area is spacious with plenty of green and spaces for all ages. A big " +
                "lake promenade stretches the area of Dam Sen Parklands.",
                Arrays.asList("restaurants", "pool", "shops", "Ferris wheel")
            },
            {
                3,
                "Ho Chi Minh City, Downtown",
                "D",
                "The Ho Chi Minh City central business district (CBD), or 'the City' is located on a central point in district " +
                "One. The point, known at its tip as Central Point, slopes upward to the north-west where 'the city' is " +
                "bounded by parkland and the inner city suburb of District 3, District 4 and District 5.",
                Arrays.asList("restaurants", "shops", "market", "bowling")
            },
        };
        return Arrays.asList(expectedResult);
    }

    @Test
    public void testReadCityInfo() throws Exception{
        Method method = (ContextManager.class).getDeclaredMethod("readCityInfo");
        method.setAccessible(true);
        List<LocationDetails> locationDetails =  (List<LocationDetails>) method.invoke(null);
        System.out.println("test result of location with index " + index);
        assertEquals("Match name", name, locationDetails.get(index).getName());
        assertEquals("Match location", location, locationDetails.get(index).getLocation());
        assertEquals("Match info", info, locationDetails.get(index).getInfo());
        assertEquals("Match services", services, locationDetails.get(index).getServices());
    }
}
