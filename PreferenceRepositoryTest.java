package main;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;

import helper.PreferenceRequest;
import helper.SensorData;
import helper.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.*;
import support.Preference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static main.PreferenceRepository.*;
import static org.junit.jupiter.api.Assertions.*;

class PreferenceRepositoryTest {
    PreferenceWorkerI preferenceWorkerI = new PreferenceWorkerI();
    Current current = new Current();

    static Method readPreference;
    static Method setupPreferenceWorker;
    static Method getSuggestionTemp;
    static Method getSuggestionAPO;
    static Method getSuggestionWeather;
    static List<Preference> actual ;
    static Field preferences;
    static Field communicator;


    @BeforeAll
    static void beforeAll() throws Exception{
        readPreference = (PreferenceRepository.class).getDeclaredMethod("readPreference");
        readPreference.setAccessible(true);
        preferences = PreferenceRepository.class.getDeclaredField("preferences");
        preferences.setAccessible(true);
        preferences.set(null, (List<Preference>) readPreference.invoke(null));
    }

    //Read Preference Repository File
    @DisplayName("If File Preference exists")
    @Test
    void testPreferenceFileExists() throws FileNotFoundException {
        File file = new File("Preference");
        boolean expectedResult = true;
        boolean actualResult = false;
        if (file.exists()){
            actualResult = true;
        }
        assertEquals(expectedResult, actualResult);
    }

    @DisplayName("If File Preference is empty")
    @Test
    void testReadPreferenceEmpty() throws Exception {
        actual = (List<Preference>) readPreference.invoke(null);
        assertNotNull(actual);
    }

    //Preference Repository reads user’s medical condition
    @DisplayName("Validate the medical condition of user")
    @Test
    void getUserInfoTestGetValidUserMedicalType(){
        User user = new User();
        user = preferenceWorkerI.getUserInfo("Jack",current);
        assertEquals(2,user.medicalConditionType);
    }

    @DisplayName("Invalidate the medical condition of user")
    @Test
    void getUserInfoTestGetInvalidUserMedicalType(){
        User user = new User();
        user = preferenceWorkerI.getUserInfo("dummy",current);
        assertEquals("0",String.valueOf(user.medicalConditionType));

    }

    //Preference Repository reads user’s weather alarm preference
    @DisplayName("Validate the weather alarm preference format")
    @Test
    void getPreferenceValidWeatherTest(){
        PreferenceRequest request = new PreferenceRequest("Jack",1,"20");
        String preference = preferenceWorkerI.getPreference(request,current);
        assertEquals("shops",preference);
    }

    @DisplayName("Invalidate the weather alarm preference format")
    @Test
    void getPreferenceInvalidWeatherTest(){
        PreferenceRequest request = new PreferenceRequest("dummy",1,"20");
        String preference = preferenceWorkerI.getPreference(request,current);
        assertEquals(null,preference);
    }


    //Preference Repository reads user’s APO preference
    @DisplayName("Validate APO alarm preference format")
    @Test
    void getPreferenceTestValidAPO(){
        PreferenceRequest request = new PreferenceRequest("Jack",1,"APO");
        String preference = preferenceWorkerI.getPreference(request,current);
        assertEquals("bowling",preference);
    }

    @DisplayName("Validate APO alarm preference format")
    @Test
    void getPreferenceTestInvalidAPO(){
        PreferenceRequest request = new PreferenceRequest("dummy",1,"APO");
        String preference = preferenceWorkerI.getPreference(request,current);
        assertEquals(null,preference);
    }

    //Preference Repository reads user’s temperature preference
    @DisplayName("Validate the temperature alarm preference format")
    @Test
    void getUserInfoTestGetValidTempThreshold(){
        User user = preferenceWorkerI.getUserInfo("David",current);
        assertEquals(16,user.tempThreshholds[0]);
    }

    @DisplayName("Invalidate the temperature alarm preference format")
    @Test
    void getUserInfoTestGetInvalidTempThreshold(){
        try {
            User user = preferenceWorkerI.getUserInfo("dummy", current);
        } catch (ArrayIndexOutOfBoundsException e){
            assertEquals("java.lang.ArrayIndexOutOfBoundsException: 0",e);
        }
    }

