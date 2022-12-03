package org.app.api;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.app.api.dto.CurrentWeatherReportDto;
import org.app.api.dto.MainDetailsDto;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;

import static com.sun.jersey.api.client.Client.create;
import static com.sun.jersey.api.json.JSONConfiguration.FEATURE_POJO_MAPPING;

public class WeatherApi {
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5";
    public static final String API_KEY = "30cedc93688377bea3b5d76eef35d77f";
    private static final String RESOURCE_URL = BASE_URL + "/weather";

    private Client configureClient() {
        ClientConfig configuration = new DefaultClientConfig();
        configuration.getClasses().add(JacksonJaxbJsonProvider.class);
        configuration.getFeatures().put(FEATURE_POJO_MAPPING, true);
        return create(configuration);
    }

    private ClientResponse getClientResponse(Client client, String cityName){
        return client.resource(RESOURCE_URL)
                .queryParam("q", cityName)
                .queryParam("appid", API_KEY)
                .queryParam("units", "metric")
                .get(ClientResponse.class);
    }

    public MainDetailsDto getMainDataDto(String cityName) {
        Client client = configureClient();
        ClientResponse response = getClientResponse(client, cityName);

        return response.getEntity(MainDetailsDto.class);

    }

    public CurrentWeatherReportDto getCurrentWeatherReportDto(String cityName) {
        Client client = configureClient();
        ClientResponse response = getClientResponse(client, cityName);

        return response.getEntity(CurrentWeatherReportDto.class);
    }
}

