package integration_test.forecast_report;


import org.app.api.WeatherApi;
import org.app.api.dto.ForecastReportDto;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class WeatherApiTests {

    private static final String CITY = "Tallinn";
    private static final WeatherApi weatherApi;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    static {
        weatherApi = new WeatherApi();
    }

    @Test
    void requestWithCityNameReturnsDtoWithThreeDaysReports() {
        ForecastReportDto actualDto = weatherApi.getFreeDaysForecastDto(CITY);
        assertThat(actualDto.getFreeDaysReports()).hasSize(3);
    }

    @Test
    void requestWithCityNameFirstForecastHaveTheNextDay() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, 1);

        ForecastReportDto actualDto = weatherApi.getFreeDaysForecastDto(CITY);

        assertThat(actualDto.getFreeDaysReports()[0].getDt()).isEqualTo(formatter.format(calendar.getTime()));
    }
}
