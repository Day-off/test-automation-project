package integration_test.main_details;


import org.app.api.WeatherApi;
import org.app.api.dto.MainDetailsDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WeatherApiTests {

    private static final String CITY = "Tallinn";
    private static final WeatherApi weatherApi;

    static {
        weatherApi = new WeatherApi();
    }

    @Test
    void requestWithCityNameReturnsDtoWithGivenCityName() {
        MainDetailsDto actualDto = weatherApi.getMainData(CITY);
        assertThat(actualDto.getCity()).isEqualTo(CITY);
    }
}
