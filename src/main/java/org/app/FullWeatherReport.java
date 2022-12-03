package org.app;

import lombok.extern.java.Log;
import org.app.api.WeatherApi;
import org.app.api.dto.CurrentWeatherReportDto;
import org.app.api.dto.ForecastReportDto;
import org.app.api.dto.MainDetailsDto;
import org.app.api.dto.details.WeatherReport;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.Scanner;

@Log
public class FullWeatherReport {

    public JSONObject getInfoAboutWeather() {
        try {
            log.info("Enter city: ");
            Scanner scanner = new Scanner(System.in);
            String city = scanner.next();
            scanner.close();

            MainDetailsDto mainDetailsDto = new WeatherApi().getMainDataDto(city);
            CurrentWeatherReportDto currentWeatherReportDto = new WeatherApi().getCurrentWeatherReportDto(city);
            ForecastReportDto freeDaysForecastDto = new WeatherApi().getFreeDaysForecastDto(city);

            JSONObject output = new JSONObject();

            setMainDetailsToOutPut(mainDetailsDto, output);
            setCurrentWeatherReportToOutPut(currentWeatherReportDto, output);
            setForecastReport(freeDaysForecastDto, output);


            log.info(output.toString());

            return output;

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setForecastReport(ForecastReportDto freeDaysForecastDto, JSONObject output) throws JSONException {
        JSONArray forecastReport = getForecastReport(freeDaysForecastDto);
        output.put("forecastReport", forecastReport);
    }

    private static JSONArray getForecastReport(ForecastReportDto freeDaysForecastDto) throws JSONException {
        JSONArray forecastReport = new JSONArray();
        for (WeatherReport weatherReport : freeDaysForecastDto.getFreeDaysReports()) {
            JSONObject report = new JSONObject();
            JSONObject weather = new JSONObject();
            setWeatherReport(forecastReport, weatherReport, report, weather);
        }

        return forecastReport;
    }

    private static void setWeatherReport(JSONArray forecastReport, WeatherReport weatherReport, JSONObject report, JSONObject weather) throws JSONException {
        report.put("date", weatherReport.getDt());
        weather.put("temperature", weatherReport.getMain().getTemp());
        weather.put("humidity", weatherReport.getMain().getHumidity());
        weather.put("pressure", weatherReport.getMain().getPressure());
        report.put("weather", weather);
        forecastReport.put(report);
    }

    public static void setCurrentWeatherReportToOutPut(CurrentWeatherReportDto currentWeatherReportDto, JSONObject output) throws JSONException {
        JSONObject currentWeatherReport = new JSONObject();
        setCurrentWeatherReport(currentWeatherReportDto, currentWeatherReport);
        output.put("currentWeatherReport", currentWeatherReport);
    }

    private static void setCurrentWeatherReport(CurrentWeatherReportDto currentWeatherReportDto, JSONObject currentWeatherReport) throws JSONException {
        currentWeatherReport.put("date", currentWeatherReportDto.getDate());
        currentWeatherReport.put("temperature", currentWeatherReportDto.getMain().getTemp());
        currentWeatherReport.put("humidity", currentWeatherReportDto.getMain().getHumidity());
        currentWeatherReport.put("pressure", currentWeatherReportDto.getMain().getPressure());
    }

    public void setMainDetailsToOutPut(MainDetailsDto mainDetailsDto, JSONObject output) throws JSONException {
        try {
            JSONObject mainDetails = new JSONObject();
            setMainWeatherDetails(mainDetailsDto, mainDetails);
            output.put("mainDetails", mainDetails);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setMainWeatherDetails(MainDetailsDto mainDetailsDto, JSONObject mainDetails) throws JSONException {
        mainDetails.put("city", mainDetailsDto.getCity());
        mainDetails.put("coordinates", mainDetailsDto.getCoordinates().getLatAndLon());
        mainDetails.put("temperatureUnit", mainDetailsDto.getMain().getTemperatureUnit());


    }


}