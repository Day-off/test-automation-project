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

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Log
public class FullWeatherReport {

    public JSONObject getFullWeatherReport(String city) {
        try {
            MainDetailsDto mainDetailsDto = new WeatherApi().getMainDataDto(city);
            CurrentWeatherReportDto currentWeatherReportDto = new WeatherApi().getCurrentWeatherReportDto(city);
            ForecastReportDto freeDaysForecastDto = new WeatherApi().getFreeDaysForecastDto(city);

            JSONObject output = new JSONObject();

            setMainDetailsToOutPut(mainDetailsDto, output);
            setCurrentWeatherReportToOutPut(currentWeatherReportDto, output);
            setForecastReportToOutPut(freeDaysForecastDto, output);


            log.info(output.toString());

            return output;

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public JSONObject getReportFromStdin() {
        log.info("Enter city: ");
        Scanner scanner = new Scanner(System.in);
        String city = scanner.next();
        scanner.close();
        return getFullWeatherReport(city);
    }

    public List<String> readFromFile(String fileName) {
        List<String> cities = new ArrayList<>();
        try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                cities.add(line);
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cities;
    }

    public void writeToFile(JSONObject output){
        try {
                FileWriter file = new FileWriter("src/main/resources/output/output.json");
                file.write(output.toString());
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void getReportFromFileToFile(String file) throws Exception {
        isValidFile(!getFileExtension(file).equals("txt"), "UNSUPPORTED FILE TYPE!");
        List<String> cities = readFromFile("src/main/resources/input/"+file);
        for (String city: cities){
            JSONObject output = getFullWeatherReport(city);
            writeToFile(output);
        }
    }

    private void isValidFile(boolean file, String msg) throws Exception {
        if (file){
            log.info(msg);
            throw new Exception(msg);
        }
    }

    public String getFileExtension(String fullName) throws Exception {
        checkNotNull(fullName);
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    private void checkNotNull(String fullName) throws Exception {
        isValidFile(fullName.isEmpty(), "FILE NAME IS EMPTY!\n");
    }

    private static JSONArray setForecastReports(ForecastReportDto freeDaysForecastDto) throws JSONException {
        JSONArray forecastReport = new JSONArray();
        for (WeatherReport weatherReport : freeDaysForecastDto.getFreeDaysReports()) {
            JSONObject report = new JSONObject();
            JSONObject weather = new JSONObject();
            setForecastWeatherReport(forecastReport, weatherReport, report, weather);
        }

        return forecastReport;
    }

    private static void setForecastReportToOutPut(ForecastReportDto freeDaysForecastDto, JSONObject output) throws JSONException {
        JSONArray forecastReport = setForecastReports(freeDaysForecastDto);
        output.put("forecastReport", forecastReport);
    }

    private static void setForecastWeatherReport(JSONArray forecastReport, WeatherReport weatherReport, JSONObject report, JSONObject weather) throws JSONException {
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

    public static void main(String[] args) throws Exception {
        FullWeatherReport a= new FullWeatherReport();
        a.getReportFromFileToFile("");
    }
}