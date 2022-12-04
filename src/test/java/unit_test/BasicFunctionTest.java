package unit_test;

import org.app.FullWeatherReport;
import org.codehaus.jettison.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BasicFunctionTest {

    private static String result;

    @BeforeAll
    static void enterCity() {
        FullWeatherReport fullWeatherReport = new FullWeatherReport();
        String city = "Tallinn";
        System.setIn(new ByteArrayInputStream(city.getBytes()));

        JSONObject cityData = fullWeatherReport.getReportFromStdin();
        result = cityData.toString();
    }

    @Test
    void outputShouldContainsMainDetails() {
        assertTrue(result.contains("mainDetails"));
    }

    @Test
    void outputShouldContainsCoordinates() {
        assertTrue(result.contains("\"coordinates\":\"59.437, 24.7535\""));
    }

    @Test
    void outputShouldContainsTemperatureUnits() {
        assertTrue(result.contains("\"temperatureUnit\":\"Celsius\""));
    }

    @Test
    void outputShouldContainsCurrentWeatherReport() {
        assertTrue(result.contains("currentWeatherReport"));
    }

    @Test
    void outputShouldContainsTemperature() {
        assertTrue(result.contains("temperature"));
    }

    @Test
    void outputShouldContainsPressure() {
        assertTrue(result.contains("pressure"));
    }

    @Test
    void outputShouldContainsCityName() {
        List<String> cities = List.of("Narva", "Tartu", "Paris");
        for (String city : cities) {
            FullWeatherReport fullWeatherReport = new FullWeatherReport();
            System.setIn(new ByteArrayInputStream(city.getBytes()));

            JSONObject cityData = fullWeatherReport.getReportFromStdin();
            String result = cityData.toString();
            assertTrue(result.contains("\"city\":" + "\"" + city + "\""));
        }
    }

}
