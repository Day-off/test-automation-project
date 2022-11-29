package org.app;

import org.app.api.WeatherApi;
import org.app.api.dto.MainDetailsDto;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.Scanner;

public class Main {

    public JSONObject getInfoAboutWeather() {
        try {
            System.out.println("Enter city: ");
            Scanner scanner = new Scanner(System.in);
            String city = scanner.next();
            scanner.close();

            MainDetailsDto mainDetailsDto = new WeatherApi().getMainData(city);


            JSONObject mainDetails = new JSONObject();
            JSONObject output = new JSONObject();

            mainDetails.put("city", mainDetailsDto.getCity());
            mainDetails.put("coordinates", mainDetailsDto.getCoordinates().getLatAndLon());
            mainDetails.put("temperatureUnit", mainDetailsDto.getMain().getTemperatureUnit());
            output.put("mainDetails", mainDetails);

            System.out.println(output);
            return output;

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}