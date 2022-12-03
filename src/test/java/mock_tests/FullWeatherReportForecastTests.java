package mock_tests;

import org.app.api.WeatherApi;
import org.app.api.dto.ForecastReportDto;
import org.app.api.dto.details.MainDto;
import org.app.api.dto.details.WeatherReport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class FullWeatherReportForecastTests {

    @Mock
    private static WeatherApi weatherApiMock;

    private static final ForecastReportDto forecastDto = new ForecastReportDto();

    private static final String city = "Tallinn";

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");




    @BeforeAll
    static void givenCityWeatherReportMainDetailsShouldConfig() {
        WeatherReport[] weatherReports = new WeatherReport[3];
        Date curDate = new Date();
        for (int i = 0; i < 3; ++i){
            WeatherReport weatherReport = new WeatherReport();

            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(curDate);
            calendar.add(Calendar.DAY_OF_YEAR, 1);

            weatherReport.setDt(formatter.format(calendar.getTime()));
            weatherReport.setMain(new MainDto());
            curDate = calendar.getTime();
            weatherReports[i] = weatherReport;
        }

        forecastDto.setFreeDaysReports(weatherReports);
    }

    @Test
    void forecastShouldNotContainsTodayDate() {
        when(weatherApiMock.getFreeDaysForecastDto(
                Mockito.anyString()))
                .thenReturn(forecastDto);
        assertThat(weatherApiMock.getFreeDaysForecastDto(city).getFreeDaysReports()[0].getDt()).isNotEqualTo(formatter.format(new Date()));
        assertThat(weatherApiMock.getFreeDaysForecastDto(city).getFreeDaysReports()[1].getDt()).isNotEqualTo(formatter.format(new Date()));
        assertThat(weatherApiMock.getFreeDaysForecastDto(city).getFreeDaysReports()[2].getDt()).isNotEqualTo(formatter.format(new Date()));
    }



}
