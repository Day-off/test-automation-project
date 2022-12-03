package unit_test;

import org.app.FullWeatherReport;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

class ForecastUnitTests {

    private static JSONObject result = new JSONObject();

    @BeforeAll
    static void enterCity() {
        FullWeatherReport fullWeatherReport = new FullWeatherReport();
        String city = "Tallinn";
        System.setIn(new ByteArrayInputStream(city.getBytes()));

        result = fullWeatherReport.getInfoAboutWeather();
    }

    @Test
    void outputShouldContainsForecastReport() throws JSONException {
        assertThat(result.getString("forecastReport")).isNotNull();
    }

    @Test
    void outputShouldContainsForecastHaveAList() throws JSONException {
        JSONArray actualForecast = result.getJSONArray("forecastReport");
        assertThat(actualForecast.length()).isEqualTo(3);
    }


}
