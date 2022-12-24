package integration_test.main_details;

import org.app.FullWeatherReport;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

class FullWeatherReportTests {

    private JSONObject mainDetails = new JSONObject();
    private final String CITY = "Tallinn";


    @BeforeEach
    public void initializeCurrentWeatherHandler() throws JSONException {
        FullWeatherReport fullWeatherReport = new FullWeatherReport();
        System.setIn(new ByteArrayInputStream(CITY.getBytes()));
        JSONObject report = fullWeatherReport.getReportFromStdin();
        mainDetails = report.getJSONObject("mainDetails");
    }

    @Test
    void reportShouldContainsGivenCityName() throws JSONException {
        assertThat(mainDetails.getString("city")).isEqualTo(CITY);
    }

    @Test
    void reportShouldContainsTemperatureUnit() throws JSONException {
        assertThat(mainDetails.getString("temperatureUnit")).isEqualTo("Celsius");
    }

    @Test
    void reportShouldContainsCoords() throws JSONException {
        assertThat(mainDetails.getString("coordinates")).isNotBlank();
    }


}
