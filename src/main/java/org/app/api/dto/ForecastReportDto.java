package org.app.api.dto;

import lombok.Data;
import org.app.api.dto.details.WeatherReport;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastReportDto {

    @JsonProperty("list")
    private WeatherReport[] weatherReports;

    private WeatherReport[] freeDaysReports;

    public void setFreeDaysReports() {
        String check = "";
        int forecastIndex = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        WeatherReport[] forecastReport = new WeatherReport[3];
        for (int i = 0; i <= weatherReports.length - 1; i++) {

            if (forecastReport[2] != null) {
                break;
            }

            WeatherReport weatherReport = weatherReports[i];

            if (isNextDay(check, formatter, weatherReport)) {
                check = weatherReport.getDt();
                forecastReport[forecastIndex] = weatherReport;
                forecastIndex++;
            }
        }
        this.freeDaysReports = forecastReport;
    }

    private static boolean isNextDay(String check, SimpleDateFormat formatter, WeatherReport weatherReport) {
        return (check.isEmpty() || !check.equals(weatherReport.getDt())) && !weatherReport.getDt().equals(formatter.format(new Date()));
    }

    public WeatherReport[] getFreeDaysReports() {
        if (freeDaysReports == null) {
            setFreeDaysReports();
        }
        return freeDaysReports;
    }
}
