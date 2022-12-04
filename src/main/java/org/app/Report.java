package org.app;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.app.api.WeatherApi;
import org.app.api.dto.CurrentWeatherReportDto;
import org.app.api.dto.ForecastReportDto;
import org.app.api.dto.MainDetailsDto;
import org.app.api.dto.details.WeatherReport;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Log
@AllArgsConstructor
public class Report {

    private final JSONObject output = new JSONObject();

    private MainDetailsDto mainDetailsDto;
    private CurrentWeatherReportDto currentWeatherReportDto;
    private ForecastReportDto freeDaysForecastDto;

    public Report(String city) {
        this.mainDetailsDto = new WeatherApi().getMainDataDto(city);
        this.currentWeatherReportDto = new WeatherApi().getCurrentWeatherReportDto(city);
        this.freeDaysForecastDto = new WeatherApi().getFreeDaysForecastDto(city);
        createReport();
    }


    public void createReport() {
        try {
            setMainDetailsToOutPut();
            setCurrentWeatherReportToOutPut();
            setForecastReportToOutPut();
            log.info(output.toString());

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void setForecastReportToOutPut() throws JSONException {
        JSONArray forecastReport = setForecastReports();
        output.put("forecastReport", forecastReport);
    }

    private  JSONArray setForecastReports() throws JSONException {
        JSONArray forecastReport = new JSONArray();
        for (WeatherReport weatherReport : freeDaysForecastDto.getFreeDaysReports()) {
            JSONObject report = new JSONObject();
            JSONObject weather = new JSONObject();
            setForecastWeatherReport(forecastReport, weatherReport, report, weather);
        }

        return forecastReport;
    }

    private  void setForecastWeatherReport(JSONArray forecastReport, WeatherReport weatherReport, JSONObject report, JSONObject weather) throws JSONException {
        report.put("date", weatherReport.getDt());
        weather.put("temperature", weatherReport.getMain().getTemp());
        weather.put("humidity", weatherReport.getMain().getHumidity());
        weather.put("pressure", weatherReport.getMain().getPressure());
        report.put("weather", weather);
        forecastReport.put(report);
    }

    public  void setCurrentWeatherReportToOutPut() throws JSONException {
        JSONObject currentWeatherReport = new JSONObject();
        setCurrentWeatherReport(currentWeatherReport);
        output.put("currentWeatherReport", currentWeatherReport);
    }

    private void setCurrentWeatherReport(JSONObject currentWeatherReport) throws JSONException {
        currentWeatherReport.put("date", currentWeatherReportDto.getDate());
        currentWeatherReport.put("temperature", currentWeatherReportDto.getMain().getTemp());
        currentWeatherReport.put("humidity", currentWeatherReportDto.getMain().getHumidity());
        currentWeatherReport.put("pressure", currentWeatherReportDto.getMain().getPressure());
    }

    public void setMainDetailsToOutPut() throws JSONException {
        try {
            JSONObject mainDetails = new JSONObject();
            setMainWeatherDetails(mainDetails);
            output.put("mainDetails", mainDetails);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setMainWeatherDetails(JSONObject mainDetails) throws JSONException {
        mainDetails.put("city", mainDetailsDto.getCity());
        mainDetails.put("coordinates", mainDetailsDto.getCoordinates().getLatAndLon());
        mainDetails.put("temperatureUnit", mainDetailsDto.getMain().getTemperatureUnit());
    }

    public JSONObject getOutput() {
        return output;
    }
}
