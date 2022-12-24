package integration_test.current_weather;


import org.app.api.WeatherApi;
import org.app.api.dto.CurrentWeatherReportDto;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class WeatherApiTests {

    private static final String CITY = "Tallinn";
    private static final WeatherApi weatherApi;

    static {
        weatherApi = new WeatherApi();
    }

    @Test
    void requestWithCityNameReturnsTodayDate() {
        CurrentWeatherReportDto actualDto = weatherApi.getCurrentWeatherReportDto(CITY);
        assertThat(actualDto.getDate()).isEqualTo(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
    }

    @Test
    void requestWithCityNameReturnsDtoContainsHumidity() {
        CurrentWeatherReportDto actualDto = weatherApi.getCurrentWeatherReportDto(CITY);
        assertThat(actualDto.getMain().getHumidity()).isNotNaN();

    }

    @Test
    void requestWithCityNameReturnsDtoContainsTemperature() {
        CurrentWeatherReportDto actualDto = weatherApi.getCurrentWeatherReportDto(CITY);
        assertThat(actualDto.getMain().getTemp()).isNotNaN();

    }

    @Test
    void requestWithCityNameReturnsDtoContainsPressure() {
        CurrentWeatherReportDto actualDto = weatherApi.getCurrentWeatherReportDto(CITY);
        assertThat(actualDto.getMain().getPressure()).isNotNaN();

    }
}