    // Username validate in preference repository
    @DisplayName("Return information with the respective username")
    @Test
    void validateUsername() throws Exception {
        actual = (List<Preference>) readPreference.invoke(null);
        List<Preference> expectedResults = new ArrayList<>();

        List<String> username = new ArrayList<>();
        username.add("Jack");
        username.add("David");

        List<ArrayList<String>> suggestion = new ArrayList<ArrayList<String>>();
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("when 20 suggest shops, when 30 suggest pool, when APO suggest bowling, when weather suggest cinema");
        suggestion.add(stringList);
        ArrayList<String> stringList1 = new ArrayList<>();
        stringList1.add("when 16 suggest pool, when APO suggest cinema, when weather suggest shops");
        suggestion.add(stringList1);

        List<Integer> medicalCondition = new ArrayList<>();
        medicalCondition.add(2);
        medicalCondition.add(3);
        Preference preference1 = new Preference(username.get(0),medicalCondition.get(0),suggestion.get(0));
        Preference preference2 = new Preference(username.get(1),medicalCondition.get(1),suggestion.get(1));

        expectedResults.add(preference1);
        expectedResults.add(preference2);

        assertEquals(expectedResults.get(0).toString(),actual.get(0).toString());
    }

    @DisplayName("User preference does not exist in file")
    @Test
    void getUserInfoTestNoUserInfo() throws InvocationTargetException, IllegalAccessException {
        actual = (List<Preference>) readPreference.invoke(null);
        Boolean userAvailable = false;
        for (Preference preference : actual) {
            if (preference.getName().equals("LOL")){
                userAvailable = true;
            }
        }
        assertFalse(userAvailable);
    }

    //Get Suggestion for Temperature
    @DisplayName("Get Suggestion for Temperature ")
    @Test
    void getSuggestionTempValidTest() throws Exception {
        getSuggestionTemp = PreferenceRepository.class.getDeclaredMethod("getSuggestionTemp",String.class,Integer.class);
        getSuggestionTemp.setAccessible(true);

        assertEquals("shops",getSuggestionTemp.invoke(null,"Jack",20));
    }

    //Get Suggestion for APO
    @DisplayName("Get Suggestion for APO ")
    @Test
    void getSuggestionAPOValidTest() throws Exception {
        getSuggestionAPO = PreferenceRepository.class.getDeclaredMethod("getSuggestionAPO",String.class);
        getSuggestionAPO.setAccessible(true);

        String suggest = (String) getSuggestionAPO.invoke(null,"Jack");
        assertEquals("bowling",suggest);
    }

    //Get suggestion for Weather
    @DisplayName("Get suggestion for Weather")
    @Test
    void getSuggestionValidWeatherTest() throws Exception {
        getSuggestionWeather = PreferenceRepository.class.getDeclaredMethod("getSuggestionWeather", String.class,Integer.class);
        getSuggestionWeather.setAccessible(true);
        assertEquals("cinema",getSuggestionWeather.invoke(null,"Jack",1));
    }

    //Set up preference worker test
    @DisplayName("Set up preference worker test")
    @Test
    void setupPreferenceWorkerTest() throws Exception{
        String[] array = {};
        Communicator testObj = com.zeroc.Ice.Util.initialize(array);
        com.zeroc.Ice.ObjectAdapter adapter = testObj.createObjectAdapterWithEndpoints("PreferenceWorker",
                "default -p 14444");
        adapter.add(new PreferenceWorkerI(), com.zeroc.Ice.Util.stringToIdentity("PreferenceWorker"));
        adapter.activate();
        assertNotNull( testObj);
    }

    //Terminate Preference Repository
    @DisplayName("Terminate function test")
    @Test
    void termiateTest() throws Exception {
        String[] array = {};
        communicator = PreferenceRepository.class.getDeclaredField("communicator");
        communicator.setAccessible(true);
        communicator.set(null, com.zeroc.Ice.Util.initialize(array));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        preferenceWorkerI.terminate(current);
        assertEquals("Preference Repository has terminated!\n",outContent.toString());
    }


    // no test case table
    @Test
    void getPreferenceTestEmptyRquest() throws Error{
        PreferenceRequest request = new PreferenceRequest();
        String preference = preferenceWorkerI.getPreference(request,current);
        assertEquals(null,preference);
    }

    @DisplayName("Invalid weather alarm preference request")
    @Test
    void getPreferenceTestInvalidRequest() throws NumberFormatException{
        assertThrows(NumberFormatException.class, () ->{
            PreferenceRequest request = new PreferenceRequest("LOL", 1, "shop");
            String preference = preferenceWorkerI.getPreference(request, current);
        });
    }
}