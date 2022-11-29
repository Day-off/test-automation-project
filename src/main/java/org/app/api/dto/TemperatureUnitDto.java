package org.app.api.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemperatureUnitDto {
    private String temperatureUnit = "Celsius";
}