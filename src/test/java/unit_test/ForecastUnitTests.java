package unit_test;

import org.app.FullWeatherReport;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class ForecastUnitTests {

    private static JSONObject result = new JSONObject();

    @BeforeAll
    static void enterCity() {
        FullWeatherReport fullWeatherReport = new FullWeatherReport();
        String city = "Tallinn";
        System.setIn(new ByteArrayInputStream(city.getBytes()));

        result = fullWeatherReport.getReportFromStdin();
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

    @Test
    void outputShouldContainsForecastDiffFromCurrentDay() throws JSONException {
        JSONArray actualForecast = result.getJSONArray("forecastReport");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        assertThat(actualForecast.getJSONObject(0).getString("date")).isNotEqualTo(formatter.format(new Date()));
    }




}
