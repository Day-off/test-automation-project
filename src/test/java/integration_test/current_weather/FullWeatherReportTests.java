package integration_test.current_weather;

import org.app.FullWeatherReport;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class FullWeatherReportTests {

    private JSONObject currentWeatherDetails = new JSONObject();


    @BeforeEach
    public void initializeCurrentWeatherHandler() throws JSONException {
        FullWeatherReport fullWeatherReport = new FullWeatherReport();
        String CITY = "Tallinn";
        System.setIn(new ByteArrayInputStream(CITY.getBytes()));
        JSONObject report = fullWeatherReport.getInfoAboutWeather();
        currentWeatherDetails = report.getJSONObject("currentWeatherReport");
    }

    @Test
    void reportShouldContainsTodayDate() throws JSONException {
        assertThat(currentWeatherDetails.getString("date")).isEqualTo(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
    }

    @Test
    void reportShouldContainsTemperature() throws JSONException {
        assertThat(currentWeatherDetails.getString("temperature")).isNotNull();
    }

    @Test
    void reportShouldContainsHumidity() throws JSONException {
        assertThat(currentWeatherDetails.getString("humidity")).isNotNull();
    }

    @Test
    void reportShouldContainsPressure() throws JSONException {
        assertThat(currentWeatherDetails.getString("pressure")).isNotNull();
    }


}
