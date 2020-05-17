package unit.contextManager;

import main.ContextManager;
import org.junit.Test;
import support.LocationDetails;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CityInfoTest {

    List<String> nameList = Arrays.asList("Vivo City Shopping Centre","Crescent Mall","Dam Sen Parklands","Ho Chi Minh City, Downtown");
    List<String> locationList = Arrays.asList("A", "B", "C", "D");
    List<String> infoList = Arrays.asList(
            "Vivo City Shopping Centre is a major regional shopping centre in the southern suburb of Ho Chi Minh City, " +
            "Vietnam. It is the second largest shopping centre in the southern suburbs of Ho Chi Minh City, by gross area," +
            " and contains the only H&M store in that region.",
            "Crescent Mall Shopping Centre is located 10km South of the Ho Chi Minh City central business district(CBD) " +
            "and includes Banana Republic, Baskin Robins, CGV Cinema, Bobapop and over 130 specialty stores.",
            "The Dam Sen Parklands area was created as part of the rejuvenation of the industrial upgrade undertaken " +
            "for World Expo 1988. The Parklands area is spacious with plenty of green and spaces for all ages. A big " +
            "lake promenade stretches the area of Dam Sen Parklands.","The Ho Chi Minh City central business district " +
            "(CBD), or 'the City' is located on a central point in district " +
            "One. The point, known at its tip as Central Point, slopes upward to the north-west where 'the city' is " +
            "bounded by parkland and the inner city suburb of District 3, District 4 and District 5.");
    List<List<String>> servicesList = Arrays.asList(
            Arrays.asList("cinema", "restaurants", "pool", "shops", "bowling"),
            Arrays.asList("cinema", "restaurants", "shops"),
            Arrays.asList("restaurants", "pool", "shops", "Ferris wheel"),
            Arrays.asList("restaurants", "shops", "market", "bowling"));

    @Test
    public void testReadCityInfo() throws Exception{
        Method method = (ContextManager.class).getDeclaredMethod("readCityInfo");
        method.setAccessible(true);
        List<LocationDetails> locationDetailsList =  (List<LocationDetails>) method.invoke(null);

        for (int i = 0; i < 4; i++){
            LocationDetails locationDetails = locationDetailsList.get(i);
            System.out.println("test result of location with index " + i);
            assertEquals(nameList.get(i), locationDetails.getName());
            assertEquals(locationList.get(i), locationDetails.getLocation());
            assertEquals(infoList.get(i), locationDetails.getInfo());
            assertEquals(servicesList.get(i), locationDetails.getServices());
        }
    }
}
