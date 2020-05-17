package main.low;

import helper.SensorData;
import main.AllSensors;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AllSensorsGetSensorDataTest {
    private static Method getSensorData;
    static boolean signal = true;

    public static void main(String[] args) throws NoSuchMethodException {
        getSensorData = (AllSensors.class).getDeclaredMethod("getSensorData");
        getSensorData.setAccessible(true);

        // Create an AllSensors
        // At construction, data for Jack has already been read into AllSensors object
        AllSensors s = new AllSensors("Jack");

        // Create a Thread object to track location output through time
        Thread thread = new Thread() {
            @Override
            public void run() {
                int i = 1; // to keep track of data each second
                while (signal) {
                    try {
                        // Call getSensorData for each thread
                        SensorData data = (SensorData) getSensorData.invoke(s);
                        //System.out.println(data.location + " " + i++);
                        //System.out.println(data.temperature + " " + i++);
                        //System.out.println(data.aqi + " " + i++);
                        System.out.println(data.location + " " + data.temperature + " " + data.aqi + " " + i++);

                        Thread.currentThread();
                        Thread.sleep(1000);

                    } catch (IllegalAccessException | InvocationTargetException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }
}
