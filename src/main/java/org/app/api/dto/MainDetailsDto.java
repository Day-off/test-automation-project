package org.app.api.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainDetailsDto {

    @JsonProperty("name")
    private String city;

    @JsonProperty("coord")
    private CoordinatesDto coordinates;

    private TemperatureUnitDto main;

}
