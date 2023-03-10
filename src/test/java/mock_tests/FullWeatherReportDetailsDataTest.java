package mock_tests;

import org.app.api.WeatherApi;
import org.app.api.dto.MainDetailsDto;
import org.app.api.dto.details.CoordinatesDto;
import org.app.api.dto.details.TemperatureUnitDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class FullWeatherReportDetailsDataTest {

    @Mock
    private static WeatherApi weatherApiMock;

    private static final MainDetailsDto mainDetailsDto = new MainDetailsDto();
    private static final String city = "Tallinn";


    @BeforeAll
    static void givenCityWeatherReportMainDetailsShouldConfig() {
        CoordinatesDto coordinates = new CoordinatesDto();
        coordinates.setLat(-12);
        coordinates.setLon(12);

        mainDetailsDto.setCity(city);
        mainDetailsDto.setCoordinates(coordinates);
        mainDetailsDto.setMain(new TemperatureUnitDto());

    }

    @Test
    void mainDetailsShouldContainsRightCityName() {
        when(weatherApiMock.getMainDataDto(Mockito.anyString()))
                .thenReturn(mainDetailsDto);
        assertThat(weatherApiMock.getMainDataDto(city).getCity()).isEqualTo(city);
    }

    @Test
    void mainDetailsShouldContainsRightCoords() {
        when(weatherApiMock.getMainDataDto(Mockito.anyString()))
                .thenReturn(mainDetailsDto);
        assertThat(weatherApiMock.getMainDataDto(city).getCoordinates().getLatAndLon()).isEqualTo("-12.0, 12.0");
    }

    @Test
    void mainDetailsTemperatureUnitsShouldBeCelsius() {
        when(weatherApiMock.getMainDataDto(Mockito.anyString()))
                .thenReturn(mainDetailsDto);
        assertThat(weatherApiMock.getMainDataDto(city).getMain().getTemperatureUnit()).isEqualTo("Celsius");
    }


}
