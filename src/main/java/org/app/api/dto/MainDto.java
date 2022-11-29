package org.app.api.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainDto {

    private double temp;
    private double humidity;
    private double pressure;
}
