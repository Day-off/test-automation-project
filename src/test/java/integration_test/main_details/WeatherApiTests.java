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
        MainDetailsDto actualDto = weatherApi.getMainDataDto(CITY);
        assertThat(actualDto.getCity()).isEqualTo(CITY);
    }

    @Test
    void requestWithCityNameReturnsDtoWithRightCoords() {
        Double expectedLat = 59.437;
        Double expectedLon = 24.7535;

        MainDetailsDto actualDto = weatherApi.getMainDataDto(CITY);

        assertThat(actualDto.getCoordinates()).extracting("lat", "lon").contains(expectedLat, expectedLon);
    }
}
