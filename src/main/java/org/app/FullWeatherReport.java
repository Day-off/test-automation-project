package org.app;

import lombok.extern.java.Log;
import org.app.api.WeatherApi;
import org.app.api.dto.CurrentWeatherReportDto;
import org.app.api.dto.MainDetailsDto;
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

            JSONObject output = new JSONObject();

            setMainDetailsToOutPut(mainDetailsDto, output);

            setCurrentWeatherReportToOutPut(currentWeatherReportDto, output);

            log.info(output.toString());

            return output;

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
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
        try{
            JSONObject mainDetails = new JSONObject();
            setMainWeatherDetails(mainDetailsDto, mainDetails);
            output.put("mainDetails", mainDetails);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void setMainWeatherDetails(MainDetailsDto mainDetailsDto, JSONObject mainDetails) throws JSONException {
        mainDetails.put("city", mainDetailsDto.getCity());
        mainDetails.put("coordinates", mainDetailsDto.getCoordinates().getLatAndLon());
        mainDetails.put("temperatureUnit", mainDetailsDto.getMain().getTemperatureUnit());


    }

}