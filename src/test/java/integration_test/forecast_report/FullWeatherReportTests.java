package integration_test.forecast_report;

import org.app.FullWeatherReport;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import static org.assertj.core.api.Assertions.assertThat;

class FullWeatherReportTests {

    private JSONArray forecastDetails = new JSONArray();


    @BeforeEach
    public void initializeCurrentWeatherHandler() throws JSONException {
        FullWeatherReport fullWeatherReport = new FullWeatherReport();
        String CITY = "Tallinn";
        System.setIn(new ByteArrayInputStream(CITY.getBytes()));
        JSONObject report = fullWeatherReport.getReportFromStdin();
        forecastDetails = report.getJSONArray("forecastReport");
    }

    @Test
    void reportShouldContainsForecastOfFreeDays() {
        assertThat(forecastDetails.length()).isEqualTo(3);
    }

    @Test
    void eachForecastShouldContainsDate() throws JSONException {
        assertThat(forecastDetails.getJSONObject(0).getString("date")).isNotNull();
        assertThat(forecastDetails.getJSONObject(1).getString("date")).isNotNull();
        assertThat(forecastDetails.getJSONObject(2).getString("date")).isNotNull();
    }

    @Test
    void forecastShouldContainsWeather() throws JSONException {
        assertThat(forecastDetails.getJSONObject(0).getString("weather")).isNotNull();
    }


    @Test
    void forecastWeatherShouldContainsTemperature() throws JSONException {
        assertThat(forecastDetails.getJSONObject(0)
                .getJSONObject("weather").getString("temperature")).isNotEmpty();
    }

    @Test
    void forecastWeatherShouldContainsHumidity() throws JSONException {
        assertThat(forecastDetails.getJSONObject(0)
                .getJSONObject("weather").getString("humidity")).isNotEmpty();
    }

    @Test
    void forecastWeatherShouldContainsPressure() throws JSONException {
        assertThat(forecastDetails.getJSONObject(0)
                .getJSONObject("weather").getString("pressure")).isNotEmpty();
    }





}
